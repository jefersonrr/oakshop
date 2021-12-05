/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Galeriaimg;
import Persistencia.GaleriaimgJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class GaleriaimgDAO {
     GaleriaimgJpaController cit;

    public GaleriaimgDAO() {
        Conexion con = Conexion.getConexion();
        cit = new GaleriaimgJpaController(con.getBd());
    }
    
    public void create(Galeriaimg galeria){
        try {
            cit.create(galeria);
        } catch (Exception ex) {
            Logger.getLogger(GaleriaimgDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Galeriaimg> read(){  //devuelve todas las Galeriaimgs
        return cit.findGaleriaimgEntities();
    }
    
    public Galeriaimg readGaleriaimg(int id){
        return cit.findGaleriaimg(id);
    }
    
    public void update(Galeriaimg d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(GaleriaimgDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GaleriaimgDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public Galeriaimg fisrtImg(int id){
    
        List<Galeriaimg> galeria = read();
        Galeriaimg gale;
        for(Galeriaimg g : galeria){
            
            if(g.getIdPublicacion().getId().equals(id)){
                return g;
            
            }
           
        }
         return null;
    
    }
}
