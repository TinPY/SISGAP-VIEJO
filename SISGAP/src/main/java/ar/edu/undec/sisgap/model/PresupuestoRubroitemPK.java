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
public class PresupuestoRubroitemPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "rubroid")
    private int rubroid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "presupuestoid")
    private int presupuestoid;

    public PresupuestoRubroitemPK() {
    }

    public PresupuestoRubroitemPK(int rubroid, int presupuestoid) {
        this.rubroid = rubroid;
        this.presupuestoid = presupuestoid;
    }

    public int getRubroid() {
        return rubroid;
    }

    public void setRubroid(int rubroid) {
        this.rubroid = rubroid;
    }

    public int getPresupuestoid() {
        return presupuestoid;
    }

    public void setPresupuestoid(int presupuestoid) {
        this.presupuestoid = presupuestoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rubroid;
        hash += (int) presupuestoid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PresupuestoRubroitemPK)) {
            return false;
        }
        PresupuestoRubroitemPK other = (PresupuestoRubroitemPK) object;
        if (this.rubroid != other.rubroid) {
            return false;
        }
        if (this.presupuestoid != other.presupuestoid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.PresupuestoRubroitemPK[ rubroid=" + rubroid + ", presupuestoid=" + presupuestoid + " ]";
    }
    
}
