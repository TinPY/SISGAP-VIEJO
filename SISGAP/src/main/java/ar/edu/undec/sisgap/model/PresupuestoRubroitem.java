/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "presupuesto_rubroitem", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresupuestoRubroitem.findAll", query = "SELECT p FROM PresupuestoRubroitem p"),
    @NamedQuery(name = "PresupuestoRubroitem.findByDescripcion", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "PresupuestoRubroitem.findByCostounitario", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.costounitario = :costounitario"),
    @NamedQuery(name = "PresupuestoRubroitem.findByCantidad", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "PresupuestoRubroitem.findByTotal", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.total = :total"),
    @NamedQuery(name = "PresupuestoRubroitem.findByRubroid", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.presupuestoRubroitemPK.rubroid = :rubroid"),
    @NamedQuery(name = "PresupuestoRubroitem.findByPresupuestoid", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.presupuestoRubroitemPK.presupuestoid = :presupuestoid"),
    @NamedQuery(name = "PresupuestoRubroitem.findByAportecomitente", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.aportecomitente = :aportecomitente"),
    @NamedQuery(name = "PresupuestoRubroitem.findByAporteuniversidad", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.aporteuniversidad = :aporteuniversidad"),
    @NamedQuery(name = "PresupuestoRubroitem.findByAporteorganismo", query = "SELECT p FROM PresupuestoRubroitem p WHERE p.aporteorganismo = :aporteorganismo")})
public class PresupuestoRubroitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresupuestoRubroitemPK presupuestoRubroitemPK;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costounitario")
    private BigDecimal costounitario;
    @Column(name = "cantidad")
    private BigDecimal cantidad;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "aportecomitente")
    private BigDecimal aportecomitente;
    @Column(name = "aporteuniversidad")
    private BigDecimal aporteuniversidad;
    @Column(name = "aporteorganismo")
    private BigDecimal aporteorganismo;
     @JoinColumn(name = "rubroid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rubro rubro;
    @JoinColumn(name = "presupuestoid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Presupuesto presupuesto;
    
    public PresupuestoRubroitem() {
    }

    public PresupuestoRubroitem(PresupuestoRubroitemPK presupuestoRubroitemPK) {
        this.presupuestoRubroitemPK = presupuestoRubroitemPK;
    }

    public PresupuestoRubroitem(PresupuestoRubroitemPK presupuestoRubroitemPK, int id) {
        this.presupuestoRubroitemPK = presupuestoRubroitemPK;
        
    }

    public PresupuestoRubroitem(int rubroid, int presupuestoid) {
        this.presupuestoRubroitemPK = new PresupuestoRubroitemPK(rubroid, presupuestoid);
    }

    public PresupuestoRubroitemPK getPresupuestoRubroitemPK() {
        return presupuestoRubroitemPK;
    }

    public void setPresupuestoRubroitemPK(PresupuestoRubroitemPK presupuestoRubroitemPK) {
        this.presupuestoRubroitemPK = presupuestoRubroitemPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostounitario() {
        return costounitario;
    }

    public void setCostounitario(BigDecimal costounitario) {
        this.costounitario = costounitario;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getAportecomitente() {
        return aportecomitente;
    }

    public void setAportecomitente(BigDecimal aportecomitente) {
        this.aportecomitente = aportecomitente;
    }

    public BigDecimal getAporteuniversidad() {
        return aporteuniversidad;
    }

    public void setAporteuniversidad(BigDecimal aporteuniversidad) {
        this.aporteuniversidad = aporteuniversidad;
    }

    public BigDecimal getAporteorganismo() {
        return aporteorganismo;
    }

    public void setAporteorganismo(BigDecimal aporteorganismo) {
        this.aporteorganismo = aporteorganismo;
    }

    public Rubro getRubro() {
        return rubro;
    }

    public void setRubro(Rubro rubro) {
        this.rubro = rubro;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (presupuestoRubroitemPK != null ? presupuestoRubroitemPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoRubroitem)) {
            return false;
        }
        PresupuestoRubroitem other = (PresupuestoRubroitem) object;
        if ((this.presupuestoRubroitemPK == null && other.presupuestoRubroitemPK != null) || (this.presupuestoRubroitemPK != null && !this.presupuestoRubroitemPK.equals(other.presupuestoRubroitemPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.PresupuestoRubroitem[ presupuestoRubroitemPK=" + presupuestoRubroitemPK + " ]";
    }
    
}
