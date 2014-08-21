/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Evaluacion;
import ar.edu.undec.sisgap.model.EvaluacionPregunta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class EvaluacionPreguntaFacade extends AbstractFacade<EvaluacionPregunta> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvaluacionPreguntaFacade() {
        super(EvaluacionPregunta.class);
    }
    
    public List<EvaluacionPregunta> listaEvaluaciones(int evaluacionId){
        try{
            return em.createQuery("SELECT ep FROM EvaluacionPregunta ep join ep.evaluacion e WHERE e.id = :id", EvaluacionPregunta.class).setParameter("id", evaluacionId).getResultList();
        }catch(Exception e){
            System.out.println("Error en query EvaluacionPreguntaFacade.listaEvaluaciones(int evaluacionId)");
            return null;
        }
        
    }
    
}
