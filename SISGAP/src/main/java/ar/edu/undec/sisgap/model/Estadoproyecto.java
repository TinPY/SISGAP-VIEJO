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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "estadoproyecto", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estadoproyecto.findAll", query = "SELECT e FROM Estadoproyecto e"),
    @NamedQuery(name = "Estadoproyecto.findById", query = "SELECT e FROM Estadoproyecto e WHERE e.id = :id"),
    @NamedQuery(name = "Estadoproyecto.findByEstado", query = "SELECT e FROM Estadoproyecto e WHERE e.estado = :estado"),
    @NamedQuery(name = "Estadoproyecto.findByDescripcion", query = "SELECT e FROM Estadoproyecto e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Estadoproyecto.findByEstadoabreviado", query = "SELECT e FROM Estadoproyecto e WHERE e.estadoabreviado = :estadoabreviado")})
public class Estadoproyecto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 3)
    @Column(name = "estadoabreviado")
    private String estadoabreviado;
    @Column(name = "proyecto")
    private Boolean proyecto;

    public Estadoproyecto() {
    }

    public Estadoproyecto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoabreviado() {
        return estadoabreviado;
    }

    public void setEstadoabreviado(String estadoabreviado) {
        this.estadoabreviado = estadoabreviado;
    }

    public Boolean getProyecto() {
        return proyecto;
    }

    public void setProyecto(Boolean proyecto) {
        this.proyecto = proyecto;
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
        if (!(object instanceof Estadoproyecto)) {
            return false;
        }
        Estadoproyecto other = (Estadoproyecto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return estado;
    }
    
}
