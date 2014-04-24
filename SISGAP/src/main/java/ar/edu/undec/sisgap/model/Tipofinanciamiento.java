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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "tipofinanciamiento", schema = "ap")
@SequenceGenerator(name="tipofinanciamiento_id_seq", sequenceName="ap.tipofinanciamiento_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipofinanciamiento.findAll", query = "SELECT t FROM Tipofinanciamiento t"),
    @NamedQuery(name = "Tipofinanciamiento.findById", query = "SELECT t FROM Tipofinanciamiento t WHERE t.id = :id"),
    @NamedQuery(name = "Tipofinanciamiento.findByTipofinanciamiento", query = "SELECT t FROM Tipofinanciamiento t WHERE t.tipofinanciamiento = :tipofinanciamiento")})
public class Tipofinanciamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipofinanciamiento_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "tipofinanciamiento")
    private String tipofinanciamiento;

    public Tipofinanciamiento() {
    }

    public Tipofinanciamiento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipofinanciamiento() {
        return tipofinanciamiento;
    }

    public void setTipofinanciamiento(String tipofinanciamiento) {
        this.tipofinanciamiento = tipofinanciamiento;
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
        if (!(object instanceof Tipofinanciamiento)) {
            return false;
        }
        Tipofinanciamiento other = (Tipofinanciamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipofinanciamiento;
    }
    
}
