/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mpaez
 */
@Entity
@Table(name = "tipoevaluacion", schema = "ap")
@SequenceGenerator(name="tipoevaluacion_tipoevaluacionid_seq", sequenceName="ap.tipoevaluacion_tipoevaluacionid_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoevaluacion.findAll", query = "SELECT t FROM Tipoevaluacion t"),
    @NamedQuery(name = "Tipoevaluacion.findByTipoevaluacionid", query = "SELECT t FROM Tipoevaluacion t WHERE t.tipoevaluacionid = :tipoevaluacionid"),
    @NamedQuery(name = "Tipoevaluacion.findByTipoevaluacion", query = "SELECT t FROM Tipoevaluacion t WHERE t.tipoevaluacion = :tipoevaluacion")})
public class Tipoevaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipoevaluacion_tipoevaluacionid_seq")
    @Basic(optional = false)
    @Column(name = "tipoevaluacionid")
    private Integer tipoevaluacionid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "tipoevaluacion")
    private String tipoevaluacion;

    public Tipoevaluacion() {
    }

    public Tipoevaluacion(Integer tipoevaluacionid) {
        this.tipoevaluacionid = tipoevaluacionid;
    }

    public Tipoevaluacion(Integer tipoevaluacionid, String tipoevaluacion) {
        this.tipoevaluacionid = tipoevaluacionid;
        this.tipoevaluacion = tipoevaluacion;
    }

    public Integer getTipoevaluacionid() {
        return tipoevaluacionid;
    }

    public void setTipoevaluacionid(Integer tipoevaluacionid) {
        this.tipoevaluacionid = tipoevaluacionid;
    }

    public String getTipoevaluacion() {
        return tipoevaluacion;
    }

    public void setTipoevaluacion(String tipoevaluacion) {
        this.tipoevaluacion = tipoevaluacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoevaluacionid != null ? tipoevaluacionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoevaluacion)) {
            return false;
        }
        Tipoevaluacion other = (Tipoevaluacion) object;
        if ((this.tipoevaluacionid == null && other.tipoevaluacionid != null) || (this.tipoevaluacionid != null && !this.tipoevaluacionid.equals(other.tipoevaluacionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getTipoevaluacionid()+ " - " + this.getTipoevaluacion();
    }
    
}
