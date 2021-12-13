/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Carrito;
import DTO.CarritoPK;
import Persistencia.CarritoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CarritoDAO {
     CarritoJpaController cit;

    public CarritoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CarritoJpaController(con.getBd());
    }
    
    public void create(Carrito carrito){
        try {
            cit.create(carrito);
        } catch (Exception ex) {
            Logger.getLogger(CarritoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Carrito> read(){  //devuelve todas las Carritos
        return cit.findCarritoEntities();
    }
    
    public Carrito readCarrito(CarritoPK id){
        return cit.findCarrito(id);
    }
    
    public void update(Carrito d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CarritoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(CarritoPK id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CarritoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
