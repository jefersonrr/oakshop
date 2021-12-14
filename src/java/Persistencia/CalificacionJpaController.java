/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Calificacion;
import DTO.CalificacionPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Persona;
import DTO.Producto;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class CalificacionJpaController implements Serializable {

    public CalificacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Calificacion calificacion) throws PreexistingEntityException, Exception {
        if (calificacion.getCalificacionPK() == null) {
            calificacion.setCalificacionPK(new CalificacionPK());
        }
        calificacion.getCalificacionPK().setIdCliente(calificacion.getPersona().getCedula());
        calificacion.getCalificacionPK().setIdProducto(calificacion.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = calificacion.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getCedula());
                calificacion.setPersona(persona);
            }
            Producto producto = calificacion.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                calificacion.setProducto(producto);
            }
            em.persist(calificacion);
            if (persona != null) {
                persona.getCalificacionList().add(calificacion);
                persona = em.merge(persona);
            }
            if (producto != null) {
                producto.getCalificacionList().add(calificacion);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCalificacion(calificacion.getCalificacionPK()) != null) {
                throw new PreexistingEntityException("Calificacion " + calificacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Calificacion calificacion) throws NonexistentEntityException, Exception {
        calificacion.getCalificacionPK().setIdCliente(calificacion.getPersona().getCedula());
        calificacion.getCalificacionPK().setIdProducto(calificacion.getProducto().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion persistentCalificacion = em.find(Calificacion.class, calificacion.getCalificacionPK());
            Persona personaOld = persistentCalificacion.getPersona();
            Persona personaNew = calificacion.getPersona();
            Producto productoOld = persistentCalificacion.getProducto();
            Producto productoNew = calificacion.getProducto();
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getCedula());
                calificacion.setPersona(personaNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                calificacion.setProducto(productoNew);
            }
            calificacion = em.merge(calificacion);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getCalificacionList().remove(calificacion);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getCalificacionList().add(calificacion);
                personaNew = em.merge(personaNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getCalificacionList().remove(calificacion);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getCalificacionList().add(calificacion);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CalificacionPK id = calificacion.getCalificacionPK();
                if (findCalificacion(id) == null) {
                    throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CalificacionPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Calificacion calificacion;
            try {
                calificacion = em.getReference(Calificacion.class, id);
                calificacion.getCalificacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The calificacion with id " + id + " no longer exists.", enfe);
            }
            Persona persona = calificacion.getPersona();
            if (persona != null) {
                persona.getCalificacionList().remove(calificacion);
                persona = em.merge(persona);
            }
            Producto producto = calificacion.getProducto();
            if (producto != null) {
                producto.getCalificacionList().remove(calificacion);
                producto = em.merge(producto);
            }
            em.remove(calificacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Calificacion> findCalificacionEntities() {
        return findCalificacionEntities(true, -1, -1);
    }

    public List<Calificacion> findCalificacionEntities(int maxResults, int firstResult) {
        return findCalificacionEntities(false, maxResults, firstResult);
    }

    private List<Calificacion> findCalificacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Calificacion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Calificacion findCalificacion(CalificacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Calificacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCalificacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Calificacion> rt = cq.from(Calificacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
