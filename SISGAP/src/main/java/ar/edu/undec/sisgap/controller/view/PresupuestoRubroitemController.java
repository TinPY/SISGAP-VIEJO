package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.PresupuestoRubroitem;
import ar.edu.undec.sisgap.controller.view.util.JsfUtil;
import ar.edu.undec.sisgap.controller.view.util.PaginationHelper;
import ar.edu.undec.sisgap.controller.PresupuestoRubroitemFacade;
import ar.edu.undec.sisgap.controller.RubroFacade;
import ar.edu.undec.sisgap.model.Presupuesto;
import ar.edu.undec.sisgap.model.Proyecto;
import ar.edu.undec.sisgap.model.Rubro;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "presupuestoRubroitemController")
@ViewScoped
public class PresupuestoRubroitemController implements Serializable {

    private PresupuestoRubroitem current;
    private DataModel items = null;
    @EJB
    private ar.edu.undec.sisgap.controller.PresupuestoRubroitemFacade ejbFacade;
    @EJB
    private RubroFacade ejbFacadeRubro;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<PresupuestoRubroitem> presupuestosrubrositems = new ArrayList();
    private PresupuestoRubroitem current2 = new PresupuestoRubroitem();
    private TreeNode root = new DefaultTreeNode();
    private PieChartModel pieModelAportes;
    private PieChartModel pieModelRubros;

    public PresupuestoRubroitemController() {
    }

    public PresupuestoRubroitem getSelected() {
        if (current == null) {
            current = new PresupuestoRubroitem();
            selectedItemIndex = -1;
        }
        return current;
    }

    private PresupuestoRubroitemFacade getFacade() {
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
        current = (PresupuestoRubroitem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new PresupuestoRubroitem();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroitemCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (PresupuestoRubroitem) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroitemUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (PresupuestoRubroitem) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PresupuestoRubroitemDeleted"));
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

    @FacesConverter(forClass = PresupuestoRubroitem.class)
    public static class PresupuestoRubroitemControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PresupuestoRubroitemController controller = (PresupuestoRubroitemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "presupuestoRubroitemController");
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
            if (object instanceof PresupuestoRubroitem) {
                PresupuestoRubroitem o = (PresupuestoRubroitem) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PresupuestoRubroitem.class.getName());
            }
        }

    }

    
    public List<PresupuestoRubroitem> getPresupuestosrubrositems()
  {
    return this.presupuestosrubrositems;
  }
  
  public void setPresupuestosrubrositems(List<PresupuestoRubroitem> presupuestosrubrositems)
  {
    this.presupuestosrubrositems = presupuestosrubrositems;
  }
  
  public String reinit()
  {
    this.current2 = new PresupuestoRubroitem();
    this.current2.setAportecomitente(BigDecimal.ZERO);
    this.current2.setAporteorganismo(BigDecimal.ZERO);
    this.current2.setAporteuniversidad(BigDecimal.ZERO);
    this.current2.setCantidad(BigDecimal.ONE);
    this.current2.setCostounitario(BigDecimal.ZERO);
    return null;
  }
  
  public PresupuestoRubroitem getCurrent2()
  {
    return this.current2;
  }
  
  public void setCurrent2(PresupuestoRubroitem current2)
  {
    this.current2 = current2;
  }
  
  public void sumarTotalCurrent()
  {
    this.current2.setTotal(this.current2.getCostounitario().multiply(this.current2.getCantidad()));
  }
  
  public boolean verificarAportes()
  {
    int valor = 0;
    BigDecimal sumaaportes = BigDecimal.ZERO;
    
    sumaaportes = this.current2.getAportecomitente().add(this.current2.getAporteorganismo()).add(this.current2.getAporteuniversidad());
    sumaaportes.compareTo(this.current2.getTotal());
    valor = sumaaportes.compareTo(this.current2.getTotal());
    if (valor == 1) {
      return false;
    }
    return true;
  }
  
  public void armarPresupuestoNodos()
  {
    PresupuestoRubroitem pri;
    TreeNode treeNode;
    TreeNode treeNodech;
    
      this.root = new DefaultTreeNode("INICIO", null);
      this.root.setExpanded(true);
      this.root.setSelectable(false);
      
      for (Rubro rub : this.ejbFacadeRubro.findAll())
      {
          
        pri = new PresupuestoRubroitem();
        pri.setRubro(rub);
        treeNode = new DefaultTreeNode(pri, this.root);
        treeNode.setExpanded(true);
        for (PresupuestoRubroitem p : getPresupuestosrubrositems()) {
          if (p.getRubro().equals(pri.getRubro())) {
            treeNodech = new DefaultTreeNode(p, treeNode);
              
          }
        }
      }
   
  }
  
  public void armarPresupuestosNodos2(Presupuesto presu){
      if(this.getPresupuestosrubrositems().size()>0){
         
      }else{
          
       this.setPresupuestosrubrositems(this.ejbFacade.findByPresupuesto(presu));
      }
        this.root = null;
        this.root = new DefaultTreeNode(new PresupuestoRubroitem(),null);
       PresupuestoRubroitem pri;
        TreeNode treeNode;
        TreeNode treeNodech;
      this.root.setExpanded(true);
      this.root.setSelectable(false);
     
      for (Rubro rub : this.ejbFacadeRubro.findAll())
      {
          
        pri = new PresupuestoRubroitem();
        pri.setRubro(rub);
        treeNode = new DefaultTreeNode(pri, this.root);
        treeNode.setExpanded(true);
        for (PresupuestoRubroitem p : getPresupuestosrubrositems()) {
          if (p.getRubro().equals(pri.getRubro())) {
            treeNodech = new DefaultTreeNode(p, treeNode);
              
          }
        }
      }
   
  }
  
  public TreeNode getRoot()
  {
    return this.root;
  }
  
  public void setRoot(TreeNode root)
  {
    this.root = root;
  }
  
  public void agregarPresupuesto()
  {
    if (verificarAportes()){
      this.presupuestosrubrositems.add(this.current2);
      armarPresupuestoNodos();
     }
    else
    {
      reinit();
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La suma de Aportes no puede superar al Total"));
    }
    armarGraficosPresupuesto();
  }
  
  public void eliminarPresupuesto(PresupuestoRubroitem pri)
  {
      
      
    int contador = 0;
    int posicion = 0;
    for (PresupuestoRubroitem p : getPresupuestosrubrositems())
    {
      if ((p.getRubro().equals(pri.getRubro())) && 
        (p.getDescripcion().equals(pri.getDescripcion())) && 
        (p.getTotal().equals(pri.getTotal()))) {
        posicion = contador;
      }
      
      contador++;
    }
    getPresupuestosrubrositems().remove(posicion);
    //getPresupuestosrubrositems().remove(pri);
     
    armarPresupuestoNodos();
  }
  
  public void eliminarPresupuesto2(PresupuestoRubroitem pri)
  {
      
    getPresupuestosrubrositems().remove(pri);
     
    //ejbFacade.remove(pri);
  }
  
  public void armarGraficosPresupuesto()
  {
    this.pieModelAportes = new PieChartModel();
    this.pieModelRubros = new PieChartModel();
    BigDecimal sumaaportecomitente = BigDecimal.ZERO;
    BigDecimal sumaaporteorganismo = BigDecimal.ZERO;
    BigDecimal sumaaporteuniversidad = BigDecimal.ZERO;
    for (PresupuestoRubroitem pri : getPresupuestosrubrositems())
    {
      sumaaportecomitente = sumaaportecomitente.add(pri.getAportecomitente());
      sumaaporteorganismo = sumaaporteorganismo.add(pri.getAporteorganismo());
      sumaaporteuniversidad = sumaaporteuniversidad.add(pri.getAporteuniversidad());
     
    }
    this.pieModelAportes.set("Aporte Comitente", sumaaportecomitente);
    this.pieModelAportes.set("Aporte Organismo", sumaaporteorganismo);
    this.pieModelAportes.set("Aporte Universidad", sumaaporteuniversidad);
    


    BigDecimal sumarubro = BigDecimal.ZERO;
    for (Rubro rub : this.ejbFacadeRubro.findAll())
    {
      for (PresupuestoRubroitem p : getPresupuestosrubrositems()) {
        if (rub.getRubro().equals(p.getRubro().getRubro())) {
          sumarubro = sumarubro.add(p.getTotal());
        }
      }
      this.pieModelRubros.set(rub.getRubro(), sumarubro);
      sumarubro = BigDecimal.ZERO;
    }
  }
  
  public PieChartModel getPieModelAportes()
  {
      
    if (this.pieModelAportes == null)
    {
      this.pieModelAportes = new PieChartModel();
      
      this.pieModelAportes.set("Gasto Organismo", Integer.valueOf(0));
      this.pieModelAportes.set("Gasto Comitente", Integer.valueOf(0));
      this.pieModelAportes.set("Gasto Universidad", Integer.valueOf(0));
    }
    return this.pieModelAportes;
  }
  
  public void setPieModelAportes(PieChartModel pieModelAportes)
  {
    this.pieModelAportes = pieModelAportes;
  }
  
  public PieChartModel getPieModelRubros()
  {
    if (this.pieModelRubros == null)
    {
      this.pieModelRubros = new PieChartModel();
      for (Rubro r : this.ejbFacadeRubro.findAll()) {
        this.pieModelRubros.set(r.getRubro(), Integer.valueOf(0));
      }
    }
    return this.pieModelRubros;
  }
  
  public void setPieModelRubros(PieChartModel pieModelRubros)
  {
    this.pieModelRubros = pieModelRubros;
  }
    
}
