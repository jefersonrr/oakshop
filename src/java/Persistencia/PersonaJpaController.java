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
import java.util.ArrayList;
import java.util.List;
import DTO.Domicilio;
import DTO.Calificacion;
import DTO.Compra;
import DTO.MetodoPago;
import DTO.Persona;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
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
        if (persona.getCarritoList() == null) {
            persona.setCarritoList(new ArrayList<Carrito>());
        }
        if (persona.getDomicilioList() == null) {
            persona.setDomicilioList(new ArrayList<Domicilio>());
        }
        if (persona.getCalificacionList() == null) {
            persona.setCalificacionList(new ArrayList<Calificacion>());
        }
        if (persona.getCompraList() == null) {
            persona.setCompraList(new ArrayList<Compra>());
        }
        if (persona.getMetodoPagoList() == null) {
            persona.setMetodoPagoList(new ArrayList<MetodoPago>());
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
            List<Carrito> attachedCarritoList = new ArrayList<Carrito>();
            for (Carrito carritoListCarritoToAttach : persona.getCarritoList()) {
                carritoListCarritoToAttach = em.getReference(carritoListCarritoToAttach.getClass(), carritoListCarritoToAttach.getCarritoPK());
                attachedCarritoList.add(carritoListCarritoToAttach);
            }
            persona.setCarritoList(attachedCarritoList);
            List<Domicilio> attachedDomicilioList = new ArrayList<Domicilio>();
            for (Domicilio domicilioListDomicilioToAttach : persona.getDomicilioList()) {
                domicilioListDomicilioToAttach = em.getReference(domicilioListDomicilioToAttach.getClass(), domicilioListDomicilioToAttach.getId());
                attachedDomicilioList.add(domicilioListDomicilioToAttach);
            }
            persona.setDomicilioList(attachedDomicilioList);
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : persona.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getCalificacionPK());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            persona.setCalificacionList(attachedCalificacionList);
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : persona.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            persona.setCompraList(attachedCompraList);
            List<MetodoPago> attachedMetodoPagoList = new ArrayList<MetodoPago>();
            for (MetodoPago metodoPagoListMetodoPagoToAttach : persona.getMetodoPagoList()) {
                metodoPagoListMetodoPagoToAttach = em.getReference(metodoPagoListMetodoPagoToAttach.getClass(), metodoPagoListMetodoPagoToAttach.getId());
                attachedMetodoPagoList.add(metodoPagoListMetodoPagoToAttach);
            }
            persona.setMetodoPagoList(attachedMetodoPagoList);
            em.persist(persona);
            if (idRol != null) {
                idRol.getPersonaList().add(persona);
                idRol = em.merge(idRol);
            }
            for (Carrito carritoListCarrito : persona.getCarritoList()) {
                Persona oldPersonaOfCarritoListCarrito = carritoListCarrito.getPersona();
                carritoListCarrito.setPersona(persona);
                carritoListCarrito = em.merge(carritoListCarrito);
                if (oldPersonaOfCarritoListCarrito != null) {
                    oldPersonaOfCarritoListCarrito.getCarritoList().remove(carritoListCarrito);
                    oldPersonaOfCarritoListCarrito = em.merge(oldPersonaOfCarritoListCarrito);
                }
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
            for (Calificacion calificacionListCalificacion : persona.getCalificacionList()) {
                Persona oldPersonaOfCalificacionListCalificacion = calificacionListCalificacion.getPersona();
                calificacionListCalificacion.setPersona(persona);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldPersonaOfCalificacionListCalificacion != null) {
                    oldPersonaOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldPersonaOfCalificacionListCalificacion = em.merge(oldPersonaOfCalificacionListCalificacion);
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
            for (MetodoPago metodoPagoListMetodoPago : persona.getMetodoPagoList()) {
                Persona oldIdClienteOfMetodoPagoListMetodoPago = metodoPagoListMetodoPago.getIdCliente();
                metodoPagoListMetodoPago.setIdCliente(persona);
                metodoPagoListMetodoPago = em.merge(metodoPagoListMetodoPago);
                if (oldIdClienteOfMetodoPagoListMetodoPago != null) {
                    oldIdClienteOfMetodoPagoListMetodoPago.getMetodoPagoList().remove(metodoPagoListMetodoPago);
                    oldIdClienteOfMetodoPagoListMetodoPago = em.merge(oldIdClienteOfMetodoPagoListMetodoPago);
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
            List<Carrito> carritoListOld = persistentPersona.getCarritoList();
            List<Carrito> carritoListNew = persona.getCarritoList();
            List<Domicilio> domicilioListOld = persistentPersona.getDomicilioList();
            List<Domicilio> domicilioListNew = persona.getDomicilioList();
            List<Calificacion> calificacionListOld = persistentPersona.getCalificacionList();
            List<Calificacion> calificacionListNew = persona.getCalificacionList();
            List<Compra> compraListOld = persistentPersona.getCompraList();
            List<Compra> compraListNew = persona.getCompraList();
            List<MetodoPago> metodoPagoListOld = persistentPersona.getMetodoPagoList();
            List<MetodoPago> metodoPagoListNew = persona.getMetodoPagoList();
            List<String> illegalOrphanMessages = null;
            for (Carrito carritoListOldCarrito : carritoListOld) {
                if (!carritoListNew.contains(carritoListOldCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrito " + carritoListOldCarrito + " since its persona field is not nullable.");
                }
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
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its persona field is not nullable.");
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
            for (MetodoPago metodoPagoListOldMetodoPago : metodoPagoListOld) {
                if (!metodoPagoListNew.contains(metodoPagoListOldMetodoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MetodoPago " + metodoPagoListOldMetodoPago + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getId());
                persona.setIdRol(idRolNew);
            }
            List<Carrito> attachedCarritoListNew = new ArrayList<Carrito>();
            for (Carrito carritoListNewCarritoToAttach : carritoListNew) {
                carritoListNewCarritoToAttach = em.getReference(carritoListNewCarritoToAttach.getClass(), carritoListNewCarritoToAttach.getCarritoPK());
                attachedCarritoListNew.add(carritoListNewCarritoToAttach);
            }
            carritoListNew = attachedCarritoListNew;
            persona.setCarritoList(carritoListNew);
            List<Domicilio> attachedDomicilioListNew = new ArrayList<Domicilio>();
            for (Domicilio domicilioListNewDomicilioToAttach : domicilioListNew) {
                domicilioListNewDomicilioToAttach = em.getReference(domicilioListNewDomicilioToAttach.getClass(), domicilioListNewDomicilioToAttach.getId());
                attachedDomicilioListNew.add(domicilioListNewDomicilioToAttach);
            }
            domicilioListNew = attachedDomicilioListNew;
            persona.setDomicilioList(domicilioListNew);
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getCalificacionPK());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            persona.setCalificacionList(calificacionListNew);
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            persona.setCompraList(compraListNew);
            List<MetodoPago> attachedMetodoPagoListNew = new ArrayList<MetodoPago>();
            for (MetodoPago metodoPagoListNewMetodoPagoToAttach : metodoPagoListNew) {
                metodoPagoListNewMetodoPagoToAttach = em.getReference(metodoPagoListNewMetodoPagoToAttach.getClass(), metodoPagoListNewMetodoPagoToAttach.getId());
                attachedMetodoPagoListNew.add(metodoPagoListNewMetodoPagoToAttach);
            }
            metodoPagoListNew = attachedMetodoPagoListNew;
            persona.setMetodoPagoList(metodoPagoListNew);
            persona = em.merge(persona);
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getPersonaList().remove(persona);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getPersonaList().add(persona);
                idRolNew = em.merge(idRolNew);
            }
            for (Carrito carritoListNewCarrito : carritoListNew) {
                if (!carritoListOld.contains(carritoListNewCarrito)) {
                    Persona oldPersonaOfCarritoListNewCarrito = carritoListNewCarrito.getPersona();
                    carritoListNewCarrito.setPersona(persona);
                    carritoListNewCarrito = em.merge(carritoListNewCarrito);
                    if (oldPersonaOfCarritoListNewCarrito != null && !oldPersonaOfCarritoListNewCarrito.equals(persona)) {
                        oldPersonaOfCarritoListNewCarrito.getCarritoList().remove(carritoListNewCarrito);
                        oldPersonaOfCarritoListNewCarrito = em.merge(oldPersonaOfCarritoListNewCarrito);
                    }
                }
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
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Persona oldPersonaOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getPersona();
                    calificacionListNewCalificacion.setPersona(persona);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldPersonaOfCalificacionListNewCalificacion != null && !oldPersonaOfCalificacionListNewCalificacion.equals(persona)) {
                        oldPersonaOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldPersonaOfCalificacionListNewCalificacion = em.merge(oldPersonaOfCalificacionListNewCalificacion);
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
            List<Carrito> carritoListOrphanCheck = persona.getCarritoList();
            for (Carrito carritoListOrphanCheckCarrito : carritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Carrito " + carritoListOrphanCheckCarrito + " in its carritoList field has a non-nullable persona field.");
            }
            List<Domicilio> domicilioListOrphanCheck = persona.getDomicilioList();
            for (Domicilio domicilioListOrphanCheckDomicilio : domicilioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Domicilio " + domicilioListOrphanCheckDomicilio + " in its domicilioList field has a non-nullable idCliente field.");
            }
            List<Calificacion> calificacionListOrphanCheck = persona.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable persona field.");
            }
            List<Compra> compraListOrphanCheck = persona.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable idCliente field.");
            }
            List<MetodoPago> metodoPagoListOrphanCheck = persona.getMetodoPagoList();
            for (MetodoPago metodoPagoListOrphanCheckMetodoPago : metodoPagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the MetodoPago " + metodoPagoListOrphanCheckMetodoPago + " in its metodoPagoList field has a non-nullable idCliente field.");
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
