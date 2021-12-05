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
import DTO.Color;
import DTO.Publicacion;
import DTO.Talla;
import DTO.DetalleCompra;
import java.util.ArrayList;
import java.util.List;
import DTO.Carrito;
import DTO.Calificacion;
import DTO.Producto;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Cristian
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getDetalleCompraList() == null) {
            producto.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        if (producto.getCarritoList() == null) {
            producto.setCarritoList(new ArrayList<Carrito>());
        }
        if (producto.getCalificacionList() == null) {
            producto.setCalificacionList(new ArrayList<Calificacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Color idColor = producto.getIdColor();
            if (idColor != null) {
                idColor = em.getReference(idColor.getClass(), idColor.getId());
                producto.setIdColor(idColor);
            }
            Publicacion idPublicacion = producto.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion = em.getReference(idPublicacion.getClass(), idPublicacion.getId());
                producto.setIdPublicacion(idPublicacion);
            }
            Talla idTalla = producto.getIdTalla();
            if (idTalla != null) {
                idTalla = em.getReference(idTalla.getClass(), idTalla.getId());
                producto.setIdTalla(idTalla);
            }
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : producto.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getIdCompra());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            producto.setDetalleCompraList(attachedDetalleCompraList);
            List<Carrito> attachedCarritoList = new ArrayList<Carrito>();
            for (Carrito carritoListCarritoToAttach : producto.getCarritoList()) {
                carritoListCarritoToAttach = em.getReference(carritoListCarritoToAttach.getClass(), carritoListCarritoToAttach.getIdCliente());
                attachedCarritoList.add(carritoListCarritoToAttach);
            }
            producto.setCarritoList(attachedCarritoList);
            List<Calificacion> attachedCalificacionList = new ArrayList<Calificacion>();
            for (Calificacion calificacionListCalificacionToAttach : producto.getCalificacionList()) {
                calificacionListCalificacionToAttach = em.getReference(calificacionListCalificacionToAttach.getClass(), calificacionListCalificacionToAttach.getIdCliente());
                attachedCalificacionList.add(calificacionListCalificacionToAttach);
            }
            producto.setCalificacionList(attachedCalificacionList);
            em.persist(producto);
            if (idColor != null) {
                idColor.getProductoList().add(producto);
                idColor = em.merge(idColor);
            }
            if (idPublicacion != null) {
                idPublicacion.getProductoList().add(producto);
                idPublicacion = em.merge(idPublicacion);
            }
            if (idTalla != null) {
                idTalla.getProductoList().add(producto);
                idTalla = em.merge(idTalla);
            }
            for (DetalleCompra detalleCompraListDetalleCompra : producto.getDetalleCompraList()) {
                Producto oldIdProductoOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getIdProducto();
                detalleCompraListDetalleCompra.setIdProducto(producto);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldIdProductoOfDetalleCompraListDetalleCompra != null) {
                    oldIdProductoOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldIdProductoOfDetalleCompraListDetalleCompra = em.merge(oldIdProductoOfDetalleCompraListDetalleCompra);
                }
            }
            for (Carrito carritoListCarrito : producto.getCarritoList()) {
                Producto oldIdProductoOfCarritoListCarrito = carritoListCarrito.getIdProducto();
                carritoListCarrito.setIdProducto(producto);
                carritoListCarrito = em.merge(carritoListCarrito);
                if (oldIdProductoOfCarritoListCarrito != null) {
                    oldIdProductoOfCarritoListCarrito.getCarritoList().remove(carritoListCarrito);
                    oldIdProductoOfCarritoListCarrito = em.merge(oldIdProductoOfCarritoListCarrito);
                }
            }
            for (Calificacion calificacionListCalificacion : producto.getCalificacionList()) {
                Producto oldIdProductoOfCalificacionListCalificacion = calificacionListCalificacion.getIdProducto();
                calificacionListCalificacion.setIdProducto(producto);
                calificacionListCalificacion = em.merge(calificacionListCalificacion);
                if (oldIdProductoOfCalificacionListCalificacion != null) {
                    oldIdProductoOfCalificacionListCalificacion.getCalificacionList().remove(calificacionListCalificacion);
                    oldIdProductoOfCalificacionListCalificacion = em.merge(oldIdProductoOfCalificacionListCalificacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Color idColorOld = persistentProducto.getIdColor();
            Color idColorNew = producto.getIdColor();
            Publicacion idPublicacionOld = persistentProducto.getIdPublicacion();
            Publicacion idPublicacionNew = producto.getIdPublicacion();
            Talla idTallaOld = persistentProducto.getIdTalla();
            Talla idTallaNew = producto.getIdTalla();
            List<DetalleCompra> detalleCompraListOld = persistentProducto.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = producto.getDetalleCompraList();
            List<Carrito> carritoListOld = persistentProducto.getCarritoList();
            List<Carrito> carritoListNew = producto.getCarritoList();
            List<Calificacion> calificacionListOld = persistentProducto.getCalificacionList();
            List<Calificacion> calificacionListNew = producto.getCalificacionList();
            List<String> illegalOrphanMessages = null;
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCompra " + detalleCompraListOldDetalleCompra + " since its idProducto field is not nullable.");
                }
            }
            for (Carrito carritoListOldCarrito : carritoListOld) {
                if (!carritoListNew.contains(carritoListOldCarrito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Carrito " + carritoListOldCarrito + " since its idProducto field is not nullable.");
                }
            }
            for (Calificacion calificacionListOldCalificacion : calificacionListOld) {
                if (!calificacionListNew.contains(calificacionListOldCalificacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Calificacion " + calificacionListOldCalificacion + " since its idProducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idColorNew != null) {
                idColorNew = em.getReference(idColorNew.getClass(), idColorNew.getId());
                producto.setIdColor(idColorNew);
            }
            if (idPublicacionNew != null) {
                idPublicacionNew = em.getReference(idPublicacionNew.getClass(), idPublicacionNew.getId());
                producto.setIdPublicacion(idPublicacionNew);
            }
            if (idTallaNew != null) {
                idTallaNew = em.getReference(idTallaNew.getClass(), idTallaNew.getId());
                producto.setIdTalla(idTallaNew);
            }
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getIdCompra());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            producto.setDetalleCompraList(detalleCompraListNew);
            List<Carrito> attachedCarritoListNew = new ArrayList<Carrito>();
            for (Carrito carritoListNewCarritoToAttach : carritoListNew) {
                carritoListNewCarritoToAttach = em.getReference(carritoListNewCarritoToAttach.getClass(), carritoListNewCarritoToAttach.getIdCliente());
                attachedCarritoListNew.add(carritoListNewCarritoToAttach);
            }
            carritoListNew = attachedCarritoListNew;
            producto.setCarritoList(carritoListNew);
            List<Calificacion> attachedCalificacionListNew = new ArrayList<Calificacion>();
            for (Calificacion calificacionListNewCalificacionToAttach : calificacionListNew) {
                calificacionListNewCalificacionToAttach = em.getReference(calificacionListNewCalificacionToAttach.getClass(), calificacionListNewCalificacionToAttach.getIdCliente());
                attachedCalificacionListNew.add(calificacionListNewCalificacionToAttach);
            }
            calificacionListNew = attachedCalificacionListNew;
            producto.setCalificacionList(calificacionListNew);
            producto = em.merge(producto);
            if (idColorOld != null && !idColorOld.equals(idColorNew)) {
                idColorOld.getProductoList().remove(producto);
                idColorOld = em.merge(idColorOld);
            }
            if (idColorNew != null && !idColorNew.equals(idColorOld)) {
                idColorNew.getProductoList().add(producto);
                idColorNew = em.merge(idColorNew);
            }
            if (idPublicacionOld != null && !idPublicacionOld.equals(idPublicacionNew)) {
                idPublicacionOld.getProductoList().remove(producto);
                idPublicacionOld = em.merge(idPublicacionOld);
            }
            if (idPublicacionNew != null && !idPublicacionNew.equals(idPublicacionOld)) {
                idPublicacionNew.getProductoList().add(producto);
                idPublicacionNew = em.merge(idPublicacionNew);
            }
            if (idTallaOld != null && !idTallaOld.equals(idTallaNew)) {
                idTallaOld.getProductoList().remove(producto);
                idTallaOld = em.merge(idTallaOld);
            }
            if (idTallaNew != null && !idTallaNew.equals(idTallaOld)) {
                idTallaNew.getProductoList().add(producto);
                idTallaNew = em.merge(idTallaNew);
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Producto oldIdProductoOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getIdProducto();
                    detalleCompraListNewDetalleCompra.setIdProducto(producto);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldIdProductoOfDetalleCompraListNewDetalleCompra != null && !oldIdProductoOfDetalleCompraListNewDetalleCompra.equals(producto)) {
                        oldIdProductoOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldIdProductoOfDetalleCompraListNewDetalleCompra = em.merge(oldIdProductoOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            for (Carrito carritoListNewCarrito : carritoListNew) {
                if (!carritoListOld.contains(carritoListNewCarrito)) {
                    Producto oldIdProductoOfCarritoListNewCarrito = carritoListNewCarrito.getIdProducto();
                    carritoListNewCarrito.setIdProducto(producto);
                    carritoListNewCarrito = em.merge(carritoListNewCarrito);
                    if (oldIdProductoOfCarritoListNewCarrito != null && !oldIdProductoOfCarritoListNewCarrito.equals(producto)) {
                        oldIdProductoOfCarritoListNewCarrito.getCarritoList().remove(carritoListNewCarrito);
                        oldIdProductoOfCarritoListNewCarrito = em.merge(oldIdProductoOfCarritoListNewCarrito);
                    }
                }
            }
            for (Calificacion calificacionListNewCalificacion : calificacionListNew) {
                if (!calificacionListOld.contains(calificacionListNewCalificacion)) {
                    Producto oldIdProductoOfCalificacionListNewCalificacion = calificacionListNewCalificacion.getIdProducto();
                    calificacionListNewCalificacion.setIdProducto(producto);
                    calificacionListNewCalificacion = em.merge(calificacionListNewCalificacion);
                    if (oldIdProductoOfCalificacionListNewCalificacion != null && !oldIdProductoOfCalificacionListNewCalificacion.equals(producto)) {
                        oldIdProductoOfCalificacionListNewCalificacion.getCalificacionList().remove(calificacionListNewCalificacion);
                        oldIdProductoOfCalificacionListNewCalificacion = em.merge(oldIdProductoOfCalificacionListNewCalificacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleCompra> detalleCompraListOrphanCheck = producto.getDetalleCompraList();
            for (DetalleCompra detalleCompraListOrphanCheckDetalleCompra : detalleCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the DetalleCompra " + detalleCompraListOrphanCheckDetalleCompra + " in its detalleCompraList field has a non-nullable idProducto field.");
            }
            List<Carrito> carritoListOrphanCheck = producto.getCarritoList();
            for (Carrito carritoListOrphanCheckCarrito : carritoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Carrito " + carritoListOrphanCheckCarrito + " in its carritoList field has a non-nullable idProducto field.");
            }
            List<Calificacion> calificacionListOrphanCheck = producto.getCalificacionList();
            for (Calificacion calificacionListOrphanCheckCalificacion : calificacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Calificacion " + calificacionListOrphanCheckCalificacion + " in its calificacionList field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Color idColor = producto.getIdColor();
            if (idColor != null) {
                idColor.getProductoList().remove(producto);
                idColor = em.merge(idColor);
            }
            Publicacion idPublicacion = producto.getIdPublicacion();
            if (idPublicacion != null) {
                idPublicacion.getProductoList().remove(producto);
                idPublicacion = em.merge(idPublicacion);
            }
            Talla idTalla = producto.getIdTalla();
            if (idTalla != null) {
                idTalla.getProductoList().remove(producto);
                idTalla = em.merge(idTalla);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
