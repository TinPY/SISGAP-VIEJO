package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.ProyectoAgente;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ProyectoAgenteFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Proyecto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "proyectoAgenteController")
@SessionScoped
public class ProyectoAgenteController implements Serializable {

    private ProyectoAgente current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.ProyectoAgenteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean iseditar=true;
    
    private List<ProyectoAgente> equipotrabajo = new ArrayList<ProyectoAgente>() ;

    public ProyectoAgenteController() {
    }

    public ProyectoAgente getSelected() {
        if (current == null) {
            current = new ProyectoAgente();
            current.setProyectoAgentePK(new ar.edu.undec.sisgap.model.ProyectoAgentePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProyectoAgenteFacade getFacade() {
        return ejbFacade;
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
        current = (ProyectoAgente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ProyectoAgente();
        current.setProyectoAgentePK(new ar.edu.undec.sisgap.model.ProyectoAgentePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getProyectoAgentePK().setProyectoid(current.getProyecto().getId());
            current.getProyectoAgentePK().setAgenteid(current.getAgente().getId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoAgenteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (ProyectoAgente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getProyectoAgentePK().setProyectoid(current.getProyecto().getId());
            current.getProyectoAgentePK().setAgenteid(current.getAgente().getId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoAgenteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (ProyectoAgente) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoAgenteDeleted"));
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

    @FacesConverter(forClass = ProyectoAgente.class)
    public static class ProyectoAgenteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoAgenteController controller = (ProyectoAgenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoAgenteController");
            return controller.ejbFacade.find(getKey(value));
        }

        ar.edu.undec.sisgap.model.ProyectoAgentePK getKey(String value) {
            ar.edu.undec.sisgap.model.ProyectoAgentePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ar.edu.undec.sisgap.model.ProyectoAgentePK();
            key.setAgenteid(Integer.parseInt(values[0]));
            key.setProyectoid(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(ar.edu.undec.sisgap.model.ProyectoAgentePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getAgenteid());
            sb.append(SEPARATOR);
            sb.append(value.getProyectoid());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ProyectoAgente) {
                ProyectoAgente o = (ProyectoAgente) object;
                return getStringKey(o.getProyectoAgentePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ProyectoAgente.class.getName());
            }
        }

    }

    public List<ProyectoAgente> getEquipotrabajo() {
        return equipotrabajo;
    }

    public void setEquipotrabajo(List<ProyectoAgente> equipotrabajo) {
        this.equipotrabajo = equipotrabajo;
    }

    public void buscarEquipoTrabajoEditar(int p){
        if((equipotrabajo.isEmpty()) & (iseditar)){
            this.equipotrabajo=this.ejbFacade.buscarEquipoTrabajo(p);
            iseditar = false;
        }
    }
    
    public void agregarEquipo(){
        boolean inserto =false;
         FacesContext context = FacesContext.getCurrentInstance();
        AgenteViewController agenteviewcontroller = (AgenteViewController) context.getApplication().evaluateExpressionGet(context, "#{agenteViewController}", AgenteViewController.class);
     
        for(ProyectoAgente pa:equipotrabajo){
            if((agenteviewcontroller.getSelected().getId().equals(pa.getAgente().getId())) | (agenteviewcontroller.getSelected().getApellido().isEmpty()) ){
                inserto=true;
            }
            
        }
        if(!inserto){
            FacesContext context2 = FacesContext.getCurrentInstance();    
            ProyectoController proyectocontroller = (ProyectoController) context2.getApplication().evaluateExpressionGet(context2, "#{proyectoController}", ProyectoController.class);
            ProyectoAgente nuevo = new ProyectoAgente();
            nuevo.setAgente(agenteviewcontroller.getSelected());
            nuevo.setProyecto(proyectocontroller.getSelected());
            equipotrabajo.add(nuevo);
            
        }
        
         for(ProyectoAgente pa:equipotrabajo){
            System.out.println(pa.getAgente().getApellido());
            
        }
        
    }
    
}
