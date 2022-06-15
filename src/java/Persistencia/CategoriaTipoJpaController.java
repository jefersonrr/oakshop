/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Categoria;
import DTO.CategoriaTipo;
import DTO.Tipo;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jefer
 */
public class CategoriaTipoJpaController implements Serializable {

    public CategoriaTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaTipo categoriaTipo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = categoriaTipo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getId());
                categoriaTipo.setIdCategoria(idCategoria);
            }
            Tipo idTipo = categoriaTipo.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                categoriaTipo.setIdTipo(idTipo);
            }
            em.persist(categoriaTipo);
            if (idCategoria != null) {
                idCategoria.getCategoriaTipoList().add(categoriaTipo);
                idCategoria = em.merge(idCategoria);
            }
            if (idTipo != null) {
                idTipo.getCategoriaTipoList().add(categoriaTipo);
                idTipo = em.merge(idTipo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaTipo categoriaTipo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaTipo persistentCategoriaTipo = em.find(CategoriaTipo.class, categoriaTipo.getId());
            Categoria idCategoriaOld = persistentCategoriaTipo.getIdCategoria();
            Categoria idCategoriaNew = categoriaTipo.getIdCategoria();
            Tipo idTipoOld = persistentCategoriaTipo.getIdTipo();
            Tipo idTipoNew = categoriaTipo.getIdTipo();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getId());
                categoriaTipo.setIdCategoria(idCategoriaNew);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                categoriaTipo.setIdTipo(idTipoNew);
            }
            categoriaTipo = em.merge(categoriaTipo);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getCategoriaTipoList().remove(categoriaTipo);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getCategoriaTipoList().add(categoriaTipo);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getCategoriaTipoList().remove(categoriaTipo);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getCategoriaTipoList().add(categoriaTipo);
                idTipoNew = em.merge(idTipoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoriaTipo.getId();
                if (findCategoriaTipo(id) == null) {
                    throw new NonexistentEntityException("The categoriaTipo with id " + id + " no longer exists.");
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
            CategoriaTipo categoriaTipo;
            try {
                categoriaTipo = em.getReference(CategoriaTipo.class, id);
                categoriaTipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaTipo with id " + id + " no longer exists.", enfe);
            }
            Categoria idCategoria = categoriaTipo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getCategoriaTipoList().remove(categoriaTipo);
                idCategoria = em.merge(idCategoria);
            }
            Tipo idTipo = categoriaTipo.getIdTipo();
            if (idTipo != null) {
                idTipo.getCategoriaTipoList().remove(categoriaTipo);
                idTipo = em.merge(idTipo);
            }
            em.remove(categoriaTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaTipo> findCategoriaTipoEntities() {
        return findCategoriaTipoEntities(true, -1, -1);
    }

    public List<CategoriaTipo> findCategoriaTipoEntities(int maxResults, int firstResult) {
        return findCategoriaTipoEntities(false, maxResults, firstResult);
    }

    private List<CategoriaTipo> findCategoriaTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaTipo.class));
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

    public CategoriaTipo findCategoriaTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaTipo> rt = cq.from(CategoriaTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}