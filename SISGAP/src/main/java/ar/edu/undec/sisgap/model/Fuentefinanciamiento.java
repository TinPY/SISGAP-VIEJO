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
@Table(name = "fuentefinanciamiento", schema = "ap")
@SequenceGenerator(name="fuentefinanciamiento_id_seq", sequenceName="ap.fuentefinanciamiento_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fuentefinanciamiento.findAll", query = "SELECT f FROM Fuentefinanciamiento f"),
    @NamedQuery(name = "Fuentefinanciamiento.findById", query = "SELECT f FROM Fuentefinanciamiento f WHERE f.id = :id"),
    @NamedQuery(name = "Fuentefinanciamiento.findByFuentefinanciamiento", query = "SELECT f FROM Fuentefinanciamiento f WHERE f.fuentefinanciamiento = :fuentefinanciamiento")})
public class Fuentefinanciamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="fuentefinanciamiento_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "fuentefinanciamiento")
    private String fuentefinanciamiento;

    public Fuentefinanciamiento() {
    }

    public Fuentefinanciamiento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFuentefinanciamiento() {
        return fuentefinanciamiento;
    }

    public void setFuentefinanciamiento(String fuentefinanciamiento) {
        this.fuentefinanciamiento = fuentefinanciamiento;
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
        if (!(object instanceof Fuentefinanciamiento)) {
            return false;
        }
        Fuentefinanciamiento other = (Fuentefinanciamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.fuentefinanciamiento;
    }
    
}
