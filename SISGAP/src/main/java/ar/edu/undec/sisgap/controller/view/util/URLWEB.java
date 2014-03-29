/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller.view.util;

import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Hugo
 */
public class URLWEB {

    public URLWEB() {
    }
    
    public String getAbsoluteApplicationUrl() throws MalformedURLException{
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        URL url = new URL(request.getRequestURL().toString());
        URL newUrl = new URL(url.getProtocol(),
                         url.getHost(),
                         url.getPort(),
                         request.getContextPath());
        return newUrl.toString();
    }
    
}
