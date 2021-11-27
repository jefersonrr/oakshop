/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Envio;
import Persistencia.EnvioJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class EnvioDAO {
     EnvioJpaController cit;

    public EnvioDAO() {
        Conexion con = Conexion.getConexion();
        cit = new EnvioJpaController(con.getBd());
    }
    
    public void create(Envio envio){
        try {
            cit.create(envio);
        } catch (Exception ex) {
            Logger.getLogger(EnvioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Envio> read(){  //devuelve todas las Envios
        return cit.findEnvioEntities();
    }
    
    public Envio readEnvio(int id){
        return cit.findEnvio(id);
    }
    
    public void update(Envio d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(EnvioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(EnvioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
