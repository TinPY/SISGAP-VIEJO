package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Convocatoria;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ConvocatoriaFacade;
import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.model.Beneficiario;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.nio.file.Files;
import java.util.Hashtable;
import java.util.List;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "convocatoriaController")
@SessionScoped
public class ConvocatoriaController implements Serializable {

    private Convocatoria current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.ConvocatoriaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private StreamedContent file;
    private List<Convocatoria> tablafiltrada;

    public ConvocatoriaController() {
    }

    public Convocatoria getSelected() {
        if (current == null) {
            current = new Convocatoria();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Convocatoria convocatoria) {
        current = convocatoria;
    }

    private ConvocatoriaFacade getFacade() {
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
        current = (Convocatoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Convocatoria();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleSISGAP").getString("ConvocatoriaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleSISGAP").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Convocatoria) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleSISGAP").getString("ConvocatoriaUpdated"));
            //return "View";
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleSISGAP").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Convocatoria) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/BundleSISGAP").getString("ConvocatoriaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/BundleSISGAP").getString("PersistenceErrorOccured"));
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

    public StreamedContent getFile() {
        System.out.println("vvvvvvvvvvv");
        InputStream stream = new ByteArrayInputStream(current.getFormulario());
        file = new DefaultStreamedContent(stream, "docx/doc/pdf/rar", current.getLink());
        System.out.println("qqqqqqqqqqqqqqqq");
        return file;

    }

    @FacesConverter(forClass = Convocatoria.class)
    public static class ConvocatoriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConvocatoriaController controller = (ConvocatoriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "convocatoriaController");
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
            if (object instanceof Convocatoria) {
                Convocatoria o = (Convocatoria) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Convocatoria.class.getName());
            }
        }

    }

    public void findConvocatoriafinanciamiento(Integer tipoproyectoid, Integer tipofinanciamientoid) {

        items = new ListDataModel(getFacade().findConvocatoriafinanciamiento(tipoproyectoid, tipofinanciamientoid));
    }

    public StreamedContent getFileConvocatoria() {
        System.out.println("vvvvvvvvvvv");
        InputStream stream = new ByteArrayInputStream(this.getSelected().getFormulario());
        file = new DefaultStreamedContent(stream, "docx/doc/pdf/rar", current.getLink());
        System.out.println("qqqqqqqqqqqqqqqq");
        return file;

    }

    public List<Convocatoria> getTablafiltrada() {
        return tablafiltrada;
    }

    public void setTablafiltrada(List<Convocatoria> tablafiltrada) {
        this.tablafiltrada = tablafiltrada;
    }

    public void handleFileUploadBases(FileUploadEvent event) {

        try {
            current.setBases(event.getFile().getContents());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleFileUploadFormulario(FileUploadEvent event) {
        try {
            current.setFormulario(event.getFile().getContents());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StreamedContent obtenerBases() {
        InputStream is = new ByteArrayInputStream(current.getBases());

        StreamedContent sc = new DefaultStreamedContent(is);

        return sc;
    }

    public StreamedContent obtenerFormulario() {
        InputStream is = new ByteArrayInputStream(current.getFormulario());

        StreamedContent sc = new DefaultStreamedContent(is);

        return sc;
    }
}
