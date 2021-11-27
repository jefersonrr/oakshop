/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.TipoTalla;
import Persistencia.TipoTallaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class TipoTallaDAO {
     TipoTallaJpaController cit;

    public TipoTallaDAO() {
        Conexion con = Conexion.getConexion();
        cit = new TipoTallaJpaController(con.getBd());
    }
    
    public void create(TipoTalla tipotalla){
        try {
            cit.create(tipotalla);
        } catch (Exception ex) {
            Logger.getLogger(TipoTallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<TipoTalla> read(){  //devuelve todas las TipoTallas
        return cit.findTipoTallaEntities();
    }
    
    public TipoTalla readTipoTalla(int id){
        return cit.findTipoTalla(id);
    }
    
    public void update(TipoTalla d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(TipoTallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TipoTallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
