package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.controller.AgenteFacade;
import ar.edu.undec.sisgap.controller.EncriptarSHA256;
import ar.edu.undec.sisgap.controller.EnviarMail;
import ar.edu.undec.sisgap.model.Usuario;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.UsuarioFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Perfil;
import java.io.IOException;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    private Usuario current;
    private Agente currenta;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.UsuarioFacade ejbFacade;
    @EJB
    private ar.edu.undec.sisgap.controller.AgenteFacade ejbFacadea;
    private AgenteController acontroller;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public UsuarioController() {
    }

    public Usuario getSelected() {
        if (current == null) {
            current = new Usuario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private UsuarioFacade getFacade() {
        return ejbFacade;
    }
     public Agente getSelecteda() {
        if (currenta == null) {
            currenta = new Agente();
            selectedItemIndex = -1;
        }
        return currenta;
    }

    private AgenteFacade getFacadea() {
        return ejbFacadea;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10000000) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Usuario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Usuario creado!");
            //return prepareCreate();
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar crear un nuevo Usuario");
            return null;
        }
    }

    public String prepareEdit() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Usuario actualizado satisfactoriamente!");
            //return "View";
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencial al intentar actualizar el Usuario");
            return null;
        }
    }

    public String destroy() {
        current = (Usuario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UsuarioDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    public String registrar() {
        try {
            current.setUsuarioestado('P');
            current.setUsuarioclave(new EncriptarSHA256().hash256(current.getUsuarioclave().trim()) );
            current.setUsuariorol(new Perfil(2,"Docente"));
            current.setUsuariofechaalta(new java.sql.Timestamp(new Date().getTime()));
            
            getFacade().createWithPersist(current);
            
            
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"+current.getUsuarioid());
            currenta.setUsuarioid(current);
            getFacadea().createWithPersist(currenta);
            if(!new EnviarMail().enviarMailConfirmacion(currenta, current)){
                JsfUtil.addErrorMessage("No se pudo enviar el mail para activar su cuenta!");
                
                //getFacadea().remove(currenta);
                getFacade().remove(current);
                current=null;
               currenta=null;
                return null;
            }else{
               current=null;
               currenta=null;
              //RequestContext.getCurrentInstance().execute("dregistrar.hide()");
               FacesContext.getCurrentInstance().getExternalContext().redirect("./registracionexitosa.xhtml");
               
               return "registracionexitosa";
            }
            
            // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");  
               
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "No se pudo crear el usuario!");
            getFacade().remove(current);
           // getFacadea().remove(currenta);
            current=null;
            currenta=null;
            
            
            return null;
        }
    }
    
    public String activarCuenta() throws NoSuchAlgorithmException{
   Usuario u; 
   try{
      u=getFacade().find2(current.getUsuarionombre(), current.getUsuarioclave());
     JsfUtil.addSuccessMessage("Su Cuenta Fue Activada! Muchas Gracias!");
   }catch(Exception e){
      System.out.println("usuariocontroller"+e);
      JsfUtil.addErrorMessage(e, "No se pudo realizar la activación! Intente mas tarde! Disculpe");
   }
 
 //

        return "index";
    }
    
   public String recuperarPassword(){
             try {
            
           final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJLMNOPQRSTUVWXYZ1234567890!@#$%^&*()_+";
       StringBuilder result = new StringBuilder();
       int length=8;
       while(length > 0) {
           Random rand = new Random();
           result.append(characters.charAt(rand.nextInt(characters.length())));
           length--;
       }
      
                 
                 
           current= getFacade().findUsuarioNombreEmail(current.getUsuarionombre());
           
            current.setUsuarioclave(new EncriptarSHA256().hash256(result.toString()) );
           
            getFacade().edit(current);
            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"+current.getUsuarioid());

            if(!new EnviarMail().mailRecuperarPassword(current.getUsuarionombre(),result.toString(),((Agente) getFacadea().findAgente(current.getUsuarionombre())).getEmail() )){
                JsfUtil.addErrorMessage("No se pudo enviar el mail para restablecer su password!");


                current=null;
               currenta=null;
                return "index";
            }else{
               current=null;
               currenta=null;
              //RequestContext.getCurrentInstance().execute("dregistrar.hide()");
               FacesContext.getCurrentInstance().getExternalContext().redirect("./index.xhtml");
               
               return "index";
            }
              
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Disculpe las molestias, no se pudo realizar la recuperacion de password");

            current=null;
            currenta=null;
            
            
            return "index";
        }
    }
    
    public void logout() {
        FacesContext fc= FacesContext.getCurrentInstance();
            HttpSession session= (HttpSession) fc.getExternalContext().getSession(false);
            session.invalidate();
            fc.getExternalContext().invalidateSession();
            
        try {
            fc.getExternalContext().redirect(fc.getExternalContext().getRequestContextPath()+"/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getUsuarioid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Usuario.class.getName());
            }
        }

    }
    
    
}
