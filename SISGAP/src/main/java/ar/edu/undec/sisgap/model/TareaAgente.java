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
@Table(name = "tarea_agente", schema = "ap")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TareaAgente.findAll", query = "SELECT t FROM TareaAgente t"),
    @NamedQuery(name = "TareaAgente.findByTareaid", query = "SELECT t FROM TareaAgente t WHERE t.tareaAgentePK.tareaid = :tareaid"),
    @NamedQuery(name = "TareaAgente.findByAgenteid", query = "SELECT t FROM TareaAgente t WHERE t.tareaAgentePK.agenteid = :agenteid"),
    @NamedQuery(name = "TareaAgente.findByFuncion", query = "SELECT t FROM TareaAgente t WHERE t.funcion = :funcion")})
public class TareaAgente implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TareaAgentePK tareaAgentePK;
    @Size(max = 50)
    @Column(name = "funcion")
    private String funcion;
    @JoinColumn(name = "tareaid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tarea tarea;
    @JoinColumn(name = "agenteid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Agente agente;

    public TareaAgente() {
    }

    public TareaAgente(TareaAgentePK tareaAgentePK) {
        this.tareaAgentePK = tareaAgentePK;
    }

    public TareaAgente(int tareaid, int agenteid) {
        this.tareaAgentePK = new TareaAgentePK(tareaid, agenteid);
    }

    public TareaAgentePK getTareaAgentePK() {
        return tareaAgentePK;
    }

    public void setTareaAgentePK(TareaAgentePK tareaAgentePK) {
        this.tareaAgentePK = tareaAgentePK;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }

    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tareaAgentePK != null ? tareaAgentePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TareaAgente)) {
            return false;
        }
        TareaAgente other = (TareaAgente) object;
        if ((this.tareaAgentePK == null && other.tareaAgentePK != null) || (this.tareaAgentePK != null && !this.tareaAgentePK.equals(other.tareaAgentePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.edu.undec.sisgap.model.TareaAgente[ tareaAgentePK=" + tareaAgentePK + " ]";
    }
    
}
