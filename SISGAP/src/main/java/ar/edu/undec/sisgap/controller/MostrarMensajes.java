/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.undec.sisgap.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author mpaez
 */
@ManagedBean(name = "mostrarMensajes")
public class MostrarMensajes {

    public void mensajeGrowl(String titulo, String mensaje) {
        //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");
        //RequestContext.getCurrentInstance().showMessageInDialog(message);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
