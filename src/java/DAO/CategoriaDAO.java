/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Categoria;
import Persistencia.CategoriaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class CategoriaDAO {
     CategoriaJpaController cit;

    public CategoriaDAO() {
        Conexion con = Conexion.getConexion();
        cit = new CategoriaJpaController(con.getBd());
    }
    
    public void create(Categoria categoria){
        try {
            cit.create(categoria);
        } catch (Exception ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Categoria> read(){  //devuelve todas las Categorias
        return cit.findCategoriaEntities();
    }
    
    public Categoria readCategoria(int id){
        return cit.findCategoria(id);
    }
    
    public void update(Categoria d){
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
