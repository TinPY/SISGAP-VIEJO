/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "pregunta", schema = "ap")
@SequenceGenerator(name="pregunta_id_seq", sequenceName="ap.pregunta_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findById", query = "SELECT p FROM Pregunta p WHERE p.id = :id"),
    @NamedQuery(name = "Pregunta.findByPregunta", query = "SELECT p FROM Pregunta p WHERE p.pregunta = :pregunta"),
    @NamedQuery(name = "Pregunta.findByProyecto", query = "SELECT p FROM Pregunta p WHERE p.proyecto = :proyecto")})
public class Pregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="pregunta_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "pregunta")
    private String pregunta;
    @Column(name = "proyecto")
    private Boolean proyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregunta")
    private List<EvaluacionPregunta> evaluacionPreguntaList;

    public Pregunta() {
    }

    public Pregunta(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Boolean getProyecto() {
        return proyecto;
    }

    public void setProyecto(Boolean proyecto) {
        this.proyecto = proyecto;
    }

    @XmlTransient
    public List<EvaluacionPregunta> getEvaluacionPreguntaList() {
        return evaluacionPreguntaList;
    }

    public void setEvaluacionPreguntaList(List<EvaluacionPregunta> evaluacionPreguntaList) {
        this.evaluacionPreguntaList = evaluacionPreguntaList;
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
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.Pregunta[ id=" + id + " ]";
    }
    
}
