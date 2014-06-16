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
public class TareaAgentePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "tareaid")
    private int tareaid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "agenteid")
    private int agenteid;

    public TareaAgentePK() {
    }

    public TareaAgentePK(int tareaid, int agenteid) {
        this.tareaid = tareaid;
        this.agenteid = agenteid;
    }

    public int getTareaid() {
        return tareaid;
    }

    public void setTareaid(int tareaid) {
        this.tareaid = tareaid;
    }

    public int getAgenteid() {
        return agenteid;
    }

    public void setAgenteid(int agenteid) {
        this.agenteid = agenteid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tareaid;
        hash += (int) agenteid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaAgentePK)) {
            return false;
        }
        TareaAgentePK other = (TareaAgentePK) object;
        if (this.tareaid != other.tareaid) {
            return false;
        }
        if (this.agenteid != other.agenteid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.TareaAgentePK[ tareaid=" + tareaid + ", agenteid=" + agenteid + " ]";
    }
    
}
