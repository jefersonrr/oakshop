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
import java.util.ArrayList;
import java.util.List;
import DTO.Publicacion;
import DTO.TipoTalla;
import DTO.CategoriaTipo;
import DTO.Tipo;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jefer
 */
public class TipoJpaController implements Serializable {

    public TipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipo tipo) {
        if (tipo.getCategoriaList() == null) {
            tipo.setCategoriaList(new ArrayList<Categoria>());
        }
        if (tipo.getPublicacionList() == null) {
            tipo.setPublicacionList(new ArrayList<Publicacion>());
        }
        if (tipo.getTipoTallaList() == null) {
            tipo.setTipoTallaList(new ArrayList<TipoTalla>());
        }
        if (tipo.getCategoriaTipoList() == null) {
            tipo.setCategoriaTipoList(new ArrayList<CategoriaTipo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Categoria> attachedCategoriaList = new ArrayList<Categoria>();
            for (Categoria categoriaListCategoriaToAttach : tipo.getCategoriaList()) {
                categoriaListCategoriaToAttach = em.getReference(categoriaListCategoriaToAttach.getClass(), categoriaListCategoriaToAttach.getId());
                attachedCategoriaList.add(categoriaListCategoriaToAttach);
            }
            tipo.setCategoriaList(attachedCategoriaList);
            List<Publicacion> attachedPublicacionList = new ArrayList<Publicacion>();
            for (Publicacion publicacionListPublicacionToAttach : tipo.getPublicacionList()) {
                publicacionListPublicacionToAttach = em.getReference(publicacionListPublicacionToAttach.getClass(), publicacionListPublicacionToAttach.getId());
                attachedPublicacionList.add(publicacionListPublicacionToAttach);
            }
            tipo.setPublicacionList(attachedPublicacionList);
            List<TipoTalla> attachedTipoTallaList = new ArrayList<TipoTalla>();
            for (TipoTalla tipoTallaListTipoTallaToAttach : tipo.getTipoTallaList()) {
                tipoTallaListTipoTallaToAttach = em.getReference(tipoTallaListTipoTallaToAttach.getClass(), tipoTallaListTipoTallaToAttach.getId());
                attachedTipoTallaList.add(tipoTallaListTipoTallaToAttach);
            }
            tipo.setTipoTallaList(attachedTipoTallaList);
            List<CategoriaTipo> attachedCategoriaTipoList = new ArrayList<CategoriaTipo>();
            for (CategoriaTipo categoriaTipoListCategoriaTipoToAttach : tipo.getCategoriaTipoList()) {
                categoriaTipoListCategoriaTipoToAttach = em.getReference(categoriaTipoListCategoriaTipoToAttach.getClass(), categoriaTipoListCategoriaTipoToAttach.getId());
                attachedCategoriaTipoList.add(categoriaTipoListCategoriaTipoToAttach);
            }
            tipo.setCategoriaTipoList(attachedCategoriaTipoList);
            em.persist(tipo);
            for (Categoria categoriaListCategoria : tipo.getCategoriaList()) {
                categoriaListCategoria.getTipoList().add(tipo);
                categoriaListCategoria = em.merge(categoriaListCategoria);
            }
            for (Publicacion publicacionListPublicacion : tipo.getPublicacionList()) {
                Tipo oldIdTipoOfPublicacionListPublicacion = publicacionListPublicacion.getIdTipo();
                publicacionListPublicacion.setIdTipo(tipo);
                publicacionListPublicacion = em.merge(publicacionListPublicacion);
                if (oldIdTipoOfPublicacionListPublicacion != null) {
                    oldIdTipoOfPublicacionListPublicacion.getPublicacionList().remove(publicacionListPublicacion);
                    oldIdTipoOfPublicacionListPublicacion = em.merge(oldIdTipoOfPublicacionListPublicacion);
                }
            }
            for (TipoTalla tipoTallaListTipoTalla : tipo.getTipoTallaList()) {
                Tipo oldIdTipoOfTipoTallaListTipoTalla = tipoTallaListTipoTalla.getIdTipo();
                tipoTallaListTipoTalla.setIdTipo(tipo);
                tipoTallaListTipoTalla = em.merge(tipoTallaListTipoTalla);
                if (oldIdTipoOfTipoTallaListTipoTalla != null) {
                    oldIdTipoOfTipoTallaListTipoTalla.getTipoTallaList().remove(tipoTallaListTipoTalla);
                    oldIdTipoOfTipoTallaListTipoTalla = em.merge(oldIdTipoOfTipoTallaListTipoTalla);
                }
            }
            for (CategoriaTipo categoriaTipoListCategoriaTipo : tipo.getCategoriaTipoList()) {
                Tipo oldIdTipoOfCategoriaTipoListCategoriaTipo = categoriaTipoListCategoriaTipo.getIdTipo();
                categoriaTipoListCategoriaTipo.setIdTipo(tipo);
                categoriaTipoListCategoriaTipo = em.merge(categoriaTipoListCategoriaTipo);
                if (oldIdTipoOfCategoriaTipoListCategoriaTipo != null) {
                    oldIdTipoOfCategoriaTipoListCategoriaTipo.getCategoriaTipoList().remove(categoriaTipoListCategoriaTipo);
                    oldIdTipoOfCategoriaTipoListCategoriaTipo = em.merge(oldIdTipoOfCategoriaTipoListCategoriaTipo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipo tipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipo persistentTipo = em.find(Tipo.class, tipo.getId());
            List<Categoria> categoriaListOld = persistentTipo.getCategoriaList();
            List<Categoria> categoriaListNew = tipo.getCategoriaList();
            List<Publicacion> publicacionListOld = persistentTipo.getPublicacionList();
            List<Publicacion> publicacionListNew = tipo.getPublicacionList();
            List<TipoTalla> tipoTallaListOld = persistentTipo.getTipoTallaList();
            List<TipoTalla> tipoTallaListNew = tipo.getTipoTallaList();
            List<CategoriaTipo> categoriaTipoListOld = persistentTipo.getCategoriaTipoList();
            List<CategoriaTipo> categoriaTipoListNew = tipo.getCategoriaTipoList();
            List<String> illegalOrphanMessages = null;
            for (Publicacion publicacionListOldPublicacion : publicacionListOld) {
                if (!publicacionListNew.contains(publicacionListOldPublicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacion " + publicacionListOldPublicacion + " since its idTipo field is not nullable.");
                }
            }
            for (TipoTalla tipoTallaListOldTipoTalla : tipoTallaListOld) {
                if (!tipoTallaListNew.contains(tipoTallaListOldTipoTalla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoTalla " + tipoTallaListOldTipoTalla + " since its idTipo field is not nullable.");
                }
            }
            for (CategoriaTipo categoriaTipoListOldCategoriaTipo : categoriaTipoListOld) {
                if (!categoriaTipoListNew.contains(categoriaTipoListOldCategoriaTipo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CategoriaTipo " + categoriaTipoListOldCategoriaTipo + " since its idTipo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Categoria> attachedCategoriaListNew = new ArrayList<Categoria>();
            for (Categoria categoriaListNewCategoriaToAttach : categoriaListNew) {
                categoriaListNewCategoriaToAttach = em.getReference(categoriaListNewCategoriaToAttach.getClass(), categoriaListNewCategoriaToAttach.getId());
                attachedCategoriaListNew.add(categoriaListNewCategoriaToAttach);
            }
            categoriaListNew = attachedCategoriaListNew;
            tipo.setCategoriaList(categoriaListNew);
            List<Publicacion> attachedPublicacionListNew = new ArrayList<Publicacion>();
            for (Publicacion publicacionListNewPublicacionToAttach : publicacionListNew) {
                publicacionListNewPublicacionToAttach = em.getReference(publicacionListNewPublicacionToAttach.getClass(), publicacionListNewPublicacionToAttach.getId());
                attachedPublicacionListNew.add(publicacionListNewPublicacionToAttach);
            }
            publicacionListNew = attachedPublicacionListNew;
            tipo.setPublicacionList(publicacionListNew);
            List<TipoTalla> attachedTipoTallaListNew = new ArrayList<TipoTalla>();
            for (TipoTalla tipoTallaListNewTipoTallaToAttach : tipoTallaListNew) {
                tipoTallaListNewTipoTallaToAttach = em.getReference(tipoTallaListNewTipoTallaToAttach.getClass(), tipoTallaListNewTipoTallaToAttach.getId());
                attachedTipoTallaListNew.add(tipoTallaListNewTipoTallaToAttach);
            }
            tipoTallaListNew = attachedTipoTallaListNew;
            tipo.setTipoTallaList(tipoTallaListNew);
            List<CategoriaTipo> attachedCategoriaTipoListNew = new ArrayList<CategoriaTipo>();
            for (CategoriaTipo categoriaTipoListNewCategoriaTipoToAttach : categoriaTipoListNew) {
                categoriaTipoListNewCategoriaTipoToAttach = em.getReference(categoriaTipoListNewCategoriaTipoToAttach.getClass(), categoriaTipoListNewCategoriaTipoToAttach.getId());
                attachedCategoriaTipoListNew.add(categoriaTipoListNewCategoriaTipoToAttach);
            }
            categoriaTipoListNew = attachedCategoriaTipoListNew;
            tipo.setCategoriaTipoList(categoriaTipoListNew);
            tipo = em.merge(tipo);
            for (Categoria categoriaListOldCategoria : categoriaListOld) {
                if (!categoriaListNew.contains(categoriaListOldCategoria)) {
                    categoriaListOldCategoria.getTipoList().remove(tipo);
                    categoriaListOldCategoria = em.merge(categoriaListOldCategoria);
                }
            }
            for (Categoria categoriaListNewCategoria : categoriaListNew) {
                if (!categoriaListOld.contains(categoriaListNewCategoria)) {
                    categoriaListNewCategoria.getTipoList().add(tipo);
                    categoriaListNewCategoria = em.merge(categoriaListNewCategoria);
                }
            }
            for (Publicacion publicacionListNewPublicacion : publicacionListNew) {
                if (!publicacionListOld.contains(publicacionListNewPublicacion)) {
                    Tipo oldIdTipoOfPublicacionListNewPublicacion = publicacionListNewPublicacion.getIdTipo();
                    publicacionListNewPublicacion.setIdTipo(tipo);
                    publicacionListNewPublicacion = em.merge(publicacionListNewPublicacion);
                    if (oldIdTipoOfPublicacionListNewPublicacion != null && !oldIdTipoOfPublicacionListNewPublicacion.equals(tipo)) {
                        oldIdTipoOfPublicacionListNewPublicacion.getPublicacionList().remove(publicacionListNewPublicacion);
                        oldIdTipoOfPublicacionListNewPublicacion = em.merge(oldIdTipoOfPublicacionListNewPublicacion);
                    }
                }
            }
            for (TipoTalla tipoTallaListNewTipoTalla : tipoTallaListNew) {
                if (!tipoTallaListOld.contains(tipoTallaListNewTipoTalla)) {
                    Tipo oldIdTipoOfTipoTallaListNewTipoTalla = tipoTallaListNewTipoTalla.getIdTipo();
                    tipoTallaListNewTipoTalla.setIdTipo(tipo);
                    tipoTallaListNewTipoTalla = em.merge(tipoTallaListNewTipoTalla);
                    if (oldIdTipoOfTipoTallaListNewTipoTalla != null && !oldIdTipoOfTipoTallaListNewTipoTalla.equals(tipo)) {
                        oldIdTipoOfTipoTallaListNewTipoTalla.getTipoTallaList().remove(tipoTallaListNewTipoTalla);
                        oldIdTipoOfTipoTallaListNewTipoTalla = em.merge(oldIdTipoOfTipoTallaListNewTipoTalla);
                    }
                }
            }
            for (CategoriaTipo categoriaTipoListNewCategoriaTipo : categoriaTipoListNew) {
                if (!categoriaTipoListOld.contains(categoriaTipoListNewCategoriaTipo)) {
                    Tipo oldIdTipoOfCategoriaTipoListNewCategoriaTipo = categoriaTipoListNewCategoriaTipo.getIdTipo();
                    categoriaTipoListNewCategoriaTipo.setIdTipo(tipo);
                    categoriaTipoListNewCategoriaTipo = em.merge(categoriaTipoListNewCategoriaTipo);
                    if (oldIdTipoOfCategoriaTipoListNewCategoriaTipo != null && !oldIdTipoOfCategoriaTipoListNewCategoriaTipo.equals(tipo)) {
                        oldIdTipoOfCategoriaTipoListNewCategoriaTipo.getCategoriaTipoList().remove(categoriaTipoListNewCategoriaTipo);
                        oldIdTipoOfCategoriaTipoListNewCategoriaTipo = em.merge(oldIdTipoOfCategoriaTipoListNewCategoriaTipo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipo.getId();
                if (findTipo(id) == null) {
                    throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.");
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
            Tipo tipo;
            try {
                tipo = em.getReference(Tipo.class, id);
                tipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Publicacion> publicacionListOrphanCheck = tipo.getPublicacionList();
            for (Publicacion publicacionListOrphanCheckPublicacion : publicacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the Publicacion " + publicacionListOrphanCheckPublicacion + " in its publicacionList field has a non-nullable idTipo field.");
            }
            List<TipoTalla> tipoTallaListOrphanCheck = tipo.getTipoTallaList();
            for (TipoTalla tipoTallaListOrphanCheckTipoTalla : tipoTallaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the TipoTalla " + tipoTallaListOrphanCheckTipoTalla + " in its tipoTallaList field has a non-nullable idTipo field.");
            }
            List<CategoriaTipo> categoriaTipoListOrphanCheck = tipo.getCategoriaTipoList();
            for (CategoriaTipo categoriaTipoListOrphanCheckCategoriaTipo : categoriaTipoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipo (" + tipo + ") cannot be destroyed since the CategoriaTipo " + categoriaTipoListOrphanCheckCategoriaTipo + " in its categoriaTipoList field has a non-nullable idTipo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Categoria> categoriaList = tipo.getCategoriaList();
            for (Categoria categoriaListCategoria : categoriaList) {
                categoriaListCategoria.getTipoList().remove(tipo);
                categoriaListCategoria = em.merge(categoriaListCategoria);
            }
            em.remove(tipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipo> findTipoEntities() {
        return findTipoEntities(true, -1, -1);
    }

    public List<Tipo> findTipoEntities(int maxResults, int firstResult) {
        return findTipoEntities(false, maxResults, firstResult);
    }

    private List<Tipo> findTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipo.class));
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

    public Tipo findTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipo> rt = cq.from(Tipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
