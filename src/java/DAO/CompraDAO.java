/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Compra;
import Persistencia.CompraJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CompraDAO {
     CompraJpaController cit;

    public CompraDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CompraJpaController(con.getBd());
    }
    
    public void create(Compra compra){
        try {
            cit.create(compra);
        } catch (Exception ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Compra> read(){  //devuelve todas las Compras
        return cit.findCompraEntities();
    }
    
    public Compra readCompra(int id){
        return cit.findCompra(id);
    }
    
    public void update(Compra d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CompraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
