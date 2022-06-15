/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import DTO.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Tipo;
import java.util.ArrayList;
import java.util.List;
import DTO.Publicacion;
import DTO.CategoriaTipo;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jefer
 */
public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getTipoList() == null) {
            categoria.setTipoList(new ArrayList<Tipo>());
        }
        if (categoria.getPublicacionList() == null) {
            categoria.setPublicacionList(new ArrayList<Publicacion>());
        }
        if (categoria.getCategoriaTipoList() == null) {
            categoria.setCategoriaTipoList(new ArrayList<CategoriaTipo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tipo> attachedTipoList = new ArrayList<Tipo>();
            for (Tipo tipoListTipoToAttach : categoria.getTipoList()) {
                tipoListTipoToAttach = em.getReference(tipoListTipoToAttach.getClass(), tipoListTipoToAttach.getId());
                attachedTipoList.add(tipoListTipoToAttach);
            }
            categoria.setTipoList(attachedTipoList);
            List<Publicacion> attachedPublicacionList = new ArrayList<Publicacion>();
            for (Publicacion publicacionListPublicacionToAttach : categoria.getPublicacionList()) {
                publicacionListPublicacionToAttach = em.getReference(publicacionListPublicacionToAttach.getClass(), publicacionListPublicacionToAttach.getId());
                attachedPublicacionList.add(publicacionListPublicacionToAttach);
            }
            categoria.setPublicacionList(attachedPublicacionList);
            List<CategoriaTipo> attachedCategoriaTipoList = new ArrayList<CategoriaTipo>();
            for (CategoriaTipo categoriaTipoListCategoriaTipoToAttach : categoria.getCategoriaTipoList()) {
                categoriaTipoListCategoriaTipoToAttach = em.getReference(categoriaTipoListCategoriaTipoToAttach.getClass(), categoriaTipoListCategoriaTipoToAttach.getId());
                attachedCategoriaTipoList.add(categoriaTipoListCategoriaTipoToAttach);
            }
            categoria.setCategoriaTipoList(attachedCategoriaTipoList);
            em.persist(categoria);
            for (Tipo tipoListTipo : categoria.getTipoList()) {
                tipoListTipo.getCategoriaList().add(categoria);
                tipoListTipo = em.merge(tipoListTipo);
            }
            for (Publicacion publicacionListPublicacion : categoria.getPublicacionList()) {
                Categoria oldIdCategoriaOfPublicacionListPublicacion = publicacionListPublicacion.getIdCategoria();
                publicacionListPublicacion.setIdCategoria(categoria);
                publicacionListPublicacion = em.merge(publicacionListPublicacion);
                if (oldIdCategoriaOfPublicacionListPublicacion != null) {
                    oldIdCategoriaOfPublicacionListPublicacion.getPublicacionList().remove(publicacionListPublicacion);
                    oldIdCategoriaOfPublicacionListPublicacion = em.merge(oldIdCategoriaOfPublicacionListPublicacion);
                }
            }
            for (CategoriaTipo categoriaTipoListCategoriaTipo : categoria.getCategoriaTipoList()) {
                Categoria oldIdCategoriaOfCategoriaTipoListCategoriaTipo = categoriaTipoListCategoriaTipo.getIdCategoria();
                categoriaTipoListCategoriaTipo.setIdCategoria(categoria);
                categoriaTipoListCategoriaTipo = em.merge(categoriaTipoListCategoriaTipo);
                if (oldIdCategoriaOfCategoriaTipoListCategoriaTipo != null) {
                    oldIdCategoriaOfCategoriaTipoListCategoriaTipo.getCategoriaTipoList().remove(categoriaTipoListCategoriaTipo);
                    oldIdCategoriaOfCategoriaTipoListCategoriaTipo = em.merge(oldIdCategoriaOfCategoriaTipoListCategoriaTipo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId());
            List<Tipo> tipoListOld = persistentCategoria.getTipoList();
            List<Tipo> tipoListNew = categoria.getTipoList();
            List<Publicacion> publicacionListOld = persistentCategoria.getPublicacionList();
            List<Publicacion> publicacionListNew = categoria.getPublicacionList();
            List<CategoriaTipo> categoriaTipoListOld = persistentCategoria.getCategoriaTipoList();
            List<CategoriaTipo> categoriaTipoListNew = categoria.getCategoriaTipoList();
            List<String> illegalOrphanMessages = null;
            for (Publicacion publicacionListOldPublicacion : publicacionListOld) {
                if (!publicacionListNew.contains(publicacionListOldPublicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacion " + publicacionListOldPublicacion + " since its idCategoria field is not nullable.");
                }
            }
            for (CategoriaTipo categoriaTipoListOldCategoriaTipo : categoriaTipoListOld) {
                if (!categoriaTipoListNew.contains(categoriaTipoListOldCategoriaTipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CategoriaTipo " + categoriaTipoListOldCategoriaTipo + " since its idCategoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tipo> attachedTipoListNew = new ArrayList<Tipo>();
            for (Tipo tipoListNewTipoToAttach : tipoListNew) {
                tipoListNewTipoToAttach = em.getReference(tipoListNewTipoToAttach.getClass(), tipoListNewTipoToAttach.getId());
                attachedTipoListNew.add(tipoListNewTipoToAttach);
            }
            tipoListNew = attachedTipoListNew;
            categoria.setTipoList(tipoListNew);
            List<Publicacion> attachedPublicacionListNew = new ArrayList<Publicacion>();
            for (Publicacion publicacionListNewPublicacionToAttach : publicacionListNew) {
                publicacionListNewPublicacionToAttach = em.getReference(publicacionListNewPublicacionToAttach.getClass(), publicacionListNewPublicacionToAttach.getId());
                attachedPublicacionListNew.add(publicacionListNewPublicacionToAttach);
            }
            publicacionListNew = attachedPublicacionListNew;
            categoria.setPublicacionList(publicacionListNew);
            List<CategoriaTipo> attachedCategoriaTipoListNew = new ArrayList<CategoriaTipo>();
            for (CategoriaTipo categoriaTipoListNewCategoriaTipoToAttach : categoriaTipoListNew) {
                categoriaTipoListNewCategoriaTipoToAttach = em.getReference(categoriaTipoListNewCategoriaTipoToAttach.getClass(), categoriaTipoListNewCategoriaTipoToAttach.getId());
                attachedCategoriaTipoListNew.add(categoriaTipoListNewCategoriaTipoToAttach);
            }
            categoriaTipoListNew = attachedCategoriaTipoListNew;
            categoria.setCategoriaTipoList(categoriaTipoListNew);
            categoria = em.merge(categoria);
            for (Tipo tipoListOldTipo : tipoListOld) {
                if (!tipoListNew.contains(tipoListOldTipo)) {
                    tipoListOldTipo.getCategoriaList().remove(categoria);
                    tipoListOldTipo = em.merge(tipoListOldTipo);
                }
            }
            for (Tipo tipoListNewTipo : tipoListNew) {
                if (!tipoListOld.contains(tipoListNewTipo)) {
                    tipoListNewTipo.getCategoriaList().add(categoria);
                    tipoListNewTipo = em.merge(tipoListNewTipo);
                }
            }
            for (Publicacion publicacionListNewPublicacion : publicacionListNew) {
                if (!publicacionListOld.contains(publicacionListNewPublicacion)) {
                    Categoria oldIdCategoriaOfPublicacionListNewPublicacion = publicacionListNewPublicacion.getIdCategoria();
                    publicacionListNewPublicacion.setIdCategoria(categoria);
                    publicacionListNewPublicacion = em.merge(publicacionListNewPublicacion);
                    if (oldIdCategoriaOfPublicacionListNewPublicacion != null && !oldIdCategoriaOfPublicacionListNewPublicacion.equals(categoria)) {
                        oldIdCategoriaOfPublicacionListNewPublicacion.getPublicacionList().remove(publicacionListNewPublicacion);
                        oldIdCategoriaOfPublicacionListNewPublicacion = em.merge(oldIdCategoriaOfPublicacionListNewPublicacion);
                    }
                }
            }
            for (CategoriaTipo categoriaTipoListNewCategoriaTipo : categoriaTipoListNew) {
                if (!categoriaTipoListOld.contains(categoriaTipoListNewCategoriaTipo)) {
                    Categoria oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo = categoriaTipoListNewCategoriaTipo.getIdCategoria();
                    categoriaTipoListNewCategoriaTipo.setIdCategoria(categoria);
                    categoriaTipoListNewCategoriaTipo = em.merge(categoriaTipoListNewCategoriaTipo);
                    if (oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo != null && !oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo.equals(categoria)) {
                        oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo.getCategoriaTipoList().remove(categoriaTipoListNewCategoriaTipo);
                        oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo = em.merge(oldIdCategoriaOfCategoriaTipoListNewCategoriaTipo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getId();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Publicacion> publicacionListOrphanCheck = categoria.getPublicacionList();
            for (Publicacion publicacionListOrphanCheckPublicacion : publicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Publicacion " + publicacionListOrphanCheckPublicacion + " in its publicacionList field has a non-nullable idCategoria field.");
            }
            List<CategoriaTipo> categoriaTipoListOrphanCheck = categoria.getCategoriaTipoList();
            for (CategoriaTipo categoriaTipoListOrphanCheckCategoriaTipo : categoriaTipoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the CategoriaTipo " + categoriaTipoListOrphanCheckCategoriaTipo + " in its categoriaTipoList field has a non-nullable idCategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Tipo> tipoList = categoria.getTipoList();
            for (Tipo tipoListTipo : tipoList) {
                tipoListTipo.getCategoriaList().remove(categoria);
                tipoListTipo = em.merge(tipoListTipo);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
