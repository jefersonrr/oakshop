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
import DTO.Categoria;
import DTO.Tipo;
import DTO.Producto;
import java.util.ArrayList;
import java.util.List;
import DTO.Galeriaimg;
import DTO.Publicacion;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USUARIO
 */
public class PublicacionJpaController implements Serializable {

    public PublicacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Publicacion publicacion) {
        if (publicacion.getProductoList() == null) {
            publicacion.setProductoList(new ArrayList<Producto>());
        }
        if (publicacion.getGaleriaimgList() == null) {
            publicacion.setGaleriaimgList(new ArrayList<Galeriaimg>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idCategoria = publicacion.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getId());
                publicacion.setIdCategoria(idCategoria);
            }
            Tipo idTipo = publicacion.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                publicacion.setIdTipo(idTipo);
            }
            List<Producto> attachedProductoList = new ArrayList<Producto>();
            for (Producto productoListProductoToAttach : publicacion.getProductoList()) {
                productoListProductoToAttach = em.getReference(productoListProductoToAttach.getClass(), productoListProductoToAttach.getId());
                attachedProductoList.add(productoListProductoToAttach);
            }
            publicacion.setProductoList(attachedProductoList);
            List<Galeriaimg> attachedGaleriaimgList = new ArrayList<Galeriaimg>();
            for (Galeriaimg galeriaimgListGaleriaimgToAttach : publicacion.getGaleriaimgList()) {
                galeriaimgListGaleriaimgToAttach = em.getReference(galeriaimgListGaleriaimgToAttach.getClass(), galeriaimgListGaleriaimgToAttach.getId());
                attachedGaleriaimgList.add(galeriaimgListGaleriaimgToAttach);
            }
            publicacion.setGaleriaimgList(attachedGaleriaimgList);
            em.persist(publicacion);
            if (idCategoria != null) {
                idCategoria.getPublicacionList().add(publicacion);
                idCategoria = em.merge(idCategoria);
            }
            if (idTipo != null) {
                idTipo.getPublicacionList().add(publicacion);
                idTipo = em.merge(idTipo);
            }
            for (Producto productoListProducto : publicacion.getProductoList()) {
                Publicacion oldIdPublicacionOfProductoListProducto = productoListProducto.getIdPublicacion();
                productoListProducto.setIdPublicacion(publicacion);
                productoListProducto = em.merge(productoListProducto);
                if (oldIdPublicacionOfProductoListProducto != null) {
                    oldIdPublicacionOfProductoListProducto.getProductoList().remove(productoListProducto);
                    oldIdPublicacionOfProductoListProducto = em.merge(oldIdPublicacionOfProductoListProducto);
                }
            }
            for (Galeriaimg galeriaimgListGaleriaimg : publicacion.getGaleriaimgList()) {
                Publicacion oldIdPublicacionOfGaleriaimgListGaleriaimg = galeriaimgListGaleriaimg.getIdPublicacion();
                galeriaimgListGaleriaimg.setIdPublicacion(publicacion);
                galeriaimgListGaleriaimg = em.merge(galeriaimgListGaleriaimg);
                if (oldIdPublicacionOfGaleriaimgListGaleriaimg != null) {
                    oldIdPublicacionOfGaleriaimgListGaleriaimg.getGaleriaimgList().remove(galeriaimgListGaleriaimg);
                    oldIdPublicacionOfGaleriaimgListGaleriaimg = em.merge(oldIdPublicacionOfGaleriaimgListGaleriaimg);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacion publicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion persistentPublicacion = em.find(Publicacion.class, publicacion.getId());
            Categoria idCategoriaOld = persistentPublicacion.getIdCategoria();
            Categoria idCategoriaNew = publicacion.getIdCategoria();
            Tipo idTipoOld = persistentPublicacion.getIdTipo();
            Tipo idTipoNew = publicacion.getIdTipo();
            List<Producto> productoListOld = persistentPublicacion.getProductoList();
            List<Producto> productoListNew = publicacion.getProductoList();
            List<Galeriaimg> galeriaimgListOld = persistentPublicacion.getGaleriaimgList();
            List<Galeriaimg> galeriaimgListNew = publicacion.getGaleriaimgList();
            List<String> illegalOrphanMessages = null;
            for (Producto productoListOldProducto : productoListOld) {
                if (!productoListNew.contains(productoListOldProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Producto " + productoListOldProducto + " since its idPublicacion field is not nullable.");
                }
            }
            for (Galeriaimg galeriaimgListOldGaleriaimg : galeriaimgListOld) {
                if (!galeriaimgListNew.contains(galeriaimgListOldGaleriaimg)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Galeriaimg " + galeriaimgListOldGaleriaimg + " since its idPublicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getId());
                publicacion.setIdCategoria(idCategoriaNew);
            }
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                publicacion.setIdTipo(idTipoNew);
            }
            List<Producto> attachedProductoListNew = new ArrayList<Producto>();
            for (Producto productoListNewProductoToAttach : productoListNew) {
                productoListNewProductoToAttach = em.getReference(productoListNewProductoToAttach.getClass(), productoListNewProductoToAttach.getId());
                attachedProductoListNew.add(productoListNewProductoToAttach);
            }
            productoListNew = attachedProductoListNew;
            publicacion.setProductoList(productoListNew);
            List<Galeriaimg> attachedGaleriaimgListNew = new ArrayList<Galeriaimg>();
            for (Galeriaimg galeriaimgListNewGaleriaimgToAttach : galeriaimgListNew) {
                galeriaimgListNewGaleriaimgToAttach = em.getReference(galeriaimgListNewGaleriaimgToAttach.getClass(), galeriaimgListNewGaleriaimgToAttach.getId());
                attachedGaleriaimgListNew.add(galeriaimgListNewGaleriaimgToAttach);
            }
            galeriaimgListNew = attachedGaleriaimgListNew;
            publicacion.setGaleriaimgList(galeriaimgListNew);
            publicacion = em.merge(publicacion);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getPublicacionList().remove(publicacion);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getPublicacionList().add(publicacion);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getPublicacionList().remove(publicacion);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getPublicacionList().add(publicacion);
                idTipoNew = em.merge(idTipoNew);
            }
            for (Producto productoListNewProducto : productoListNew) {
                if (!productoListOld.contains(productoListNewProducto)) {
                    Publicacion oldIdPublicacionOfProductoListNewProducto = productoListNewProducto.getIdPublicacion();
                    productoListNewProducto.setIdPublicacion(publicacion);
                    productoListNewProducto = em.merge(productoListNewProducto);
                    if (oldIdPublicacionOfProductoListNewProducto != null && !oldIdPublicacionOfProductoListNewProducto.equals(publicacion)) {
                        oldIdPublicacionOfProductoListNewProducto.getProductoList().remove(productoListNewProducto);
                        oldIdPublicacionOfProductoListNewProducto = em.merge(oldIdPublicacionOfProductoListNewProducto);
                    }
                }
            }
            for (Galeriaimg galeriaimgListNewGaleriaimg : galeriaimgListNew) {
                if (!galeriaimgListOld.contains(galeriaimgListNewGaleriaimg)) {
                    Publicacion oldIdPublicacionOfGaleriaimgListNewGaleriaimg = galeriaimgListNewGaleriaimg.getIdPublicacion();
                    galeriaimgListNewGaleriaimg.setIdPublicacion(publicacion);
                    galeriaimgListNewGaleriaimg = em.merge(galeriaimgListNewGaleriaimg);
                    if (oldIdPublicacionOfGaleriaimgListNewGaleriaimg != null && !oldIdPublicacionOfGaleriaimgListNewGaleriaimg.equals(publicacion)) {
                        oldIdPublicacionOfGaleriaimgListNewGaleriaimg.getGaleriaimgList().remove(galeriaimgListNewGaleriaimg);
                        oldIdPublicacionOfGaleriaimgListNewGaleriaimg = em.merge(oldIdPublicacionOfGaleriaimgListNewGaleriaimg);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = publicacion.getId();
                if (findPublicacion(id) == null) {
                    throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.");
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
            Publicacion publicacion;
            try {
                publicacion = em.getReference(Publicacion.class, id);
                publicacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Producto> productoListOrphanCheck = publicacion.getProductoList();
            for (Producto productoListOrphanCheckProducto : productoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicacion (" + publicacion + ") cannot be destroyed since the Producto " + productoListOrphanCheckProducto + " in its productoList field has a non-nullable idPublicacion field.");
            }
            List<Galeriaimg> galeriaimgListOrphanCheck = publicacion.getGaleriaimgList();
            for (Galeriaimg galeriaimgListOrphanCheckGaleriaimg : galeriaimgListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Publicacion (" + publicacion + ") cannot be destroyed since the Galeriaimg " + galeriaimgListOrphanCheckGaleriaimg + " in its galeriaimgList field has a non-nullable idPublicacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria idCategoria = publicacion.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getPublicacionList().remove(publicacion);
                idCategoria = em.merge(idCategoria);
            }
            Tipo idTipo = publicacion.getIdTipo();
            if (idTipo != null) {
                idTipo.getPublicacionList().remove(publicacion);
                idTipo = em.merge(idTipo);
            }
            em.remove(publicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicacion> findPublicacionEntities() {
        return findPublicacionEntities(true, -1, -1);
    }

    public List<Publicacion> findPublicacionEntities(int maxResults, int firstResult) {
        return findPublicacionEntities(false, maxResults, firstResult);
    }

    private List<Publicacion> findPublicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicacion.class));
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

    public Publicacion findPublicacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicacion> rt = cq.from(Publicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
