/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Producto;
import Persistencia.ProductoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ProductoDAO {

    ProductoJpaController cit;

    public ProductoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new ProductoJpaController(con.getBd());
    }

    public void create(Producto producto) {
        try {
            cit.create(producto);
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Producto> read() {  //devuelve todas las Productos
        return cit.findProductoEntities();
    }

    public Producto readProducto(int id) {
        return cit.findProducto(id);
    }

    public void update(Producto d) {
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException {

        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Producto firstReadPublicacion(int id) {

        List<Producto> pro = read();

        for (Producto p : pro) {

            if (p.getIdPublicacion().getId().equals(id)) {
                return p;
            }

        }
        return null;
    }
}
