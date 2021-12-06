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
import DTO.Ciudad;
import DTO.Domicilio;
import DTO.Persona;
import DTO.Envio;
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
public class DomicilioJpaController implements Serializable {

    public DomicilioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Domicilio domicilio) {
        if (domicilio.getEnvioList() == null) {
            domicilio.setEnvioList(new ArrayList<Envio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad idCiudad = domicilio.getIdCiudad();
            if (idCiudad != null) {
                idCiudad = em.getReference(idCiudad.getClass(), idCiudad.getId());
                domicilio.setIdCiudad(idCiudad);
            }
            Persona idCliente = domicilio.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getCedula());
                domicilio.setIdCliente(idCliente);
            }
            List<Envio> attachedEnvioList = new ArrayList<Envio>();
            for (Envio envioListEnvioToAttach : domicilio.getEnvioList()) {
                envioListEnvioToAttach = em.getReference(envioListEnvioToAttach.getClass(), envioListEnvioToAttach.getId());
                attachedEnvioList.add(envioListEnvioToAttach);
            }
            domicilio.setEnvioList(attachedEnvioList);
            em.persist(domicilio);
            if (idCiudad != null) {
                idCiudad.getDomicilioList().add(domicilio);
                idCiudad = em.merge(idCiudad);
            }
            if (idCliente != null) {
                idCliente.getDomicilioList().add(domicilio);
                idCliente = em.merge(idCliente);
            }
            for (Envio envioListEnvio : domicilio.getEnvioList()) {
                Domicilio oldIdDomicilioOfEnvioListEnvio = envioListEnvio.getIdDomicilio();
                envioListEnvio.setIdDomicilio(domicilio);
                envioListEnvio = em.merge(envioListEnvio);
                if (oldIdDomicilioOfEnvioListEnvio != null) {
                    oldIdDomicilioOfEnvioListEnvio.getEnvioList().remove(envioListEnvio);
                    oldIdDomicilioOfEnvioListEnvio = em.merge(oldIdDomicilioOfEnvioListEnvio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Domicilio domicilio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Domicilio persistentDomicilio = em.find(Domicilio.class, domicilio.getId());
            Ciudad idCiudadOld = persistentDomicilio.getIdCiudad();
            Ciudad idCiudadNew = domicilio.getIdCiudad();
            Persona idClienteOld = persistentDomicilio.getIdCliente();
            Persona idClienteNew = domicilio.getIdCliente();
            List<Envio> envioListOld = persistentDomicilio.getEnvioList();
            List<Envio> envioListNew = domicilio.getEnvioList();
            List<String> illegalOrphanMessages = null;
            for (Envio envioListOldEnvio : envioListOld) {
                if (!envioListNew.contains(envioListOldEnvio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Envio " + envioListOldEnvio + " since its idDomicilio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCiudadNew != null) {
                idCiudadNew = em.getReference(idCiudadNew.getClass(), idCiudadNew.getId());
                domicilio.setIdCiudad(idCiudadNew);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getCedula());
                domicilio.setIdCliente(idClienteNew);
            }
            List<Envio> attachedEnvioListNew = new ArrayList<Envio>();
            for (Envio envioListNewEnvioToAttach : envioListNew) {
                envioListNewEnvioToAttach = em.getReference(envioListNewEnvioToAttach.getClass(), envioListNewEnvioToAttach.getId());
                attachedEnvioListNew.add(envioListNewEnvioToAttach);
            }
            envioListNew = attachedEnvioListNew;
            domicilio.setEnvioList(envioListNew);
            domicilio = em.merge(domicilio);
            if (idCiudadOld != null && !idCiudadOld.equals(idCiudadNew)) {
                idCiudadOld.getDomicilioList().remove(domicilio);
                idCiudadOld = em.merge(idCiudadOld);
            }
            if (idCiudadNew != null && !idCiudadNew.equals(idCiudadOld)) {
                idCiudadNew.getDomicilioList().add(domicilio);
                idCiudadNew = em.merge(idCiudadNew);
            }
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getDomicilioList().remove(domicilio);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getDomicilioList().add(domicilio);
                idClienteNew = em.merge(idClienteNew);
            }
            for (Envio envioListNewEnvio : envioListNew) {
                if (!envioListOld.contains(envioListNewEnvio)) {
                    Domicilio oldIdDomicilioOfEnvioListNewEnvio = envioListNewEnvio.getIdDomicilio();
                    envioListNewEnvio.setIdDomicilio(domicilio);
                    envioListNewEnvio = em.merge(envioListNewEnvio);
                    if (oldIdDomicilioOfEnvioListNewEnvio != null && !oldIdDomicilioOfEnvioListNewEnvio.equals(domicilio)) {
                        oldIdDomicilioOfEnvioListNewEnvio.getEnvioList().remove(envioListNewEnvio);
                        oldIdDomicilioOfEnvioListNewEnvio = em.merge(oldIdDomicilioOfEnvioListNewEnvio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = domicilio.getId();
                if (findDomicilio(id) == null) {
                    throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.");
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
            Domicilio domicilio;
            try {
                domicilio = em.getReference(Domicilio.class, id);
                domicilio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The domicilio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Envio> envioListOrphanCheck = domicilio.getEnvioList();
            for (Envio envioListOrphanCheckEnvio : envioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Domicilio (" + domicilio + ") cannot be destroyed since the Envio " + envioListOrphanCheckEnvio + " in its envioList field has a non-nullable idDomicilio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ciudad idCiudad = domicilio.getIdCiudad();
            if (idCiudad != null) {
                idCiudad.getDomicilioList().remove(domicilio);
                idCiudad = em.merge(idCiudad);
            }
            Persona idCliente = domicilio.getIdCliente();
            if (idCliente != null) {
                idCliente.getDomicilioList().remove(domicilio);
                idCliente = em.merge(idCliente);
            }
            em.remove(domicilio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Domicilio> findDomicilioEntities() {
        return findDomicilioEntities(true, -1, -1);
    }

    public List<Domicilio> findDomicilioEntities(int maxResults, int firstResult) {
        return findDomicilioEntities(false, maxResults, firstResult);
    }

    private List<Domicilio> findDomicilioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Domicilio.class));
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

    public Domicilio findDomicilio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Domicilio.class, id);
        } finally {
            em.close();
        }
    }

    public int getDomicilioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Domicilio> rt = cq.from(Domicilio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
