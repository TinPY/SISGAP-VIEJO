package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.controller.EnviarMail;
import ar.edu.undec.sisgap.model.Proyecto;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ProyectoFacade;
import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.model.Estadoproyecto;
import ar.edu.undec.sisgap.model.Etapa;
import ar.edu.undec.sisgap.model.EvaluacionPregunta;
import ar.edu.undec.sisgap.model.EvaluacionPreguntaPK;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.PresupuestoRubro;
import ar.edu.undec.sisgap.model.PresupuestoRubroitem;
import ar.edu.undec.sisgap.model.PresupuestoRubroitemPK;
import ar.edu.undec.sisgap.model.ProyectoAgente;
import ar.edu.undec.sisgap.model.ProyectoAgentePK;
import ar.edu.undec.sisgap.model.Tarea;
import ar.edu.undec.sisgap.model.TareaAgente;
import ar.edu.undec.sisgap.model.TareaAgentePK;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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

    private PaginationHelper pagination;
    private int selectedItemIndex;
    private byte[] imagen = null;
    private StreamedContent file;
    private boolean columnorganismo = false;
    private boolean columncomitente = false;
    private boolean columnuniversidad = false;
    private boolean verarchivo = false;
    private boolean iseditableSolicitud = false;
    private String habilitarcomitente = "0";
    private String observacionfinal;
    private Proyecto proyectoViejo;

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

//        System.out.println("ProyectoController >> obtenerPresupuestoTotalProyecto: " + resultado.toString());
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
        try {
            //Capturo el managed bean en el contexto
            FacesContext contextc = FacesContext.getCurrentInstance();
            ConvocatoriaController convocatoria = (ConvocatoriaController) contextc.getApplication().evaluateExpressionGet(contextc, "#{convocatoriaController}", ConvocatoriaController.class);

            if (convocatoria.getSelected() != null) {
                FacesContext context = FacesContext.getCurrentInstance();
                AgenteController agente = (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);
                FacesContext context3 = FacesContext.getCurrentInstance();
                PresupuestoRubroController pr = (PresupuestoRubroController) context3.getApplication().evaluateExpressionGet(context3, "#{presupuestoRubroController}", PresupuestoRubroController.class);
                FacesContext context2 = FacesContext.getCurrentInstance();
                ArchivoproyectoController ap = (ArchivoproyectoController) context2.getApplication().evaluateExpressionGet(context2, "#{archivoproyectoController}", ArchivoproyectoController.class);

                current.setAgenteid(agente.getSelected());

                Estadoproyecto ep = new Estadoproyecto();
                ep.setId(1);
                current.setEstadoproyectoid(ep);
                current.setFecha(new Date());
                System.out.println("mmmmmmmmmmmmmmmmmmooooooooooooooo" + convocatoria.getSelected().getId());
                if (convocatoria.getSelected().getId() == null) {
                    current.setConvocatoriaid(null);
                } else {
                    current.setConvocatoriaid(convocatoria.getSelected());
                }
                getFacade().createWithPersist(current);
                Presupuesto p = new Presupuesto();
                p.setFecha(new Date());
                p.setProyectoid(current);
                p.setEstado('P');

                ejbFacadep.createWithPersist(p);
                //  System.out.println("--------------------------ttttt-------------------------"+p.getId() );
                PresupuestoRubro prerub;
                Iterator i = pr.getPresupuestosrubros().iterator();
                while (i.hasNext()) {
                    prerub = ((PresupuestoRubro) i.next());
                    prerub.setPresupuesto(p);
                    pr.soloCrear(prerub);
                }
                    //p.getSelected().setPresupuestoRubroList(pr.getPresupuestosrubros());

                // 
                pr.setPresupuestosrubros();
                System.out.println("--------------------final1-------------------------------" + ap.getCollectorArchivoProyecto().size());

                i = ap.getCollectorArchivoProyecto().iterator();
                System.out.println("--------------------final2-------------------------------");

                while (i.hasNext()) {
                    Archivoproyecto archivoproyecto = new Archivoproyecto();
                    archivoproyecto = ((Archivoproyecto) i.next());
                    archivoproyecto.setId(null);
                    archivoproyecto.setProyectoid(current);
                    ap.soloCrear(archivoproyecto);
                }

                System.out.println("--------------------final3-------------------------------");

                // EQUIPO DE TRABAJO ***********************
                FacesContext contextAV = FacesContext.getCurrentInstance();
                AgenteViewController agenteViewController = (AgenteViewController) contextAV.getApplication().evaluateExpressionGet(contextAV, "#{agenteViewController}", AgenteViewController.class);

                System.out.println(agenteViewController.toString());

                List<Agente> listaAgentes = agenteViewController.getCollectoragentes();
                System.out.println("(Equipo de Trabajo) listaAgentes - Tamaño: " + String.valueOf(listaAgentes.size()));

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
                    System.out.println("[ProyectoAgente] Proyecto: " + String.valueOf(proyectoAgente.getProyectoAgentePK().getProyectoid()) + " - Agente: " + String.valueOf(proyectoAgente.getProyectoAgentePK().getAgenteid()));
                }

                // FIN - EQUIPO DE TRABAJO ******************
                p = null;
                pr = null;
                //current=null;
                ap = null;
                System.out.println("--------------------final2-------------------------------");

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alta idea proyecto Creada!", "Su Solicitud a Proyecto fue creado!!!");
                EnviarMail enviarmail = new EnviarMail();
                //   enviarmail.enviarMailIngresoIdeaProyecto(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName(),current.getAgenteid().getEmail() , habilitarcomitente);

                RequestContext.getCurrentInstance().execute("dfinal.show()");
                //  FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
                // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));

                System.out.println("iiiiiiiiiiiiiiiiiiiii");
                //current = new Proyecto();

                return null;

                // EQUIPO DE TABAJO
                // proyecto >> etapa >> tarea >> tareaAgente >> agente
//                FacesContext contextE = FacesContext.getCurrentInstance();
//                EtapaController etapacontroller = (EtapaController) contextE.getApplication().evaluateExpressionGet(contextE, "#{etapaController}", EtapaController.class);
//
//                FacesContext contextTA = FacesContext.getCurrentInstance();
//                TareaAgenteController tareaAgenteController = (TareaAgenteController) contextTA.getApplication().evaluateExpressionGet(contextTA, "#{TareaAgenteController}", TareaAgenteController.class);
//
//                List<TareaAgente> listaTareasAgente = tareaAgenteController.getTareasagentes();
//
//                // CREAR ETAPA -> SOLICITUD
//                Etapa etapaSolicitud = new Etapa();
//
//                etapaSolicitud.setId(null);
//                etapaSolicitud.setProyectoid(current);
//                etapaSolicitud.setEstado("solicitud");
//                etapaSolicitud.setEtapa("solicitud");
//
//                //Persistir la etapa para la solicitud
//                ejbetapa.createWithPersist(etapaSolicitud);
//                System.out.println("ID Etapa: " + etapaSolicitud.getId());
//                
//                // Consultar la etapa persistida (ahora con ID)
//                //Etapa etapaSolicitudNueva = ejbetapa.buscarEtapaPorProyectoYEstado("solicitud", current.getId());
//
//                for (TareaAgente tareaAgente : listaTareasAgente) {
//                    // Tarea
//                    Tarea t = tareaAgente.getTarea();
//                    
//                    t.setId(null);
//                    t.setEtapaid(etapaSolicitud);
//                    t.setFechacreacion(new Date());
//                    t.setFechamodificacion(new Date());
//                    
//                    // Persistimos Tarea
//                    ejbtarea.createWithPersist(t);
//                    System.out.println("Id Tarea: " + t.getId());
//                    
//                    // Tarea Agente
//                    tareaAgente.setTareaAgentePK(new TareaAgentePK());
//                    tareaAgente.getTareaAgentePK().setAgenteid(tareaAgente.getAgente().getId());
//                    //tareaAgente.getTareaAgentePK().setTareaid(t.getId());
//                    tareaAgente.getTareaAgentePK().setTareaid(t.getId());
//                    tareaAgente.setAgente(tareaAgente.getAgente());
//                    tareaAgente.setTarea(t);
//                    
//                    // Persistimos TareaAgente
//                    ejbtareaagente.createWithPersist(tareaAgente);
//                }
            } else {
                RequestContext.getCurrentInstance().scrollTo("wconvocatoria");
                FacesMessage message = new FacesMessage();
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                message.setSummary("ERROR");
                message.setDetail("Por favor seleccione una fila en la tabla de Convocatoria");
                FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
                return null;
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            message.setSummary("ERROR");
            message.setDetail("No se pudo crear la Solicitud del Proyecto " + e);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", message);
            System.out.println("llll" + e);
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
        System.out.println("Succesful" + event.getFile().getFileName() + " is uploaded.");
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

    public void buscarTodos() {
        recreateModel();
        items = new ListDataModel(getFacade().findAll());

    }

    public void buscarPorFecha() {
        recreateModel();
        items = new ListDataModel(getFacade().buscarProyectoFecha(filtroFechaInicio));
    }

    public void buscarEntreFechas() {
        recreateModel();
        items = new ListDataModel(getFacade().buscarProyectoEntreFechas(filtroFechaInicio, filtroFechaFin));

        proyectosFiltrados = getFacade().buscarProyectoEntreFechas(filtroFechaInicio, filtroFechaFin);
    }

    public void buscarProyectoAgente(int agenteid) {
        recreateModel();
        items = new ListDataModel(getFacade().buscarProyectoAgente(agenteid));
    }

    // Estados [id = 1 Solicitud]
    public void buscarProyectoEstado(int idEstado) {
        recreateModel();
        items = new ListDataModel(getFacade().buscarProyectoEstado(idEstado));
    }

//    public void buscarProyectoEstado(long estado) {
//        recreateModel();
//        items = new ListDataModel(getFacade().buscarProyectoEstado((int) estado));
//    }
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

        System.out.println("---------------------------------------is-----" + current.getConvocatoriaid());
        if (current.getConvocatoriaid().getId() > 0) {
            this.verarchivo = true;
        } else {
            this.verarchivo = false;
        }
        System.out.println("---------------------------------------is-----" + verarchivo + current.getConvocatoriaid().getId());

    }

    public boolean getVerarchivo() {
        return verarchivo;
        // System.out.println("---------------------------------------set-----"+verarchivo);
    }

    public void cambioColPresupuesto() {
        if (current.getTipofinanciamientoid().getId() < 3) {
            columnorganismo = true;
        } else {
            columnorganismo = false;
        }
    }

    public boolean isIseditableSolicitud() {
        if (current.getEstadoproyectoid().getId() == 1) {
            iseditableSolicitud = true;
        } else {
            iseditableSolicitud = false;
        }

        return iseditableSolicitud;
    }

    public void setIseditableSolicitud(boolean iseditableSolicitud) {
        this.iseditableSolicitud = iseditableSolicitud;
    }

    public void actualizarTabla() {
        System.out.println("actalizando tabla000000000000000000000000000");
        RequestContext.getCurrentInstance().update("tpresupuesto");
    }

    public String soloEditar() {
        try {
            //Capturo el managed bean en el contexto

            FacesContext context = FacesContext.getCurrentInstance();
            PresupuestoController p = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

            current.setFecha(new Date());
            getFacade().edit(current);

            PresupuestoRubro prerub;
            Iterator i = p.getSelected().getPresupuestoRubroList().iterator();
            while (i.hasNext()) {
                prerub = ((PresupuestoRubro) i.next());
                // p.soloEditar(prerub);
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/secure/solicitud/View.xhtml");
            System.out.println("iiiiiiiiiiiiiiiiiiiii");

            return "ViewSolicitud";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ", "No se pudo crear la Solicitud del Proyecto "));

            return null;
        }
    }

    public String getHabilitarcomitente() {
        return habilitarcomitente;
    }

    public void setHabilitarcomitente(String habilitarcomitente) {
        this.habilitarcomitente = habilitarcomitente;
    }

    public void evaluarIdea() {
        FacesContext context = FacesContext.getCurrentInstance();
        EvaluacionPreguntaController evaluacionpregunta = (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
        EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);;
        AgenteController agente = (AgenteController) context.getApplication().evaluateExpressionGet(context, "#{agenteController}", AgenteController.class);;

        try {

            evaluacion.getSelected().setFecha(new Date());
            evaluacion.getSelected().setProyectoid(current);
            evaluacion.getSelected().setUsuarioid(agente.getSelected().getUsuarioid());
            ejbevaluacion.createWithPersist(evaluacion.getSelected());
            proyectoViejo = current;
            for (EvaluacionPregunta eval : evaluacionpregunta.getEvaluaciones()) {
                eval.setEvaluacionPreguntaPK(new EvaluacionPreguntaPK());
                eval.getEvaluacionPreguntaPK().setEvaluacionid(evaluacion.getSelected().getId());
                eval.getEvaluacionPreguntaPK().setPreguntaid(eval.getPregunta().getId());

                ejbevaluacionproyecto.create(eval);
            }

            this.ejbFacade.edit(current);

            if (new EnviarMail().enviarMailEvaluacionIdeaProyecto(current.getAgenteid(), current.getObservaciones())) {
                RequestContext.getCurrentInstance().execute("dfinal.show()");
            } else {
                FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ", "No se pudo evaluar del Proyecto."));
                this.ejbFacade.edit(proyectoViejo);
                ejbevaluacion.remove(evaluacion.getSelected());
            }
        } catch (Exception e) {
            ejbevaluacion.remove(evaluacion.getSelected());
            this.ejbFacade.edit(proyectoViejo);
            FacesContext.getCurrentInstance().addMessage("growlprincipal", new FacesMessage("Error! ", "No se pudo evaluar del Proyecto "));

        }

    }

    public String armarObservaciones() {
        observacionfinal = "";
        FacesContext context = FacesContext.getCurrentInstance();
        EvaluacionPreguntaController evaluacionpregunta = (EvaluacionPreguntaController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionPreguntaController}", EvaluacionPreguntaController.class);
        EvaluacionController evaluacion = (EvaluacionController) context.getApplication().evaluateExpressionGet(context, "#{evaluacionController}", EvaluacionController.class);;
        String obs = "";
        String calificacionpregunta = "";
        try {
            observacionfinal = "Estimado docente-investigador:\n"
                    + "Por medio del presente informamos a Ud. que la Idea-Proyecto Nº " + current.getId() + " de la cual Ud. Es responsable, ha sido " + current.getEstadoproyectoid().getEstado() + " según el siguiente detalle:\n"
                    + "Observaciones:\n";
            for (EvaluacionPregunta eval : evaluacionpregunta.getEvaluaciones()) {

                if (eval.getRating() != null) {
                    if (eval.getRating().intValue() < 3) {
                        calificacionpregunta = "REGULAR";
                    }
                    if (eval.getRating().intValue() == 3) {
                        calificacionpregunta = "CORRECTO";
                    }
                    if (eval.getRating() == 4) {
                        calificacionpregunta = "MUY BUENO";
                    }
                    if (eval.getRating() == 5) {
                        calificacionpregunta = "EXCELENTE";
                    }
                }
                if (eval.getObservacion() != null) {
                    observacionfinal += " - " + eval.getObservacion() + "\n";
                }
                obs += " - " + eval.getPregunta().getPregunta() + " - " + calificacionpregunta + "\n";
            }
            String isaceptada = "";
            if (current.getEstadoproyectoid().getId() == 2) {
                isaceptada = "A partir de la recepción del presente correo, el sistema quedará habilitado para la carga del proyecto definitivo.\n";
            }
            observacionfinal += "Resultados según criterios evaluados:\n" + obs
                    + "Sin otro particular lo saludo a Ud. cordialmente.\n"
                    + "Unidad de Vinculación Tecnológica";
            evaluacion.getSelected().setObservacion(observacionfinal);
        } catch (Exception e) {
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

    public void onCellEdit() {
        System.out.println("bienn");
    }

    public void buscarProyectosAgenteTrue(int agenteid) {
        recreateModel();

        items = new ListDataModel(getFacade().buscarProyectosAgente(true, agenteid));
    }

    public String enviarEvaluarProyecto() {
        FacesContext context = FacesContext.getCurrentInstance();
        EtapaController etapacontroller = (EtapaController) context.getApplication().evaluateExpressionGet(context, "#{etapaController}", EtapaController.class);

        PresupuestoRubroitemController presupuestorubroitemcontroller = (PresupuestoRubroitemController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoRubroitemController}", PresupuestoRubroitemController.class);
        PresupuestoController presupuestocontroller = (PresupuestoController) context.getApplication().evaluateExpressionGet(context, "#{presupuestoController}", PresupuestoController.class);

        for (Etapa e : etapacontroller.getEtapas()) {
            e.setId(null);
            e.setProyectoid(current);
            ejbetapa.createWithPersist(e);
            for (Tarea t : e.getTareaList()) {
                t.setId(null);
                System.out.println("dddddddddddddddddddddddddddddd" + e.getId());
                t.setEtapaid(e);
                t.setFechacreacion(new Date());
                t.setFechamodificacion(new Date());
                List<TareaAgente> listaagentes = t.getTareaAgenteList();
                t.setTareaAgenteList(null);
                ejbtarea.createWithPersist(t);
                System.out.println("ddddd7777777777777ddddddddddddd" + t.getId());
                for (TareaAgente ta : listaagentes) {
                    ta.setTareaAgentePK(new TareaAgentePK());
                    ta.getTareaAgentePK().setAgenteid(ta.getAgente().getId());
                    ta.getTareaAgentePK().setTareaid(t.getId());
                    ta.setAgente(ta.getAgente());
                    ta.setTarea(t);
                    ejbtareaagente.createWithPersist(ta);
                }
            }
        }

        for (PresupuestoRubroitem pri : presupuestorubroitemcontroller.getPresupuestosrubrositems()) {

            pri.setPresupuesto(presupuestocontroller.getSelected());
            pri.setPresupuestoRubroitemPK(new PresupuestoRubroitemPK());
            pri.getPresupuestoRubroitemPK().setPresupuestoid(presupuestocontroller.getSelected().getId());
            pri.getPresupuestoRubroitemPK().setRubroid(pri.getRubro().getId());
            // pri.setPresupuesto(presupuestocontroller.getSelected());
            ejbpresupuestorubroitem.createWithPersist(pri);

        }
        EnviarMail enviarmail = new EnviarMail();
       //  enviarmail.enviarMailEvaluarPlanEPres(current.getAgenteid() );

        //  RequestContext.getCurrentInstance().execute("dfinal.show()"); 
        //  FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()+"/secure/solicitud/View.xhtml");
        // context4.addMessage("growlprincipal", new FacesMessage("Excelente! " + context4.getExternalContext().getUserPrincipal(),"Su Solicitud a Proyecto fue creado, en breve recibira un email"));
        System.out.println("iiiiiiiiiiiiiiiiiiiii");

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

    // **************************  REPORTES  **********************************
    public void pdfIdeaProyecto() throws JRException, IOException {

        // Obtengo la ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/solicitud.jasper");

        // Fuente de datos del reporte
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // Fuente de datos del subreporte (detalle del presupuesto)
        Presupuesto presupuesto = this.ejbFacadep.findporProyecto(this.getSelected().getId());
        JRDataSource detallePresupuesto = new JRBeanCollectionDataSource(presupuesto.getPresupuestoRubroList());

        // Fuente de datos para el equipo de trabajo
        
        List<Agente> listaAgentes = new ArrayList<Agente>() ;
        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
        
        for(ProyectoAgente pa : listaProyectoAgente){
            System.out.println(pa.getAgente().getApellido() + ", " + pa.getAgente().getNombres());
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
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/listaSolicitudes.jasper");

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
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/solicitud.jasper");

        // Fuente de datos del reporte
        JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Proyecto[]{this.getSelected()});

        // Fuente de datos del subreporte (detalle del presupuesto)
        Presupuesto presupuesto = this.ejbFacadep.findporProyecto(this.getSelected().getId());
        JRDataSource detallePresupuesto = new JRBeanCollectionDataSource(presupuesto.getPresupuestoRubroList());

        // Fuente de datos para el equipo de trabajo
        
        List<Agente> listaAgentes = new ArrayList<Agente>() ;
        List<ProyectoAgente> listaProyectoAgente = this.ejbproyectoagente.buscarEquipoTrabajo(current.getId());
        
        for(ProyectoAgente pa : listaProyectoAgente){
            System.out.println(pa.getAgente().getApellido() + ", " + pa.getAgente().getNombres());
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
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/listaSolicitudes.jasper");

        // Fuente de datos del reporte
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());

        // Llenamos el reporte con la fuente de datos
        String archivo = JasperFillManager.fillReportToFile(rutaJasper, null, beanCollectionDataSource);

        if (archivo != null) {
            JasperPrintManager.printReport(
                    archivo, true);
        }

    }

 
    // REPORTE GANTT
    public void pdfEtapas() throws JRException, IOException {

       // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/evaluacion.jasper");

        // Fuente de datos del reporte
        //JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.ejbetapa.buscarEtapasProyecto(current.getId()));
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.ejbetapa.buscarEtapasProyecto(current.getId()));

        // Llenamos el reporte con la fuente de datos
        JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, null, beanCollectionDataSource);

        // Generamos el archivo a descargar
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=gantt.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
        
    }
    
    public void pdfTareas() throws JRException, IOException {

       // Ruta absoluta del archivo compilado del reporte
        String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/ganttTareas.jasper");

        // Fuente de datos del reporte
       
        //Obtenemos las tareas de un proyecto
        List<Etapa> listaEtapas = this.ejbetapa.buscarEtapasProyecto(current.getId());
        List<Tarea> listaTareas = new ArrayList<Tarea>();
        
        for(Etapa e : listaEtapas){
            for(Tarea t : this.ejbtarea.buscarTareasEtapa(e.getId())){
                listaTareas.add(t);
            }
        }
        
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
}



