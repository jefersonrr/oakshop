/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Talla;
import DTO.Tipo;
import DTO.TipoTalla;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class TipoTallaJpaController implements Serializable {

    public TipoTallaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoTalla tipoTalla) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Talla idTalla = tipoTalla.getIdTalla();
            if (idTalla != null) {
                idTalla = em.getReference(idTalla.getClass(), idTalla.getId());
                tipoTalla.setIdTalla(idTalla);
            }
            Tipo idTipo = tipoTalla.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                tipoTalla.setIdTipo(idTipo);
            }
            em.persist(tipoTalla);
            if (idTalla != null) {
                idTalla.getTipoTallaList().add(tipoTalla);
                idTalla = em.merge(idTalla);
            }
            if (idTipo != null) {
                idTipo.getTipoTallaList().add(tipoTalla);
                idTipo = em.merge(idTipo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoTalla tipoTalla) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoTalla persistentTipoTalla = em.find(TipoTalla.class, tipoTalla.getId());
            Talla idTallaOld = persistentTipoTalla.getIdTalla();
            Talla idTallaNew = tipoTalla.getIdTalla();
            Tipo idTipoOld = persistentTipoTalla.getIdTipo();
            Tipo idTipoNew = tipoTalla.getIdTipo();
            if (idTallaNew != null) {
                idTallaNew = em.getReference(idTallaNew.getClass(), idTallaNew.getId());
                tipoTalla.setIdTalla(idTallaNew);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                tipoTalla.setIdTipo(idTipoNew);
            }
            tipoTalla = em.merge(tipoTalla);
            if (idTallaOld != null && !idTallaOld.equals(idTallaNew)) {
                idTallaOld.getTipoTallaList().remove(tipoTalla);
                idTallaOld = em.merge(idTallaOld);
            }
            if (idTallaNew != null && !idTallaNew.equals(idTallaOld)) {
                idTallaNew.getTipoTallaList().add(tipoTalla);
                idTallaNew = em.merge(idTallaNew);
            }
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getTipoTallaList().remove(tipoTalla);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getTipoTallaList().add(tipoTalla);
                idTipoNew = em.merge(idTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoTalla.getId();
                if (findTipoTalla(id) == null) {
                    throw new NonexistentEntityException("The tipoTalla with id " + id + " no longer exists.");
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
            TipoTalla tipoTalla;
            try {
                tipoTalla = em.getReference(TipoTalla.class, id);
                tipoTalla.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoTalla with id " + id + " no longer exists.", enfe);
            }
            Talla idTalla = tipoTalla.getIdTalla();
            if (idTalla != null) {
                idTalla.getTipoTallaList().remove(tipoTalla);
                idTalla = em.merge(idTalla);
            }
            Tipo idTipo = tipoTalla.getIdTipo();
            if (idTipo != null) {
                idTipo.getTipoTallaList().remove(tipoTalla);
                idTipo = em.merge(idTipo);
            }
            em.remove(tipoTalla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoTalla> findTipoTallaEntities() {
        return findTipoTallaEntities(true, -1, -1);
    }

    public List<TipoTalla> findTipoTallaEntities(int maxResults, int firstResult) {
        return findTipoTallaEntities(false, maxResults, firstResult);
    }

    private List<TipoTalla> findTipoTallaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoTalla.class));
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

    public TipoTalla findTipoTalla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoTalla.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoTallaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoTalla> rt = cq.from(TipoTalla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
