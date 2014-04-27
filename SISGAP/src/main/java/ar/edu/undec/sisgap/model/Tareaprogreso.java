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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "tareaprogreso", schema = "ap")
@SequenceGenerator(name="tareaprogreso_id_seq", sequenceName="ap.tareaprogreso_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tareaprogreso.findAll", query = "SELECT t FROM Tareaprogreso t"),
    @NamedQuery(name = "Tareaprogreso.findById", query = "SELECT t FROM Tareaprogreso t WHERE t.id = :id"),
    @NamedQuery(name = "Tareaprogreso.findByFecha", query = "SELECT t FROM Tareaprogreso t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "Tareaprogreso.findByProgreso", query = "SELECT t FROM Tareaprogreso t WHERE t.progreso = :progreso"),
    @NamedQuery(name = "Tareaprogreso.findByEstado", query = "SELECT t FROM Tareaprogreso t WHERE t.estado = :estado")})
public class Tareaprogreso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tareaprogreso_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "progreso")
    private Integer progreso;
    @Column(name = "estado")
    private Character estado;
    @JoinColumn(name = "tareaid", referencedColumnName = "id")
    @ManyToOne
    private Tarea tareaid;

    public Tareaprogreso() {
    }

    public Tareaprogreso(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getProgreso() {
        return progreso;
    }

    public void setProgreso(Integer progreso) {
        this.progreso = progreso;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }

    public Tarea getTareaid() {
        return tareaid;
    }

    public void setTareaid(Tarea tareaid) {
        this.tareaid = tareaid;
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
        if (!(object instanceof Tareaprogreso)) {
            return false;
        }
        Tareaprogreso other = (Tareaprogreso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.Tareaprogreso[ id=" + id + " ]";
    }
    
}
