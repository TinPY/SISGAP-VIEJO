package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Proyecto;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ProyectoFacade;
import ar.edu.undec.sisgap.controller.view.util.URLWEB;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.PresupuestoRubro;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Iterator;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "proyectoController")
@SessionScoped
public class ProyectoController implements Serializable {

    private Proyecto current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.ProyectoFacade ejbFacade;
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoFacade ejbFacadep;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private byte[] imagen=null ;
    private StreamedContent file;
    private boolean columnorganismo=false;

    public ProyectoController() {
    }

    public Proyecto getSelected() {
        if (current == null) {
            current = new Proyecto();
            current.setDuracion(Short.valueOf("1"));
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProyectoFacade getFacade() {
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
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Proyecto();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String soloCrear() throws MalformedURLException{
        try{
            //Capturo el managed bean en el contexto
           
            FacesContext context = FacesContext.getCurrentInstance();
            AgenteController agente= (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
            FacesContext context3 = FacesContext.getCurrentInstance();
            PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
            //FacesContext context2 = FacesContext.getCurrentInstance();
            //PresupuestoController p= (PresupuestoController)context2.getApplication().evaluateExpressionGet(context2, "#{presupuestoController}", PresupuestoController.class);
           
            current.setAgenteid(agente.getSelected());
            current.setEstado('S');
            current.setFecha(new Date());
            getFacade().create(current);
            Presupuesto p= new Presupuesto();
            p.setFecha(new Date());
            p.setProyectoid(current);
            p.setEstado('P');
            
             ejbFacadep.create(p);
               System.out.println("--------------------------ttttt-------------------------"+p.getId() );
            PresupuestoRubro prerub;
            Iterator i= pr.getPresupuestosrubros().iterator();
            while(i.hasNext()){
                prerub=((PresupuestoRubro)i.next());
                prerub.setPresupuesto(p);
                pr.soloCrear(prerub);
            }
            //p.getSelected().setPresupuestoRubroList(pr.getPresupuestosrubros());
          
         
            
            System.out.println("---------------------------------------------------" );
           
            
            current = new Proyecto();
           // pr=null;
           pr.setPresupuestosrubros();
           
            
            p=null;
             FacesContext context4 = FacesContext.getCurrentInstance();
         
        context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado"));
        
        return new URLWEB().getAbsoluteApplicationUrl()+"/index.xhtml"; 
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo crear la Solicitud del Proyecto "));
        
            return null;
        }
    }

    public String prepareEdit() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Proyecto) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoDeleted"));
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
    
    public void handleFileUpload(FileUploadEvent event) { 
        System.out.println("fdsfdsdfadf");
       
        current.setDocumentacion(event.getFile().getContents());
         System.out.println("Succesful"+event.getFile().getFileName() + " is uploaded.");  
       // FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    public StreamedContent getFileConvocatoria() {          
        System.out.println("vvvvvvvvvvv");
        InputStream stream = new ByteArrayInputStream(current.getConvocatoriaid().getFormulario());
        file = new DefaultStreamedContent(stream, "docx/doc/pdf/rar", current.getConvocatoriaid().getLink());  
            System.out.println("qqqqqqqqqqqqqqqq");
            return file;
            
    }
     public StreamedContent getFile() {          
//        System.out.println("vvvvvvvvvvv");
//        InputStream stream = new ByteArrayInputStream(current.getDocumentacion());
//        MagicMatch mm=null;
//        try {
//            mm = Magic.getMagicMatch(current.getDocumentacion());
//        } catch (MagicParseException ex) {
//            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MagicMatchNotFoundException ex) {
//            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MagicException ex) {
//            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String extension;
//        if(mm!=null){
//            extension= mm.getExtension();
//        }else{
//            extension="rar";
//        }
//            
//        System.out.println(extension+"---------------------");
//       file = new DefaultStreamedContent(stream, "image/jpg/png/rar", "documentacion."+extension);  
//   System.out.println("qqqqqqqqqqqqqqqq");
   return file;
        
    }  
  
    
    @FacesConverter(forClass = Proyecto.class)
    public static class ProyectoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoController controller = (ProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoController");
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
            if (object instanceof Proyecto) {
                Proyecto o = (Proyecto) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Proyecto.class.getName());
            }
        }

    }
    public void buscarProyectoAgente(int agenteid){
        recreateModel();
        items=new ListDataModel(getFacade().buscarProyectoAgente(agenteid));
    }

    public boolean isColumnorganismo() {
        return columnorganismo;
    }

    public void setColumnorganismo(boolean columnorganismo) {
        this.columnorganismo = columnorganismo;
    }
    
    public void cambioColPresupuesto(){
        if(current.getTipofinanciamientoid().getId()<3)
        columnorganismo=true;
    }
}
