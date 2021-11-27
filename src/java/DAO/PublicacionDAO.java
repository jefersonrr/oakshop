/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Publicacion;
import Persistencia.PublicacionJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class PublicacionDAO {
    PublicacionJpaController cit;

    public PublicacionDAO() {
        Conexion con = Conexion.getConexion();
        cit = new PublicacionJpaController(con.getBd());
    }
    
    public void create(Publicacion publicacion){
        try {
            cit.create(publicacion);
        } catch (Exception ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Publicacion> read(){  //devuelve todas las Publicacions
        return cit.findPublicacionEntities();
    }
    
    public Publicacion readPublicacion(int id){
        return cit.findPublicacion(id);
    }
    
    public void update(Publicacion d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
}
