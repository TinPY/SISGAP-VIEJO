package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Dependencia;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.DependenciaFacade;

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

@ManagedBean(name = "dependenciaController")
@SessionScoped
public class DependenciaController implements Serializable {

    private Dependencia current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.DependenciaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DependenciaController() {
    }

    public Dependencia getSelected() {
        if (current == null) {
            current = new Dependencia();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DependenciaFacade getFacade() {
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
        current = (Dependencia) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Dependencia();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Dependencia creada!");
            //return prepareCreate();
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar crear una nueva Dependencia");
            return null;
        }
    }

    public String prepareEdit() {
        current = (Dependencia) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Dependencia actualizada correctamente!");
            //return "View";
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar actualizar la Dependecia");
            return null;
        }
    }

    public String destroy() {
        current = (Dependencia) getItems().getRowData();
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
            //JsfUtil.addSuccessMessage("Agente Borrado");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "SISGAP", "Dependencia Borrada");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            //JsfUtil.addErrorMessage(e, "Ocurrio un error durante el borrado del agente");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SISGAP", "Error al intentar borrar la Dependencia: " + e.getMessage());
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

    @FacesConverter(forClass = Dependencia.class)
    public static class DependenciaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DependenciaController controller = (DependenciaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "dependenciaController");
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
            if (object instanceof Dependencia) {
                Dependencia o = (Dependencia) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Dependencia.class.getName());
            }
        }

    }

}
