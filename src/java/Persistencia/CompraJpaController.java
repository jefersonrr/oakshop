/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Compra;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Persona;
import DTO.MetodoPago;
import DTO.DetalleCompra;
import java.util.ArrayList;
import java.util.List;
import DTO.Envio;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getDetalleCompraList() == null) {
            compra.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        if (compra.getEnvioList() == null) {
            compra.setEnvioList(new ArrayList<Envio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona idCliente = compra.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getCedula());
                compra.setIdCliente(idCliente);
            }
            MetodoPago idMetodoPago = compra.getIdMetodoPago();
            if (idMetodoPago != null) {
                idMetodoPago = em.getReference(idMetodoPago.getClass(), idMetodoPago.getId());
                compra.setIdMetodoPago(idMetodoPago);
            }
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : compra.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getDetalleCompraPK());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            compra.setDetalleCompraList(attachedDetalleCompraList);
            List<Envio> attachedEnvioList = new ArrayList<Envio>();
            for (Envio envioListEnvioToAttach : compra.getEnvioList()) {
                envioListEnvioToAttach = em.getReference(envioListEnvioToAttach.getClass(), envioListEnvioToAttach.getId());
                attachedEnvioList.add(envioListEnvioToAttach);
            }
            compra.setEnvioList(attachedEnvioList);
            em.persist(compra);
            if (idCliente != null) {
                idCliente.getCompraList().add(compra);
                idCliente = em.merge(idCliente);
            }
            if (idMetodoPago != null) {
                idMetodoPago.getCompraList().add(compra);
                idMetodoPago = em.merge(idMetodoPago);
            }
            for (DetalleCompra detalleCompraListDetalleCompra : compra.getDetalleCompraList()) {
                Compra oldCompraOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getCompra();
                detalleCompraListDetalleCompra.setCompra(compra);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldCompraOfDetalleCompraListDetalleCompra != null) {
                    oldCompraOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldCompraOfDetalleCompraListDetalleCompra = em.merge(oldCompraOfDetalleCompraListDetalleCompra);
                }
            }
            for (Envio envioListEnvio : compra.getEnvioList()) {
                Compra oldIdCompraOfEnvioListEnvio = envioListEnvio.getIdCompra();
                envioListEnvio.setIdCompra(compra);
                envioListEnvio = em.merge(envioListEnvio);
                if (oldIdCompraOfEnvioListEnvio != null) {
                    oldIdCompraOfEnvioListEnvio.getEnvioList().remove(envioListEnvio);
                    oldIdCompraOfEnvioListEnvio = em.merge(oldIdCompraOfEnvioListEnvio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getId());
            Persona idClienteOld = persistentCompra.getIdCliente();
            Persona idClienteNew = compra.getIdCliente();
            MetodoPago idMetodoPagoOld = persistentCompra.getIdMetodoPago();
            MetodoPago idMetodoPagoNew = compra.getIdMetodoPago();
            List<DetalleCompra> detalleCompraListOld = persistentCompra.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = compra.getDetalleCompraList();
            List<Envio> envioListOld = persistentCompra.getEnvioList();
            List<Envio> envioListNew = compra.getEnvioList();
            List<String> illegalOrphanMessages = null;
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCompra " + detalleCompraListOldDetalleCompra + " since its compra field is not nullable.");
                }
            }
            for (Envio envioListOldEnvio : envioListOld) {
                if (!envioListNew.contains(envioListOldEnvio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Envio " + envioListOldEnvio + " since its idCompra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getCedula());
                compra.setIdCliente(idClienteNew);
            }
            if (idMetodoPagoNew != null) {
                idMetodoPagoNew = em.getReference(idMetodoPagoNew.getClass(), idMetodoPagoNew.getId());
                compra.setIdMetodoPago(idMetodoPagoNew);
            }
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getDetalleCompraPK());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            compra.setDetalleCompraList(detalleCompraListNew);
            List<Envio> attachedEnvioListNew = new ArrayList<Envio>();
            for (Envio envioListNewEnvioToAttach : envioListNew) {
                envioListNewEnvioToAttach = em.getReference(envioListNewEnvioToAttach.getClass(), envioListNewEnvioToAttach.getId());
                attachedEnvioListNew.add(envioListNewEnvioToAttach);
            }
            envioListNew = attachedEnvioListNew;
            compra.setEnvioList(envioListNew);
            compra = em.merge(compra);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getCompraList().remove(compra);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getCompraList().add(compra);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idMetodoPagoOld != null && !idMetodoPagoOld.equals(idMetodoPagoNew)) {
                idMetodoPagoOld.getCompraList().remove(compra);
                idMetodoPagoOld = em.merge(idMetodoPagoOld);
            }
            if (idMetodoPagoNew != null && !idMetodoPagoNew.equals(idMetodoPagoOld)) {
                idMetodoPagoNew.getCompraList().add(compra);
                idMetodoPagoNew = em.merge(idMetodoPagoNew);
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Compra oldCompraOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getCompra();
                    detalleCompraListNewDetalleCompra.setCompra(compra);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldCompraOfDetalleCompraListNewDetalleCompra != null && !oldCompraOfDetalleCompraListNewDetalleCompra.equals(compra)) {
                        oldCompraOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldCompraOfDetalleCompraListNewDetalleCompra = em.merge(oldCompraOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            for (Envio envioListNewEnvio : envioListNew) {
                if (!envioListOld.contains(envioListNewEnvio)) {
                    Compra oldIdCompraOfEnvioListNewEnvio = envioListNewEnvio.getIdCompra();
                    envioListNewEnvio.setIdCompra(compra);
                    envioListNewEnvio = em.merge(envioListNewEnvio);
                    if (oldIdCompraOfEnvioListNewEnvio != null && !oldIdCompraOfEnvioListNewEnvio.equals(compra)) {
                        oldIdCompraOfEnvioListNewEnvio.getEnvioList().remove(envioListNewEnvio);
                        oldIdCompraOfEnvioListNewEnvio = em.merge(oldIdCompraOfEnvioListNewEnvio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getId();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleCompra> detalleCompraListOrphanCheck = compra.getDetalleCompraList();
            for (DetalleCompra detalleCompraListOrphanCheckDetalleCompra : detalleCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the DetalleCompra " + detalleCompraListOrphanCheckDetalleCompra + " in its detalleCompraList field has a non-nullable compra field.");
            }
            List<Envio> envioListOrphanCheck = compra.getEnvioList();
            for (Envio envioListOrphanCheckEnvio : envioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the Envio " + envioListOrphanCheckEnvio + " in its envioList field has a non-nullable idCompra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona idCliente = compra.getIdCliente();
            if (idCliente != null) {
                idCliente.getCompraList().remove(compra);
                idCliente = em.merge(idCliente);
            }
            MetodoPago idMetodoPago = compra.getIdMetodoPago();
            if (idMetodoPago != null) {
                idMetodoPago.getCompraList().remove(compra);
                idMetodoPago = em.merge(idMetodoPago);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
