package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Tarea;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.TareaFacade;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

@ManagedBean(name = "tareaController")
@SessionScoped
public class TareaController implements Serializable {

    private Tarea current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.TareaFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Tarea> tareasdeproyecto = new ArrayList<Tarea>() ;
    private Tarea tareanueva=new Tarea();
    private String gsoncategoria="[]";
    private String data ;
    private long mindate;
    private Tarea tarea1=new Tarea();

    public TareaController() {
    }

    public Tarea getSelected() {
        if (current == null) {
            current = new Tarea();
            current.setTarea("");
            current.setDescripcion("");
            current.setFechainicio(new Date());
            current.setFechafin(null);
            current.setDias(0);
            selectedItemIndex = -1;
        }
        return current;
    }

    private TareaFacade getFacade() {
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
        current = (Tarea) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Tarea();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Tarea) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Tarea) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TareaDeleted"));
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

    @FacesConverter(forClass = Tarea.class)
    public static class TareaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TareaController controller = (TareaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tareaController");
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
            if (object instanceof Tarea) {
                Tarea o = (Tarea) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tarea.class.getName());
            }
        }

    }

    public List<Tarea> getTareasdeproyecto() {
        
        return tareasdeproyecto;
    }

    public void setTareasdeproyecto(List<Tarea> tareasdeproyecto) {
        this.tareasdeproyecto = tareasdeproyecto;
    }
    
     public void rearmarTareasProyecto(){
         current=null;
     }
    
    public void armarTareasProyecto(){
        
        if(current.getId()==null){
            
        
            current.setId(tareasdeproyecto.size()+1);
            //current.setEstado("0%");
        
            tareasdeproyecto.add(current);
        }
        
        current=null;
        
       crearChart();
       
    }
    
       
    public void calcularDiferenciaDias(){
         // tomamos la instancia del tipo de calendario
        Calendar calendarInicio = Calendar.getInstance();
        Calendar calendarFinal = Calendar.getInstance();

         // Configramos la fecha del calendatio, tomando los valores del date que 
        // generamos en el parse
        calendarInicio.setTime(current.getFechainicio());
        calendarFinal.setTime(current.getFechafin());

         // obtenemos el valor de las fechas en milisegundos
        long milisegundos1 = calendarInicio.getTimeInMillis();
        long milisegundos2 = calendarFinal.getTimeInMillis();

         // tomamos la diferencia
        long diferenciaMilisegundos = milisegundos2 - milisegundos1;

         // Despues va a depender en que formato queremos  mostrar esa 
        // diferencia, minutos, segundo horas, dias, etc, aca van algunos 
        // ejemplos de conversion

         // calcular la diferencia en segundos
        long diffSegundos =  Math.abs (diferenciaMilisegundos / 1000);

         // calcular la diferencia en minutos
        long diffMinutos =  Math.abs (diferenciaMilisegundos / (60 * 1000));
        long restominutos = diffMinutos%60;

         // calcular la diferencia en horas
        long diffHoras =   (diferenciaMilisegundos / (60 * 60 * 1000));

         // calcular la diferencia en dias
        long diffdias = Math.abs( diferenciaMilisegundos / (24 * 60 * 60 * 1000) );
        int entero=(int) diffdias;
        current.setDias(entero+1);
        System.out.println("-------------"+diffdias);
        

    }

    public Tarea getTareanueva() {
        return tareanueva;
    }

    public void setTareanueva(Tarea tareanueva) {
        this.tareanueva = tareanueva;
    }
    
    public void crearChart(){
        Gson gson= new Gson();
        List<String> categoria=new ArrayList<String>();
        data="[";
        mindate=Long.MAX_VALUE;
       int cant=tareasdeproyecto.size();
       int contador=0;
        for(Tarea t:this.tareasdeproyecto){
            if(t.getFechainicio().getTime()<mindate){
                mindate=t.getFechainicio().getTime();
            }
            contador++;
            categoria.add(t.getTarea());
            data+="["+t.getFechainicio().getTime()+","+t.getFechafin().getTime()+"]";
            if(contador<cant){
                data+=",";
            }
        }
        gsoncategoria=""+gson.toJson(categoria).replace('\"', '\'');
        data+="]";
        System.out.println("--------------------"+gsoncategoria);
    }
    
    public void removerTareadeProyecto(){
        System.out.println("oooooooooooo"+current.getId());
       
        this.tareasdeproyecto.remove(current);
        crearChart();
        
        
    }
    
   

    public String getGsoncategoria() {
        return gsoncategoria;
    }

    public void setGsoncategoria(String gsoncategoria) {
        this.gsoncategoria = gsoncategoria;
    }
    
    public void sumarDias(){
        Calendar cal=Calendar.getInstance();
        System.out.println("nnnnnnnnnnnn"+current.getTarea());
        System.out.println("nnnnnnnnnnnn"+current.getDias());
        cal.setTime(current.getFechainicio());
        cal.add(Calendar.DAY_OF_YEAR, current.getDias());
        current.setFechafin(cal.getTime());
//        System.out.println("--------"+current.getFechafin());
                
    }
    
    public void setSelected(Tarea tarea){
        System.out.println("llllllllllllll");
        current= tarea;
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

    public Tarea getTarea1() {
        return tarea1;
    }

    public void setTarea1(Tarea tarea1) {
        this.tarea1 = tarea1;
    }
    
    public void grabarTareas(){
        for(Tarea t:tareasdeproyecto){
            this.ejbFacade.createWithPersist(t);
            
        }
    }
    

}
