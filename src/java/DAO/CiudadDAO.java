/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Ciudad;
import Persistencia.CiudadJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CiudadDAO {
     CiudadJpaController cit;

    public CiudadDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CiudadJpaController(con.getBd());
    }
    
    public void create(Ciudad ciudad){
        try {
            cit.create(ciudad);
        } catch (Exception ex) {
            Logger.getLogger(CiudadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Ciudad> read(){  //devuelve todas las Ciudads
        return cit.findCiudadEntities();
    }
    
    public Ciudad readCiudad(int id){
        return cit.findCiudad(id);
    }
    
    public void update(Ciudad d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CiudadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CiudadDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
