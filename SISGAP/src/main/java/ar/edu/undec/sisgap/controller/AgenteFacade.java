/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Agente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class AgenteFacade extends AbstractFacade<Agente> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgenteFacade() {
        super(Agente.class);
    }
    
    public Agente agentedocumento(String documento){
        System.out.println("--------------"+documento);
        try{
           return em.createQuery("select a from Agente a where a.numerodocumento like '"+documento+"%'", Agente.class).getResultList().get(0); 
        }catch(Exception e){
            return null;
        }
        
    }
    
    public List<Agente> filtroDocumentooApellido(String documentooapellido){
        try{
           System.out.println("ffffffffffffffffffffffffffffffff"+em.createNativeQuery("select * from ap.agente where numerodocumento like '%"+documentooapellido+"%' or apellido like '%"+documentooapellido+"%'", Agente.class).getResultList().size()); 
           return em.createQuery("select a from Agente a where a.numerodocumento like '"+documentooapellido+"%' or a.apellido like '%"+documentooapellido+"%'", Agente.class).getResultList(); 
        }catch(Exception e){
            return null;
        }
    }
}
