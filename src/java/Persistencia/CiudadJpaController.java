/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Ciudad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Departamento;
import DTO.Domicilio;
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
public class CiudadJpaController implements Serializable {

    public CiudadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ciudad ciudad) {
        if (ciudad.getDomicilioList() == null) {
            ciudad.setDomicilioList(new ArrayList<Domicilio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento idDepartamento = ciudad.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento = em.getReference(idDepartamento.getClass(), idDepartamento.getId());
                ciudad.setIdDepartamento(idDepartamento);
            }
            List<Domicilio> attachedDomicilioList = new ArrayList<Domicilio>();
            for (Domicilio domicilioListDomicilioToAttach : ciudad.getDomicilioList()) {
                domicilioListDomicilioToAttach = em.getReference(domicilioListDomicilioToAttach.getClass(), domicilioListDomicilioToAttach.getId());
                attachedDomicilioList.add(domicilioListDomicilioToAttach);
            }
            ciudad.setDomicilioList(attachedDomicilioList);
            em.persist(ciudad);
            if (idDepartamento != null) {
                idDepartamento.getCiudadList().add(ciudad);
                idDepartamento = em.merge(idDepartamento);
            }
            for (Domicilio domicilioListDomicilio : ciudad.getDomicilioList()) {
                Ciudad oldIdCiudadOfDomicilioListDomicilio = domicilioListDomicilio.getIdCiudad();
                domicilioListDomicilio.setIdCiudad(ciudad);
                domicilioListDomicilio = em.merge(domicilioListDomicilio);
                if (oldIdCiudadOfDomicilioListDomicilio != null) {
                    oldIdCiudadOfDomicilioListDomicilio.getDomicilioList().remove(domicilioListDomicilio);
                    oldIdCiudadOfDomicilioListDomicilio = em.merge(oldIdCiudadOfDomicilioListDomicilio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ciudad ciudad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ciudad persistentCiudad = em.find(Ciudad.class, ciudad.getId());
            Departamento idDepartamentoOld = persistentCiudad.getIdDepartamento();
            Departamento idDepartamentoNew = ciudad.getIdDepartamento();
            List<Domicilio> domicilioListOld = persistentCiudad.getDomicilioList();
            List<Domicilio> domicilioListNew = ciudad.getDomicilioList();
            List<String> illegalOrphanMessages = null;
            for (Domicilio domicilioListOldDomicilio : domicilioListOld) {
                if (!domicilioListNew.contains(domicilioListOldDomicilio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Domicilio " + domicilioListOldDomicilio + " since its idCiudad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idDepartamentoNew != null) {
                idDepartamentoNew = em.getReference(idDepartamentoNew.getClass(), idDepartamentoNew.getId());
                ciudad.setIdDepartamento(idDepartamentoNew);
            }
            List<Domicilio> attachedDomicilioListNew = new ArrayList<Domicilio>();
            for (Domicilio domicilioListNewDomicilioToAttach : domicilioListNew) {
                domicilioListNewDomicilioToAttach = em.getReference(domicilioListNewDomicilioToAttach.getClass(), domicilioListNewDomicilioToAttach.getId());
                attachedDomicilioListNew.add(domicilioListNewDomicilioToAttach);
            }
            domicilioListNew = attachedDomicilioListNew;
            ciudad.setDomicilioList(domicilioListNew);
            ciudad = em.merge(ciudad);
            if (idDepartamentoOld != null && !idDepartamentoOld.equals(idDepartamentoNew)) {
                idDepartamentoOld.getCiudadList().remove(ciudad);
                idDepartamentoOld = em.merge(idDepartamentoOld);
            }
            if (idDepartamentoNew != null && !idDepartamentoNew.equals(idDepartamentoOld)) {
                idDepartamentoNew.getCiudadList().add(ciudad);
                idDepartamentoNew = em.merge(idDepartamentoNew);
            }
            for (Domicilio domicilioListNewDomicilio : domicilioListNew) {
                if (!domicilioListOld.contains(domicilioListNewDomicilio)) {
                    Ciudad oldIdCiudadOfDomicilioListNewDomicilio = domicilioListNewDomicilio.getIdCiudad();
                    domicilioListNewDomicilio.setIdCiudad(ciudad);
                    domicilioListNewDomicilio = em.merge(domicilioListNewDomicilio);
                    if (oldIdCiudadOfDomicilioListNewDomicilio != null && !oldIdCiudadOfDomicilioListNewDomicilio.equals(ciudad)) {
                        oldIdCiudadOfDomicilioListNewDomicilio.getDomicilioList().remove(domicilioListNewDomicilio);
                        oldIdCiudadOfDomicilioListNewDomicilio = em.merge(oldIdCiudadOfDomicilioListNewDomicilio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ciudad.getId();
                if (findCiudad(id) == null) {
                    throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.");
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
            Ciudad ciudad;
            try {
                ciudad = em.getReference(Ciudad.class, id);
                ciudad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ciudad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Domicilio> domicilioListOrphanCheck = ciudad.getDomicilioList();
            for (Domicilio domicilioListOrphanCheckDomicilio : domicilioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ciudad (" + ciudad + ") cannot be destroyed since the Domicilio " + domicilioListOrphanCheckDomicilio + " in its domicilioList field has a non-nullable idCiudad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamento idDepartamento = ciudad.getIdDepartamento();
            if (idDepartamento != null) {
                idDepartamento.getCiudadList().remove(ciudad);
                idDepartamento = em.merge(idDepartamento);
            }
            em.remove(ciudad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ciudad> findCiudadEntities() {
        return findCiudadEntities(true, -1, -1);
    }

    public List<Ciudad> findCiudadEntities(int maxResults, int firstResult) {
        return findCiudadEntities(false, maxResults, firstResult);
    }

    private List<Ciudad> findCiudadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ciudad.class));
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

    public Ciudad findCiudad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ciudad.class, id);
        } finally {
            em.close();
        }
    }

    public int getCiudadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ciudad> rt = cq.from(Ciudad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
