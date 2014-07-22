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
@Table(name = "tipocomitente", schema = "ap")
@SequenceGenerator(name="tipocomitente_id_seq", sequenceName="ap.tipocomitente_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipocomitente.findAll", query = "SELECT t FROM Tipocomitente t"),
    @NamedQuery(name = "Tipocomitente.findByTipocomitente", query = "SELECT t FROM Tipocomitente t WHERE t.tipocomitente = :tipocomitente"),
    @NamedQuery(name = "Tipocomitente.findById", query = "SELECT t FROM Tipocomitente t WHERE t.id = :id")})
public class Tipocomitente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "tipocomitente")
    private String tipocomitente;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tipocomitente_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(mappedBy = "tipocomitenteid")
    private List<Comitente> comitenteList;

    public Tipocomitente() {
    }

    public Tipocomitente(Integer id) {
        this.id = id;
    }

    public String getTipocomitente() {
        return tipocomitente;
    }

    public void setTipocomitente(String tipocomitente) {
        this.tipocomitente = tipocomitente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public List<Comitente> getComitenteList() {
        return comitenteList;
    }

    public void setComitenteList(List<Comitente> comitenteList) {
        this.comitenteList = comitenteList;
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
        if (!(object instanceof Tipocomitente)) {
            return false;
        }
        Tipocomitente other = (Tipocomitente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipocomitente ;
    }
    
}
