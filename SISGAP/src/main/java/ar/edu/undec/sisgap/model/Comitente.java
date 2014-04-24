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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "comitente", schema = "ap")
@SequenceGenerator(name="comitente_id_seq", sequenceName="ap.comitente_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comitente.findAll", query = "SELECT e FROM Comitente e"),
    @NamedQuery(name = "Comitente.findById", query = "SELECT e FROM Comitente e WHERE e.id = :id"),
    @NamedQuery(name = "Comitente.findByRazonsocial", query = "SELECT e FROM Comitente e WHERE e.razonsocial = :razonsocial"),
    @NamedQuery(name = "Comitente.findByCuit", query = "SELECT e FROM Comitente e WHERE e.cuit = :cuit"),
    @NamedQuery(name = "Comitente.findByTelefono", query = "SELECT e FROM Comitente e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Comitente.findByEmail", query = "SELECT e FROM Comitente e WHERE e.email = :email")})
public class Comitente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="comitente_id_seq")
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Size(max = 20)
    @Column(name = "cuit")
    private String cuit;
    @Size(max = 25)
    @Column(name = "telefono")
    private String telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "tipocomitenteid", referencedColumnName = "id")
    @ManyToOne
    private Tipocomitente tipocomitenteid;
    @Size(max = 150)
    @Column(name = "contacto")
    private String contacto;
    @Size(max = 150)
    @Column(name = "cargocontacto")
    private String cargocontacto;
    @JoinColumn(name = "sectorid", referencedColumnName = "id")
    @ManyToOne
    private Sector sectorid;

    public Comitente() {
    }

    public Comitente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Tipocomitente getTipocomitenteid() {
        return tipocomitenteid;
    }

    public void setTipocomitenteid(Tipocomitente tipocomitenteid) {
        this.tipocomitenteid = tipocomitenteid;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCargocontacto() {
        return cargocontacto;
    }

    public void setCargocontacto(String cargocontacto) {
        this.cargocontacto = cargocontacto;
    }

    public Sector getSectorid() {
        return sectorid;
    }

    public void setSectorid(Sector sectorid) {
        this.sectorid = sectorid;
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
        if (!(object instanceof Comitente)) {
            return false;
        }
        Comitente other = (Comitente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if(this.id>1){
            return cuit+" | "+ razonsocial;
        }else{
            return "NO";
        }
    }
    
    
    
}
