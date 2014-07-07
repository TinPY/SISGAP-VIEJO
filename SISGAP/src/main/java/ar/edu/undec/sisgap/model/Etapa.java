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
@Table(name = "etapa", schema = "ap")
@SequenceGenerator(name="etapa_id_seq", sequenceName="ap.etapa_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etapa.findAll", query = "SELECT e FROM Etapa e"),
    @NamedQuery(name = "Etapa.findById", query = "SELECT e FROM Etapa e WHERE e.id = :id"),
    @NamedQuery(name = "Etapa.findByEtapa", query = "SELECT e FROM Etapa e WHERE e.etapa = :etapa"),
    @NamedQuery(name = "Etapa.findByFechainicio", query = "SELECT e FROM Etapa e WHERE e.fechainicio = :fechainicio"),
    @NamedQuery(name = "Etapa.findByFechafin", query = "SELECT e FROM Etapa e WHERE e.fechafin = :fechafin"),
    @NamedQuery(name = "Etapa.findByDias", query = "SELECT e FROM Etapa e WHERE e.dias = :dias"),
    @NamedQuery(name = "Etapa.findByEstado", query = "SELECT e FROM Etapa e WHERE e.estado = :estado")})
public class Etapa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="etapa_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "etapa")
    private String etapa;
    @Column(name = "fechainicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @Column(name = "fechafin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechafin;
    @Column(name = "dias")
    private Integer dias;
    @Size(max = 30)
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "proyectoid", referencedColumnName = "id")
    @ManyToOne
    private Proyecto proyectoid;
    @OneToMany(mappedBy = "etapaid")
    private List<Tarea> tareaList;

    public Etapa() {
    }

    public Etapa(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Proyecto getProyectoid() {
        return proyectoid;
    }

    public void setProyectoid(Proyecto proyectoid) {
        this.proyectoid = proyectoid;
    }
    
    

    @XmlTransient
    public List<Tarea> getTareaList() {
        return tareaList;
    }

    public void setTareaList(List<Tarea> tareaList) {
        this.tareaList = tareaList;
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
        if (!(object instanceof Etapa)) {
            return false;
        }
        Etapa other = (Etapa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return etapa;
    }
    
}
