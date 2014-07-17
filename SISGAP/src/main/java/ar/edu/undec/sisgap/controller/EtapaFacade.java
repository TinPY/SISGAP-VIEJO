/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Etapa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class EtapaFacade extends AbstractFacade<Etapa> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapaFacade() {
        super(Etapa.class);
    }
    
    //Para reporte de proyecto (Gantt etapas)
    
    //Buscar las etapas de un proyecto
    
    public List<Etapa> buscarEtapasProyecto(int proyectoid){
        
        try {
            return em.createQuery("select e from Etapa e join e.proyectoid p where p.id = :id ", Etapa.class).setParameter("id", proyectoid).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta" + e);
            return null;
        }
    }
}
