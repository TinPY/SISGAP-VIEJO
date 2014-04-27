/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Convocatoria;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Hugo
 */
@Stateless
public class ConvocatoriaFacade extends AbstractFacade<Convocatoria> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConvocatoriaFacade() {
        super(Convocatoria.class);
    }
    
     public List<Convocatoria> findConvocatoriafinanciamiento(Integer tipoproyectoid,Integer tipofinanciamientoid){
         String queryStr = "Select c from Convocatoria c where c.tipofinanciamientoid.id = :tipofinanciamientoid ";
            TypedQuery<Convocatoria> query = getEntityManager().createQuery(queryStr, Convocatoria.class);
            query.setParameter("tipofinanciamientoid", tipofinanciamientoid);
            
      // System.out.println("------------------------------------"+((tipofinanciamientoid) query.getSingleResult()).getUsuariofechaalta());
        return query.getResultList();
    }
    
}
