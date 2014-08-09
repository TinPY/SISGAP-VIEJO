/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.controller.AgenteFacade;
import ar.edu.undec.sisgap.controller.EncriptarSHA256;
import ar.edu.undec.sisgap.controller.UsuarioFacade;
import ar.edu.undec.sisgap.model.Agente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Hugo
 */
@ManagedBean
@ViewScoped
public class AgenteViewController implements Serializable {
    private Agente current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.AgenteFacade ejbFacade;
    @EJB
    private ar.edu.undec.sisgap.controller.UsuarioFacade ejbFacadeu;
    private ar.edu.undec.sisgap.controller.view.util.PaginationHelper pagination;
    private int selectedItemIndex;
    
    private List<Agente> agentes = new ArrayList<Agente>() ;
    private List<Agente> collectoragentes = new ArrayList<Agente>() ;
    private Agente agente1;
    private boolean filtroapellidonombre=false;
    private boolean filtrodocumento=false;
    private SelectItem[] itemsAgentes;
    
    /**
     * Creates a new instance of AgenteViewController
     */
    public AgenteViewController() {
    }
    
     public Agente getSelected() {
        if (current == null) {
            current = new Agente();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AgenteFacade getFacade() {
        return ejbFacade;
    }
    
    private UsuarioFacade getFacadeu() {
        return ejbFacadeu;
    }

    public ar.edu.undec.sisgap.controller.view.util.PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new ar.edu.undec.sisgap.controller.view.util.PaginationHelper(10000000) {

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
        current = (Agente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Agente();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Agente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenteUpdated"));
            return "View";
        } catch (Exception e) {
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String solograbar() {
        try {
            System.out.println("----------apellido------------"+current.getApellido());
             System.out.println("---------n documento-------------"+current.getNumerodocumento());
              System.out.println("---------tipodocumento-------------"+current.getTipodocumentoid().getTipodocumento());
               System.out.println("-----------dependencia-----------"+current.getDependenciaid().getDependencia());
                System.out.println("-----------profesion-----------"+current.getProfesion());
            getFacade().edit(current);
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenteUpdated"));
          return null;  
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
           System.out.println(e); 
           return null;
        }
    }

    public String destroy() {
        current = (Agente) getItems().getRowData();
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
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AgenteDeleted"));
        } catch (Exception e) {
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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
        return ar.edu.undec.sisgap.controller.view.util.JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return ar.edu.undec.sisgap.controller.view.util.JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    public void findAgente(String nombreusuario){
        if(current==null){
             current= (Agente) getFacade().findAgente(nombreusuario);
            System.out.println("9999999999999999999999999999999999999999999999999"+current.getId());
        }
    }

    @FacesConverter(forClass = Agente.class)
    public static class AgenteViewControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AgenteViewController controller = (AgenteViewController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "agenteViewController");
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
            if (object instanceof Agente) {
                Agente o = (Agente) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Agente.class.getName());
            }
        }

    }
    
    public void editarMiCuenta(){
        try {
            current.getUsuarioid().setUsuarioclave(new EncriptarSHA256().hash256(current.getUsuarioid().getUsuarioclave().trim()) );
            getFacade().edit(current);
            if(current.getUsuarioid().getUsuarioclave().length()>5){
                getFacadeu().edit(current.getUsuarioid());
            }
            
            // FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");  
               
        } catch (Exception e) {
            ar.edu.undec.sisgap.controller.view.util.JsfUtil.addErrorMessage(e, "No se pudo Modificar su cuenta!");
            
        }
    }
    
    public void filtroDocumentooApellido(){
//        if(nrodocoapellido.length()>2)
//            System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm"+nrodocoapellido);
//        this.setItemsAgentes(ar.edu.undec.sisgap.controller.view.util.JsfUtil.getSelectItems(ejbFacade.filtroDocumentooApellido(nrodocoapellido), false));
    }

    
    public List<Agente> getAgentes() {
        return agentes;
    }

    public void setAgentes(List<Agente> agentes) {
        this.agentes = agentes;
    }

    public List<Agente> getCollectoragentes() {
        return collectoragentes;
    }

    public void setCollectoragentes(List<Agente> collectoragentes) {
        this.collectoragentes = collectoragentes;
    }
    
    public void agregarEquipo(){
        System.out.println("-----"+current.getApellido());
        collectoragentes.add(current);
    }
    
    public void removerAgenteCollector(){
        this.collectoragentes.remove(agente1);
    }
   
    public void setSelected(Agente agente){
        current = agente;
    }
    
    public String reinit() {
        current = new Agente();
         
        return null;
    }

    public Agente getAgente1() {
        if(agente1 == null){
         agente1 = new Agente();
        }
        return agente1;
    }

    public void setAgente1(Agente agente1) {
        this.agente1 = agente1;
    }

    public void registrar() {
        System.out.println("oooooooooo");
        if (this.ejbFacade.agentedocumento(agente1.getNumerodocumento()) == null) {
            System.out.println("---------------");
            ejbFacade.createWithPersist(agente1);
            System.out.println("lllllllllllllllllllllll");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo Agente", "La creación del nuevo Agente se realizó satisfactoriamente"));
        } else {
            System.out.println("8888888888888888");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ya Existe un Agente con ese número de documento."));
        }
    }

    public boolean isFiltroapellidonombre() {
        return filtroapellidonombre;
    }

    public void setFiltroapellidonombre(boolean filtroapellidonombre) {
        this.filtroapellidonombre = filtroapellidonombre;
    }

    public boolean isFiltrodocumento() {
        return filtrodocumento;
    }

    public void setFiltrodocumento(boolean filtrodocumento) {
        this.filtrodocumento = filtrodocumento;
    }

    public SelectItem[] getItemsAgentes() {
        return itemsAgentes;
    }

    public void setItemsAgentes(SelectItem[] i) {
        itemsAgentes = i;
    }

    // CREAR USUARIO
    // Rellenar combo de Agentes sin Usuario en Usuarios/Create
    public SelectItem[] getItemsAvailableSelectOneAgentesSinUsuario() {

        System.out.println("Agentes sin Usuario");

        for (Agente a : this.ejbFacade.agentesSinUsuario()) {
            System.out.println(a.getApellido() + ", " + a.getNombres());
        }

        System.out.println("-------------");

        return ar.edu.undec.sisgap.controller.view.util.JsfUtil.getSelectItems(ejbFacade.agentesSinUsuario(), true);
    }

    public void findAgente1(String nombreusuario) {
        agente1 = null;
        agente1 = (Agente) getFacade().findAgente(nombreusuario);

    }
}
