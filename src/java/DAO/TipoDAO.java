/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Tipo;
import Persistencia.TipoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class TipoDAO {
     TipoJpaController cit;

    public TipoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new TipoJpaController(con.getBd());
    }
    
    public void create(Tipo tipo){
        try {
            cit.create(tipo);
        } catch (Exception ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Tipo> read(){  //devuelve todas las Tipos
        return cit.findTipoEntities();
    }
    
    public Tipo readTipo(int id){
        return cit.findTipo(id);
    }
    
    public void update(Tipo d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(TipoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
