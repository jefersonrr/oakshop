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
import DTO.Domicilio;
import DTO.Envio;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class EnvioJpaController implements Serializable {

    public EnvioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Envio envio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra idCompra = envio.getIdCompra();
            if (idCompra != null) {
                idCompra = em.getReference(idCompra.getClass(), idCompra.getId());
                envio.setIdCompra(idCompra);
            }
            Domicilio idDomicilio = envio.getIdDomicilio();
            if (idDomicilio != null) {
                idDomicilio = em.getReference(idDomicilio.getClass(), idDomicilio.getId());
                envio.setIdDomicilio(idDomicilio);
            }
            em.persist(envio);
            if (idCompra != null) {
                idCompra.getEnvioList().add(envio);
                idCompra = em.merge(idCompra);
            }
            if (idDomicilio != null) {
                idDomicilio.getEnvioList().add(envio);
                idDomicilio = em.merge(idDomicilio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Envio envio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Envio persistentEnvio = em.find(Envio.class, envio.getId());
            Compra idCompraOld = persistentEnvio.getIdCompra();
            Compra idCompraNew = envio.getIdCompra();
            Domicilio idDomicilioOld = persistentEnvio.getIdDomicilio();
            Domicilio idDomicilioNew = envio.getIdDomicilio();
            if (idCompraNew != null) {
                idCompraNew = em.getReference(idCompraNew.getClass(), idCompraNew.getId());
                envio.setIdCompra(idCompraNew);
            }
            if (idDomicilioNew != null) {
                idDomicilioNew = em.getReference(idDomicilioNew.getClass(), idDomicilioNew.getId());
                envio.setIdDomicilio(idDomicilioNew);
            }
            envio = em.merge(envio);
            if (idCompraOld != null && !idCompraOld.equals(idCompraNew)) {
                idCompraOld.getEnvioList().remove(envio);
                idCompraOld = em.merge(idCompraOld);
            }
            if (idCompraNew != null && !idCompraNew.equals(idCompraOld)) {
                idCompraNew.getEnvioList().add(envio);
                idCompraNew = em.merge(idCompraNew);
            }
            if (idDomicilioOld != null && !idDomicilioOld.equals(idDomicilioNew)) {
                idDomicilioOld.getEnvioList().remove(envio);
                idDomicilioOld = em.merge(idDomicilioOld);
            }
            if (idDomicilioNew != null && !idDomicilioNew.equals(idDomicilioOld)) {
                idDomicilioNew.getEnvioList().add(envio);
                idDomicilioNew = em.merge(idDomicilioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = envio.getId();
                if (findEnvio(id) == null) {
                    throw new NonexistentEntityException("The envio with id " + id + " no longer exists.");
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
            Envio envio;
            try {
                envio = em.getReference(Envio.class, id);
                envio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The envio with id " + id + " no longer exists.", enfe);
            }
            Compra idCompra = envio.getIdCompra();
            if (idCompra != null) {
                idCompra.getEnvioList().remove(envio);
                idCompra = em.merge(idCompra);
            }
            Domicilio idDomicilio = envio.getIdDomicilio();
            if (idDomicilio != null) {
                idDomicilio.getEnvioList().remove(envio);
                idDomicilio = em.merge(idDomicilio);
            }
            em.remove(envio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Envio> findEnvioEntities() {
        return findEnvioEntities(true, -1, -1);
    }

    public List<Envio> findEnvioEntities(int maxResults, int firstResult) {
        return findEnvioEntities(false, maxResults, firstResult);
    }

    private List<Envio> findEnvioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Envio.class));
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

    public Envio findEnvio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Envio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnvioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Envio> rt = cq.from(Envio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
