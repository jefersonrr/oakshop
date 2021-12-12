/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CategoriaDAO;
import DAO.ColorDAO;
import DAO.GaleriaimgDAO;
import DAO.ProductoDAO;
import DAO.PublicacionDAO;
import DAO.TallaDAO;
import DAO.TipoDAO;
import DAO.TipoTallaDAO;
import DTO.Categoria;
import DTO.Color;
import DTO.Galeriaimg;
import DTO.Producto;
import DTO.Publicacion;
import DTO.Tipo;
import DTO.TipoTalla;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public String getFecha(Date fecha) {
        SimpleDateFormat formateador = new SimpleDateFormat(
                "dd '/' MM '/' yyyy", new Locale("es_ES"));
        SimpleDateFormat formateador2 = new SimpleDateFormat(
                "hh:mm", new Locale("es_ES"));
        String fechad = formateador.format(fecha);
        String horas = formateador2.format(fecha);
        return "Dia: " + fechad.replace(" ", "") + "<br>Hora: " + horas;
    }

    public String getTipos() {
        TipoDAO t = new TipoDAO();
        List<Tipo> tipos = t.read();
        String rta = "";
        for (Tipo ti : tipos) {
            rta += " <option value=\"" + ti.getId() + "\">" + ti.getNombre() + "</option>\n";
        }
        return rta;
    }

    public String getCategorias() {
        CategoriaDAO c = new CategoriaDAO();
        List<Categoria> categorias = c.read();
        String rta = "";
        for (Categoria ca : categorias) {
            rta += " <option value=\"" + ca.getId() + "\">" + ca.getNombre() + "</option>\n";
        }
        return rta;
    }

    public String tipo_talla(int tipo) {

        TipoTallaDAO t = new TipoTallaDAO();
        List<TipoTalla> tipoTalla = t.read();
        String rta = "";
        for (TipoTalla ti : tipoTalla) {

            if (ti.getIdTipo().getId() == tipo) {
                rta += " <option value=\"" + ti.getIdTalla().getId() + "\">" + ti.getIdTalla().getValor() + "</option>\n";
            }
        }
        return rta;
    }

    public String getColores() {

        ColorDAO c = new ColorDAO();
        List<Color> colores = c.read();
        String rta = "";
        for (Color co : colores) {

            rta += " <option value=\"" + co.getId() + "\">" + co.getNombre() + "</option>\n";

        }
        return rta;
    }

    public void crearPublicacion(String nombre, String marca, String categoria, String tipo,
            String descripcion, String[] referencias, String[] costos, String[] descuentos, String[] tallas, String[] imgs, String[] colores, String[] cantidades) {

        PublicacionDAO p = new PublicacionDAO();
        Date fecha = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        int id = hashPublicacion(fecha);
        Publicacion pu = new Publicacion(id, marca, nombre, fecha, descripcion);

        CategoriaDAO ca = new CategoriaDAO();
        TipoDAO ti = new TipoDAO();
        pu.setIdCategoria(ca.readCategoria(Integer.parseInt(categoria)));
        pu.setIdTipo(ti.readTipo(Integer.parseInt(tipo)));
        p.create(pu); //creo la publicacion

        ColorDAO c = new ColorDAO();
        TallaDAO t = new TallaDAO();
        ProductoDAO pro = new ProductoDAO();
        GaleriaimgDAO ga = new GaleriaimgDAO();
        Publicacion pinsertada = p.readPublicacion(id);

        for (int i = 0; i < referencias.length; i++) {

            Producto producto = new Producto(0, referencias[i], "",
                    Double.parseDouble(costos[i]), Integer.parseInt(descuentos[i]), Integer.parseInt(cantidades[i]));
            producto.setIdColor(c.readColor(Integer.parseInt(colores[i])));
            producto.setIdPublicacion(p.readPublicacion(id));
            producto.setIdTalla(t.readTalla(Integer.parseInt(tallas[i])));
            Galeriaimg g = new Galeriaimg(0, imgs[i]);
            g.setIdPublicacion(pinsertada);
            ga.create(g);

            pro.create(producto);
        }

            PublicacionDAO p = new PublicacionDAO();
            Date fecha = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
            int id = hashPublicacion(fecha);
            Publicacion pu = new Publicacion(id, marca, nombre, fecha, descripcion,null);
            
            CategoriaDAO ca = new CategoriaDAO();
            TipoDAO ti = new TipoDAO();
            pu.setIdCategoria(ca.readCategoria(Integer.parseInt(categoria)));
            pu.setIdTipo(ti.readTipo(Integer.parseInt(tipo)));
            p.create(pu); //creo la publicacion
            
            ColorDAO c = new ColorDAO();
            TallaDAO t = new TallaDAO();
            ProductoDAO pro = new ProductoDAO();
            GaleriaimgDAO ga = new GaleriaimgDAO();
            Publicacion pinsertada = p.readPublicacion(id);
            
            for (int i = 0; i < referencias.length; i++) {
                
                 Producto producto = new Producto(0, referencias[i], 
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]),null);
                 producto.setIdColor(c.readColor(Integer.parseInt(colores[i])));
                 producto.setIdPublicacion(p.readPublicacion(id));
                 producto.setIdTalla(t.readTalla(Integer.parseInt(tallas[i])));
                 Galeriaimg g = new Galeriaimg(0, imgs[i]);
                 g.setIdPublicacion(pinsertada);
                 ga.create(g);
                 
                 pro.create(producto);
            }
            
            
    }

    public int hashPublicacion(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHss");
        int h = Integer.parseInt(sdf.format(fecha));;
        return h;

    }

    public void actualizarPublicacion(int idP, String nombre, String marca, int categoria, int tipo, String descripcion, String[] referencias, String[] costos, String[] descuentos, String[] tallas, String[] imgs, String[] colores, String[] cantidades, String[] idProductos, String[] idImgs) {

            PublicacionDAO p = new PublicacionDAO();
            Publicacion pu = p.readPublicacion(idP);
            pu.setNombre(nombre);
            pu.setMarca(marca);
            pu.setDescripcion(descripcion);
            p.update(pu); //actualizo la publicacion
            
            ColorDAO c = new ColorDAO();
            TallaDAO t = new TallaDAO();
            ProductoDAO pro = new ProductoDAO();
            GaleriaimgDAO ga = new GaleriaimgDAO();
            Publicacion pinsertada = p.readPublicacion(idP);   
            for (int i = 0; i < referencias.length; i++) {
                 
                 if(i<idProductos.length){
                     Producto producto = pro.readProducto(Integer.parseInt(idProductos[i]));
                     producto.setIdColor(c.readColor(Integer.parseInt(colores[i])));
                     producto.setIdPublicacion(pinsertada);
                     producto.setIdTalla(t.readTalla(Integer.parseInt(tallas[i])));
                     producto.setReferencia(referencias[i]);
                     producto.setCosto(Double.parseDouble(costos[i]));
                     producto.setDescuento(Integer.parseInt(descuentos[i]));
                     producto.setCantidad(Integer.parseInt(cantidades[i]));
                     Galeriaimg g = ga.readGaleriaimg(Integer.parseInt(idImgs[i]));
                     g.setUrl(imgs[i]);
                     ga.update(g);
                     pro.update(producto);
                 }
                 else{
                    Producto producto = new Producto(0, referencias[i], 
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]),null);
                    producto.setIdColor(c.readColor(Integer.parseInt(colores[i])));
                    producto.setIdPublicacion(pinsertada);
                    producto.setIdTalla(t.readTalla(Integer.parseInt(tallas[i])));
                    Galeriaimg g = new Galeriaimg(0, imgs[i]);
                    g.setIdPublicacion(pinsertada);
                    ga.create(g);
                    pro.create(producto);
                 }
            }
        }
    }

    public void eliminarPublicacion(int idP) {
        try {
            PublicacionDAO p = new PublicacionDAO();
            GaleriaimgDAO g = new GaleriaimgDAO();
            ProductoDAO pro = new ProductoDAO();

            Publicacion pu = p.readPublicacion(idP);
            List<Producto> productos = pu.getProductoList();
            List<Galeriaimg> imagenes = pu.getGaleriaimgList();
            for (Producto pros : productos) {
                try {
                    pro.delete(pros.getId());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            for (Galeriaimg ga : imagenes) {
                try {
                    g.delete(ga.getId());
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            p.delete(pu.getId());
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public String publicacionesTipoCliente(int id, int categoria) {

        PublicacionDAO pudao = new PublicacionDAO();
        List<Publicacion> publicaciones = pudao.readTipo(id,categoria);
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
                    + "						<form action=\"mostrarDetalles.do\" method=\"POST\"><button class=\"flex-c-m size1 bg4 bo-rad-23 hov1 s-text1 trans-0-4\">\n"
                    + "                                                    Detalles\n"
                    + "						</button><input name='id_publicacion' type='hidden' value='"+p.getId()+"' /> </form>\n"
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

        if (rta.equals("")) {
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

    public String filtrarPublicaciones(String[] color, String talla, String precio, int id,int categoria) {

        PublicacionDAO pudao = new PublicacionDAO();
        List<Publicacion> publicaciones = pudao.readTipo(id,categoria);
        if (color != null) {

            publicaciones = pudao.readColor(publicaciones, color);

        }

        if (!talla.equals("")) {

            publicaciones = pudao.readTalla(publicaciones, talla);

        }

        if (!precio.equals("")) {
            publicaciones = pudao.readPrecio(publicaciones, precio);

        }

        return cardPublicaciones(publicaciones);

    }

    public String indexCategorias() {

        String rta = "";
        CategoriaDAO cadao = new CategoriaDAO();
        List<Categoria> ca = cadao.readActivo();
                int l =0;
        for (Categoria c : ca) {
            

            rta += seccionIndexCart(c,l);
            l++;
            List<Tipo> tipoBody = c.getTipoList();
            int i = tipoBody.size();

            if (i > 0) {
                rta += cardIncial(tipoBody,c);
                i += -4;
                if (tipoBody.size() != 4) {
                    rta += carruselIndex(tipoBody, c);
                }
        
            }
            rta+= " </div>\n" +
"                </div>\n" +
"            </section>";
        }
        
        return rta;
    }

    private String seccionIndexCart(Categoria c,int i) {

        return "   <section>\n"
                + "                <div class=\"container\">\n"
                + "                    <div class=\"row\">\n"
                + "                        <div class=\"col-6\">\n"
                + "                            <h3 class=\"mb-3\">" + c.getNombre() + "</h3>\n"
                + "                        </div>\n"
                + "                        <div class=\"col-6 text-right\">\n"
                + "                            <a class=\"btn btn-primary mb-3 mr-1\" href=\"#carouselExampleIndicators"+i+'"'+" role=\"button\" data-slide=\"prev\">\n"
                + "                                <i class=\"fa fa-arrow-left\"></i>\n"
                + "                            </a>\n"
                + "                            <a class=\"btn btn-primary mb-3 \" href=\"#carouselExampleIndicators"+i+'"'+"role=\"button\" data-slide=\"next\">\n"
                + "                                <i class=\"fa fa-arrow-right\"></i>\n"
                + "                            </a>\n"
                + "                        </div>\n"
                + "                        <div class=\"col-12\">\n"
                + "                            <div id=\"carouselExampleIndicators"+i+'"'+" class=\"carousel slide\" data-ride=\"carousel\">\n"
                + "\n"
                + "                                <div class=\"carousel-inner\">\n"
                + "\n"
                + "                                    <div class=\"carousel-item active\">\n"
                + "\n"
                + "                                        <div class=\"row row-cols-3 row-cols-md-4 g-4\">";
    }

    private String cardIncial(List<Tipo> tipoBody,Categoria ca) {

        String rta = "";
        for (int j = 0; j < 4; j++) {

            rta += "<div class=\"col \">\n"
                    + "                                                <div class=\"card\">\n"
                    + "                                                    <div class=\"card-body d-flex flex-column\">\n"
                    + "                                                        <img src=" + '"' + tipoBody.get(j).getUrlFoto() + '"' + "class=\"card-img-top\" alt=\"...\" width=\"50\" height=\"270\">\n"
                    + "                                                        <a  href=\"./PublicacionesCategoria.do?tipo="+tipoBody.get(j).getId()+"&cate="+ ca.getId()+'"'+ "class=\"btn  text-white mt-auto align-self-center\">"+tipoBody.get(j).getNombre()+"</a>                          \n"
                    + "                                                    </div>\n"
                    + "                                                </div>\n"
                    + "                                            </div>";

            if (j + 1 == tipoBody.size()) {
                rta += " </div>\n"
                        + "                                    </div>";
                break;
            }
        }
        rta += " </div>\n"
                + "                                    </div>";
        return rta;
    }

    private String carruselIndex(List<Tipo> tipoBody,Categoria ca) {

        String rta = "  <div class=\"carousel-item\">\n"
                + "\n"
                + "                                        <div class=\"row row-cols-3 row-cols-md-4 g-4\">";
        int i = 4;
        while (i < tipoBody.size()) {

            for (int k = 0; k < 4; k++) {
                rta += " <div class=\"col\">\n"
                        + "                                                <div class=\"card \">\n"
                        + "                                                    <div class=\"card-body d-flex flex-column\">\n"
                        + "                                                        <img src="+'"'+tipoBody.get(i).getUrlFoto()+'"'+" class=\"card-img-top\" alt=\"...\">\n"
                        + "                                                        <a  href=\"./PublicacionesCategoria.do?tipo="+tipoBody.get(i).getId()+"&cate="+ ca.getId()+'"'+ "class=\"btn  text-white mt-auto align-self-center\">"+tipoBody.get(i).getNombre()+"</a>                          \n"
                        + "                                                    </div>\n"
                        + "                                                </div>\n"
                        + "                                            </div>";
                
                if (i + 1 == tipoBody.size()) {
                    rta += " </div>\n"
                            + "                                    </div>";
                    i++;
                    break;
                }
                i++;
            }

        }

        rta += " </div>\n"
                + "                                    </div>\n"
                + "";
        return rta;
    }

    public void actualizarProductoPublicacion(String idProducto, String referencia, String costo, String descuento, String color, String talla, String cantidad) {

        ProductoDAO p = new ProductoDAO();
        Producto pro = p.readProducto(Integer.parseInt(idProducto));
        pro.setReferencia(referencia);
        pro.setCosto(Double.parseDouble(costo));
        pro.setDescuento(Integer.parseInt(descuento));
        ColorDAO c = new ColorDAO();
        TallaDAO t = new TallaDAO();
        
        pro.setIdColor(c.readColor(Integer.parseInt(color)));
        pro.setIdTalla(t.readTalla(Integer.parseInt(talla)));
        pro.setCantidad(Integer.parseInt(cantidad));
        
        p.update(pro);

    }

    public void agregarProductosPublicacion(String[] referencias, String[] costos, String[] descuentos, String[] tallas, String[] imgs, String[] colores, String[] cantidades, String pub) {

            PublicacionDAO p = new PublicacionDAO();
            Publicacion pu = p.readPublicacion(Integer.parseInt(pub));
            
            ColorDAO c = new ColorDAO();
            TallaDAO t = new TallaDAO();
            ProductoDAO pro = new ProductoDAO();
            GaleriaimgDAO ga = new GaleriaimgDAO();   
            for (int i = 0; i < referencias.length; i++) {
                
                    Producto producto = new Producto(0, referencias[i],
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]),null);
                    producto.setIdColor(c.readColor(Integer.parseInt(colores[i])));
                    producto.setIdPublicacion(pu);
                    producto.setIdTalla(t.readTalla(Integer.parseInt(tallas[i])));
                    Galeriaimg g = new Galeriaimg(0, imgs[i]);
                    g.setIdPublicacion(pu);
                    ga.create(g);
                    pro.create(producto);
                 
            }
    }

    public void desactivarProducto(int idp) {
        
        ProductoDAO p = new ProductoDAO();
        Producto po = p.readProducto(idp);
        po.setEstado("INACTIVO");
        p.update(po);
        
    }
    
   
}
