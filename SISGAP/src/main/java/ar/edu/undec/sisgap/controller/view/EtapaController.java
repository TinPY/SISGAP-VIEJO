package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Etapa;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.EtapaFacade;
import ar.edu.undec.sisgap.model.Tarea;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "etapaController")
@SessionScoped
public class EtapaController implements Serializable {

    private Etapa current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.EtapaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    @ManagedProperty("#{tareaController}")
    private TareaController tareacontroller;
    private TreeNode root = new DefaultTreeNode() ;
    private List<Etapa> etapas = new ArrayList<Etapa>() ;
    private String gsoncategoria="[]";
    private String data ;
    private String dataactual;
    private long mindate;
    private Tarea tareaseleccionada;
    private boolean paraeditar=false;
    
    public EtapaController() {
    }

    public Etapa getSelected() {
        if (current == null) {
            current = new Etapa();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EtapaFacade getFacade() {
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
        current = (Etapa) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Etapa();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EtapaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Etapa) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EtapaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Etapa) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EtapaDeleted"));
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

    @FacesConverter(forClass = Etapa.class)
    public static class EtapaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EtapaController controller = (EtapaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "etapaController");
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
            if (object instanceof Etapa) {
                Etapa o = (Etapa) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Etapa.class.getName());
            }
        }

    }
    
    public String onFlowProcess(FlowEvent event) {
       
        
            return event.getNewStep();
        
    }

    public TareaController getTareacontroller() {
        return tareacontroller;
    }

    public void setTareacontroller(TareaController tareacontroller) {
        this.tareacontroller = tareacontroller;
    }
    
    @PostConstruct
    public void init() {
        
        
        
    }

    public TreeNode getRoot() {
        
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }
    
    public void agregaralListadoEtapas(){
        int contardias = 0;
        Date mindia = new Date("2999/12/12");
        Date maxdia = new Date("2001/01/01");
        root = new DefaultTreeNode(new Tarea(),null);
        root.setExpanded(true);
        current.setTareaList(tareacontroller.getTareasdeproyecto());
        for(Tarea t:tareacontroller.getTareasdeproyecto()){
            contardias = contardias + t.getDias();
            if(t.getFechainicio().before(mindia)){
               mindia=t.getFechainicio();
            }
            if(t.getFechafin().after(maxdia)){
                maxdia=t.getFechafin();
            }
        }
        current.setFechainicio(mindia);
        current.setFechafin(maxdia);
        current.setDias(contardias);
        etapas.add(current);
        for(Etapa etapa: etapas){
            System.out.println("fffffffffffff"+etapa.getEtapa());
            Tarea t = new Tarea();
            t.setTarea(etapa.getEtapa());
            t.setFechainicio(etapa.getFechainicio());
            t.setFechafin(etapa.getFechafin());
            t.setDias(etapa.getDias());
            TreeNode et = new DefaultTreeNode(t,root);
            et.setExpanded(true);
            for(Tarea tarea:etapa.getTareaList()){
                TreeNode tar = new DefaultTreeNode(tarea,et);
            }
            
        }
        crearChart();
         current=null;
         tareacontroller.setTareasdeproyecto(null);
    }
    
    public void eliminaralListadoEtapas(){
        etapas.remove(current);
    }

    public String getGsoncategoria() {
        return gsoncategoria;
    }

    public void setGsoncategoria(String gsoncategoria) {
        this.gsoncategoria = gsoncategoria;
    }
    
    public void crearChart(){
        Gson gson= new Gson();
        List<String> categoria=new ArrayList<String>();
        data="[";
        dataactual="[";
        mindate=Long.MAX_VALUE;
       if(tareacontroller.getTareasdeproyecto()==null){
           int cant=etapas.size();
       }else{
            int cant=tareacontroller.getTareasdeproyecto().size()+etapas.size();
       }
       int contaretapas=0;
       int contador=0;
            for(Etapa e: etapas){
                contador++;
                
                    categoria.add(e.getEtapa());
                    
                    //data+="["+e.getFechainicio().getTime()+","+e.getFechafin().getTime()+"],";
                    data+="{low:"+e.getFechainicio().getTime()+",high:"+e.getFechafin().getTime()+",color:'red'},";
                    dataactual+="["+e.getFechainicio().getTime()+","+e.getFechainicio().getTime()+"],";
                    contaretapas++;
                for(Tarea t:this.etapas.get(contaretapas-1).getTareaList()){
                    if(t.getFechainicio().getTime()<mindate){
                        mindate=t.getFechainicio().getTime();
                    }
                    contador++;
                    categoria.add(t.getTarea());
                    data+="["+t.getFechainicio().getTime()+","+t.getFechafin().getTime()+"],";
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(t.getFechainicio()); 
                    calendar.add(Calendar.DAY_OF_YEAR, (t.getDias()* t.getEstado())/100);
                    dataactual+="["+t.getFechainicio().getTime()+","+calendar.getTime().getTime()+"],";
                }
            }
        gsoncategoria=""+gson.toJson(categoria).replace('\"', '\'');
        data+="]";
        dataactual+="]";
        System.out.println("--------------------"+gsoncategoria);
        System.out.println("--------------------"+data);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getMindate() {
        return mindate;
    }

    public void setMindate(long mindate) {
        this.mindate = mindate;
    }
  
    public void rearmarEtapasProyecto(){
         current=null;
         tareacontroller.setTareasdeproyecto(null);
         
     }

    public String getDataactual() {
        return dataactual;
    }

    public void setDataactual(String dataactual) {
        this.dataactual = dataactual;
    }

    public Tarea getTareaseleccionada() {
        return tareaseleccionada;
    }

    public void setTareaseleccionada(Tarea tareaseleccionada) {
        this.tareaseleccionada = tareaseleccionada;
    }
    
    public void eliminarEtapaTreeTable(Tarea tar){
        int contador=0;
        int lugar=0;
        for(TreeNode tn :root.getChildren()){
            
            if(((Tarea)tn.getData()).getTarea().equals(tar.getTarea())){
               lugar=contador; 
            }
            contador++;
         }
        root.getChildren().remove(lugar);
        Etapa etapaguardada = new Etapa();
        contador = 0;
        lugar = 0;
        for(Etapa et : etapas){
            
            if(tar.getTarea().equals(et.getEtapa())){
             lugar = contador ;
           }
            contador++;
        }
        etapas.remove(lugar);
        
        crearChart();
    }
    
    public void prepararEditarEtapaTreeTable(Tarea tar){
        int contador = 0;
        int posicion = 0;
        for(Etapa e: etapas){
            if(e.getEtapa().equals(tar.getTarea())){
                if(e.getFechainicio().equals(tar.getFechainicio())){
                    posicion = contador;
                }
                
            }
            contador++;
        }
        current = etapas.get(posicion);
        
        tareacontroller.setTareasdeproyecto(current.getTareaList());
        this.paraeditar=true;
    }
    
    public void editarListadoEtapas(){
        Date mindia = new Date("2999/12/12");
        Date maxdia = new Date("2001/01/01");
        int contardias = 0;
        root = new DefaultTreeNode(new Tarea(),null);
        root.setExpanded(true);
        //current.setTareaList(tareacontroller.getTareasdeproyecto());
        for(Tarea t:tareacontroller.getTareasdeproyecto()){
            contardias = contardias + t.getDias();
            if(t.getFechainicio().before(mindia)){
               mindia=t.getFechainicio();
            }
            if(t.getFechafin().after(maxdia)){
                maxdia=t.getFechafin();
            }
        }
        current.setFechainicio(mindia);
        current.setFechafin(maxdia);
        current.setDias(contardias);
        //etapas.add(current);
        for(Etapa etapa: etapas){
            System.out.println("fffffffffffff"+etapa.getEtapa());
            Tarea t = new Tarea();
            t.setTarea(etapa.getEtapa());
            t.setFechainicio(etapa.getFechainicio());
            t.setFechafin(etapa.getFechafin());
            t.setDias(etapa.getDias());
            TreeNode et = new DefaultTreeNode(t,root);
            et.setExpanded(true);
            for(Tarea tarea:etapa.getTareaList()){
                TreeNode tar = new DefaultTreeNode(tarea,et);
            }
             
        }
        crearChart();
         current=null;
         tareacontroller.setTareasdeproyecto(null);
         this.paraeditar=false;
        
    }

    public boolean isParaeditar() {
        return paraeditar;
    }

    public void setParaeditar(boolean paraeditar) {
        this.paraeditar = paraeditar;
    }
    
    
}
