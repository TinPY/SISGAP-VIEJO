/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Estadoproyecto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class EstadoproyectoFacade extends AbstractFacade<Estadoproyecto> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstadoproyectoFacade() {
        super(Estadoproyecto.class);
    }
    
    public List<Estadoproyecto> finporProyecto(Boolean x){
        return em.createQuery("select e from Estadoproyecto e where e.proyecto="+x, Estadoproyecto.class).getResultList();
    }
    
}
