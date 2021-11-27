/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Rol;
import Persistencia.RolJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class RolDAO {
     RolJpaController cit;

    public RolDAO() {
        Conexion con = Conexion.getConexion();
        cit = new RolJpaController(con.getBd());
    }
    
    public void create(Rol rol){
        try {
            cit.create(rol);
        } catch (Exception ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Rol> read(){  //devuelve todas las Rols
        return cit.findRolEntities();
    }
    
    public Rol readRol(int id){
        return cit.findRol(id);
    }
    
    public void update(Rol d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RolDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
