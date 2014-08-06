package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.controller.EnviarMail;
import ar.edu.undec.sisgap.controller.PresupuestoRubroFacade;
import ar.edu.undec.sisgap.model.Proyecto;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ProyectoFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.model.Estadoproyecto;
import ar.edu.undec.sisgap.model.Etapa;
import ar.edu.undec.sisgap.model.Evaluacion;
import ar.edu.undec.sisgap.model.EvaluacionPregunta;
import ar.edu.undec.sisgap.model.EvaluacionPreguntaPK;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.PresupuestoRubro;
import ar.edu.undec.sisgap.model.PresupuestoRubroitem;
import ar.edu.undec.sisgap.model.ProyectoAgente;
import ar.edu.undec.sisgap.model.ProyectoAgentePK;
import ar.edu.undec.sisgap.model.Tarea;
import ar.edu.undec.sisgap.model.TareaAgente;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "proyectoController")
@SessionScoped
public class ProyectoController implements Serializable {

    private Proyecto current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.ProyectoFacade ejbFacade;
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoFacade ejbFacadep;
    @EJB
    private ar.edu.undec.sisgap.controller.ArchivoproyectoFacade ejbFacadeap;
    @EJB
    private ar.edu.undec.sisgap.controller.EvaluacionFacade ejbevaluacion;
    @EJB
    private ar.edu.undec.sisgap.controller.EvaluacionPreguntaFacade ejbevaluacionproyecto;
    @EJB
    private ar.edu.undec.sisgap.controller.EtapaFacade ejbetapa;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaFacade ejbtarea;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaAgenteFacade ejbtareaagente;
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoFacade ejbpresupuesto;
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoRubroitemFacade ejbpresupuestorubroitem;
    @EJB
    private ar.edu.undec.sisgap.controller.ProyectoAgenteFacade ejbproyectoagente;
    @EJB
    PresupuestoRubroFacade prfacade;
    @EJB
    private ar.edu.undec.sisgap.controller.ArchivoproyectoFacade ejbarchivoproyecto;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private byte[] imagen=null ;
    private StreamedContent file;
    private boolean columnorganismo=false;
    private boolean columncomitente=false;
    private boolean columnuniversidad=false;
    private boolean verarchivo=false;
    private boolean iseditableSolicitud=false;
    private String habilitarcomitente="0";
    private String observacionfinal;
    private Proyecto proyectoViejo;
    
    public ProyectoController() {
    }

    public Proyecto getSelected() {
        if (current == null) {
            current = new Proyecto();
            current.setDuracion(Short.valueOf("1"));
            selectedItemIndex = -1;
        }
         proyectoViejo = current; 
        return current;
       
    }

    private ProyectoFacade getFacade() {
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
    
    public String soloCrear() {
        try{
            //Capturo el managed bean en el contexto
            FacesContext contextc = FacesContext.getCurrentInstance();
            ConvocatoriaController convocatoria= (ConvocatoriaController) contextc.getApplication().evaluateExpressionGet(contextc, "#{convocatoriaController}", ConvocatoriaController.class);
                    
           if(convocatoria.getSelected()!=null){
                    FacesContext context = FacesContext.getCurrentInstance();
                    AgenteController agente= (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
                    FacesContext context3 = FacesContext.getCurrentInstance();
                    PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
                    FacesContext context2 = FacesContext.getCurrentInstance();
                    ArchivoproyectoController ap= (ArchivoproyectoController)context2.getApplication().evaluateExpressionGet(context2, "#{archivoproyectoController}", ArchivoproyectoController.class);

                    current.setAgenteid(agente.getSelected());
                    
                    Estadoproyecto ep= new Estadoproyecto();
                    ep.setId(1);
                    current.setEstadoproyectoid(ep);
                    current.setFecha(new Date());
                   
                    if(convocatoria.getSelected().getId()==null){
                        current.setConvocatoriaid(null);
                    } else {
                        current.setConvocatoriaid(convocatoria.getSelected());
                }
                    getFacade().createWithPersist(current);
                    Presupuesto p= new Presupuesto();
                    p.setFecha(new Date());
                    p.setProyectoid(current);
                    p.setEstado('P');

                     ejbFacadep.createWithPersist(p);
                    
                    PresupuestoRubro prerub;
                    Iterator i= pr.getPresupuestosrubros().iterator();
                    while(i.hasNext()){
                        prerub=((PresupuestoRubro)i.next());
                        prerub.setPresupuesto(p);
                        pr.soloCrear(prerub);
                    }
                    //p.getSelected().setPresupuestoRubroList(pr.getPresupuestosrubros());

                   // 
                   pr.setPresupuestosrubros(null);
                   

                   i=ap.getCollectorArchivoProyecto().iterator();
                   

                   while(i.hasNext()){
                       Archivoproyecto archivoproyecto=new Archivoproyecto();
                        archivoproyecto=((Archivoproyecto)i.next());
                        archivoproyecto.setId(null);
                        archivoproyecto.setProyectoid(current);
                        ap.soloCrear(archivoproyecto);
                    }
                    
                   // EQUIPO DE TRABAJO ***********************
                FacesContext contextAV = FacesContext.getCurrentInstance();
                AgenteViewController agenteViewController = (AgenteViewController) contextAV.getApplication().evaluateExpressionGet(contextAV, "#{agenteViewController}", AgenteViewController.class);

                

                List<Agente> listaAgentes = agenteViewController.getCollectoragentes();
               

                for (Agente a : listaAgentes) {
                    ProyectoAgente proyectoAgente = new ProyectoAgente();

                    ProyectoAgentePK paPK = new ProyectoAgentePK();
                    paPK.setProyectoid(current.getId());
                    paPK.setAgenteid(a.getId());

                    proyectoAgente.setProyectoAgentePK(paPK);
                    proyectoAgente.setAgente(a);
                    proyectoAgente.setProyecto(current);
                    proyectoAgente.setFuncionproyecto("solicitud");

                    ejbproyectoagente.createWithPersist(proyectoAgente);
                   
                }

                // FIN - EQUIPO DE TRABAJO ******************

                   
                  
                  


                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta idea proyecto Creada!", "Su Solicitud a Proyecto fue creado!!!");  
                   

                    EnviarMail enviarmail = new EnviarMail();
                    //enviarmail.enviarMailIngresoIdeaProyecto(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName(),current.getAgenteid().getEmail() , habilitarcomitente);
                   
                     RequestContext.getCurrentInstance().execute("dfinal.show()"); 
                   //  FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
                // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
                    
                System.out.println("Solicitud Creado");
                    //current = new Proyecto();
                 return null;
           }else{
               RequestContext.getCurrentInstance().scrollTo("wconvocatoria");
               FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("Por favor seleccione una fila en la tabla de Convocatoria");
               FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
                return null;
           }
        }catch(Exception e){
             FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("No se pudo crear la Solicitud del Proyecto "+e);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
         System.out.println("Error al grabar la Solicitud  = "+e);
            return null;
        }
    }

    public String prepareEdit() {
        current = (Proyecto) getItems().getRowData();
          
//           FacesContext context = FacesContext.getCurrentInstance();
//           EtapaController etapacontroller = (EtapaController)context.getApplication().evaluateExpressionGet(context, "#{etapaController}", EtapaController.class);
//           
//        etapacontroller.setEtapas(this.ejbetapa.findByProyecto(current));
//        etapacontroller.prepareEditarListadoEtapas();
//         
//        FacesContext contextp = FacesContext.getCurrentInstance();
//          
//        PresupuestoRubroitemController presupuestorubroitemcontroller = (PresupuestoRubroitemController)contextp.getApplication().evaluateExpressionGet(contextp, "#{presupuestoRubroitemController}", PresupuestoRubroitemController.class);
//        presupuestorubroitemcontroller.setPresupuestosrubrositems(this.ejbpresupuestorubroitem.findByPresupuesto(this.ejbpresupuesto.findporProyecto(current.getId())));
        
//        presupuestorubroitemcontroller.armarPresupuestosNodos2();
//        presupuestorubroitemcontroller.armarGraficosPresupuesto();
//        
        //selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
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
        
       current.setDocumentacionnombre(event.getFile().getFileName());
        current.setDocumentacion(event.getFile().getContents());
         System.out.println("Succesful"+event.getFile().getFileName() + " is uploaded.");  
       // FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    public StreamedContent getFileConvocatoria() {          
        
        InputStream stream = new ByteArrayInputStream(current.getConvocatoriaid().getFormulario());
        file = new DefaultStreamedContent(stream, "docx/doc/pdf/rar", current.getConvocatoriaid().getLink());  
            
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

//       file = new DefaultStreamedContent(stream, "image/jpg/png/rar", "documentacion."+extension);  

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
    
    public void buscarProyectoEstado(long estado){
        recreateModel();
        items=new ListDataModel(getFacade().buscarProyectoEstado((int)estado));
    }

    public boolean isColumnorganismo() {
        return columnorganismo;
    }

    public void setColumnorganismo(boolean columnorganismo) {
        this.columnorganismo = columnorganismo;
    }

    public boolean isColumncomitente() {
        return columncomitente;
    }

    public void setColumncomitente(boolean columncomitente) {
        this.columncomitente = columncomitente;
    }

    public boolean isColumnuniversidad() {
        return columnuniversidad;
    }

    public void setColumnuniversidad(boolean columnuniversidad) {
        this.columnuniversidad = columnuniversidad;
    }

    public void isVerarchivox() {
        
       
        if(current.getConvocatoriaid().getId()>0){
            this.verarchivo = true;
        }else{
           this.verarchivo = false; 
        }
        
        
    }

    public boolean getVerarchivo() {
        return verarchivo;
       
    }
    
    
    
    public void cambioColPresupuesto(){
        if(current.getTipofinanciamientoid().getId()<3){
            columnorganismo=true;
        }else{
            columnorganismo=false;
        }
    }

    public boolean isIseditableSolicitud() {
            if(current.getEstadoproyectoid().getId()==1 ){
                iseditableSolicitud=true;
            }else{
                iseditableSolicitud =false;
            }
            
        return iseditableSolicitud;
    }

    public void setIseditableSolicitud(boolean iseditableSolicitud) {
        this.iseditableSolicitud = iseditableSolicitud;
    }
    
    
    public void actualizarTabla(){
        
        RequestContext.getCurrentInstance().update("tpresupuesto");
    }
    
//     public String soloEditar() {
//        try{
//            //Capturo el managed bean en el contexto
//           
//           FacesContext context = FacesContext.getCurrentInstance();
//           PresupuestoController p= (PresupuestoController)context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);
//           
//            current.setFecha(new Date());
//            getFacade().edit(current);
//            
//             PresupuestoRubro prerub;
//            Iterator i= p.getSelected().getPresupuestoRubroList().iterator();
//            while(i.hasNext()){
//                prerub=((PresupuestoRubro)i.next());
//               // p.soloEditar(prerub);
//            }
//         
//            
//             FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");

//           
//         return "ViewSolicitud";
//        }catch(Exception e){
//            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo crear la Solicitud del Proyecto "));
//        
//            return null;
//        }
//    }

    public String getHabilitarcomitente() {
        return habilitarcomitente;
    }

    public void setHabilitarcomitente(String habilitarcomitente) {
        this.habilitarcomitente = habilitarcomitente;
    }
    
    public void evaluarIdea(){
        boolean todobien=false;
        if(current.getEstadoproyectoid().getId()>1){
            
            FacesContext context = FacesContext.getCurrentInstance();
            EvaluacionPreguntaController evaluacionpregunta= (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
            EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);
            AgenteController agente = (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);

        try{
            
            evaluacion.getSelected().setFecha(new Date());
            evaluacion.getSelected().setProyectoid(current);
            evaluacion.getSelected().setUsuarioid(agente.getSelected().getUsuarioid());
            ejbevaluacion.createWithPersist(evaluacion.getSelected());
            
            for(EvaluacionPregunta eval:evaluacionpregunta.getEvaluaciones()){
                 eval.setEvaluacionPreguntaPK(new EvaluacionPreguntaPK());
               eval.getEvaluacionPreguntaPK().setEvaluacionid(evaluacion.getSelected().getId());
              eval.getEvaluacionPreguntaPK().setPreguntaid(eval.getPregunta().getId());

              ejbevaluacionproyecto.create(eval);
             }
              this.ejbFacade.edit(current);  
             
             
             if(new EnviarMail().enviarMailEvaluacionIdeaProyecto(current.getAgenteid(),evaluacion.getSelected().getObservacion())){
                
               todobien=true;
                
               //  RequestContext.getCurrentInstance().execute("dfinal.show()"); 
                 
             }else{
                 FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo evaluar del Proyecto.")); 
                 
                ejbevaluacion.remove(evaluacion.getSelected());
                Estadoproyecto ep = new Estadoproyecto();
                ep.setId(1);
                current.setEstadoproyectoid(ep);
                this.ejbFacade.edit(current);
                
//                HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//                try {
//                    response.sendRedirect("List.xhtml");
//                } catch (IOException ex) {
//                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
//                }
                
                RequestContext.getCurrentInstance().execute("dfinalerror.show()"); 
             
             }
        }catch(Exception e){
            ejbevaluacion.remove(evaluacion.getSelected());
            Estadoproyecto ep = new Estadoproyecto();
                ep.setId(1);
                current.setEstadoproyectoid(ep);
                this.ejbFacade.edit(current);
           
             FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo evaluar del Proyecto "));
//             
//             HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
//                try {
//                    response.sendRedirect("List.xhtml");
//                } catch (IOException ex) {
//                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
//                }
             RequestContext.getCurrentInstance().execute("dfinalerror.show()"); 
        }
        }else{
             RequestContext.getCurrentInstance().execute("dfinalerrore.show()"); 
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Seleccione el Estado del Proyecto! Muchas Gracias ");
//            RequestContext.getCurrentInstance().showMessageInDialog(message);
//            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","Seleccione el Estado del Proyecto! Muchas Gracias "));
        }
         if(todobien){
            RequestContext.getCurrentInstance().execute("dfinal.show()"); 
        }
                
       
        
    }
     
  public String armarObservaciones(){
      
      observacionfinal="";
      FacesContext context = FacesContext.getCurrentInstance();
        EvaluacionPreguntaController evaluacionpregunta = (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
        EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);;
       String obs="";
       String calificacionpregunta="";
       try{
        observacionfinal="Estimado docente-investigador:\n" +
"Por medio del presente informamos a Ud. que la Idea-Proyecto Nº "+current.getId()+" - "+current.getNombre()+" de la cual Ud. Es responsable, ha sido "+current.getEstadoproyectoid().getEstado().replace("Idea Proyecto","").toUpperCase()+" según el siguiente detalle:\n" +
"Observaciones:\n";
        
      for(EvaluacionPregunta eval:evaluacionpregunta.getEvaluaciones()){
          switch(eval.getRating()){
              case 3 :
                  calificacionpregunta="CORRECTO";
                   break;
              case 4 :
                  calificacionpregunta="MUY BUENO";
                  break;
              case 5 :
                  calificacionpregunta="EXCELENTE";
                  break;
              default :
                  calificacionpregunta="REGULAR";
                  break;
          }
          
          if(eval.getObservacion().trim().length()>0){
              observacionfinal+= " - "+eval.getObservacion()+"\n";
          }
          obs+=" - " + eval.getPregunta().getPregunta()+" - "+calificacionpregunta+"\n";
         }
      String isaceptada="";
      if(current.getEstadoproyectoid().getId()==2)
          isaceptada="A partir de la recepción del presente correo, el sistema quedará habilitado para la carga del proyecto definitivo.\n";
      observacionfinal+="Resultados según criterios evaluados:\n" + obs +
              "Sin otro particular lo saludo a Ud. cordialmente.\n" +
                "Unidad de Vinculación Tecnológica"
              ;
      evaluacion.getSelected().setObservacion(observacionfinal);
       }catch(Exception e){
           System.out.println(e);
           
       }
      return null;
  }   

    public String getObservacionfinal() {
        return observacionfinal;
    }

    public void setObservacionfinal(String observacionfinal) {
        this.observacionfinal = observacionfinal;
    }
    public void onCellEdit(){
        
    }
    
    public void buscarProyectosAgenteTrue(int agenteid){
        recreateModel();
       
        items=new ListDataModel(getFacade().buscarProyectosAgente(true,agenteid));
    }
    
    public String enviarEvaluarProyecto(){
        boolean todobien=false;
        try {
         FacesContext context = FacesContext.getCurrentInstance();
        EtapaController etapacontroller = (EtapaController) context.getApplication().evaluateExpressionGet(context, "#{etapaController}", EtapaController.class);
        
        PresupuestoRubroitemController presupuestorubroitemcontroller = (PresupuestoRubroitemController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoRubroitemController}", PresupuestoRubroitemController.class);
        PresupuestoController presupuestocontroller = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);
        for (Etapa e : etapacontroller.getEtapas()) {
           e.setId(null);
           e.setProyectoid(current);
           
            for(Tarea t:e.getTareaList()){
               t.setId(null);
               t.setFechacreacion(new Date());
               t.setFechamodificacion(new Date());
                
//                int contador2=0;
//                for(TareaAgente ta:t.getTareaAgenteList()){
//                    ta.setTareaid(null);
//                    t.getTareaAgenteList().set(contador2, ta);
//                    contador2++;
//                    
//               }
                
          }
            List<Tarea> OldTarea = e.getTareaList();
           e.setTareaList(null);
           ejbetapa.createWithPersist(e);
           if(OldTarea!=null){
                for(Tarea t : OldTarea ){
                    t.setEtapaid(e);
                    List<TareaAgente> OldTareaAgente = t.getTareaAgenteList();
                    t.setTareaAgenteList(null);
                    this.ejbtarea.createWithPersist(t);
                    if(OldTareaAgente != null){
                         for (TareaAgente ta : OldTareaAgente){
                             ta.setTareaid(t);
                           ejbtareaagente.create(ta);
                         }
                    }
                }
           }
        }
        
        for(PresupuestoRubroitem pri : presupuestorubroitemcontroller.getPresupuestosrubrositems()){
           
           
             pri.setPresupuesto(presupuestocontroller.getSelected());
            
            ejbpresupuestorubroitem.createWithPersist(pri);
           
        }
         EnviarMail enviarmail = new EnviarMail();
        // enviarmail.enviarMailEvaluarPlanEPres(current.getAgenteid() );

               todobien=true;       
        
           // RequestContext.getCurrentInstance().execute("dfinal.show()");
           // FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
            // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
        } catch (Exception e) {
             FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("No se pudo crear el Plan y equipo del Proyecto "+e);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
         System.out.println("Error al grabar Plan y equipo de trabajo  = "+e);
            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, e);
        }
         if(todobien){
            RequestContext.getCurrentInstance().execute("dfinal.show()"); 
        }
                
        return null;
    }
    
      public String prepareCreatePlanEquipo() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "CreatePlanEquipo";
    }
      public String CrearConEtapa() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "CrearConEtapa";
    }
    
      
    // PANELES DE CONTROL Y GRAFICOS  **************************/
    // Filtrado entre fechas
    private Date filtroFechaInicio;
    private Date filtroFechaFin;

    // Promedio de Presupuestos de los proyectos
    private float promedioPresupuestoPorProyecto = 0;

    // Sumado del total de presupuestos de todos los proyectos
    private float totalPresupuestosProyectos = 0;

    // Usado para el filtrado del datatable
    private List<Proyecto> proyectosFiltrados;

    // Grafico de Lineas
    private CartesianChartModel modeloGraficoLineas;

    public CartesianChartModel getmodeloGraficoLineas() {
        return modeloGraficoLineas;
    }

    // Grafico de Lineas Acumulado
    private CartesianChartModel modeloGraficoLineasAcumulado;

    public CartesianChartModel getmodeloGraficoLineasAcumulado() {
        return modeloGraficoLineasAcumulado;
    }

    // Grafico de torta
    private PieChartModel modeloGraficoTorta;

    public PieChartModel getmodeloGraficoTorta() {
        return modeloGraficoTorta;
    }

    // Grafico de Donuts
    private DonutChartModel modeloGraficoDonut;

    public DonutChartModel getmodeloGraficoDonut() {
        return modeloGraficoDonut;
    }

    // Filtro Fecha Inicio
    public Date getFiltroFechaInicio() {
        return filtroFechaInicio;
    }

    public void setFiltroFechaInicio(Date filtroFechaInicio) {
        this.filtroFechaInicio = filtroFechaInicio;
    }

    // Filtro Fecha Fin
    public void setFiltroFechaFin(Date filtroFechaFin) {
        this.filtroFechaFin = filtroFechaFin;
    }

    public Date getFiltroFechaFin() {
        return filtroFechaFin;
    }

    // Total de la suma de presupuestos de proyectos
    public float getTotalPresupuestosProyectos() {
        return totalPresupuestosProyectos;
    }

    // Lista de Proyectos Filtrados
    public List<Proyecto> getProyectosFiltrados() {
        return proyectosFiltrados;
    }

    public void setProyectosFiltrados(List<Proyecto> proyectosFiltrados) {
        this.proyectosFiltrados = proyectosFiltrados;
    }

    public float getPromedioPresupuestoPorProyecto() {

        // Poner esta logica en otro lugar
        this.promedioPresupuestoPorProyecto = obtenerTotalPresupuestosItems() / items.getRowCount();

        return promedioPresupuestoPorProyecto;
    }

    public float obtenerPresupuestoTotalProyecto(int idProyecto) {

        BigDecimal resultado = BigDecimal.ZERO;

        FacesContext context = FacesContext.getCurrentInstance();
        PresupuestoController p = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

        resultado = p.obtenerTotal(idProyecto);


        return resultado.floatValue();
    }

    public BigDecimal obtenerPresupuestoTotalCurrent() {

        BigDecimal resultado = BigDecimal.ZERO;

        FacesContext context = FacesContext.getCurrentInstance();
        PresupuestoController p = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

        resultado = p.obtenerTotal(current.getId());

        System.out.println("ProyectoController >> obtenerPresupuestoTotalCurrent: " + resultado.toString());

        return resultado;

    }

    public float obtenerTotalPresupuestosItems() {

        //BigDecimal resultado = BigDecimal.ZERO;
        float resultado = 0;
        Iterator i = items.iterator();

        FacesContext context = FacesContext.getCurrentInstance();
        PresupuestoController p = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();

            BigDecimal tmp = p.obtenerTotal(proyecto.getId());
            float tmpF = tmp.floatValue();
            resultado += tmpF;

//            System.out.println("obtenerTotalPresupuestosItems() >> tmp: " + tmp.toString());
//            System.out.println(resultado);
            //resultado.add(tmp);
        }

//        System.out.println("obtenerTotalPresupuestosItems() >> " + resultado);
        return resultado;
    }

    public float obtenerTotalPresupuestosFiltrados() {

        //BigDecimal resultado = BigDecimal.ZERO;
        float resultado = 0;
        //Iterator i = items.iterator();

        //Iterator i = proyectosFiltrados.iterator();
        ListIterator i = proyectosFiltrados.listIterator();

        FacesContext context = FacesContext.getCurrentInstance();
        PresupuestoController p = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();

            BigDecimal tmp = p.obtenerTotal(proyecto.getId());
            float tmpF = tmp.floatValue();
            resultado += tmpF;

//            System.out.println("obtenerTotalPresupuestosItems() >> tmp: " + tmp.toString());
//            System.out.println(resultado);
            //resultado.add(tmp);
        }

//        System.out.println("obtenerTotalPresupuestosItems() >> " + resultado);
        return resultado;
    }

    public void resetearFiltroEntreFechas() {
        filtroFechaInicio = null;
        filtroFechaFin = null;

    }

    public void filterListener(FilterEvent filterEvent) {
        //List<Proyecto> lista = (List<Proyecto>)filterEvent.getData();

        totalPresupuestosProyectos = obtenerTotalPresupuestosFiltrados();
    }

    public void armarGraficoLineas() {
        modeloGraficoLineas = new CartesianChartModel();

        ChartSeries ideasProyecto = new ChartSeries();
        ideasProyecto.setLabel("Presupuestos Ideas Proyecto");

        Iterator i = items.iterator();

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();
            DateFormat formateador = DateFormat.getDateInstance(DateFormat.SHORT);
            String fechaProyecto = formateador.format(proyecto.getFecha());

            ideasProyecto.set(proyecto.getId(), obtenerPresupuestoTotalProyecto(proyecto.getId()));
        }

        modeloGraficoLineas.addSeries(ideasProyecto);
    }

    public void armarGraficoLineasAcumulado() {

        float acumulado = 0;
        modeloGraficoLineasAcumulado = new CartesianChartModel();

        ChartSeries ideasProyecto = new ChartSeries();
        ideasProyecto.setLabel("Presupuestos Acumulados de Ideas-Proyecto");

        Iterator i = items.iterator();

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();
            DateFormat formateador = DateFormat.getDateInstance(DateFormat.SHORT);
            String fechaProyecto = formateador.format(proyecto.getFecha());

            acumulado = acumulado + obtenerPresupuestoTotalProyecto(proyecto.getId());
            System.out.println("ACUMULADO (" + proyecto.getId().toString() + ") >> " + acumulado);
            ideasProyecto.set(proyecto.getId(), acumulado);
        }

        modeloGraficoLineasAcumulado.addSeries(ideasProyecto);
    }

    public void armarGraficoTortas() {
        modeloGraficoTorta = new PieChartModel();

        Iterator i = items.iterator();

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();

            modeloGraficoTorta.set(proyecto.getNombre(), obtenerPresupuestoTotalProyecto(proyecto.getId()));
        }

    }

    public void armarGraficoDonut() {

        modeloGraficoDonut = new DonutChartModel();
        Map<String, Number> circulo1 = new LinkedHashMap<String, Number>();

        Iterator i = items.iterator();

        while (i.hasNext()) {

            Proyecto proyecto = (Proyecto) i.next();

            //modeloGraficoDonut.set(proyecto.getNombre(), obtenerPresupuestoTotalProyecto(proyecto.getId()).floatValue());
            circulo1.put(proyecto.getNombre(), obtenerPresupuestoTotalProyecto(proyecto.getId()));

        }

        modeloGraficoDonut.addCircle(circulo1);

    }

    /**
     * *********************************************************
     */
    public void reiniciarValoresEvaluacion(){
       
         FacesContext context = FacesContext.getCurrentInstance();
            EvaluacionPreguntaController evaluacionpregunta= (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
            EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);
             evaluacion.setCurrent(null);
                evaluacionpregunta.setEvaluaciones(null);
                evaluacionpregunta.setCurrent(null);
                
                current=null;
                
    }
    
    public String reiniciarValoresSolicitud(){
          FacesContext contextc = FacesContext.getCurrentInstance();
          ConvocatoriaController convocatoria= (ConvocatoriaController) contextc.getApplication().evaluateExpressionGet(contextc, "#{convocatoriaController}", ConvocatoriaController.class);
           FacesContext context = FacesContext.getCurrentInstance();
            AgenteController agente= (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
            FacesContext context3 = FacesContext.getCurrentInstance();
            PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
            FacesContext context2 = FacesContext.getCurrentInstance();
            ArchivoproyectoController ap= (ArchivoproyectoController)context2.getApplication().evaluateExpressionGet(context2, "#{archivoproyectoController}", ArchivoproyectoController.class);
              FacesContext contextAV = FacesContext.getCurrentInstance();
                AgenteViewController agenteViewController = (AgenteViewController) contextAV.getApplication().evaluateExpressionGet(contextAV, "#{agenteViewController}", AgenteViewController.class);

          pr=null;
          convocatoria.setSelected(null);
          convocatoria.setTablafiltrada(null);
          
          current=null;
          ap.setCollectorArchivoProyecto(null);
          ap.setSelected(null);
          agenteViewController.setAgentes(null);
          agenteViewController.setSelected(null);
          agenteViewController.setAgente1(null);
          agenteViewController.setCollectoragentes(null);
          return null;
    } 
    
    public void reiniciarValoresProyecto(){
          FacesContext contextc = FacesContext.getCurrentInstance();
          ConvocatoriaController convocatoria= (ConvocatoriaController) contextc.getApplication().evaluateExpressionGet(contextc, "#{convocatoriaController}", ConvocatoriaController.class);
           FacesContext context = FacesContext.getCurrentInstance();
            AgenteController agente= (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
            FacesContext context3 = FacesContext.getCurrentInstance();
            PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
            FacesContext context2 = FacesContext.getCurrentInstance();
            ArchivoproyectoController ap= (ArchivoproyectoController)context2.getApplication().evaluateExpressionGet(context2, "#{archivoproyectoController}", ArchivoproyectoController.class);
              FacesContext contextAV = FacesContext.getCurrentInstance();
                AgenteViewController agenteViewController = (AgenteViewController) contextAV.getApplication().evaluateExpressionGet(contextAV, "#{agenteViewController}", AgenteViewController.class);

          pr=null;
          convocatoria.setSelected(null);
          convocatoria.setTablafiltrada(null);
          
          current=null;
          ap.setCollectorArchivoProyecto(null);
          ap.setSelected(null);
          agenteViewController.setAgentes(null);
          agenteViewController.setSelected(null);
          agenteViewController.setAgente1(null);
          agenteViewController.setCollectoragentes(null);
    }
   
    public String soloEditar() {
        boolean todobien=false;
        try{
                           FacesContext context = FacesContext.getCurrentInstance();
                    AgenteController agente= (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
                    FacesContext context3 = FacesContext.getCurrentInstance();
                    PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
                    FacesContext context2 = FacesContext.getCurrentInstance();
                    ArchivoproyectoController ap= (ArchivoproyectoController)context2.getApplication().evaluateExpressionGet(context2, "#{archivoproyectoController}", ArchivoproyectoController.class);
                    
                    Estadoproyecto ep= new Estadoproyecto();
                    ep.setId(1);
                    getFacade().edit(current);
                    
                    PresupuestoRubro prerub;
                    Iterator i= pr.getPresupuestosrubros().iterator();
                    
                    while(i.hasNext()){
                        prfacade.edit((PresupuestoRubro)i.next());
                    }
                   
                   i=ap.getCollectorArchivoProyecto().iterator();
                   
                    int encontrado=0;
                    for(Archivoproyecto ap1:ejbarchivoproyecto.findporProyecto(current.getId())){
                        for(Archivoproyecto ap2: ap.getCollectorArchivoProyecto()){
                            if(ap2.getId()==ap1.getId()){
                                encontrado+=1;
                            }
                        }
                        if(encontrado==0){
                            ejbarchivoproyecto.remove(ap1);
                        }
                        encontrado = 0;
                    }

                   while(i.hasNext()){
                       Archivoproyecto archivoproyecto = new Archivoproyecto();
                        archivoproyecto=((Archivoproyecto)i.next());
                        if(archivoproyecto.getProyectoid()==null){
                            
                       
                            archivoproyecto.setId(null);
                            archivoproyecto.setProyectoid(current);

                                ejbarchivoproyecto.edit(archivoproyecto);

                        }
                    }
                   
                   // EQUIPO DE TRABAJO ***********************
                FacesContext contextpa = FacesContext.getCurrentInstance();
                ProyectoAgenteController proyectoagentecontroller = (ProyectoAgenteController) contextpa.getApplication().evaluateExpressionGet(contextpa, "#{proyectoAgenteController}", ProyectoAgenteController.class);
              
              encontrado = 0;
              for(ProyectoAgente pa2 : ejbproyectoagente.buscarEquipoTrabajo(current.getId())){
                  for(ProyectoAgente pa1 : proyectoagentecontroller.getEquipotrabajo()){
                      if(pa1.equals(pa2)){
                          encontrado+=1;
                      }
                  }
                  ejbproyectoagente.remove(pa2);
                  encontrado = 0;
              }
              
                for(ProyectoAgente pa : proyectoagentecontroller.getEquipotrabajo()){
                    if((pa.getProyectoAgentePK()==null)){
                        pa.setProyectoAgentePK(new ProyectoAgentePK());
                        pa.getProyectoAgentePK().setProyectoid(current.getId());
                        pa.getProyectoAgentePK().setAgenteid(pa.getAgente().getId());

                        pa.setAgente(pa.getAgente());
                        pa.setProyecto(current);
                         pa.setFuncionproyecto("solicitud");

                    ejbproyectoagente.create(pa);
                    }else{
                        pa.setFuncionproyecto("solicitud");
                    

                    ejbproyectoagente.edit(pa);
                    }
                   
    
                }

                // FIN - EQUIPO DE TRABAJO ******************

                   
                  
                  


                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta idea proyecto Creada!", "Su Solicitud a Proyecto fue creado!!!");  
                  

                    EnviarMail enviarmail = new EnviarMail();
                   
                    //enviarmail.enviarMailIngresoIdeaProyecto(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName(),current.getAgenteid().getEmail() , habilitarcomitente);
                    

                    
                   //  FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
                // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
                    todobien=true;
                System.out.println("Solicitud editada");
                    //current = new Proyecto();
                 
           
        }catch(Exception e){
             FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("No se pudo editar la Solicitud del Proyecto "+e);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
         System.out.println("Error al editar la Solicitud  = "+e);
            return null;
        }
        if(todobien){
            RequestContext.getCurrentInstance().execute("dfinal.show()"); 
        }
         return null;
        
    }
    
    public String prepareEditEtapa() {
        current = (Proyecto) getItems().getRowData();
          
           FacesContext context = FacesContext.getCurrentInstance();
           EtapaController etapacontroller = (EtapaController)context.getApplication().evaluateExpressionGet(context, "#{etapaController}", EtapaController.class);
           
        etapacontroller.setEtapas(this.ejbetapa.findByProyecto(current));
        //etapacontroller.agregaralListadoEtapas();
       etapacontroller.prepareEditarListadoEtapas();
        return "Edit";
    }
    
    public long buscarProyectosEvaluar(int estado){
       return  this.ejbFacade.buscarProyectosEvaluar(estado);
    }
    
    public void buscarProyectosAgenteFalse(int agenteid){
        recreateModel();
       
        items=new ListDataModel(getFacade().buscarProyectosAgente(false,agenteid));
    }
    
    public String enviarEditarEvaluarProyecto(){
        boolean todobien=false;
        try {
         FacesContext context = FacesContext.getCurrentInstance();
        EtapaController etapacontroller = (EtapaController) context.getApplication().evaluateExpressionGet(context, "#{etapaController}", EtapaController.class);
        
        PresupuestoRubroitemController presupuestorubroitemcontroller = (PresupuestoRubroitemController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoRubroitemController}", PresupuestoRubroitemController.class);
        PresupuestoController presupuestocontroller = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);
      
        //Eliminacion etapas
       boolean encontroetapa = true;
       boolean encontrotarea = true;
       boolean encontrotareaagente = true;
       
       
       //eliminar todo
      for(Etapa e: ejbetapa.findByProyecto(current)){
              ejbetapa.remove(e);
      }
      
      //Eliminacion de presupuestosrubrositem
      for(PresupuestoRubroitem pri: ejbpresupuestorubroitem.findByPresupuesto(presupuestocontroller.getSelected())){
          if(presupuestorubroitemcontroller.getPresupuestosrubrositems().contains(pri)){
              
          }else{
                  ejbpresupuestorubroitem.remove(pri);
          }
          
      }
            
      for (Etapa e : etapacontroller.getEtapas()) {
           e.setId(null);
           e.setProyectoid(current);
           
            for(Tarea t:e.getTareaList()){
               t.setId(null);
               t.setFechacreacion(new Date());
               t.setFechamodificacion(new Date());
                
//                int contador2=0;
//                for(TareaAgente ta:t.getTareaAgenteList()){
//                    ta.setTareaid(null);
//                    t.getTareaAgenteList().set(contador2, ta);
//                    contador2++;
//                    
//               }
                
          }
            List<Tarea> OldTarea = e.getTareaList();
           e.setTareaList(null);
           ejbetapa.createWithPersist(e);
           if(OldTarea!=null){
                for(Tarea t : OldTarea ){
                    t.setEtapaid(e);
                    List<TareaAgente> OldTareaAgente = t.getTareaAgenteList();
                    t.setTareaAgenteList(null);
                    this.ejbtarea.createWithPersist(t);
                    if(OldTareaAgente != null){
                         for (TareaAgente ta : OldTareaAgente){
                             ta.setTareaid(t);
                           ejbtareaagente.create(ta);
                         }
                    }
                }
           }
        }
        
        
        for(PresupuestoRubroitem pri : presupuestorubroitemcontroller.getPresupuestosrubrositems()){
            
           
             pri.setPresupuesto(presupuestocontroller.getSelected());
            
            ejbpresupuestorubroitem.create(pri);
           
        }
         EnviarMail enviarmail = new EnviarMail();
        // enviarmail.enviarMailEvaluarPlanEPres(current.getAgenteid() );

               todobien=true;       
        
           // RequestContext.getCurrentInstance().execute("dfinal.show()");
           // FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
            // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
        } catch (Exception e) {
             FacesMessage message = new FacesMessage();
               message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("No se pudo Editar el Plan y equipo del Proyecto "+e);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
         System.out.println("Error al Editar Plan y equipo de trabajo  = "+e);
            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, e);
        }
         if(todobien){
            RequestContext.getCurrentInstance().execute("dfinal.show()"); 
        }
                
        return null;
    }
    
        // **************************  REPORTES  **********************************
    public void pdfIdeaProyecto() throws JRException, IOException {

        // Obtengo la ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/solicitud.jasper");

        // Fuente de datos del reporte
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // Fuente de datos del subreporte (detalle del presupuesto)
        Presupuesto presupuesto = this.ejbFacadep.findporProyecto(this.getSelected().getId());
        JRDataSource detallePresupuesto = new JRBeanCollectionDataSource(presupuesto.getPresupuestoRubroList());

        // Fuente de datos para el equipo de trabajo
        
        List<Agente> listaAgentes = new ArrayList<Agente>() ;
        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
        
        for(ProyectoAgente pa : listaProyectoAgente){
            
            listaAgentes.add(pa.getAgente());
        }
        
        JRDataSource equipoTrabajo = new JRBeanCollectionDataSource(listaAgentes);
        
        //Agregando los parametros
        Hashtable<String, Object> parametros = new Hashtable<String, Object>();
        parametros.put("idProyecto", this.getSelected().getId());
        parametros.put("presupuesto", detallePresupuesto);
        parametros.put("equipoTrabajo", equipoTrabajo);

        // Llenamos el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=idea-proyecto.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void pdfListaIdeasProyecto() throws JRException, IOException {

        // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/listaSolicitudes.jasper");

        // Fuente de datos del reporte
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());

        // Llenamos el reporte con la fuente de datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, null, beanCollectionDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=listaIdeasProyecto.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void imprimirIdeaProyecto() throws JRException, IOException {

        // Obtengo la ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/solicitud.jasper");

        // Fuente de datos del reporte
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // Fuente de datos del subreporte (detalle del presupuesto)
        Presupuesto presupuesto = this.ejbFacadep.findporProyecto(this.getSelected().getId());
        JRDataSource detallePresupuesto = new JRBeanCollectionDataSource(presupuesto.getPresupuestoRubroList());

        // Fuente de datos para el equipo de trabajo
        
        List<Agente> listaAgentes = new ArrayList<Agente>() ;
        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
        
        for(ProyectoAgente pa : listaProyectoAgente){
            
            listaAgentes.add(pa.getAgente());
        }
        
        JRDataSource equipoTrabajo = new JRBeanCollectionDataSource(listaAgentes);
        
        //Agregando los parametros
        Hashtable<String, Object> parametros = new Hashtable<String, Object>();
        parametros.put("idProyecto", this.getSelected().getId());
        parametros.put("presupuesto", detallePresupuesto);
        parametros.put("equipoTrabajo", equipoTrabajo);

        // Llenamos el reporte
        //JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);
        String archivo = JasperFillManager.fillReportToFile(rutaJasper, parametros, beanArrayDataSource);

        if (archivo != null) {
            JasperPrintManager.printReport(
                    archivo, true);
        }
    }

    public void imprimirListaIdeasProyecto() throws JRException, IOException {

        // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/listaSolicitudes.jasper");

        // Fuente de datos del reporte
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());

        // Llenamos el reporte con la fuente de datos
        String archivo = JasperFillManager.fillReportToFile(rutaJasper, null, beanCollectionDataSource);

        if (archivo != null) {
            JasperPrintManager.printReport(
                    archivo, true);
        }
    }
    
    // REPORTE EVALUACION IDEA PROYECTO
    
    public void pdfEvaluacionIdeaProyecto() throws JRException, IOException {

        // Obtengo la ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/evaluacion.jasper");

        // Fuente de datos del reporte
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // FUENTE DE DATOS PARA EL SUBREPORTE (DETALLE DEL PRESUPUESTO)
        // Presupuesto presupuesto = this.ejbFacadep.findporProyecto(this.getSelected().getId());
        // JRDataSource detallePresupuesto = new JRBeanCollectionDataSource(presupuesto.getPresupuestoRubroList());
        
        // FUENTE DE DATOS PARA EL EQUIPO DE TRABAJO
//        List<Agente> listaAgentes = new ArrayList<Agente>() ;
//        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
//        
//        for(ProyectoAgente pa : listaProyectoAgente){
//            System.out.println(pa.getAgente().getApellido() + ", " + pa.getAgente().getNombres());
//            listaAgentes.add(pa.getAgente());
//        }
//        
//        JRDataSource equipoTrabajo = new JRBeanCollectionDataSource(listaAgentes);
        
        // FUENTE DE DATOS PARA LA EVALUACION
        Evaluacion e = this.ejbevaluacion.obtenerEvaluacionPorProyecto(current.getId());
        
        JRBeanArrayDataSource evaluacion = new JRBeanArrayDataSource(new Evaluacion[]{e});
        
//        for(EvaluacionPregunta ep : e.getEvaluacionPreguntaList()){
//            System.out.println("Pregunta: " + ep.getPregunta().getPregunta());
//        }
        
        JRDataSource preguntas = new JRBeanCollectionDataSource(e.getEvaluacionPreguntaList());
        
        String observacion = e.getObservacion();
        
        //Agregando los parametros
        Hashtable<String, Object> parametros = new Hashtable<String, Object>();
        parametros.put("idProyecto", this.getSelected().getId());
        // parametros.put("presupuesto", detallePresupuesto);
        // parametros.put("equipoTrabajo", equipoTrabajo);
        parametros.put("evaluacion", evaluacion);
        parametros.put("preguntas", preguntas);
        parametros.put("observacion", observacion);

        // Llenamos el reporte
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=evaluacion.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
 // REPORTE GANTT
    public void pdfEtapas() throws JRException, IOException {

        // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/proyecto.jasper");

        // FUENTES DE DATOS
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // TAREAS
        // Obtenemos las tareas de un proyecto
        List<Etapa> listaEtapas = this.ejbetapa.buscarEtapasProyecto(current.getId());
        List<Tarea> listaTareas = new ArrayList<Tarea>();

        for (Etapa e : listaEtapas) {
            for (Tarea t : this.ejbtarea.buscarTareasEtapa(e.getId())) {
                listaTareas.add(t);
            }
        }

        // Ordenación por fecha de inicio
        Collections.sort(listaTareas, new Comparator<Tarea>() {
            @Override
            public int compare(Tarea tarea1, Tarea tarea2) {
                return tarea1.getFechainicio().compareTo(tarea2.getFechainicio());
            }
        });

        JRBeanCollectionDataSource tareas = new JRBeanCollectionDataSource(listaTareas);

        // PRESUPUESTO
        List<PresupuestoRubroitem> listaPresupuestoItems = this.ejbpresupuestorubroitem.findByPresupuesto(this.ejbFacadep.findporProyecto(current.getId()));

        for (PresupuestoRubroitem pri : listaPresupuestoItems) {
            System.out.println("PRI" + pri.getDescripcion() + " - " + pri.getCantidad().toString() + " - " + pri.getTotal().toString());
        }

        JRBeanCollectionDataSource presupuesto = new JRBeanCollectionDataSource(listaPresupuestoItems);

        //Agregando los parametros
        Hashtable<String, Object> parametros = new Hashtable<String, Object>();
        parametros.put("idProyecto", this.getSelected().getId());
        parametros.put("presupuesto", presupuesto);
        parametros.put("tareas", tareas);

        // Llenamos el reporte con la fuente de datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=gantt.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();

    }
 
    
    public void pdfTareas() throws JRException, IOException {

       // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/secure/reportes/ganttTareas.jasper");

        // Fuente de datos del reporte
       
        //Obtenemos las tareas de un proyecto
        List<Etapa> listaEtapas = this.ejbetapa.buscarEtapasProyecto(current.getId());
        List<Tarea> listaTareas = new ArrayList<Tarea>();
        
        for(Etapa e : listaEtapas){
            for(Tarea t : this.ejbtarea.buscarTareasEtapa(e.getId())){
                listaTareas.add(t);
            }
        }
        
        // Debug
        for(Tarea t : listaTareas){
            System.out.println("ID: " + t.getId() + " - Nombre: " + t.getTarea() + " - Estado: " + t.getEstado() + " - Etapa: " + t.getEtapaid().getEtapa() );
        }
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listaTareas);

        // Llenamos el reporte con la fuente de datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, null, beanCollectionDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=gantt.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
        
    }
    
    public List<Agente> obtenerEquipoTrabajo(){
        
        List<Agente> listaAgentes = new ArrayList<Agente>() ;
        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
        
        for(ProyectoAgente pa : listaProyectoAgente){
            //System.out.println(pa.getAgente().getApellido() + ", " + pa.getAgente().getNombres());
            listaAgentes.add(pa.getAgente());
        }
        return listaAgentes;
    }
    
    public List<Archivoproyecto> obtenerArchivosProyecto(){
        
//        List<Archivoproyecto> listaArchivos = this.ejbFacadeap.buscarArchivosProyecto(current.getId());
//        
//        for(Archivoproyecto ap : listaArchivos){
//            System.out.println(ap.getNombre());
//        }
        
        return this.ejbFacadeap.buscarArchivosProyecto(current.getId());
    }

    public String prepareCreateEvaluacion() {
        current = (Proyecto) getItems().getRowData();
       // selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Create";
    }

    
}
