package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Beneficiario;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.BeneficiarioFacade;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.Proyecto;
import java.io.IOException;

import java.io.Serializable;
import java.util.Hashtable;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "beneficiarioController")
@SessionScoped
public class BeneficiarioController implements Serializable {

    private Beneficiario current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.BeneficiarioFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public BeneficiarioController() {
    }

    public Beneficiario getSelected() {
        if (current == null) {
            current = new Beneficiario();
            selectedItemIndex = -1;
        }
        return current;
    }

    private BeneficiarioFacade getFacade() {
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
        current = (Beneficiario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Beneficiario();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Beneficiario Creado!");
            //return prepareCreate();
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar crear un nuevo Beneficiario");
            return null;
        }
    }

    public String prepareEdit() {
        current = (Beneficiario) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Beneficiario actualizado!");
            //return "View";
            RequestContext.getCurrentInstance().execute("dfinal.show()");
            return null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error de persistencia al intentar actualizar un nuevo Beneficiario");
            return null;
        }
    }

    public String destroy() {
        current = (Beneficiario) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("BeneficiarioDeleted"));
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

    @FacesConverter(forClass = Beneficiario.class)
    public static class BeneficiarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BeneficiarioController controller = (BeneficiarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "beneficiarioController");
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
            if (object instanceof Beneficiario) {
                Beneficiario o = (Beneficiario) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Beneficiario.class.getName());
            }
        }

    }
    
     // **************************  REPORTES  **********************************
        public void pdfBeneficiario() throws JRException, IOException {

            // Obtengo la ruta absoluta del archivo compilado del reporte
            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/beneficiario.jasper");

            // Fuente de datos del reporte
            JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Beneficiario[]{this.getSelected()});

            
            //Agregando los parametros
            Hashtable<String, Object> parametros = new Hashtable<String, Object>();
            parametros.put("idBeneficiario", this.getSelected().getId());

            // Llenamos el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);

            // Generamos el archivo a descargar
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=beneficiario.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        }

        public void pdfListaBeneficiarios() throws JRException, IOException {

            // Ruta absoluta del archivo compilado del reporte
            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/listaBeneficiarios.jasper");

            // Fuente de datos del reporte
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());

            // Llenamos el reporte con la fuente de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, null, beanCollectionDataSource);

            // Generamos el archivo a descargar
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=listaBeneficiarios.pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        }

        public void imprimirBeneficiario() throws JRException, IOException {

            // Obtengo la ruta absoluta del archivo compilado del reporte
            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/beneficiario.jasper");

            // Fuente de datos del reporte
            JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Beneficiario[]{this.getSelected()});

            //Agregando los parametros
            Hashtable<String, Object> parametros = new Hashtable<String, Object>();
            parametros.put("idBeneficiario", this.getSelected().getId());

            // Llenamos el reporte
            //JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);
            String archivo = JasperFillManager.fillReportToFile(rutaJasper, parametros, beanArrayDataSource);

            if (archivo != null) {
                JasperPrintManager.printReport(
                        archivo, true);
            }
        }

        public void imprimirListaBeneficiarios() throws JRException, IOException {

            // Ruta absoluta del archivo compilado del reporte
            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/listaBeneficiarios.jasper");

            // Fuente de datos del reporte
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());

            // Llenamos el reporte con la fuente de datos
            String archivo = JasperFillManager.fillReportToFile(rutaJasper, null, beanCollectionDataSource);

            if (archivo != null) {
                JasperPrintManager.printReport(
                        archivo, true);
            }

        }

}
