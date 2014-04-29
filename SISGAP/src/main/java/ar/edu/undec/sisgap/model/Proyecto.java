/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "proyecto", schema = "ap")
@SequenceGenerator(name="proyecto_id_seq", sequenceName="ap.proyecto_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByFecha", query = "SELECT p FROM Proyecto p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByResumen", query = "SELECT p FROM Proyecto p WHERE p.resumen = :resumen"),
    @NamedQuery(name = "Proyecto.findByObservaciones", query = "SELECT p FROM Proyecto p WHERE p.observaciones = :observaciones"),
    @NamedQuery(name = "Proyecto.findByDuracion", query = "SELECT p FROM Proyecto p WHERE p.duracion = :duracion"),
    @NamedQuery(name = "Proyecto.findById", query = "SELECT p FROM Proyecto p WHERE p.id = :id"),
    @NamedQuery(name = "Proyecto.findByCudap", query = "SELECT p FROM Proyecto p WHERE p.cudap = :cudap")})
public class Proyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Size(max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "resumen")
    private String resumen;
    //@Lob
    @Column(name = "documentacion")
    private byte[] documentacion;
    @Column(name = "documentacionnombre")
    private String documentacionnombre;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 2147483647)
    @Column(name = "localizacion")
    private String localizacion;
    @Column(name = "duracion")
    private Short duracion;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="proyecto_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "cudap")
    private String cudap;
    @JoinColumn(name = "tipoproyectoid", referencedColumnName = "id")
    @ManyToOne
    private Tipoproyecto tipoproyectoid;
    @JoinColumn(name = "convocatoriaid", referencedColumnName = "id")
    @ManyToOne
    private Convocatoria convocatoriaid;
    @JoinColumn(name = "comitenteid", referencedColumnName = "id")
    @ManyToOne
    private Comitente comitenteid;
    @JoinColumn(name = "agenteid", referencedColumnName = "id")
    @ManyToOne
    private Agente agenteid;
    @JoinColumn(name = "beneficiarioid", referencedColumnName = "id")
    @ManyToOne
    private Beneficiario beneficiarioid;
    @JoinColumn(name = "tipofinanciamientoid", referencedColumnName = "id")
    @ManyToOne
    private Tipofinanciamiento tipofinanciamientoid;
    @JoinColumn(name = "fuentefinanciamientoid", referencedColumnName = "id")
    @ManyToOne
    private Fuentefinanciamiento fuentefinanciamientoid;
    @JoinColumn(name = "avanceproyectoid", referencedColumnName = "id")
    @ManyToOne
    private Avanceproyecto avanceproyectoid;
    @JoinColumn(name = "estadoproyectoid", referencedColumnName = "id")
    @ManyToOne
    private Estadoproyecto estadoproyectoid;

    public Proyecto() {
    }

    public Proyecto(Integer id) {
        this.id = id;
    }

    public Proyecto(Integer id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public byte[] getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(byte[] documentacion) {
        
        this.documentacion = documentacion;
    }

    public String getDocumentacionnombre() {
        return documentacionnombre;
    }

    public void setDocumentacionnombre(String documentacionnombre) {
        this.documentacionnombre = documentacionnombre;
    }

    
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    
    
    
    public Short getDuracion() {
        return duracion;
    }

    public void setDuracion(Short duracion) {
        this.duracion = duracion;
    }

   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCudap() {
        return cudap;
    }

    public void setCudap(String cudap) {
        this.cudap = cudap;
    }

    public Tipoproyecto getTipoproyectoid() {
        return tipoproyectoid;
    }

    public void setTipoproyectoid(Tipoproyecto tipoproyectoid) {
        this.tipoproyectoid = tipoproyectoid;
    }

    public Convocatoria getConvocatoriaid() {
        return convocatoriaid;
    }

    public void setConvocatoriaid(Convocatoria convocatoriaid) {
        this.convocatoriaid = convocatoriaid;
    }

    public Comitente getComitenteid() {
        return comitenteid;
    }

    public void setComitenteid(Comitente comitenteid) {
        this.comitenteid = comitenteid;
    }

    public Agente getAgenteid() {
        return agenteid;
    }

    public void setAgenteid(Agente agenteid) {
        this.agenteid = agenteid;
    }

    public Beneficiario getBeneficiarioid() {
        return beneficiarioid;
    }

    public void setBeneficiarioid(Beneficiario beneficiarioid) {
        this.beneficiarioid = beneficiarioid;
    }

    public Tipofinanciamiento getTipofinanciamientoid() {
        return tipofinanciamientoid;
    }

    public void setTipofinanciamientoid(Tipofinanciamiento tipofinanciamientoid) {
        this.tipofinanciamientoid = tipofinanciamientoid;
    }

    public Fuentefinanciamiento getFuentefinanciamientoid() {
        return fuentefinanciamientoid;
    }

    public void setFuentefinanciamientoid(Fuentefinanciamiento fuentefinanciamientoid) {
        this.fuentefinanciamientoid = fuentefinanciamientoid;
    }

    public Avanceproyecto getAvanceproyectoid() {
        return avanceproyectoid;
    }

    public void setAvanceproyectoid(Avanceproyecto avanceproyectoid) {
        this.avanceproyectoid = avanceproyectoid;
    }

    public Estadoproyecto getEstadoproyectoid() {
        return estadoproyectoid;
    }

    public void setEstadoproyectoid(Estadoproyecto estadoproyectoid) {
        this.estadoproyectoid = estadoproyectoid;
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
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "N° de Proyecto" + id + " ]";
    }
    
}
