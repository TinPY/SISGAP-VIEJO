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
 * @author mPY
 */
@Embeddable
public class ProyectoAgentePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "agenteid")
    private int agenteid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "proyectoid")
    private int proyectoid;

    public ProyectoAgentePK() {
    }

    public ProyectoAgentePK(int agenteid, int proyectoid) {
        this.agenteid = agenteid;
        this.proyectoid = proyectoid;
    }

    public int getAgenteid() {
        return agenteid;
    }

    public void setAgenteid(int agenteid) {
        this.agenteid = agenteid;
    }

    public int getProyectoid() {
        return proyectoid;
    }

    public void setProyectoid(int proyectoid) {
        this.proyectoid = proyectoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) agenteid;
        hash += (int) proyectoid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProyectoAgentePK)) {
            return false;
        }
        ProyectoAgentePK other = (ProyectoAgentePK) object;
        if (this.agenteid != other.agenteid) {
            return false;
        }
        if (this.proyectoid != other.proyectoid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.ProyectoAgentePK[ agenteid=" + agenteid + ", proyectoid=" + proyectoid + " ]";
    }
    
}
