/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller.view.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.sql.DataSource;


/**
 *
 * @author Hugo
 */
public class LoginSisgap implements LoginModule {
     private static final Logger logger = Logger.getLogger(LoginSisgap.class .getName());

        private CallbackHandler callbackHandler;
        private String dsJndiName;
        private String principalsQuery;

        private String username;
        private String password;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        this.callbackHandler = callbackHandler;
            dsJndiName = (String) options.get("dsJndiName");
            if (dsJndiName == null)
                dsJndiName = "java:jboss/datasources/SISGAP";
            Object tmp = options.get("principalsQuery");
            if (tmp != null)
                principalsQuery = tmp.toString();
    }

    @Override
    public boolean login() throws LoginException {
         try {
             getUsernameAndPassword();
         } catch (UnsupportedCallbackException ex) {
             Logger.getLogger(LoginSisgap.class.getName()).log(Level.SEVERE, null, ex);
         }
            logger.info("Provided Username: " + username);
            logger.info("Provided Password: " + password);

            String expectedPassword = null;
         try {
             expectedPassword = readUsersPassword();
         } catch (NamingException ex) {
             Logger.getLogger(LoginSisgap.class.getName()).log(Level.SEVERE, null, ex);
         } catch (SQLException ex) {
             Logger.getLogger(LoginSisgap.class.getName()).log(Level.SEVERE, null, ex);
         }
            logger.info("Expected Password: " + expectedPassword);
            if (expectedPassword.equals(username)) {
                logger.info("User " + username + " authenticated.");
            } 
            return true;
    }
     private void getUsernameAndPassword() throws LoginException, UnsupportedCallbackException {
            if (callbackHandler == null) {
                throw new LoginException("Error: no CallbackHandler available " + "to collect authentication information");
            }
            NameCallback nc = new NameCallback("User name: ", "guest");
            PasswordCallback pc = new PasswordCallback("Password: ", false);
            Callback[] callbacks = { nc, pc };
            try {
                callbackHandler.handle(callbacks);
                username = nc.getName();
                char[] tmpPassword = pc.getPassword();
                if (tmpPassword != null) {
                    char[] credential = new char[tmpPassword.length];
                    System.arraycopy(tmpPassword, 0, credential, 0, tmpPassword.length);
                    pc.clearPassword();
                    password = new String(credential);
                }
            } catch (java.io.IOException ioe) {
                throw new LoginException(ioe.toString());
            } catch (UnsupportedCallbackException uce) {
                throw new LoginException("CallbackHandler does not support: " + uce.getCallback());
            }
        }

        private String readUsersPassword() throws LoginException, NamingException, SQLException {
            String result = null;
            try {
                InitialContext ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup(dsJndiName);
                Connection conn = ds.getConnection();
                PreparedStatement ps = conn.prepareStatement(principalsQuery);
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                            if (rs.next() == false)
                                throw new FailedLoginException("No matching username found in Principals");
                            result = rs.getString(1);
                        
                    
            } catch (SQLException ex) {
                logger.info("Query failed "+ ex);
                throw new LoginException(ex.toString());
            } 
            return result;
        }

    @Override
    public boolean commit() throws LoginException {
        logger.info("commit");
            return true;
    }

    @Override
    public boolean abort() throws LoginException {
        logger.info("abort");
            return true;
    }

    @Override
    public boolean logout() throws LoginException {
        logger.info("logout");
            return true;
    }
    
}
