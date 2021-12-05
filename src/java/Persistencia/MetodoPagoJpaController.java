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
import DTO.Compra;
import DTO.MetodoPago;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class MetodoPagoJpaController implements Serializable {

    public MetodoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MetodoPago metodoPago) {
        if (metodoPago.getCompraList() == null) {
            metodoPago.setCompraList(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : metodoPago.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            metodoPago.setCompraList(attachedCompraList);
            em.persist(metodoPago);
            for (Compra compraListCompra : metodoPago.getCompraList()) {
                MetodoPago oldIdMetodoPagoOfCompraListCompra = compraListCompra.getIdMetodoPago();
                compraListCompra.setIdMetodoPago(metodoPago);
                compraListCompra = em.merge(compraListCompra);
                if (oldIdMetodoPagoOfCompraListCompra != null) {
                    oldIdMetodoPagoOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldIdMetodoPagoOfCompraListCompra = em.merge(oldIdMetodoPagoOfCompraListCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MetodoPago metodoPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MetodoPago persistentMetodoPago = em.find(MetodoPago.class, metodoPago.getId());
            List<Compra> compraListOld = persistentMetodoPago.getCompraList();
            List<Compra> compraListNew = metodoPago.getCompraList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its idMetodoPago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            metodoPago.setCompraList(compraListNew);
            metodoPago = em.merge(metodoPago);
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    MetodoPago oldIdMetodoPagoOfCompraListNewCompra = compraListNewCompra.getIdMetodoPago();
                    compraListNewCompra.setIdMetodoPago(metodoPago);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldIdMetodoPagoOfCompraListNewCompra != null && !oldIdMetodoPagoOfCompraListNewCompra.equals(metodoPago)) {
                        oldIdMetodoPagoOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldIdMetodoPagoOfCompraListNewCompra = em.merge(oldIdMetodoPagoOfCompraListNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = metodoPago.getId();
                if (findMetodoPago(id) == null) {
                    throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.");
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
            MetodoPago metodoPago;
            try {
                metodoPago = em.getReference(MetodoPago.class, id);
                metodoPago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The metodoPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = metodoPago.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This MetodoPago (" + metodoPago + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable idMetodoPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(metodoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MetodoPago> findMetodoPagoEntities() {
        return findMetodoPagoEntities(true, -1, -1);
    }

    public List<MetodoPago> findMetodoPagoEntities(int maxResults, int firstResult) {
        return findMetodoPagoEntities(false, maxResults, firstResult);
    }

    private List<MetodoPago> findMetodoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MetodoPago.class));
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

    public MetodoPago findMetodoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MetodoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getMetodoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MetodoPago> rt = cq.from(MetodoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
