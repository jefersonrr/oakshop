/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Calificacion;
import DTO.CalificacionPK;
import Persistencia.CalificacionJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CalificacionDAO {
     CalificacionJpaController cit;

    public CalificacionDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CalificacionJpaController(con.getBd());
    }
    
    public void create(Calificacion calificacion){
        try {
            cit.create(calificacion);
        } catch (Exception ex) {
            Logger.getLogger(CalificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Calificacion> read(){  //devuelve todas las Calificacions
        return cit.findCalificacionEntities();
    }
    
    public Calificacion readCalificacion(CalificacionPK id){
        return cit.findCalificacion(id);
    }
    
    public void update(Calificacion d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CalificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(CalificacionPK id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CalificacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
