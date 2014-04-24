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
public class PresupuestoRubroPK implements Serializable {
    @Basic(optional = true)
    @NotNull
    @Column(name = "presupuestoid")
    private int presupuestoid;
    @Basic(optional = true)
    @NotNull
    @Column(name = "rubroid")
    private int rubroid;

    public PresupuestoRubroPK() {
    }

    public PresupuestoRubroPK(int presupuestoid, int rubroid) {
        this.presupuestoid = presupuestoid;
        this.rubroid = rubroid;
    }

    public int getPresupuestoid() {
        return presupuestoid;
    }

    public void setPresupuestoid(int presupuestoid) {
        this.presupuestoid = presupuestoid;
    }

    public int getRubroid() {
        return rubroid;
    }

    public void setRubroid(int rubroid) {
        this.rubroid = rubroid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) presupuestoid;
        hash += (int) rubroid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoRubroPK)) {
            return false;
        }
        PresupuestoRubroPK other = (PresupuestoRubroPK) object;
        if (this.presupuestoid != other.presupuestoid) {
            return false;
        }
        if (this.rubroid != other.rubroid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.PresupuestoRubroPK[ presupuestoid=" + presupuestoid + ", rubroid=" + rubroid + " ]";
    }
    
}
