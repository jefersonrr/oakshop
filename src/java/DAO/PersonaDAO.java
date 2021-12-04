/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Persona;
import DTO.Rol;
import Persistencia.PersonaJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class PersonaDAO {
     PersonaJpaController cit;

    public PersonaDAO() {
        Conexion con = Conexion.getConexion();
        cit = new PersonaJpaController(con.getBd());
    }
    
    public void create(Persona persona){
        try {
            cit.create(persona);
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Persona> read(){  //devuelve todas las Personas
        return cit.findPersonaEntities();
    }
    
    public Persona readPersona(String cedula){
        return cit.findPersona(cedula);
    }
    
    public void update(Persona d){
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(String cedula) throws IllegalOrphanException, NonexistentEntityException{
        
        try {
            cit.destroy(cedula);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PersonaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public boolean existePersona(String cedula){
        return this.readPersona(cedula)!=null;
    }
    
    public boolean existeCorreo(String correo){
        List<Persona> personas = this.read();
        boolean existe = false;
        for(Persona p: personas){
            if(p.getCorreo().equals(correo)){
                existe = true;
                break;
            }
        }
        return existe;
    }
      public void crearPersona(String nombre, String apellido, String contrasenia, String cedula, String correo, String telef, String direccion, Rol rol) {
        
       Persona p = new Persona(cedula, nombre, apellido,  telef, direccion, correo, contrasenia);
       p.setIdRol(rol);
       p.setUrlFoto("https://i.postimg.cc/9Qys23M0/585e4bf3cb11b227491c339a.png");
       this.create(p);
    }
      
      public boolean usuarioValido(String cedula, String clave) {
        Persona user = readPersona(cedula);
        return user.getContraseña().equals(clave);
    }
}
