/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller;

import ar.edu.undec.sisgap.model.Agente;
import ar.edu.undec.sisgap.model.Usuario;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author Hugo
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }
   
    public Usuario find2(String valor1, String valor2) {
        Query q=null;
         try{
         
         String queryStr = "Select u from Usuario u where u.usuarionombre like :usuarionombre and u.usuarioclave = :usuarioclave ";
            TypedQuery<Usuario> query = getEntityManager().createQuery(queryStr, Usuario.class);
            query.setParameter("usuarionombre", valor1.trim());
            query.setParameter("usuarioclave",valor2.trim());
         System.out.println("222222222222222"+valor1+new EncriptarSHA256().hash256(valor2));
         Usuario u= query.getSingleResult();
         u.setUsuarioestado('A');
            getEntityManager().merge(u);
       
        System.out.println("lllllllllll"+((Usuario)query.getSingleResult()).getUsuariofechaalta());
        return (Usuario)query.getSingleResult();
        }catch(Exception e){
               System.out.println("AbstractFacade"+e);
               return null;
        }
    
       
    }
     
     public Agente findAgente(String nombreusuario){
         String queryStr = "Select a from Agente a JOIN a.usuarioid u where u.usuarionombre like :usuarionombre ";
            TypedQuery<Agente> query = getEntityManager().createQuery(queryStr, Agente.class);
            query.setParameter("usuarionombre", nombreusuario.trim());
         
       System.out.println("------------------------------------"+((Agente) query.getSingleResult()).getApellido());
        return (Agente) query.getSingleResult();
    }
    public Usuario findUsuarioNombreEmail(String nombreusuario){
         String queryStr = "Select u from Usuario u where u.usuarionombre like :usuarionombre  ";
            TypedQuery<Usuario> query = getEntityManager().createQuery(queryStr, Usuario.class);
            query.setParameter("usuarionombre", nombreusuario.trim());
            
       System.out.println("------------------------------------"+((Usuario) query.getSingleResult()).getUsuariofechaalta());
        return (Usuario) query.getSingleResult();
    }
     
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
