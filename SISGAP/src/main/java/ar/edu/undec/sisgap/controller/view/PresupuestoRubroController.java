package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.PresupuestoRubro;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.PresupuestoRubroFacade;
import ar.edu.undec.sisgap.controller.RubroFacade;
import ar.edu.undec.sisgap.model.PresupuestoRubroPK;
import ar.edu.undec.sisgap.model.Rubro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "presupuestoRubroController")
@SessionScoped
public class PresupuestoRubroController implements Serializable {

    private PresupuestoRubro current;

    private DataModel items = null;
    private List<PresupuestoRubro> presupuestosrubros = new ArrayList<PresupuestoRubro>();
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoRubroFacade ejbFacade;
    @EJB
    private ar.edu.undec.sisgap.controller.RubroFacade rubroFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private BigDecimal sumagastoentidad = BigDecimal.ZERO;
    private BigDecimal sumagastouniversidad = BigDecimal.ZERO;
    private BigDecimal sumagastoorganismo = BigDecimal.ZERO;

    public PresupuestoRubroController() {

    }

    @PostConstruct
    public void init() {
        try {
            // presupuestosrubros=new ArrayList<PresupuestoRubro>();
            for (Rubro rubro : rubroFacade.findAll()) {
                PresupuestoRubro presupuestorubro = new PresupuestoRubro();
                presupuestorubro.setRubro(rubro);
                presupuestorubro.setGastocomitente(new BigDecimal(0));
                presupuestorubro.setGastouniversidad(new BigDecimal(0));
                presupuestorubro.setGastoorganismo(new BigDecimal(0));
                presupuestosrubros.add(presupuestorubro);
            }

            // System.out.println( presupuestosrubros.size());
        } catch (Exception e) {
            System.out.println("mmmmmmmmmmmmmm" + e);
        }
    }

    public PresupuestoRubro getSelected() {
        if (current == null) {
            current = new PresupuestoRubro();
            current.setPresupuestoRubroPK(new ar.edu.undec.sisgap.model.PresupuestoRubroPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private PresupuestoRubroFacade getFacade() {
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
        current = (PresupuestoRubro) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PresupuestoRubro();
        current.setPresupuestoRubroPK(new ar.edu.undec.sisgap.model.PresupuestoRubroPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getPresupuestoRubroPK().setRubroid(current.getRubro().getId());
            current.getPresupuestoRubroPK().setPresupuestoid(current.getPresupuesto().getId());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PresupuestoRubro) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getPresupuestoRubroPK().setRubroid(current.getRubro().getId());
            current.getPresupuestoRubroPK().setPresupuestoid(current.getPresupuesto().getId());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PresupuestoRubro) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroDeleted"));
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

    public List<PresupuestoRubro> getPresupuestosrubros() {

        return presupuestosrubros;

    }

    public void setPresupuestosrubros() {
        this.presupuestosrubros = new ArrayList<PresupuestoRubro>();
        this.init();
    }

    public BigDecimal getSumagastoorganismo() {

        return sumagastoorganismo;
    }

    public void setSumagastoorganismo(BigDecimal sumagastoorganismo) {
        this.sumagastoorganismo = sumagastoorganismo;
    }

    public BigDecimal getSumagastocomitente() {

        return sumagastoentidad;
    }

    public void setSumagastocomitente(BigDecimal sumagastoentidad) {
        this.sumagastoentidad = sumagastoentidad;
    }

    public BigDecimal getSumagastouniversidad() {
        return sumagastouniversidad;
    }

    public void setSumagastouniversidad(BigDecimal sumagastouniversidad) {
        this.sumagastouniversidad = sumagastouniversidad;
    }

    public void sumarGastos(CellEditEvent event) {

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        Iterator it = presupuestosrubros.iterator();
        BigDecimal totalcomitente = BigDecimal.ZERO;
        BigDecimal totaluniversidad = BigDecimal.ZERO;
        BigDecimal totalorganismo = BigDecimal.ZERO;
        sumagastoentidad = BigDecimal.ZERO;
        sumagastouniversidad = BigDecimal.ZERO;
        while (it.hasNext()) {
            PresupuestoRubro pr = (PresupuestoRubro) it.next();
            totalcomitente = totalcomitente.add(new BigDecimal(pr.getGastocomitente().setScale(2).toString()));
            totaluniversidad = totaluniversidad.add(new BigDecimal(pr.getGastouniversidad().setScale(2).toString()));
            totalorganismo = totalorganismo.add(new BigDecimal(pr.getGastoorganismo().setScale(2).toString()));
            // sumagastoentidad=new BigDecimal(totalentidad).setScale(2);
            // sumagastouniversidad=new BigDecimal(totaluniversidad).setScale(2);
            // System.out.println("000000000000000+"+totalentidad.setScale(2).toString());
        }
        sumagastoentidad = totalcomitente;
        sumagastouniversidad = totaluniversidad;
        sumagastoorganismo = totalorganismo;

    }

    public void soloCrear(PresupuestoRubro pr) {

        pr.setPresupuestoRubroPK(new PresupuestoRubroPK(pr.getPresupuesto().getId(), pr.getRubro().getId()));
       //  p.getPresupuestoRubroPK().setRubroid(pr.getRubro().getId());
        // p.getPresupuestoRubroPK().setPresupuestoid(pr.getPresupuesto().getId());
        System.out.println("--------------" + pr.getRubro().getId());
        System.out.println("--------------" + pr.getPresupuesto().getId());
        System.out.println("--------------ppppppppppppppppppppppppppppppp");

        getFacade().create(pr);
    }

    @FacesConverter(forClass = PresupuestoRubro.class)
    public static class PresupuestoRubroControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PresupuestoRubroController controller = (PresupuestoRubroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "presupuestoRubroController");
            return controller.ejbFacade.find(getKey(value));
        }

        ar.edu.undec.sisgap.model.PresupuestoRubroPK getKey(String value) {
            ar.edu.undec.sisgap.model.PresupuestoRubroPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new ar.edu.undec.sisgap.model.PresupuestoRubroPK();
            key.setPresupuestoid(Integer.parseInt(values[0]));
            key.setRubroid(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(ar.edu.undec.sisgap.model.PresupuestoRubroPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPresupuestoid());
            sb.append(SEPARATOR);
            sb.append(value.getRubroid());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PresupuestoRubro) {
                PresupuestoRubro o = (PresupuestoRubro) object;
                return getStringKey(o.getPresupuestoRubroPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PresupuestoRubro.class.getName());
            }
        }

    }

}
