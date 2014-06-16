package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.TareaAgente;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.TareaAgenteFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.TareaAgentePK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "tareaAgenteController")
@SessionScoped
public class TareaAgenteController implements Serializable {

    private TareaAgente current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaAgenteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<TareaAgente> tareasagentes ;

    public TareaAgenteController() {
    }

    public TareaAgente getSelected() {
        if (current == null) {
            current = new TareaAgente();
            current.setTareaAgentePK(new ar.edu.undec.sisgap.model.TareaAgentePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private TareaAgenteFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

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
        current = (TareaAgente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TareaAgente();
        current.setTareaAgentePK(new ar.edu.undec.sisgap.model.TareaAgentePK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaAgenteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (TareaAgente) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaAgenteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (TareaAgente) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaAgenteDeleted"));
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

    @FacesConverter(forClass = TareaAgente.class)
    public static class TareaAgenteControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TareaAgenteController controller = (TareaAgenteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tareaAgenteController");
            return controller.ejbFacade.find(getKey(value));
        }

        ar.edu.undec.sisgap.model.TareaAgentePK getKey(String value) {
            ar.edu.undec.sisgap.model.TareaAgentePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ar.edu.undec.sisgap.model.TareaAgentePK();
            key.setTareaid(Integer.parseInt(values[0]));
            key.setAgenteid(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(ar.edu.undec.sisgap.model.TareaAgentePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getTareaid());
            sb.append(SEPARATOR);
            sb.append(value.getAgenteid());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TareaAgente) {
                TareaAgente o = (TareaAgente) object;
                return getStringKey(o.getTareaAgentePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TareaAgente.class.getName());
            }
        }

    }

    public List<TareaAgente> getTareasagentes() {
        return tareasagentes;
    }

    public void setTareasagentes(List<TareaAgente> tareasagentes) {
        this.tareasagentes = tareasagentes;
    }

     public void agregarEquipo(){
         
        boolean existe=false;
        FacesContext context = FacesContext.getCurrentInstance();
        AgenteViewController agenteviewcontrol= (AgenteViewController) context.getApplication().evaluateExpressionGet(context, "#{agenteViewController}", AgenteViewController.class);
        TareaAgente ta = new TareaAgente();
        ta.setAgente(agenteviewcontrol.getSelected());
        System.out.println("----------llllllllllllllllllllll-----------"+agenteviewcontrol.getSelected().getNumerodocumento());
        if(tareasagentes==null){
            tareasagentes= new ArrayList<TareaAgente>();
        }
        for (TareaAgente ta1:tareasagentes){
            if(ta1.getAgente().getId().equals(agenteviewcontrol.getSelected().getId())){
                existe=true;
            }
        }
        if(!existe){
            TareaAgentePK tapk = new TareaAgentePK();
            tapk.setAgenteid(agenteviewcontrol.getSelected().getId());
            tapk.setTareaid(tareasagentes.size()+1);
            ta.setTareaAgentePK(tapk);
            tareasagentes.add(ta);
        }
    }
    
    public void removerEquipo(Agente a){
        
        for (TareaAgente ta1:tareasagentes){
            if(ta1.getAgente().getId().equals(a.getId())){
               tareasagentes.remove(ta1);
            }
        }
    }
    
    public String reinit() {
        current = new TareaAgente();
         
        return null;
    }
    
}
