/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import DTO.Carrito;
import DTO.CarritoPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import DTO.Persona;
import DTO.Producto;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Jefersonrr
 */
public class CarritoJpaController implements Serializable {

    public CarritoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrito carrito) throws PreexistingEntityException, Exception {
        if (carrito.getCarritoPK() == null) {
            carrito.setCarritoPK(new CarritoPK());
        }
        carrito.getCarritoPK().setIdProducto(carrito.getProducto().getId());
        carrito.getCarritoPK().setIdCliente(carrito.getPersona().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = carrito.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getCedula());
                carrito.setPersona(persona);
            }
            Producto producto = carrito.getProducto();
            if (producto != null) {
                producto = em.getReference(producto.getClass(), producto.getId());
                carrito.setProducto(producto);
            }
            em.persist(carrito);
            if (persona != null) {
                persona.getCarritoList().add(carrito);
                persona = em.merge(persona);
            }
            if (producto != null) {
                producto.getCarritoList().add(carrito);
                producto = em.merge(producto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrito(carrito.getCarritoPK()) != null) {
                throw new PreexistingEntityException("Carrito " + carrito + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrito carrito) throws NonexistentEntityException, Exception {
        carrito.getCarritoPK().setIdProducto(carrito.getProducto().getId());
        carrito.getCarritoPK().setIdCliente(carrito.getPersona().getCedula());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrito persistentCarrito = em.find(Carrito.class, carrito.getCarritoPK());
            Persona personaOld = persistentCarrito.getPersona();
            Persona personaNew = carrito.getPersona();
            Producto productoOld = persistentCarrito.getProducto();
            Producto productoNew = carrito.getProducto();
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getCedula());
                carrito.setPersona(personaNew);
            }
            if (productoNew != null) {
                productoNew = em.getReference(productoNew.getClass(), productoNew.getId());
                carrito.setProducto(productoNew);
            }
            carrito = em.merge(carrito);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.getCarritoList().remove(carrito);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.getCarritoList().add(carrito);
                personaNew = em.merge(personaNew);
            }
            if (productoOld != null && !productoOld.equals(productoNew)) {
                productoOld.getCarritoList().remove(carrito);
                productoOld = em.merge(productoOld);
            }
            if (productoNew != null && !productoNew.equals(productoOld)) {
                productoNew.getCarritoList().add(carrito);
                productoNew = em.merge(productoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CarritoPK id = carrito.getCarritoPK();
                if (findCarrito(id) == null) {
                    throw new NonexistentEntityException("The carrito with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CarritoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrito carrito;
            try {
                carrito = em.getReference(Carrito.class, id);
                carrito.getCarritoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrito with id " + id + " no longer exists.", enfe);
            }
            Persona persona = carrito.getPersona();
            if (persona != null) {
                persona.getCarritoList().remove(carrito);
                persona = em.merge(persona);
            }
            Producto producto = carrito.getProducto();
            if (producto != null) {
                producto.getCarritoList().remove(carrito);
                producto = em.merge(producto);
            }
            em.remove(carrito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrito> findCarritoEntities() {
        return findCarritoEntities(true, -1, -1);
    }

    public List<Carrito> findCarritoEntities(int maxResults, int firstResult) {
        return findCarritoEntities(false, maxResults, firstResult);
    }

    private List<Carrito> findCarritoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrito.class));
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

    public Carrito findCarrito(CarritoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrito.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarritoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrito> rt = cq.from(Carrito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
