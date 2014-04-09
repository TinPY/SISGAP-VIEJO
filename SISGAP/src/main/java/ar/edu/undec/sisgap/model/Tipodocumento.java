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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "tipodocumento", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipodocumento.findAll", query = "SELECT t FROM Tipodocumento t"),
    @NamedQuery(name = "Tipodocumento.findByTipodocumentoid", query = "SELECT t FROM Tipodocumento t WHERE t.tipodocumentoid = :tipodocumentoid"),
    @NamedQuery(name = "Tipodocumento.findByTipodocumento", query = "SELECT t FROM Tipodocumento t WHERE t.tipodocumento = :tipodocumento")})
public class Tipodocumento implements Serializable {
    private static final long serialVersionUID = 1L;
   
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Basic(optional = false)
    @Column(name = "tipodocumentoid")
    private Integer tipodocumentoid;
   
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "tipodocumento")
    private String tipodocumento;
    
    @OneToMany(mappedBy = "tipodocumentoid")
    private List<Agente> agenteList;

    public Tipodocumento() {
    }

    public Tipodocumento(Integer tipodocumentoid) {
        this.tipodocumentoid = tipodocumentoid;
    }

    public Tipodocumento(Integer tipodocumentoid, String tipodocumento) {
        this.tipodocumentoid = tipodocumentoid;
        this.tipodocumento = tipodocumento;
    }

    public Integer getTipodocumentoid() {
        return tipodocumentoid;
    }

    public void setTipodocumentoid(Integer tipodocumentoid) {
        this.tipodocumentoid = tipodocumentoid;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    @XmlTransient
    public List<Agente> getAgenteList() {
        return agenteList;
    }

    public void setAgenteList(List<Agente> agenteList) {
        this.agenteList = agenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipodocumentoid != null ? tipodocumentoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipodocumento)) {
            return false;
        }
        Tipodocumento other = (Tipodocumento) object;
        if ((this.tipodocumentoid == null && other.tipodocumentoid != null) || (this.tipodocumentoid != null && !this.tipodocumentoid.equals(other.tipodocumentoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tipodocumento;
    }
    
}
