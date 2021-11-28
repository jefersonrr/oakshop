/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.PublicacionDAO;
import DTO.Publicacion;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class askshop {
    
    
    
    public String mostrarRopaIndex() {

        PublicacionDAO p = new PublicacionDAO();
        List<Publicacion> servicios = p.read();
        //AQUI SE CARGAN PUBLICACIONES DEL INDEX
//        int cantidad = 4;
//        if (servicios.size() < 4) {
//            cantidad = servicios.size();
//        }
//        String cardServicios = " ";
//        for (int j = 0; j < cantidad; j++) {
//
//            cardServicios += "<div class=\"col-md-6  mb-5 colum\">\n"
//                    + "\n"
//                    + "                    <a href=" + '"' + servicios.get(j).getImgUrl() + '"' + " data-lightbox=\"galeriaS\" data-title=\"Nombre servicio\"> <img src=" + '"' + servicios.get(j).getImgUrl() + '"' + " alt=\"\"></a>\n"
//                    + "                    <div class=\"titulo\">" + servicios.get(j).getNombre() + " </div>\n"
//                    + "\n"
//                    + "                </div>";
//
//        }

        return "<h1>Carga index</h1>";
    }
}
