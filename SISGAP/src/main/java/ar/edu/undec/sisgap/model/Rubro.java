/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "rubro", schema = "ap")
@SequenceGenerator(name="rubro_id_seq", sequenceName="ap.rubro_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubro.findAll", query = "SELECT r FROM Rubro r"),
    @NamedQuery(name = "Rubro.findById", query = "SELECT r FROM Rubro r WHERE r.id = :id"),
    @NamedQuery(name = "Rubro.findByRubro", query = "SELECT r FROM Rubro r WHERE r.rubro = :rubro")})
public class Rubro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rubro_id_seq")
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Size(max = 60)
    @Column(name = "rubro")
    private String rubro;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubro")
    private List<PresupuestoRubro> presupuestoRubroList;

    public Rubro() {
    }

    public Rubro(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    @XmlTransient
    public List<PresupuestoRubro> getPresupuestoRubroList() {
        return presupuestoRubroList;
    }

    public void setPresupuestoRubroList(List<PresupuestoRubro> presupuestoRubroList) {
        this.presupuestoRubroList = presupuestoRubroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubro)) {
            return false;
        }
        Rubro other = (Rubro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return rubro;
    }
    
}
