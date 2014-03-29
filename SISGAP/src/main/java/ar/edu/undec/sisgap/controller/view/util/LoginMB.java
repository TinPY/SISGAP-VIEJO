/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller.view.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Hugo
 */
@ManagedBean
@ViewScoped
public class LoginMB {

    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }
    private static final String PAGINA_INDEX = "/private/pages/index.xhtml";

    private String usuario;
    private String password;

    public String onClickLogar() {
        try {
	    HttpServletRequest request = (HttpServletRequest) FacesContext.
                getCurrentInstance().getExternalContext().getRequest();
            request.login(this.usuario, this.password);
            return PAGINA_INDEX;
        } catch (ServletException e) {
	    System.out.println("FALLO _________________________________________TTT");
        } finally {
            //tratar aqui mensagens de segurança que possam ter vindo 
            //do Login Module exibindo-as na forma de FacesMessage
	}

        return null;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }
    
}
