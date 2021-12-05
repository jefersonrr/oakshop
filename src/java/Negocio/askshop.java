/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CategoriaDAO;
import DAO.GaleriaimgDAO;
import DAO.ProductoDAO;
import DAO.PublicacionDAO;
import DAO.TipoDAO;
import DAO.TipoTallaDAO;
import DTO.Categoria;
import DTO.Galeriaimg;
import DTO.Producto;
import DTO.Publicacion;
import DTO.Tipo;
import DTO.TipoTalla;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                    + "                                        <td>" + c.getId() + "</td>\n"
                    + "                                        <td>" + c.getNombre() + "</td>\n"
                    + "                                        <td>" + c.getEstado() + "</td>\n"
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
                    + "                                        <td>" + t.getId() + "</td>\n"
                    + "                                        <td>" + t.getNombre() + "</td>\n"
                    + "                                        <td>" + t.getEstado() + "</td>\n"
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

    public String getFecha(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat(
                "dd '/' MM '/' yyyy", new Locale("es_ES"));
        SimpleDateFormat formateador2 = new SimpleDateFormat(
                "hh:mm", new Locale("es_ES"));
        String fechad = formateador.format(fecha);
        String horas = formateador2.format(fecha);
        return "Dia: " + fechad.replace(" ", "") + "<br>Hora: " + horas;
    }

    public String publicacionesTipoCliente(int id) {

        PublicacionDAO pudao = new PublicacionDAO();
        List<Publicacion> publicaciones = pudao.readTipo(id);
        return cardPublicaciones(publicaciones);
    }

    public String cardPublicaciones(List<Publicacion> publicaciones) {
        ProductoDAO pdao = new ProductoDAO();
        Producto pro;
        GaleriaimgDAO imgdao = new GaleriaimgDAO();
        Galeriaimg img;
        String rta = "";
        for (Publicacion p : publicaciones) {
            pro = pdao.firstReadPublicacion(p.getId());
            img = imgdao.fisrtImg(p.getId());

            rta += "        <div class=\"col-sm-12 col-md-6 col-lg-4 p-b-50\">\n"
                    + "				<!-- Block2 -->\n"
                    + "				<div class=\"block2\">\n"
                    + "                                    <div class=\"block2-img wrap-pic-w of-hidden pos-relative\"  >\n"
                    + "                                        <div class=\"imagen-producto\">"
                    + "                                        <img class=\"img-fluid\" src=" + '"' + img.getUrl() + '"' + "alt=\"IMG-PRODUCT 2\" >\n"
                    + "                                         </div>\n"
                    + "					<div class=\"block2-overlay trans-0-4\">\n"
                    + "\n"
                    + "                                            <div class=\"block2-btn-addcart w-size1 trans-0-4\">\n"
                    + "						<!-- Button -->\n"
                    + "						<button class=\"flex-c-m size1 bg4 bo-rad-23 hov1 s-text1 trans-0-4\">\n"
                    + "                                                    Detalles\n"
                    + "						</button>\n"
                    + "                                            </div>\n"
                    + "					</div>\n"
                    + "                                    </div>\n"
                    + "\n"
                    + "                                    <div class=\"block2-txt p-t-20\">\n"
                    + "					<a href=\"#\" class=\"block2-name dis-block s-text3 p-b-5\">\n"
                    + "                                            " + p.getNombre() + "\n"
                    + "					</a>\n"
                    + "\n"
                    + "                                        <span class=\"block2-price m-text6 p-r-5\">\n"
                    + "                                           " + pro.getCosto() + "\n"
                    + "                                        </span>\n"
                    + "                                    </div>\n"
                    + "				</div>\n"
                    + "                            </div>";

        }

        if(rta.equals("")){
        return "<h3>No Se Encontraron Resultados</h3>";
        }
        return rta;

    }

    public String tallasTipo(int id) {

        TipoDAO tado = new TipoDAO();
        Tipo ta = tado.readTipo(id);
        List<TipoTalla> ttalla = ta.getTipoTallaList();
        String rta = "";
        for (TipoTalla t : ttalla) {

            rta += "<option value=" + '"' + t.getIdTalla().getValor() + '"' + ">" + t.getIdTalla().getValor() + "</option>";

        }

        return rta;
    }

    public String filtrarPublicaciones(String [] color, String talla, String precio, int id) {
        
       PublicacionDAO pudao = new PublicacionDAO();
       List<Publicacion> publicaciones = pudao.readTipo(id);
        if(color!=null){
        
        publicaciones= pudao.readColor(publicaciones, color);
        
        }
        
        if(!talla.equals("")){
            
            publicaciones = pudao.readTalla(publicaciones,talla);
        
        }
        
        if(!precio.equals("")){
        publicaciones = pudao.readPrecio(publicaciones,precio);
        
        }
        
        return cardPublicaciones(publicaciones);
        

    }
    
   
}
