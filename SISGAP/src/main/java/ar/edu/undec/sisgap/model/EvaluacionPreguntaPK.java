/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Hugo
 */
@Embeddable
public class EvaluacionPreguntaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "evaluacionid")
    private int evaluacionid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "preguntaid")
    private int preguntaid;

    public EvaluacionPreguntaPK() {
    }

    public EvaluacionPreguntaPK(int evaluacionid, int preguntaid) {
        this.evaluacionid = evaluacionid;
        this.preguntaid = preguntaid;
    }

    public int getEvaluacionid() {
        return evaluacionid;
    }

    public void setEvaluacionid(int evaluacionid) {
        this.evaluacionid = evaluacionid;
    }

    public int getPreguntaid() {
        return preguntaid;
    }

    public void setPreguntaid(int preguntaid) {
        this.preguntaid = preguntaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) evaluacionid;
        hash += (int) preguntaid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionPreguntaPK)) {
            return false;
        }
        EvaluacionPreguntaPK other = (EvaluacionPreguntaPK) object;
        if (this.evaluacionid != other.evaluacionid) {
            return false;
        }
        if (this.preguntaid != other.preguntaid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.EvaluacionPreguntaPK[ evaluacionid=" + evaluacionid + ", preguntaid=" + preguntaid + " ]";
    }
    
}
