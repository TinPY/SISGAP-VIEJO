/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "presupuesto_rubro", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PresupuestoRubro.findAll", query = "SELECT p FROM PresupuestoRubro p"),
    @NamedQuery(name = "PresupuestoRubro.findByPresupuestoid", query = "SELECT p FROM PresupuestoRubro p WHERE p.presupuestoRubroPK.presupuestoid = :presupuestoid"),
    @NamedQuery(name = "PresupuestoRubro.findByRubroid", query = "SELECT p FROM PresupuestoRubro p WHERE p.presupuestoRubroPK.rubroid = :rubroid"),
    @NamedQuery(name = "PresupuestoRubro.findByGastocomitente", query = "SELECT p FROM PresupuestoRubro p WHERE p.gastocomitente = :gastocomitente"),
    @NamedQuery(name = "PresupuestoRubro.findByGastouniversidad", query = "SELECT p FROM PresupuestoRubro p WHERE p.gastouniversidad = :gastouniversidad"),
    @NamedQuery(name = "PresupuestoRubro.findByEstado", query = "SELECT p FROM PresupuestoRubro p WHERE p.estado = :estado")})
public class PresupuestoRubro implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PresupuestoRubroPK presupuestoRubroPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "gastocomitente")
    private BigDecimal gastocomitente;
    @Column(name = "gastouniversidad")
    private BigDecimal gastouniversidad;
    @Column(name = "gastoorganismo")
    private BigDecimal gastoorganismo;
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "rubroid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rubro rubro;
    @JoinColumn(name = "presupuestoid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Presupuesto presupuesto;

    public PresupuestoRubro() {
    }

    public PresupuestoRubro(PresupuestoRubroPK presupuestoRubroPK) {
        this.presupuestoRubroPK = presupuestoRubroPK;
    }

    public PresupuestoRubro(int presupuestoid, int rubroid) {
        this.presupuestoRubroPK = new PresupuestoRubroPK(presupuestoid, rubroid);
    }

    public PresupuestoRubroPK getPresupuestoRubroPK() {
        return presupuestoRubroPK;
    }

    public void setPresupuestoRubroPK(PresupuestoRubroPK presupuestoRubroPK) {
        this.presupuestoRubroPK = presupuestoRubroPK;
    }

    public BigDecimal getGastocomitente() {
        return gastocomitente;
    }

    public void setGastocomitente(BigDecimal gastoentidad) {
        this.gastocomitente = gastoentidad;
    }

    public BigDecimal getGastoorganismo() {
        return gastoorganismo;
    }

    public void setGastoorganismo(BigDecimal gastoorganismo) {
        this.gastoorganismo = gastoorganismo;
    }
    
    

    public BigDecimal getGastouniversidad() {
        return gastouniversidad;
    }

    public void setGastouniversidad(BigDecimal gastouniversidad) {
        this.gastouniversidad = gastouniversidad;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
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
        hash += (presupuestoRubroPK != null ? presupuestoRubroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoRubro)) {
            return false;
        }
        PresupuestoRubro other = (PresupuestoRubro) object;
        if ((this.presupuestoRubroPK == null && other.presupuestoRubroPK != null) || (this.presupuestoRubroPK != null && !this.presupuestoRubroPK.equals(other.presupuestoRubroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.PresupuestoRubro[ presupuestoRubroPK=" + presupuestoRubroPK + " ]";
    }
    
}
