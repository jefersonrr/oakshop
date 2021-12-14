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
import DTO.Rol;
import DTO.Carrito;
import DTO.Calificacion;
import DTO.MetodoPago;
import java.util.ArrayList;
import java.util.List;
import DTO.Domicilio;
import DTO.Compra;
import DTO.Persona;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws PreexistingEntityException, Exception {
        if (persona.getMetodoPagoList() == null) {
            persona.setMetodoPagoList(new ArrayList<MetodoPago>());
        }
        if (persona.getDomicilioList() == null) {
            persona.setDomicilioList(new ArrayList<Domicilio>());
        }
        if (persona.getCompraList() == null) {
            persona.setCompraList(new ArrayList<Compra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol idRol = persona.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getId());
                persona.setIdRol(idRol);
            }
            Carrito carrito = persona.getCarrito();
            if (carrito != null) {
                carrito = em.getReference(carrito.getClass(), carrito.getIdCliente());
                persona.setCarrito(carrito);
            }
            Calificacion calificacion = persona.getCalificacion();
            if (calificacion != null) {
                calificacion = em.getReference(calificacion.getClass(), calificacion.getIdCliente());
                persona.setCalificacion(calificacion);
            }
            List<MetodoPago> attachedMetodoPagoList = new ArrayList<MetodoPago>();
            for (MetodoPago metodoPagoListMetodoPagoToAttach : persona.getMetodoPagoList()) {
                metodoPagoListMetodoPagoToAttach = em.getReference(metodoPagoListMetodoPagoToAttach.getClass(), metodoPagoListMetodoPagoToAttach.getId());
                attachedMetodoPagoList.add(metodoPagoListMetodoPagoToAttach);
            }
            persona.setMetodoPagoList(attachedMetodoPagoList);
            List<Domicilio> attachedDomicilioList = new ArrayList<Domicilio>();
            for (Domicilio domicilioListDomicilioToAttach : persona.getDomicilioList()) {
                domicilioListDomicilioToAttach = em.getReference(domicilioListDomicilioToAttach.getClass(), domicilioListDomicilioToAttach.getId());
                attachedDomicilioList.add(domicilioListDomicilioToAttach);
            }
            persona.setDomicilioList(attachedDomicilioList);
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : persona.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            persona.setCompraList(attachedCompraList);
            em.persist(persona);
            if (idRol != null) {
                idRol.getPersonaList().add(persona);
                idRol = em.merge(idRol);
            }
            if (carrito != null) {
                Persona oldPersonaOfCarrito = carrito.getPersona();
                if (oldPersonaOfCarrito != null) {
                    oldPersonaOfCarrito.setCarrito(null);
                    oldPersonaOfCarrito = em.merge(oldPersonaOfCarrito);
                }
                carrito.setPersona(persona);
                carrito = em.merge(carrito);
            }
            if (calificacion != null) {
                Persona oldPersonaOfCalificacion = calificacion.getPersona();
                if (oldPersonaOfCalificacion != null) {
                    oldPersonaOfCalificacion.setCalificacion(null);
                    oldPersonaOfCalificacion = em.merge(oldPersonaOfCalificacion);
                }
                calificacion.setPersona(persona);
                calificacion = em.merge(calificacion);
            }
            for (MetodoPago metodoPagoListMetodoPago : persona.getMetodoPagoList()) {
                Persona oldIdClienteOfMetodoPagoListMetodoPago = metodoPagoListMetodoPago.getIdCliente();
                metodoPagoListMetodoPago.setIdCliente(persona);
                metodoPagoListMetodoPago = em.merge(metodoPagoListMetodoPago);
                if (oldIdClienteOfMetodoPagoListMetodoPago != null) {
                    oldIdClienteOfMetodoPagoListMetodoPago.getMetodoPagoList().remove(metodoPagoListMetodoPago);
                    oldIdClienteOfMetodoPagoListMetodoPago = em.merge(oldIdClienteOfMetodoPagoListMetodoPago);
                }
            }
            for (Domicilio domicilioListDomicilio : persona.getDomicilioList()) {
                Persona oldIdClienteOfDomicilioListDomicilio = domicilioListDomicilio.getIdCliente();
                domicilioListDomicilio.setIdCliente(persona);
                domicilioListDomicilio = em.merge(domicilioListDomicilio);
                if (oldIdClienteOfDomicilioListDomicilio != null) {
                    oldIdClienteOfDomicilioListDomicilio.getDomicilioList().remove(domicilioListDomicilio);
                    oldIdClienteOfDomicilioListDomicilio = em.merge(oldIdClienteOfDomicilioListDomicilio);
                }
            }
            for (Compra compraListCompra : persona.getCompraList()) {
                Persona oldIdClienteOfCompraListCompra = compraListCompra.getIdCliente();
                compraListCompra.setIdCliente(persona);
                compraListCompra = em.merge(compraListCompra);
                if (oldIdClienteOfCompraListCompra != null) {
                    oldIdClienteOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldIdClienteOfCompraListCompra = em.merge(oldIdClienteOfCompraListCompra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersona(persona.getCedula()) != null) {
                throw new PreexistingEntityException("Persona " + persona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getCedula());
            Rol idRolOld = persistentPersona.getIdRol();
            Rol idRolNew = persona.getIdRol();
            Carrito carritoOld = persistentPersona.getCarrito();
            Carrito carritoNew = persona.getCarrito();
            Calificacion calificacionOld = persistentPersona.getCalificacion();
            Calificacion calificacionNew = persona.getCalificacion();
            List<MetodoPago> metodoPagoListOld = persistentPersona.getMetodoPagoList();
            List<MetodoPago> metodoPagoListNew = persona.getMetodoPagoList();
            List<Domicilio> domicilioListOld = persistentPersona.getDomicilioList();
            List<Domicilio> domicilioListNew = persona.getDomicilioList();
            List<Compra> compraListOld = persistentPersona.getCompraList();
            List<Compra> compraListNew = persona.getCompraList();
            List<String> illegalOrphanMessages = null;
            if (carritoOld != null && !carritoOld.equals(carritoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Carrito " + carritoOld + " since its persona field is not nullable.");
            }
            if (calificacionOld != null && !calificacionOld.equals(calificacionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Calificacion " + calificacionOld + " since its persona field is not nullable.");
            }
            for (MetodoPago metodoPagoListOldMetodoPago : metodoPagoListOld) {
                if (!metodoPagoListNew.contains(metodoPagoListOldMetodoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MetodoPago " + metodoPagoListOldMetodoPago + " since its idCliente field is not nullable.");
                }
            }
            for (Domicilio domicilioListOldDomicilio : domicilioListOld) {
                if (!domicilioListNew.contains(domicilioListOldDomicilio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Domicilio " + domicilioListOldDomicilio + " since its idCliente field is not nullable.");
                }
            }
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getId());
                persona.setIdRol(idRolNew);
            }
            if (carritoNew != null) {
                carritoNew = em.getReference(carritoNew.getClass(), carritoNew.getIdCliente());
                persona.setCarrito(carritoNew);
            }
            if (calificacionNew != null) {
                calificacionNew = em.getReference(calificacionNew.getClass(), calificacionNew.getIdCliente());
                persona.setCalificacion(calificacionNew);
            }
            List<MetodoPago> attachedMetodoPagoListNew = new ArrayList<MetodoPago>();
            for (MetodoPago metodoPagoListNewMetodoPagoToAttach : metodoPagoListNew) {
                metodoPagoListNewMetodoPagoToAttach = em.getReference(metodoPagoListNewMetodoPagoToAttach.getClass(), metodoPagoListNewMetodoPagoToAttach.getId());
                attachedMetodoPagoListNew.add(metodoPagoListNewMetodoPagoToAttach);
            }
            metodoPagoListNew = attachedMetodoPagoListNew;
            persona.setMetodoPagoList(metodoPagoListNew);
            List<Domicilio> attachedDomicilioListNew = new ArrayList<Domicilio>();
            for (Domicilio domicilioListNewDomicilioToAttach : domicilioListNew) {
                domicilioListNewDomicilioToAttach = em.getReference(domicilioListNewDomicilioToAttach.getClass(), domicilioListNewDomicilioToAttach.getId());
                attachedDomicilioListNew.add(domicilioListNewDomicilioToAttach);
            }
            domicilioListNew = attachedDomicilioListNew;
            persona.setDomicilioList(domicilioListNew);
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            persona.setCompraList(compraListNew);
            persona = em.merge(persona);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getPersonaList().remove(persona);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getPersonaList().add(persona);
                idRolNew = em.merge(idRolNew);
            }
            if (carritoNew != null && !carritoNew.equals(carritoOld)) {
                Persona oldPersonaOfCarrito = carritoNew.getPersona();
                if (oldPersonaOfCarrito != null) {
                    oldPersonaOfCarrito.setCarrito(null);
                    oldPersonaOfCarrito = em.merge(oldPersonaOfCarrito);
                }
                carritoNew.setPersona(persona);
                carritoNew = em.merge(carritoNew);
            }
            if (calificacionNew != null && !calificacionNew.equals(calificacionOld)) {
                Persona oldPersonaOfCalificacion = calificacionNew.getPersona();
                if (oldPersonaOfCalificacion != null) {
                    oldPersonaOfCalificacion.setCalificacion(null);
                    oldPersonaOfCalificacion = em.merge(oldPersonaOfCalificacion);
                }
                calificacionNew.setPersona(persona);
                calificacionNew = em.merge(calificacionNew);
            }
            for (MetodoPago metodoPagoListNewMetodoPago : metodoPagoListNew) {
                if (!metodoPagoListOld.contains(metodoPagoListNewMetodoPago)) {
                    Persona oldIdClienteOfMetodoPagoListNewMetodoPago = metodoPagoListNewMetodoPago.getIdCliente();
                    metodoPagoListNewMetodoPago.setIdCliente(persona);
                    metodoPagoListNewMetodoPago = em.merge(metodoPagoListNewMetodoPago);
                    if (oldIdClienteOfMetodoPagoListNewMetodoPago != null && !oldIdClienteOfMetodoPagoListNewMetodoPago.equals(persona)) {
                        oldIdClienteOfMetodoPagoListNewMetodoPago.getMetodoPagoList().remove(metodoPagoListNewMetodoPago);
                        oldIdClienteOfMetodoPagoListNewMetodoPago = em.merge(oldIdClienteOfMetodoPagoListNewMetodoPago);
                    }
                }
            }
            for (Domicilio domicilioListNewDomicilio : domicilioListNew) {
                if (!domicilioListOld.contains(domicilioListNewDomicilio)) {
                    Persona oldIdClienteOfDomicilioListNewDomicilio = domicilioListNewDomicilio.getIdCliente();
                    domicilioListNewDomicilio.setIdCliente(persona);
                    domicilioListNewDomicilio = em.merge(domicilioListNewDomicilio);
                    if (oldIdClienteOfDomicilioListNewDomicilio != null && !oldIdClienteOfDomicilioListNewDomicilio.equals(persona)) {
                        oldIdClienteOfDomicilioListNewDomicilio.getDomicilioList().remove(domicilioListNewDomicilio);
                        oldIdClienteOfDomicilioListNewDomicilio = em.merge(oldIdClienteOfDomicilioListNewDomicilio);
                    }
                }
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Persona oldIdClienteOfCompraListNewCompra = compraListNewCompra.getIdCliente();
                    compraListNewCompra.setIdCliente(persona);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldIdClienteOfCompraListNewCompra != null && !oldIdClienteOfCompraListNewCompra.equals(persona)) {
                        oldIdClienteOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldIdClienteOfCompraListNewCompra = em.merge(oldIdClienteOfCompraListNewCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = persona.getCedula();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Carrito carritoOrphanCheck = persona.getCarrito();
            if (carritoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Carrito " + carritoOrphanCheck + " in its carrito field has a non-nullable persona field.");
            }
            Calificacion calificacionOrphanCheck = persona.getCalificacion();
            if (calificacionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Calificacion " + calificacionOrphanCheck + " in its calificacion field has a non-nullable persona field.");
            }
            List<MetodoPago> metodoPagoListOrphanCheck = persona.getMetodoPagoList();
            for (MetodoPago metodoPagoListOrphanCheckMetodoPago : metodoPagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the MetodoPago " + metodoPagoListOrphanCheckMetodoPago + " in its metodoPagoList field has a non-nullable idCliente field.");
            }
            List<Domicilio> domicilioListOrphanCheck = persona.getDomicilioList();
            for (Domicilio domicilioListOrphanCheckDomicilio : domicilioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Domicilio " + domicilioListOrphanCheckDomicilio + " in its domicilioList field has a non-nullable idCliente field.");
            }
            List<Compra> compraListOrphanCheck = persona.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable idCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rol idRol = persona.getIdRol();
            if (idRol != null) {
                idRol.getPersonaList().remove(persona);
                idRol = em.merge(idRol);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
