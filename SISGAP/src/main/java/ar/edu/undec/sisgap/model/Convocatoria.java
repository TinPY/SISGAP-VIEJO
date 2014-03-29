/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "convocatoria", schema = "ap")
@SequenceGenerator(name="convocatoria_id_seq", sequenceName="ap.convocatoria_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convocatoria.findAll", query = "SELECT c FROM Convocatoria c"),
    @NamedQuery(name = "Convocatoria.findById", query = "SELECT c FROM Convocatoria c WHERE c.id = :id"),
    @NamedQuery(name = "Convocatoria.findByConvocatoria", query = "SELECT c FROM Convocatoria c WHERE c.convocatoria = :convocatoria"),
    @NamedQuery(name = "Convocatoria.findByDescripcion", query = "SELECT c FROM Convocatoria c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Convocatoria.findByLink", query = "SELECT c FROM Convocatoria c WHERE c.link = :link"),
    @NamedQuery(name = "Convocatoria.findByFechapublicacion", query = "SELECT c FROM Convocatoria c WHERE c.fechapublicacion = :fechapublicacion"),
    @NamedQuery(name = "Convocatoria.findByFechainicio", query = "SELECT c FROM Convocatoria c WHERE c.fechainicio = :fechainicio"),
    @NamedQuery(name = "Convocatoria.findByFechacierre", query = "SELECT c FROM Convocatoria c WHERE c.fechacierre = :fechacierre")})
public class Convocatoria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="convocatoria_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 150)
    @Column(name = "convocatoria")
    private String convocatoria;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    //@Lob
    @Column(name = "bases")
    private byte[] bases;
    @Size(max = 2147483647)
    @Column(name = "link")
    private String link;
    //@Lob
    @Column(name = "formulario")
    private byte[] formulario;
    @Column(name = "fechapublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapublicacion;
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Column(name = "fechacierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechacierre;
    @JoinColumn(name = "tipoproyectoid", referencedColumnName = "id")
    @ManyToOne
    private Tipoproyecto tipoproyectoid;
    @OneToMany(mappedBy = "convocatoriaid")
    private List<Proyecto> proyectoList;

    public Convocatoria() {
    }

    public Convocatoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getBases() {
        return bases;
    }

    public void setBases(byte[] bases) {
        this.bases = bases;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getFormulario() {
        return formulario;
    }

    public void setFormulario(byte[] formulario) {
        this.formulario = formulario;
    }

    public Date getFechapublicacion() {
        return fechapublicacion;
    }

    public void setFechapublicacion(Date fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechacierre() {
        return fechacierre;
    }

    public void setFechacierre(Date fechacierre) {
        this.fechacierre = fechacierre;
    }

    public Tipoproyecto getTipoproyectoid() {
        return tipoproyectoid;
    }

    public void setTipoproyectoid(Tipoproyecto tipoproyectoid) {
        this.tipoproyectoid = tipoproyectoid;
    }

    @XmlTransient
    public List<Proyecto> getProyectoList() {
        return proyectoList;
    }

    public void setProyectoList(List<Proyecto> proyectoList) {
        this.proyectoList = proyectoList;
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
        if (!(object instanceof Convocatoria)) {
            return false;
        }
        Convocatoria other = (Convocatoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.convocatoria;
    }
    
}
