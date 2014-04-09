/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "tipoentidad", schema = "ap")
@SequenceGenerator(name="tipoentidad_id_seq", sequenceName="ap.tipoentidad_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoentidad.findAll", query = "SELECT t FROM Tipoentidad t"),
    @NamedQuery(name = "Tipoentidad.findByTipoentidad", query = "SELECT t FROM Tipoentidad t WHERE t.tipoentidad = :tipoentidad"),
    @NamedQuery(name = "Tipoentidad.findById", query = "SELECT t FROM Tipoentidad t WHERE t.id = :id")})
public class Tipoentidad implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Size(max = 2147483647)
    @Column(name = "tipoentidad")
    private String tipoentidad;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipoentidad_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @OneToMany(mappedBy = "tipoentidadid")
    private List<Entidadbeneficiaria> entidadbeneficiariaList;

    public Tipoentidad() {
    }

    public Tipoentidad(Integer id) {
        this.id = id;
    }

    public String getTipoentidad() {
        return tipoentidad;
    }

    public void setTipoentidad(String tipoentidad) {
        this.tipoentidad = tipoentidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Entidadbeneficiaria> getEntidadbeneficiariaList() {
        return entidadbeneficiariaList;
    }

    public void setEntidadbeneficiariaList(List<Entidadbeneficiaria> entidadbeneficiariaList) {
        this.entidadbeneficiariaList = entidadbeneficiariaList;
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
        if (!(object instanceof Tipoentidad)) {
            return false;
        }
        Tipoentidad other = (Tipoentidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipoentidad ;
    }
    
}
