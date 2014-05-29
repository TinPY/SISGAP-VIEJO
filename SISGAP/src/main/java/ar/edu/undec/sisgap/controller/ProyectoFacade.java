/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Proyecto;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author Hugo
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> {

    @PersistenceContext(unitName = "ar.edu.undec_SYSGAP_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }

    public List<Proyecto> buscarProyectoAgente(int agenteid) {

        List<Proyecto> proyecto;
        try {
            return em.createQuery("select p from Proyecto p join p.agenteid a where a.id = :id ", Proyecto.class).setParameter("id", agenteid).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta" + e);
            return null;
        }
    }

    public List<Proyecto> buscarProyectoEstado(int idEstado) {

        List<Proyecto> proyecto;
        try {
            return em.createQuery("select p from Proyecto p join p.agenteid a where p.estadoproyectoid.id = :estado", Proyecto.class).setParameter("estado", idEstado).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta" + e);
            return null;
        }
    }

    public List<Proyecto> buscarProyectoFecha(Date fecha) {
        try {
            return em.createQuery("select p from Proyecto p join p.agenteid a where p.fecha = :fechaBuscada", Proyecto.class).setParameter("fechaBuscada", fecha).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta" + e);
            return null;
        }
    }

    public List<Proyecto> buscarProyectoEntreFechas(Date fechaInicio, Date fechaFin) {
        List<Proyecto> lista;
        
        try {
            lista= em.createQuery("SELECT p FROM Proyecto p join p.agenteid a WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin", Proyecto.class).setParameter("fechaInicio", fechaInicio, TemporalType.DATE).setParameter("fechaFin", fechaFin, TemporalType.DATE).getResultList();
        } catch (Exception e) {
            System.out.println("No se pudo realizar la consulta en el rango de fechas" + e);
            lista = null;
        }
//        System.out.println(lista.toString());
        
        return lista;
    }

}
