/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CategoriaDAO;
import DAO.PublicacionDAO;
import DAO.TipoDAO;
import DTO.Categoria;
import DTO.Publicacion;
import DTO.Tipo;
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

    public String mostrarCategoriasAdmin() {

        CategoriaDAO cdao = new CategoriaDAO();
        List<Categoria> categorias = cdao.read();
        String rta = "";
        for (Categoria c : categorias) {

            rta += "    <tr>\n"
                    + "                                        <td>"+c.getId()+"</td>\n"
                    + "                                        <td>"+c.getNombre()+"</td>\n"
                    + "                                        <td>"+c.getEstado()+"</td>\n"
                    + "                                        <!-- Acciones: editar y cancelar. -->\n"
                    + "                                        <td>\n"
                    + "                                            <div class=\"icons-acciones\">\n"
                    + "                                                <div>\n"
                    + "                                                    <button type=\"button\" style=\"background: transparent\" class=\"fas fa-edit\" data-bs-toggle=\"modal\" data-bs-target=\"#modal2\" data-bs-whatever = \"\" ></button>\n"
                    + "                                                </div>\n"
                    + "                                            </div>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";

        }
        return rta;
    }

    public String mostrarTiposAdmin() {
        TipoDAO tdao = new TipoDAO();
        List<Tipo> tipos = tdao.read();
        String rta = "";
        for (Tipo t : tipos) {

            rta += "    <tr>\n"
                    + "                                        <td>"+t.getId()+"</td>\n"
                    + "                                        <td>"+t.getNombre()+"</td>\n"
                    + "                                        <td>"+t.getEstado()+"</td>\n"
                    + "                                        <!-- Acciones: editar y cancelar. -->\n"
                    + "                                        <td>\n"
                    + "                                            <div class=\"icons-acciones\">\n"
                    + "                                                <div>\n"
                    + "                                                    <button type=\"button\" style=\"background: transparent\" class=\"fas fa-edit\" data-bs-toggle=\"modal\" data-bs-target=\"#modal3\" data-bs-whatever = \"\" ></button>\n"
                    + "                                                </div>\n"
                    + "                                            </div>\n"
                    + "                                        </td>\n"
                    + "                                    </tr>";

        }
        return rta;
    }
  

}
