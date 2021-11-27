/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Color;
import Persistencia.ColorJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class ColorDAO {
     ColorJpaController cit;

    public ColorDAO() {
        Conexion con = Conexion.getConexion();
        cit = new ColorJpaController(con.getBd());
    }
    
    public void create(Color color){
        try {
            cit.create(color);
        } catch (Exception ex) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Color> read(){  //devuelve todas las Colors
        return cit.findColorEntities();
    }
    
    public Color readColor(int id){
        return cit.findColor(id);
    }
    
    public void update(Color d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ColorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
