package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.controller.EnviarMail;
import ar.edu.undec.sisgap.model.Proyecto;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ProyectoFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.model.Estadoproyecto;
import ar.edu.undec.sisgap.model.Evaluacion;
import ar.edu.undec.sisgap.model.EvaluacionPregunta;
import ar.edu.undec.sisgap.model.EvaluacionPreguntaPK;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.PresupuestoRubro;
import ar.edu.undec.sisgap.model.Tarea;
import ar.edu.undec.sisgap.model.TareaAgente;
import ar.edu.undec.sisgap.model.TareaAgentePK;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
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
    @EJB
    private ar.edu.undec.sisgap.controller.ArchivoproyectoFacade ejbFacadeap;
    @EJB
    private ar.edu.undec.sisgap.controller.EvaluacionFacade ejbevaluacion;
    @EJB
    private ar.edu.undec.sisgap.controller.EvaluacionPreguntaFacade ejbevaluacionproyecto;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaFacade ejbtarea;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaAgenteFacade ejbtareaagente;
    
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
                    System.out.println("mmmmmmmmmmmmmmmmmmooooooooooooooo"+convocatoria.getSelected().getId());
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
                     //  System.out.println("--------------------------ttttt-------------------------"+p.getId() );
                    PresupuestoRubro prerub;
                    Iterator i= pr.getPresupuestosrubros().iterator();
                    while(i.hasNext()){
                        prerub=((PresupuestoRubro)i.next());
                        prerub.setPresupuesto(p);
                        pr.soloCrear(prerub);
                    }
                    //p.getSelected().setPresupuestoRubroList(pr.getPresupuestosrubros());

                   // 
                   pr.setPresupuestosrubros();
                    System.out.println("--------------------final1-------------------------------"+ap.getCollectorArchivoProyecto().size() );

                   i=ap.getCollectorArchivoProyecto().iterator();
                    System.out.println("--------------------final12-------------------------------" );

                   while(i.hasNext()){
                       Archivoproyecto archivoproyecto=new Archivoproyecto();
                        archivoproyecto=((Archivoproyecto)i.next());
                        archivoproyecto.setId(null);
                        archivoproyecto.setProyectoid(current);
                        ap.soloCrear(archivoproyecto);
                    }

                    p=null;
                    pr=null;
                    //current=null;
                    ap=null;
                   System.out.println("--------------------final2-------------------------------" );


                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta idea proyecto Creada!", "Su Solicitud a Proyecto fue creado!!!");  
                    EnviarMail enviarmail = new EnviarMail();
                 //   enviarmail.enviarMailIngresoIdeaProyecto(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName(),current.getAgenteid().getEmail() , habilitarcomitente);

                     RequestContext.getCurrentInstance().execute("dfinal.show()"); 
                   //  FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
                // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
                    
                System.out.println("iiiiiiiiiiiiiiiiiiiii");
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
         System.out.println("llll"+e);
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
       current.setDocumentacionnombre(event.getFile().getFileName());
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
        
        System.out.println("---------------------------------------is-----"+current.getConvocatoriaid());
        if(current.getConvocatoriaid().getId()>0){
            this.verarchivo = true;
        }else{
           this.verarchivo = false; 
        }
        System.out.println("---------------------------------------is-----"+verarchivo+current.getConvocatoriaid().getId());
        
    }

    public boolean getVerarchivo() {
        return verarchivo;
       // System.out.println("---------------------------------------set-----"+verarchivo);
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
        System.out.println("actalizando tabla000000000000000000000000000");
        RequestContext.getCurrentInstance().update("tpresupuesto");
    }
    
     public String soloEditar() {
        try{
            //Capturo el managed bean en el contexto
           
           FacesContext context = FacesContext.getCurrentInstance();
           PresupuestoController p= (PresupuestoController)context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);
           
            current.setFecha(new Date());
            getFacade().edit(current);
            
             PresupuestoRubro prerub;
            Iterator i= p.getSelected().getPresupuestoRubroList().iterator();
            while(i.hasNext()){
                prerub=((PresupuestoRubro)i.next());
               // p.soloEditar(prerub);
            }
         
            
             FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
             System.out.println("iiiiiiiiiiiiiiiiiiiii");
           
         return "ViewSolicitud";
        }catch(Exception e){
            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo crear la Solicitud del Proyecto "));
        
            return null;
        }
    }

    public String getHabilitarcomitente() {
        return habilitarcomitente;
    }

    public void setHabilitarcomitente(String habilitarcomitente) {
        this.habilitarcomitente = habilitarcomitente;
    }
    
    public void evaluarIdea(){
            FacesContext context = FacesContext.getCurrentInstance();
            EvaluacionPreguntaController evaluacionpregunta= (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
            EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);;
            AgenteController agente = (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);;

        try{
            
            evaluacion.getSelected().setFecha(new Date());
            evaluacion.getSelected().setProyectoid(current);
            evaluacion.getSelected().setUsuarioid(agente.getSelected().getUsuarioid());
            ejbevaluacion.createWithPersist(evaluacion.getSelected());
            proyectoViejo = current; 
            for(EvaluacionPregunta eval:evaluacionpregunta.getEvaluaciones()){
                 eval.setEvaluacionPreguntaPK(new EvaluacionPreguntaPK());
               eval.getEvaluacionPreguntaPK().setEvaluacionid(evaluacion.getSelected().getId());
              eval.getEvaluacionPreguntaPK().setPreguntaid(eval.getPregunta().getId());

              ejbevaluacionproyecto.create(eval);
             }
                
             this.ejbFacade.edit(current);
             
             if(new EnviarMail().enviarMailEvaluacionIdeaProyecto(current.getAgenteid(), current.getObservaciones())){
                RequestContext.getCurrentInstance().execute("dfinal.show()"); 
             }else{
                 FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo evaluar del Proyecto.")); 
                this.ejbFacade.edit(proyectoViejo);
                ejbevaluacion.remove(evaluacion.getSelected());
             }
        }catch(Exception e){
            ejbevaluacion.remove(evaluacion.getSelected());
            this.ejbFacade.edit(proyectoViejo);
             FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ","No se pudo evaluar del Proyecto "));
        
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
"Por medio del presente informamos a Ud. que la Idea-Proyecto Nº "+current.getId()+" de la cual Ud. Es responsable, ha sido "+current.getEstadoproyectoid().getEstado()+" según el siguiente detalle:\n" +
"Observaciones:\n";
      for(EvaluacionPregunta eval:evaluacionpregunta.getEvaluaciones()){
          
          if(eval.getRating()!=null ){
                if(eval.getRating().intValue()<3 ){
                        calificacionpregunta="REGULAR";
                   } 
                 if(eval.getRating().intValue()==3){ 
                     calificacionpregunta="CORRECTO";
                 }    
                 if(eval.getRating()==4){ 
                     calificacionpregunta="MUY BUENO";
                 }    
                 if(eval.getRating()==5){ 
                     calificacionpregunta="EXCELENTE";
                 }
          }
          if(eval.getObservacion()!=null)
              observacionfinal+= " - "+eval.getObservacion()+"\n";
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
        System.out.println("bienn");
    }
    
    public void buscarProyectosAgenteTrue(int agenteid){
        recreateModel();
       
        items=new ListDataModel(getFacade().buscarProyectosAgente(true,agenteid));
    }
    
    public String evaluarProyecto(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        TareaController tareacontrol= (TareaController) context.getApplication().evaluateExpressionGet(context, "#{tareaController}", TareaController.class);
        AgenteViewController agenteviewcontroller = (AgenteViewController) context.getApplication().evaluateExpressionGet(context, "#{agenteViewController}", AgenteViewController.class);
        for (Tarea t : tareacontrol.getTareasdeproyecto()) {
            ejbtarea.createWithPersist(t);
            for (Agente a : agenteviewcontroller.getCollectoragentes() ){
                TareaAgente ta = new TareaAgente();
                 ta.setTareaAgentePK(new TareaAgentePK());
               ta.getTareaAgentePK().setAgenteid(a.getId());
              ta.getTareaAgentePK().setTareaid(t.getId());
                ta.setAgente(a);
                ta.setTarea(t);
                ejbtareaagente.createWithPersist(ta);
            }
        }
        return null;
    }
    
      public String prepareCreatePlanEquipo() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "CreatePlanEquipo";
    }
    
}
