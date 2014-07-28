/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Archivoproyecto;
import ar.edu.undec.sisgap.model.Proyecto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Hugo
 */
@Stateless
public class ArchivoproyectoFacade extends AbstractFacade<Archivoproyecto> {
    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArchivoproyectoFacade() {
        super(Archivoproyecto.class);
    }
    
    public List<Archivoproyecto> buscarArchivosProyecto(int proyectoId) {

        try {
            return em.createQuery("select ap from Archivoproyecto ap join ap.proyectoid p where p.id = :id ", Archivoproyecto.class).setParameter("id", proyectoId).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta" + e);
            return null;
        }

    }
    
}
