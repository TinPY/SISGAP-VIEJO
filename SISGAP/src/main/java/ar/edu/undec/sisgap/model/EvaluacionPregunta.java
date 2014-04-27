/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "evaluacion_pregunta", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluacionPregunta.findAll", query = "SELECT e FROM EvaluacionPregunta e"),
    @NamedQuery(name = "EvaluacionPregunta.findByEvaluacionid", query = "SELECT e FROM EvaluacionPregunta e WHERE e.evaluacionPreguntaPK.evaluacionid = :evaluacionid"),
    @NamedQuery(name = "EvaluacionPregunta.findByPreguntaid", query = "SELECT e FROM EvaluacionPregunta e WHERE e.evaluacionPreguntaPK.preguntaid = :preguntaid"),
    @NamedQuery(name = "EvaluacionPregunta.findByRating", query = "SELECT e FROM EvaluacionPregunta e WHERE e.rating = :rating"),
    @NamedQuery(name = "EvaluacionPregunta.findByObservacion", query = "SELECT e FROM EvaluacionPregunta e WHERE e.observacion = :observacion"),
    @NamedQuery(name = "EvaluacionPregunta.findByAprobado", query = "SELECT e FROM EvaluacionPregunta e WHERE e.aprobado = :aprobado")})
public class EvaluacionPregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EvaluacionPreguntaPK evaluacionPreguntaPK;
    @Column(name = "rating")
    private Integer rating;
    @Size(max = 2147483647)
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "aprobado")
    private Boolean aprobado;
    @JoinColumn(name = "preguntaid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Pregunta pregunta;
    @JoinColumn(name = "evaluacionid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evaluacion evaluacion;

    public EvaluacionPregunta() {
    }

    public EvaluacionPregunta(EvaluacionPreguntaPK evaluacionPreguntaPK) {
        this.evaluacionPreguntaPK = evaluacionPreguntaPK;
    }

    public EvaluacionPregunta(int evaluacionid, int preguntaid) {
        this.evaluacionPreguntaPK = new EvaluacionPreguntaPK(evaluacionid, preguntaid);
    }

    public EvaluacionPreguntaPK getEvaluacionPreguntaPK() {
        return evaluacionPreguntaPK;
    }

    public void setEvaluacionPreguntaPK(EvaluacionPreguntaPK evaluacionPreguntaPK) {
        this.evaluacionPreguntaPK = evaluacionPreguntaPK;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getAprobado() {
        return aprobado;
    }

    public void setAprobado(Boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (evaluacionPreguntaPK != null ? evaluacionPreguntaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPregunta)) {
            return false;
        }
        EvaluacionPregunta other = (EvaluacionPregunta) object;
        if ((this.evaluacionPreguntaPK == null && other.evaluacionPreguntaPK != null) || (this.evaluacionPreguntaPK != null && !this.evaluacionPreguntaPK.equals(other.evaluacionPreguntaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.EvaluacionPregunta[ evaluacionPreguntaPK=" + evaluacionPreguntaPK + " ]";
    }
    
}
