/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Domicilio;
import Persistencia.DomicilioJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class DomicilioDAO {
     DomicilioJpaController cit;

    public DomicilioDAO() {
        Conexion con = Conexion.getConexion();
        cit = new DomicilioJpaController(con.getBd());
    }
    
    public void create(Domicilio domicilio){
        try {
            cit.create(domicilio);
        } catch (Exception ex) {
            Logger.getLogger(DomicilioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Domicilio> read(){  //devuelve todas las Domicilios
        return cit.findDomicilioEntities();
    }
    
    public Domicilio readDomicilio(int id){
        return cit.findDomicilio(id);
    }
    
    public void update(Domicilio d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(DomicilioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DomicilioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
