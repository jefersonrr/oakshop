/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Talla;
import Persistencia.TallaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class TallaDAO {
     TallaJpaController cit;

    public TallaDAO() {
        Conexion con = Conexion.getConexion();
        cit = new TallaJpaController(con.getBd());
    }
    
    public void create(Talla talla){
        try {
            cit.create(talla);
        } catch (Exception ex) {
            Logger.getLogger(TallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Talla> read(){  //devuelve todas las Tallas
        return cit.findTallaEntities();
    }
    
    public Talla readTalla(int id){
        return cit.findTalla(id);
    }
    
    public void update(Talla d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(TallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TallaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
