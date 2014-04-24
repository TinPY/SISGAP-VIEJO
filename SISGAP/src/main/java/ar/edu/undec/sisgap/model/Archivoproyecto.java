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
@Table(name = "archivoproyecto", schema = "ap")
@SequenceGenerator(name="archivoproyecto_id_seq", sequenceName="ap.archivoproyecto_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Archivoproyecto.findAll", query = "SELECT a FROM Archivoproyecto a"),
    @NamedQuery(name = "Archivoproyecto.findById", query = "SELECT a FROM Archivoproyecto a WHERE a.id = :id"),
    @NamedQuery(name = "Archivoproyecto.findByLink", query = "SELECT a FROM Archivoproyecto a WHERE a.link = :link"),
    @NamedQuery(name = "Archivoproyecto.findByNombre", query = "SELECT a FROM Archivoproyecto a WHERE a.nombre = :nombre")})
public class Archivoproyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="archivoproyecto_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "link")
    private String link;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    //@Lob
    @Column(name = "archivo")
    private byte[] archivo;
    @JoinColumn(name = "proyectoid", referencedColumnName = "id")
    @ManyToOne
    private Proyecto proyectoid;
    

    public Archivoproyecto() {
    }

    public Archivoproyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public Proyecto getProyectoid() {
        return proyectoid;
    }

    public void setProyectoid(Proyecto proyectoid) {
        this.proyectoid = proyectoid;
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
        if (!(object instanceof Archivoproyecto)) {
            return false;
        }
        Archivoproyecto other = (Archivoproyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.Archivoproyecto[ id=" + id + " ]";
    }
    
}
