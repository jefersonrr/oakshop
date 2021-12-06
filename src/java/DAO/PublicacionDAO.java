/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Producto;
import DTO.Publicacion;
import Persistencia.PublicacionJpaController;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class PublicacionDAO {

    PublicacionJpaController cit;

    public PublicacionDAO() {
        Conexion con = Conexion.getConexion();
        cit = new PublicacionJpaController(con.getBd());
    }

    public void create(Publicacion publicacion) {
        try {
            cit.create(publicacion);
        } catch (Exception ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Publicacion> read() {  //devuelve todas las Publicacions
        return cit.findPublicacionEntities();
    }

    public Publicacion readPublicacion(int id) {
        return cit.findPublicacion(id);
    }

    public void update(Publicacion d) {
        try {
            cit.edit(d);
        } catch (Exception ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(int id) throws IllegalOrphanException, NonexistentEntityException {

        try {
            cit.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PublicacionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Publicacion> readTipo(int id, int categoria) {

        List<Publicacion> todas = read();
        List<Publicacion> rta = new ArrayList<Publicacion>();

        for (Publicacion p : todas) {

            if (p.getIdTipo().getId().equals(id) && p.getIdCategoria().getId().equals(categoria)) {
                rta.add(p);

            }

        }

        return rta;
    }

    public List<Publicacion> readColor(List<Publicacion> todas, String[] color) {

        List<Publicacion> rta = new ArrayList<Publicacion>();
        boolean e = false;
        for (Publicacion p : todas) {

            for (String s : color) {

                for (Producto pro : p.getProductoList()) {

                    if (pro.getIdColor().getNombre().equals(s)) {
                        rta.add(p);
                        e = true;
                        break;
                    }

                    if (e) {
                        e = false;
                        break;
                    }
                }

            }

        }
        return rta;
    }

    public List<Publicacion> readTalla(List<Publicacion> todas, String talla) {

        List<Publicacion> rta = new ArrayList<Publicacion>();
        for (Publicacion p : todas) {

            for (Producto pro : p.getProductoList()) {

                if (pro.getIdTalla().getValor().equals(talla)) {
                    rta.add(p);
                    break;
                }

            }

        }
        return rta;
    }

    public List<Publicacion> readPrecio(List<Publicacion> todas, String precio) {

        int inicio = -1;
        int limite = -1;

        if (precio.equals("0-40")) {

            inicio = 0;
            limite = 40000;
        }

        if (precio.equals("40-50")) {

            inicio = 40000;
            limite = 50000;
        }

        if (precio.equals("50-60")) {

            inicio = 50000;
            limite = 60000;
        }

        if (precio.equals("60-70")) {

            inicio = 60000;
            limite = 70000;
        }

        if (precio.equals("70-+")) {

            inicio = 70000;

        }

        List<Publicacion> rta = new ArrayList<Publicacion>();
        for (Publicacion p : todas) {

            for (Producto pro : p.getProductoList()) {

                if (limite == -1 && pro.getCosto() >= inicio) {
                    rta.add(p);
                    break;
                } else if (pro.getCosto() >= inicio && pro.getCosto() <= limite) {

                    rta.add(p);

                }

            }

        }
        return rta;
    }
}
