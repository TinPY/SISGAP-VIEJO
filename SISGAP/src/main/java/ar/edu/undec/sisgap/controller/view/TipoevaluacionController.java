package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Tipoevaluacion;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.TipoevaluacionFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
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
import org.primefaces.context.RequestContext;

@ManagedBean(name = "tipoevaluacionController")
@SessionScoped
public class TipoevaluacionController implements Serializable {

    private Tipoevaluacion current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.TipoevaluacionFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public TipoevaluacionController() {
    }

    public Tipoevaluacion getSelected() {
        if (current == null) {
            current = new Tipoevaluacion();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TipoevaluacionFacade getFacade() {
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
        current = (Tipoevaluacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tipoevaluacion();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Tipo de Evaluación creado correctamente!");
            //return prepareCreate();
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar crear un nuevo Tipo de Evaluación.");
            return null;
        }
    }

    public String prepareEdit() {
        current = (Tipoevaluacion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Tipo de Evaluación actualizado correctamente!");
            //return "View";
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar actualizar el Tipo de Evaluación.");
            return null;
        }
    }

    public String destroy() {
        current = (Tipoevaluacion) getItems().getRowData();
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
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BeneficiarioDeleted"));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SISGAP", "Tipo de Evaluación Borrado");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SISGAP", "Error al intentar borrar el Tipo de Evaluación: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
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

    @FacesConverter(forClass = Tipoevaluacion.class)
    public static class TipoevaluacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoevaluacionController controller = (TipoevaluacionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoevaluacionController");
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
            if (object instanceof Tipoevaluacion) {
                Tipoevaluacion o = (Tipoevaluacion) object;
                return getStringKey(o.getTipoevaluacionid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tipoevaluacion.class.getName());
            }
        }

    }

}
