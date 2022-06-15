/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.CategoriaTipo;
import Persistencia.CategoriaTipoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jefer
 */
public class CategoriaTipoDAO {
    
    
    CategoriaTipoJpaController cit;

    public CategoriaTipoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CategoriaTipoJpaController(con.getBd());
    }
    
    public void create(CategoriaTipo categoria){
        try {
            cit.create(categoria);
        } catch (Exception ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CategoriaTipo> read(){  //devuelve todas las Categorias
        return cit.findCategoriaTipoEntities();
    }
    
    public CategoriaTipo readCategoriaTipo(int id){
        return cit.findCategoriaTipo(id);
    }
    
    public void update(CategoriaTipo d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   
    
}
