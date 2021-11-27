/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.MetodoPago;
import Persistencia.MetodoPagoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class MetodoPagoDAO {
     MetodoPagoJpaController cit;

    public MetodoPagoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new MetodoPagoJpaController(con.getBd());
    }
    
    public void create(MetodoPago metodo){
        try {
            cit.create(metodo);
        } catch (Exception ex) {
            Logger.getLogger(MetodoPagoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<MetodoPago> read(){  //devuelve todas las MetodoPagos
        return cit.findMetodoPagoEntities();
    }
    
    public MetodoPago readMetodoPago(int id){
        return cit.findMetodoPago(id);
    }
    
    public void update(MetodoPago d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(MetodoPagoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MetodoPagoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
