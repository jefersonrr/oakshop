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
import DTO.Producto;
import DTO.Compra;
import DTO.DetalleCompra;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class DetalleCompraJpaController implements Serializable {

    public DetalleCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCompra detalleCompra) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Compra compraOrphanCheck = detalleCompra.getCompra();
        if (compraOrphanCheck != null) {
            DetalleCompra oldDetalleCompraOfCompra = compraOrphanCheck.getDetalleCompra();
            if (oldDetalleCompraOfCompra != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Compra " + compraOrphanCheck + " already has an item of type DetalleCompra whose compra column cannot be null. Please make another selection for the compra field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto idProducto = detalleCompra.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getId());
                detalleCompra.setIdProducto(idProducto);
            }
            Compra compra = detalleCompra.getCompra();
            if (compra != null) {
                compra = em.getReference(compra.getClass(), compra.getId());
                detalleCompra.setCompra(compra);
            }
            em.persist(detalleCompra);
            if (idProducto != null) {
                idProducto.getDetalleCompraList().add(detalleCompra);
                idProducto = em.merge(idProducto);
            }
            if (compra != null) {
                compra.setDetalleCompra(detalleCompra);
                compra = em.merge(compra);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDetalleCompra(detalleCompra.getIdCompra()) != null) {
                throw new PreexistingEntityException("DetalleCompra " + detalleCompra + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCompra detalleCompra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCompra persistentDetalleCompra = em.find(DetalleCompra.class, detalleCompra.getIdCompra());
            Producto idProductoOld = persistentDetalleCompra.getIdProducto();
            Producto idProductoNew = detalleCompra.getIdProducto();
            Compra compraOld = persistentDetalleCompra.getCompra();
            Compra compraNew = detalleCompra.getCompra();
            List<String> illegalOrphanMessages = null;
            if (compraNew != null && !compraNew.equals(compraOld)) {
                DetalleCompra oldDetalleCompraOfCompra = compraNew.getDetalleCompra();
                if (oldDetalleCompraOfCompra != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Compra " + compraNew + " already has an item of type DetalleCompra whose compra column cannot be null. Please make another selection for the compra field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idProductoNew != null) {
                idProductoNew = em.getReference(idProductoNew.getClass(), idProductoNew.getId());
                detalleCompra.setIdProducto(idProductoNew);
            }
            if (compraNew != null) {
                compraNew = em.getReference(compraNew.getClass(), compraNew.getId());
                detalleCompra.setCompra(compraNew);
            }
            detalleCompra = em.merge(detalleCompra);
            if (idProductoOld != null && !idProductoOld.equals(idProductoNew)) {
                idProductoOld.getDetalleCompraList().remove(detalleCompra);
                idProductoOld = em.merge(idProductoOld);
            }
            if (idProductoNew != null && !idProductoNew.equals(idProductoOld)) {
                idProductoNew.getDetalleCompraList().add(detalleCompra);
                idProductoNew = em.merge(idProductoNew);
            }
            if (compraOld != null && !compraOld.equals(compraNew)) {
                compraOld.setDetalleCompra(null);
                compraOld = em.merge(compraOld);
            }
            if (compraNew != null && !compraNew.equals(compraOld)) {
                compraNew.setDetalleCompra(detalleCompra);
                compraNew = em.merge(compraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCompra.getIdCompra();
                if (findDetalleCompra(id) == null) {
                    throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.");
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
            DetalleCompra detalleCompra;
            try {
                detalleCompra = em.getReference(DetalleCompra.class, id);
                detalleCompra.getIdCompra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.", enfe);
            }
            Producto idProducto = detalleCompra.getIdProducto();
            if (idProducto != null) {
                idProducto.getDetalleCompraList().remove(detalleCompra);
                idProducto = em.merge(idProducto);
            }
            Compra compra = detalleCompra.getCompra();
            if (compra != null) {
                compra.setDetalleCompra(null);
                compra = em.merge(compra);
            }
            em.remove(detalleCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCompra> findDetalleCompraEntities() {
        return findDetalleCompraEntities(true, -1, -1);
    }

    public List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult) {
        return findDetalleCompraEntities(false, maxResults, firstResult);
    }

    private List<DetalleCompra> findDetalleCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCompra.class));
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

    public DetalleCompra findDetalleCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCompra> rt = cq.from(DetalleCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
