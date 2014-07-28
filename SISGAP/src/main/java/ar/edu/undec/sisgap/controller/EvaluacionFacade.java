/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Evaluacion;
import ar.edu.undec.sisgap.model.Proyecto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class EvaluacionFacade extends AbstractFacade<Evaluacion> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvaluacionFacade() {
        super(Evaluacion.class);
    }
    
    public Evaluacion obtenerEvaluacionPorProyecto(int proyectoId){
        //return em.createQuery("select p from Proyecto p join p.agenteid a where a.id = :id ", Proyecto.class).setParameter("id", agenteid).getResultList();
        return em.createQuery("SELECT e FROM Evaluacion e join e.proyectoid p WHERE p.id = :id", Evaluacion.class).setParameter("id", proyectoId).getSingleResult();
    }
    
}
