package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.ArchivoproyectoFacade;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "archivoproyectoController")
@SessionScoped
public class ArchivoproyectoController implements Serializable {

    private Archivoproyecto current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.ArchivoproyectoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Archivoproyecto> collectorArchivoProyecto= new ArrayList<Archivoproyecto>();
    private int contador=0;

    public ArchivoproyectoController() {
    }

    public Archivoproyecto getSelected() {
        if (current == null) {
            current = new Archivoproyecto();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ArchivoproyectoFacade getFacade() {
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
        current = (Archivoproyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Archivoproyecto();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivoproyectoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Archivoproyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivoproyectoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Archivoproyecto) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ArchivoproyectoDeleted"));
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

    @FacesConverter(forClass = Archivoproyecto.class)
    public static class ArchivoproyectoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ArchivoproyectoController controller = (ArchivoproyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "archivoproyectoController");
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
            if (object instanceof Archivoproyecto) {
                Archivoproyecto o = (Archivoproyecto) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Archivoproyecto.class.getName());
            }
        }

    }
    
    public void handleFileUpload(FileUploadEvent event) { 
        System.out.println("fdsfdsdfadf");
        current= new Archivoproyecto();
        current.setId(++contador);
       current.setNombre(event.getFile().getFileName());
       current.setLink(event.getFile().getFileName());
       current.getId();
        	
		try {
			current.setArchivo(event.getFile().getContents());
                         this.collectorArchivoProyecto.add(current);
                         
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
         
       // FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 

    public List<Archivoproyecto> getCollectorArchivoProyecto() {
        return collectorArchivoProyecto;
    }

    public void setCollectorArchivoProyecto(List<Archivoproyecto> collectorArchivoProyecto) {
        this.collectorArchivoProyecto = collectorArchivoProyecto;
    }
    
    public void setSelected(Archivoproyecto archivoproyecto){
        current=archivoproyecto;
    }
    
    public void removerArchivoCollection(){
//        int contador=0;
//        int posicion=0;
//        Iterator i = this.collectorArchivoProyecto.iterator();
//        System.out.println("id"+current.getId()+ " nombre "+current.getNombre());
//        while(i.hasNext()){
//            
//            if( ((Archivoproyecto)i.next()).getNombre().equals(current.getNombre())){
//                posicion=contador;
//            }
//            contador++;
//        }
        this.collectorArchivoProyecto.remove(current);
      
    }
    
    public void soloCrear(Archivoproyecto ap){
       
              
        getFacade().create(ap);
    }
}
