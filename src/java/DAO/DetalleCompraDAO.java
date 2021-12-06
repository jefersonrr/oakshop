/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.DetalleCompra;
import Persistencia.DetalleCompraJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class DetalleCompraDAO {
     DetalleCompraJpaController cit;

    public DetalleCompraDAO() {
        Conexion con = Conexion.getConexion();
        cit = new DetalleCompraJpaController(con.getBd());
    }
    
    public void create(DetalleCompra detalle){
        try {
            cit.create(detalle);
        } catch (Exception ex) {
            Logger.getLogger(DetalleCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<DetalleCompra> read(){  //devuelve todas las DetalleCompras
        return cit.findDetalleCompraEntities();
    }
    
    public DetalleCompra readDetalleCompra(int id){
        return cit.findDetalleCompra(id);
    }
    
    public void update(DetalleCompra d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(DetalleCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DetalleCompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
