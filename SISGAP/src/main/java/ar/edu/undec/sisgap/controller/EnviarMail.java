/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.controller.view.AgenteController;
import ar.edu.undec.sisgap.controller.view.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.URLWEB;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Usuario;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.mail.Message.RecipientType;
import org.codemonkey.simplejavamail.Email;
import org.codemonkey.simplejavamail.Mailer;

/**
 *
 * @author Hugo
 */
@ManagedBean(name = "enviarMail")
@SessionScoped
public class EnviarMail implements Serializable {
      @EJB
      AgenteFacade facadeagente;
      @ManagedProperty("#{agenteController}")
      private AgenteController agentecontroller;
    
      
    public EnviarMail() {
    }
    public boolean enviarMailConfirmacion(Agente agente, Usuario usuario){
//        Properties p = System.getProperties();
//         // Nombre del host de correo, es smtp.gmail.com
//
//         p.put("mail.smtp.host", "exchange.undec.edu.ar");
//        // p.put("mail.smtp.ssl.trust", "exchange.undec.edu.ar");
//         // Puerto de gmail para envio de correos
//         p.put("mail.smtp.port","25"); //587 gmail
//        // p.put("mail.smtp.starttls.enable", "true");
//        // p.put("mail.smtp.auth", "true");
//         p.put("mail.debug", "true");
//         p.put("mail.store.protocol", "pop3");
//         p.put("mail.transport.protocol", "smtp");
//         Session session = Session.getDefaultInstance(p);
//         session.setDebug(true);
//         MimeMessage message = new MimeMessage(session);

         try {

             //seteamos el asunto
//             message.setSubject("UNDEC - Activar Cuenta SISGAP UNDEC");
//
//             //seteamos el mensaje que vamos a enviar
//             message.setContent("<html>\n" +
//                     "<body>\n" +
//                     "\n Hola, para poder activar la cuenta haga click en el siguiente link ----> " +
//                     "<a href=\""+new URLWEB().getAbsoluteApplicationUrl()+"/activarcuenta.xhtml?username="+usuario.getUsuarionombre()+
//                     "&hash="+usuario.getUsuarioclave()+
//                     "\">\n" +
//                     "Activar cuenta</a>\n" +
//                     "\n" +
//                     "En caso de no poder pegue en el browser lo siguiente\n "+
//                     new URLWEB().getAbsoluteApplicationUrl()+"/activarcuenta.xhtml?username="+usuario.getUsuarionombre()+
//                     "&hash="+usuario.getUsuarioclave()+"\n"+
//                     "Muchas Gracias"+
//                     "</body>\n" +
//                     "</html>", "text/html");
//             //colocamos la direccion de donde enviamos el correo
//             Address address = new InternetAddress(agente.getEmail());
//             message.setFrom(address);
//             //Colocamos la direccion de la persona a enviar
//             Address send = new InternetAddress(agente.getOtroemail(),false);
//             message.addRecipient(Message.RecipientType.TO,send);
//             message.addRecipient(Message.RecipientType.BCC, new InternetAddress( agente.getOtroemail()));
//             //la persona que envia con la validacion de su cuenta.
//             Transport trans = session.getTransport("smtp");
//             //Aca se autentifica que la direccion de la persona que envia sea válida
//             //trans.connect();
//             trans.connect("sisgap@undec.edu.ar","sgap*9812");
//             trans.sendMessage(message,message.getAllRecipients());
//             trans.close();
           Email email = new Email();
 email.setFromAddress("SISGAP UNDEC", "sisgap@undec.edu.ar");
 email.addRecipient(agente.getApellido(), agente.getEmail(), RecipientType.TO);
 email.addRecipient(agente.getApellido(), agente.getOtroemail(), RecipientType.BCC);
 email.setTextHTML("Hola "+usuario.getUsuarionombre()+", <br /> para poder activar la cuenta haga click en el siguiente link ----> " +
"<a href=\""+new URLWEB().getAbsoluteApplicationUrl()+"/activarcuenta.xhtml?username="+usuario.getUsuarionombre()+
                        "&hash="+usuario.getUsuarioclave() +
"\"> Activar cuenta  </a> <br />"+
                     
"En caso de no poder pegue en el browser lo siguiente " +
                     new URLWEB().getAbsoluteApplicationUrl()+"/activarcuenta.xhtml?username="+usuario.getUsuarionombre()+
"&hash="+usuario.getUsuarioclave()+"<br />" +
"Muchas Gracias");
 email.setSubject("UNDEC - Activar Cuenta SISGAP UNDEC");
 
 // or:
 new Mailer("localhost", 25, "sisgap@undec.edu.ar", "sgap*9812").sendMail(email);
         } catch (Exception ex) {

             ex.printStackTrace();
             //Si el correo tiene algun error lo retornaremos aca
            JsfUtil.addErrorMessage(ex,"No se pudo crear el Usuario");

             return false;

//         } catch (MalformedURLException ex) {
//             Logger.getLogger(EnviarMail.class.getName()).log(Level.SEVERE, null, ex);
//             return false;
         }
             //Correo tuvo exito dara una salida en este punto indicando que si se envio
             return true;
 
    } 
    public boolean mailRecuperarPassword(String nombreusuario, String password, String mail){
//        Properties p = System.getProperties();
//         // Nombre del host de correo, es smtp.gmail.com
//
//         p.put("mail.smtp.host", "mail.undec.edu.ar");
//         p.put("mail.smtp.ssl.trust", "mail.undec.edu.ar");
//         // Puerto de gmail para envio de correos
//         p.put("mail.smtp.port","25"); //587 gmail
//         p.put("mail.smtp.starttls.enable", "true");
//         p.put("mail.smtp.auth", "true");
//         p.put("mail.debug", "true");
//         p.put("mail.store.protocol", "pop3");
//         p.put("mail.transport.protocol", "smtp");
//         Session session = Session.getDefaultInstance(p);
//         session.setDebug(true);
//         MimeMessage message = new MimeMessage(session);

         try {

//             //seteamos el asunto
//             message.setSubject("UNDEC - Activar Cuenta SISGAP UNDEC");
//
//             //seteamos el mensaje que vamos a enviar
//             message.setContent("<html>\n" +
//                     "<body>\n" +
//                     "\nHola, usuario = " +nombreusuario+ ",\n su nuevo password es = "+password+
//                     "\n" +
//                     "\nUsted puede cambiar el password cuando ingrese al sistema\n "+
//                     
//                     "Muchas Gracias"+
//                     "</body>\n" +
//                     "</html>", "text/html");
//             //colocamos la direccion de donde enviamos el correo
//             Address address = new InternetAddress(mail);
//             message.setFrom(address);
//             //Colocamos la direccion de la persona a enviar
//             Address send = new InternetAddress(mail,false);
//             message.addRecipient(Message.RecipientType.TO,send);
//            
//             //la persona que envia con la validacion de su cuenta.
//             Transport trans = session.getTransport("smtp");
//             //Aca se autentifica que la direccion de la persona que envia sea válida
//
//             trans.connect("hmaza@undec.edu.ar","momomomo");
//             trans.sendMessage(message,message.getAllRecipients());
//             trans.close();
             System.out.println("maiñll");
                Email email = new Email();
 email.setFromAddress("SISGAP UNDEC", "sisgap@undec.edu.ar");
 email.addRecipient(nombreusuario, mail, RecipientType.TO);
 //email.addRecipient(agente.getApellido(), agente.getOtroemail(), RecipientType.BCC);
 email.setTextHTML("Hola, usuario = "+nombreusuario+ "su nuevo password es = "+password +

" <br />Usted puede cambiar el password cuando ingrese al sistema <br />" +

"Muchas Gracias");
 System.out.println("-----------"+mail);
 email.setSubject("UNDEC - Recuperar Password SISGAP UNDEC");
 
 // or:
 new Mailer("localhost", 25, "sisgap@undec.edu.ar", "sgap*9812").sendMail(email);
  System.out.println("-----------enviado");
         } catch (Exception ex) {

             ex.printStackTrace();
             //Si el correo tiene algun error lo retornaremos aca
            JsfUtil.addErrorMessage(ex,"No se pudo crear el Usuario");

             return false;

         
         }
             //Correo tuvo exito dara una salida en este punto indicando que si se envio
             return true;
 
    }
    public boolean enviarMailIngresoIdeaProyecto(String nombreusuario, String mail, String tipo){
       Email email = new Email();
        List<Agente> agentesadministradores=this.facadeagente.getEntityManager().createQuery("select a from Agente a join a.usuarioid u where u.usuariorol.rolid=1", Agente.class).getResultList();
        for(Agente agente:agentesadministradores){
            //email.addRecipient(agente.getUsuarioid().getUsuarionombre(),agente.getEmail() , RecipientType.BCC);
        }
        try{
           
 email.setFromAddress("SISGAP UNDEC", "sisgap@undec.edu.ar");
 email.addRecipient(nombreusuario, mail, RecipientType.TO);
 //email.addRecipient(agente.getApellido(), agente.getOtroemail(), RecipientType.BCC);
 email.setTextHTML("Hola, usuario = "+nombreusuario+ "su nuevo password es = "+tipo +

" <br />Usted puede cambiar el password cuando ingrese al sistema <br />" +

"Muchas Gracias");
 System.out.println("-----------"+mail);
 email.setSubject("UNDEC - Recuperar Password SISGAP UNDEC");
 
 // or:
 new Mailer("localhost", 25, "sisgap@undec.edu.ar", "sgap*9812").sendMail(email);
  System.out.println("-----------enviado");
         } catch (Exception ex) {

             ex.printStackTrace();
             //Si el correo tiene algun error lo retornaremos aca
            JsfUtil.addErrorMessage(ex,"No se pudo crear el Usuario");

             return false;

         
         }
             //Correo tuvo exito dara una salida en este punto indicando que si se envio
             return true;
        
    }
    
    public boolean enviarMailEvaluacionIdeaProyecto(Agente agente,String mail){

         try {

           Email email = new Email();
            email.setFromAddress("SISGAP UNDEC", "sisgap@undec.edu.ar");
            email.addRecipient(agente.getApellido(), agente.getEmail(), RecipientType.TO);
            email.addRecipient(agente.getApellido(), agente.getOtroemail(), RecipientType.BCC);
            email.setText(mail);
            email.setSubject("UNDEC - Evaluacion Idea Proyecto SISGAP UNDEC");

            // or:
            new Mailer("localhost", 25, "sisgap@undec.edu.ar", "sgap*9812").sendMail(email);
         } catch (Exception ex) {

             ex.printStackTrace();
             //Si el correo tiene algun error lo retornaremos aca
            JsfUtil.addErrorMessage(ex,"No se pudo enviar");

             return false;


         }
             //Correo tuvo exito dara una salida en este punto indicando que si se envio
             return true;
 
    }
    
    public boolean enviarMailEvaluarPlanEPres(Agente agente){

         try {

           Email email = new Email();
            email.setFromAddress("SISGAP UNDEC", "sisgap@undec.edu.ar");
            for(Agente a : facadeagente.filtroTipoUsuario(1)){
                email.addRecipient(a.getApellido(), a.getEmail(), RecipientType.TO);
            }
            
           // email.addRecipient(agente.getApellido(), agente.getOtroemail(), RecipientType.BCC);
            email.setText("El proyecto del señor/a" + agente.getApellido()+", "+agente.getNombres()+"ha sido ingresado para evaluar (Equipo y Plan de trabajo)\n Muchas Gracias!");
            email.setSubject("UNDEC - Evaluacion Proyecto - Plan y equipo de trabajo - SISGAP UNDEC");

            // or:
            new Mailer("localhost", 25, "sisgap@undec.edu.ar", "sgap*9812").sendMail(email);
         } catch (Exception ex) {

             ex.printStackTrace();
             //Si el correo tiene algun error lo retornaremos aca
            JsfUtil.addErrorMessage(ex,"No se pudo enviar");

             return false;


         }
             //Correo tuvo exito dara una salida en este punto indicando que si se envio
             return true;
 
    }
}
