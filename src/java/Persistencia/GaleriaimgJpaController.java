/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Galeriaimg;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Publicacion;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class GaleriaimgJpaController implements Serializable {

    public GaleriaimgJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Galeriaimg galeriaimg) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion idPublicacion = galeriaimg.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion = em.getReference(idPublicacion.getClass(), idPublicacion.getId());
                galeriaimg.setIdPublicacion(idPublicacion);
            }
            em.persist(galeriaimg);
            if (idPublicacion != null) {
                idPublicacion.getGaleriaimgList().add(galeriaimg);
                idPublicacion = em.merge(idPublicacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Galeriaimg galeriaimg) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Galeriaimg persistentGaleriaimg = em.find(Galeriaimg.class, galeriaimg.getId());
            Publicacion idPublicacionOld = persistentGaleriaimg.getIdPublicacion();
            Publicacion idPublicacionNew = galeriaimg.getIdPublicacion();
            if (idPublicacionNew != null) {
                idPublicacionNew = em.getReference(idPublicacionNew.getClass(), idPublicacionNew.getId());
                galeriaimg.setIdPublicacion(idPublicacionNew);
            }
            galeriaimg = em.merge(galeriaimg);
            if (idPublicacionOld != null && !idPublicacionOld.equals(idPublicacionNew)) {
                idPublicacionOld.getGaleriaimgList().remove(galeriaimg);
                idPublicacionOld = em.merge(idPublicacionOld);
            }
            if (idPublicacionNew != null && !idPublicacionNew.equals(idPublicacionOld)) {
                idPublicacionNew.getGaleriaimgList().add(galeriaimg);
                idPublicacionNew = em.merge(idPublicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = galeriaimg.getId();
                if (findGaleriaimg(id) == null) {
                    throw new NonexistentEntityException("The galeriaimg with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Galeriaimg galeriaimg;
            try {
                galeriaimg = em.getReference(Galeriaimg.class, id);
                galeriaimg.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The galeriaimg with id " + id + " no longer exists.", enfe);
            }
            Publicacion idPublicacion = galeriaimg.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion.getGaleriaimgList().remove(galeriaimg);
                idPublicacion = em.merge(idPublicacion);
            }
            em.remove(galeriaimg);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Galeriaimg> findGaleriaimgEntities() {
        return findGaleriaimgEntities(true, -1, -1);
    }

    public List<Galeriaimg> findGaleriaimgEntities(int maxResults, int firstResult) {
        return findGaleriaimgEntities(false, maxResults, firstResult);
    }

    private List<Galeriaimg> findGaleriaimgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Galeriaimg.class));
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

    public Galeriaimg findGaleriaimg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Galeriaimg.class, id);
        } finally {
            em.close();
        }
    }

    public int getGaleriaimgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Galeriaimg> rt = cq.from(Galeriaimg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
