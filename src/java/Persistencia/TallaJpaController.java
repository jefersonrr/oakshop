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
import DTO.TipoTalla;
import java.util.ArrayList;
import java.util.List;
import DTO.Producto;
import DTO.Talla;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class TallaJpaController implements Serializable {

    public TallaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Talla talla) {
        if (talla.getTipoTallaList() == null) {
            talla.setTipoTallaList(new ArrayList<TipoTalla>());
        }
        if (talla.getProductoList() == null) {
            talla.setProductoList(new ArrayList<Producto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TipoTalla> attachedTipoTallaList = new ArrayList<TipoTalla>();
            for (TipoTalla tipoTallaListTipoTallaToAttach : talla.getTipoTallaList()) {
                tipoTallaListTipoTallaToAttach = em.getReference(tipoTallaListTipoTallaToAttach.getClass(), tipoTallaListTipoTallaToAttach.getId());
                attachedTipoTallaList.add(tipoTallaListTipoTallaToAttach);
            }
            talla.setTipoTallaList(attachedTipoTallaList);
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : talla.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getId());
                attachedProductoList.add(productoListProductoToAttach);
            }
            talla.setProductoList(attachedProductoList);
            em.persist(talla);
            for (TipoTalla tipoTallaListTipoTalla : talla.getTipoTallaList()) {
                Talla oldIdTallaOfTipoTallaListTipoTalla = tipoTallaListTipoTalla.getIdTalla();
                tipoTallaListTipoTalla.setIdTalla(talla);
                tipoTallaListTipoTalla = em.merge(tipoTallaListTipoTalla);
                if (oldIdTallaOfTipoTallaListTipoTalla != null) {
                    oldIdTallaOfTipoTallaListTipoTalla.getTipoTallaList().remove(tipoTallaListTipoTalla);
                    oldIdTallaOfTipoTallaListTipoTalla = em.merge(oldIdTallaOfTipoTallaListTipoTalla);
                }
            }
            for (Producto productoListProducto : talla.getProductoList()) {
                Talla oldIdTallaOfProductoListProducto = productoListProducto.getIdTalla();
                productoListProducto.setIdTalla(talla);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdTallaOfProductoListProducto != null) {
                    oldIdTallaOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdTallaOfProductoListProducto = em.merge(oldIdTallaOfProductoListProducto);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Talla talla) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Talla persistentTalla = em.find(Talla.class, talla.getId());
            List<TipoTalla> tipoTallaListOld = persistentTalla.getTipoTallaList();
            List<TipoTalla> tipoTallaListNew = talla.getTipoTallaList();
            List<Producto> productoListOld = persistentTalla.getProductoList();
            List<Producto> productoListNew = talla.getProductoList();
            List<String> illegalOrphanMessages = null;
            for (TipoTalla tipoTallaListOldTipoTalla : tipoTallaListOld) {
                if (!tipoTallaListNew.contains(tipoTallaListOldTipoTalla)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TipoTalla " + tipoTallaListOldTipoTalla + " since its idTalla field is not nullable.");
                }
            }
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoListOldProducto + " since its idTalla field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TipoTalla> attachedTipoTallaListNew = new ArrayList<TipoTalla>();
            for (TipoTalla tipoTallaListNewTipoTallaToAttach : tipoTallaListNew) {
                tipoTallaListNewTipoTallaToAttach = em.getReference(tipoTallaListNewTipoTallaToAttach.getClass(), tipoTallaListNewTipoTallaToAttach.getId());
                attachedTipoTallaListNew.add(tipoTallaListNewTipoTallaToAttach);
            }
            tipoTallaListNew = attachedTipoTallaListNew;
            talla.setTipoTallaList(tipoTallaListNew);
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getId());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            talla.setProductoList(productoListNew);
            talla = em.merge(talla);
            for (TipoTalla tipoTallaListNewTipoTalla : tipoTallaListNew) {
                if (!tipoTallaListOld.contains(tipoTallaListNewTipoTalla)) {
                    Talla oldIdTallaOfTipoTallaListNewTipoTalla = tipoTallaListNewTipoTalla.getIdTalla();
                    tipoTallaListNewTipoTalla.setIdTalla(talla);
                    tipoTallaListNewTipoTalla = em.merge(tipoTallaListNewTipoTalla);
                    if (oldIdTallaOfTipoTallaListNewTipoTalla != null && !oldIdTallaOfTipoTallaListNewTipoTalla.equals(talla)) {
                        oldIdTallaOfTipoTallaListNewTipoTalla.getTipoTallaList().remove(tipoTallaListNewTipoTalla);
                        oldIdTallaOfTipoTallaListNewTipoTalla = em.merge(oldIdTallaOfTipoTallaListNewTipoTalla);
                    }
                }
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Talla oldIdTallaOfProductoListNewProducto = productoListNewProducto.getIdTalla();
                    productoListNewProducto.setIdTalla(talla);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdTallaOfProductoListNewProducto != null && !oldIdTallaOfProductoListNewProducto.equals(talla)) {
                        oldIdTallaOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdTallaOfProductoListNewProducto = em.merge(oldIdTallaOfProductoListNewProducto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = talla.getId();
                if (findTalla(id) == null) {
                    throw new NonexistentEntityException("The talla with id " + id + " no longer exists.");
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
            Talla talla;
            try {
                talla = em.getReference(Talla.class, id);
                talla.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The talla with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TipoTalla> tipoTallaListOrphanCheck = talla.getTipoTallaList();
            for (TipoTalla tipoTallaListOrphanCheckTipoTalla : tipoTallaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Talla (" + talla + ") cannot be destroyed since the TipoTalla " + tipoTallaListOrphanCheckTipoTalla + " in its tipoTallaList field has a non-nullable idTalla field.");
            }
            List<Producto> productoListOrphanCheck = talla.getProductoList();
            for (Producto productoListOrphanCheckProducto : productoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Talla (" + talla + ") cannot be destroyed since the Producto " + productoListOrphanCheckProducto + " in its productoList field has a non-nullable idTalla field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(talla);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Talla> findTallaEntities() {
        return findTallaEntities(true, -1, -1);
    }

    public List<Talla> findTallaEntities(int maxResults, int firstResult) {
        return findTallaEntities(false, maxResults, firstResult);
    }

    private List<Talla> findTallaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Talla.class));
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

    public Talla findTalla(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Talla.class, id);
        } finally {
            em.close();
        }
    }

    public int getTallaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Talla> rt = cq.from(Talla.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
