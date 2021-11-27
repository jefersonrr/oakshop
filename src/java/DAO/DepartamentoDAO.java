/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Departamento;
import Persistencia.DepartamentoJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class DepartamentoDAO {
     DepartamentoJpaController cit;

    public DepartamentoDAO() {
        Conexion con = Conexion.getConexion();
        cit = new DepartamentoJpaController(con.getBd());
    }
    
    public void create(Departamento departamento){
        try {
            cit.create(departamento);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Departamento> read(){  //devuelve todas las Departamentos
        return cit.findDepartamentoEntities();
    }
    
    public Departamento readDepartamento(int id){
        return cit.findDepartamento(id);
    }
    
    public void update(Departamento d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
