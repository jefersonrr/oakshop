/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CarritoDAO;
import DAO.CategoriaDAO;
import DAO.ColorDAO;
import DAO.DepartamentoDAO;
import DAO.DomicilioDAO;
import DAO.GaleriaimgDAO;
import DAO.MetodoPagoDAO;
import DAO.PersonaDAO;
import DAO.ProductoDAO;
import DAO.PublicacionDAO;
import DAO.TallaDAO;
import DAO.TipoDAO;
import DAO.TipoTallaDAO;
import DTO.Carrito;
import DTO.CarritoPK;
import DTO.Categoria;
import DTO.Ciudad;
import DTO.Color;
import DTO.Departamento;
import DTO.Domicilio;
import DTO.Galeriaimg;
import DTO.MetodoPago;
import DTO.Persona;
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

   
    public String getFecha(Date fecha){
            SimpleDateFormat formateador = new SimpleDateFormat(
                 "dd '/' MM '/' yyyy", new Locale("es_ES"));
            SimpleDateFormat formateador2 = new SimpleDateFormat(
                 "hh:mm", new Locale("es_ES"));
            String fechad = formateador.format(fecha);
            String horas = formateador2.format(fecha);
        return "Dia: "+fechad.replace(" ", "")+"<br>Hora: "+horas;
    }
    
    public String getSoloFecha(Date fecha){
            SimpleDateFormat formateador = new SimpleDateFormat(
                 "MM'/'dd'/'yyyy", new Locale("es_ES"));
            String fechad = formateador.format(fecha);
        return fechad;
    }

    public String getTipos() {
            TipoDAO t = new TipoDAO();
            List<Tipo> tipos = t.read();
            String rta ="";
                for(Tipo ti : tipos){
                    rta+=" <option value=\""+ti.getId()+"\">"+ti.getNombre()+"</option>\n";
                }
            return rta;    }

    public String getCategorias() {
        CategoriaDAO c = new CategoriaDAO();
            List<Categoria> categorias = c.read();
            String rta ="";
                for(Categoria ca : categorias){
                    rta+=" <option value=\""+ca.getId()+"\">"+ca.getNombre()+"</option>\n";
                }
            return rta; 
    }

    public String tipo_talla(int tipo) {
    
        TipoTallaDAO t = new TipoTallaDAO();
            List<TipoTalla> tipoTalla = t.read();
            String rta ="";
                for(TipoTalla ti : tipoTalla){
                    
                    if(ti.getIdTipo().getId()==tipo){
                        rta+=" <option value=\""+ti.getIdTalla().getId()+"\">"+ti.getIdTalla().getValor()+"</option>\n";
                    }
                }
            return rta; 
    }

    public String getColores() {

            ColorDAO c = new ColorDAO();
            List<Color> colores = c.read();
            String rta ="";
                for(Color co : colores){
                    
                  
                    rta+=" <option value=\""+co.getId()+"\">"+co.getNombre()+"</option>\n";
               
                }
            return rta; 
    }

    public void crearPublicacion(String nombre, String marca, String categoria, String tipo, 
            String descripcion, String[] referencias, String[] costos, String[] descuentos, String[] tallas, String[] imgs,String[] colores, String[] cantidades) {

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
    
    public int hashPublicacion(Date fecha){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHss");
        int h = Integer.parseInt(sdf.format(fecha));;
        return h;
        
    }


    public void actualizarPublicacion(int idP, String nombre, String marca, int categoria, int tipo, String descripcion, String[] referencias, String[] costos, String[] descuentos, String[] tallas, String[] imgs, String[] colores, String[] cantidades,String[] idProductos,String[] idImgs) {

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
            }  for (Galeriaimg ga : imagenes) {
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
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]),"ACTIVO");
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

    public String anadirAcarrito(String idPerson, int tallaId, int colorId, int cantidad, int idP) {
        
        PublicacionDAO pu = new PublicacionDAO();
        Publicacion p = pu.readPublicacion(idP);
        List<Producto> productos = p.getProductoList();
        CarritoDAO c = new CarritoDAO();
        PersonaDAO personadao = new PersonaDAO();
        Persona per = personadao.readPersona(idPerson);
        List<Carrito> carP = per.getCarritoList();
        
        for (Producto producto : productos) {
            
            if(producto.getIdColor().getId()==colorId && producto.getIdTalla().getId()==tallaId){ 
                //bucar si l producto esta en el carro
                boolean existe = false;
                for (Carrito car : carP) {
                     if(car.getProducto().getId()==producto.getId()){
                        int cant = car.getCantidad();
                        car.setCantidad(cant+cantidad);
                        existe=true;
                        c.update(car);
                     }
                    }
                
                if(!existe){
                    Carrito carri = new Carrito(idPerson,producto.getId());
                    carri.setPersona(per);
                    carri.setProducto(producto);
                    carri.setCantidad(cantidad);
                    carri.setCarritoPK(new CarritoPK(idPerson, producto.getId()));
                    c.create(carri);
                }
                break;
            }
        }
        return generarCarro(per.getCedula());
    }
    
    public String generarCarro(String pers){
        PersonaDAO personadao = new PersonaDAO();
        Persona per = personadao.readPersona(pers);
        CarritoDAO ca = new CarritoDAO();
        List<Carrito> carritos = per.getCarritoList();
        double subtotal=0;
        double total = 0;
        String rta = " <div class=\"row m-10 mt-3\">\n" +
                    "            <div class=\"row my-2\">\n" +
                    "                <div class=\"col d-flex\">\n" +
                    "                    <div>\n" +
                    "                        <img src=\"img/carrito.png\" width=\"50\" height=\"50\"/>    \n" +
                    "                    </div>\n" +
                    "                    <div class=\"titulo-contenido bold mt-2 ms-5 d-flex\">\n" +
                    "                        Carrito de compras\n" +
                    "                    </div>\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            \n" +
                    "            <div class=\"contenedor-inicial mt-5\">\n" +
                    "                <div class=\"contenedor\">\n" +
                    "                    <div class=\"row\">\n" +
                    "                        \n" +
                    "<div class=\"table-responsive\">                       \n" +
                    "<table id=\"tabla\" class=\"table table-borderless table-hover align-middle\">\n" +
                    "  <thead class=\"thead\">\n" +
                    "    <tr>\n" +
                    "      <th scope=\"col\">ID</th>\n" +
                    "      <th scope=\"col\">Referencia</th>\n" +
                    "      <th scope=\"col\">Descripción</th>\n" +
                    "      <th scope=\"col\">Precio</th>\n" +
                    "      <th scope=\"col\">Descuento</th>\n" +
                    "      <th scope=\"col\">Cantidad</th>\n" +
                    "      <th scope=\"col\">Acciones</th>\n" +
                    "    </tr>\n" +
                    "  </thead>\n" +
                    "  <tbody>\n";
                for(Carrito carro: carritos){
                        Producto p = carro.getProducto();
                            rta+="    <tr>\n" +
                            "      <th scope=\"row\">"+p.getId()+"</th>\n" +
                            "      <td>"+p.getReferencia()+"</td>  \n" +
                            "      <td>"+p.getIdPublicacion().getDescripcion()+"</td>\n" +
                            "      <td>"+p.getCosto()+"</td>\n" +
                            "      <td>"+p.getDescuento()+"%</td>\n" +
                            "      <td class=\"inp-cantidad\"><input type = \"number\" min = \"1\" value = \""+carro.getCantidad()+"\"></td>\n" +
                            "      <td>"
                                    + "<form action=\"eliminarProductoCarrito.do\">"
                                    + "<input hidden value=\""+p.getId()+"\" name=\"eliminarP\"/>"
                                    + "<button type=\"submit\" class=\"btn-eliminar btn\" id=\""+p.getId()+"\" text-white\">X</button></form></td>\n" +
                            "    </tr>\n" +
                            "    \n";
                            subtotal+=p.getCosto()*carro.getCantidad();
                            
                    
        }total=subtotal+15000;
                    rta+="\n" +
                    "  </tbody>\n" +
                    "</table>\n" +
                     "<form name=\"carritoTablaForm\" action=\"GuardarCarrito.do\">"
                                    + "<input hidden id=\"idProducts\" name=\"idProductos\" value=\"\">"
                                    + "<input hidden id=\"cantidadesP\" name=\"cantidades\" value=\"\">"

                                    + "<button class=\"guardar-carrito text-white\" onclick=\"enviarDatos()\">Guardar Carrito</button>\n" 
                            + "</form>"+
                    "</div>                        \n" +
                            "                    </div>\n" +
                            "                </div> \n" +
                            "                \n" +
                            "                <div class=\"row mt-4 mod-pos btn-2\">\n" +
                            "                    <div class=\"col\">\n" +
                            "                        <div class=\"d-flex justify-content-end\">\n" +
                            "                            \n" +
                            "                        </div>\n" +
                            "                    </div>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "            \n" +
                            "            <div class=\"contenedor-confirmar\">\n"
                            + "<form action=\"MostrarMetodoPago.do\">" +
                            "                    <div class=\"mt-5\">\n" +
                            "                        <div class=\"titulo-compra rounded mt-4\">\n" +
                            "                            <div class=\"text-center\">\n" +
                            "                                Resumen Compra\n" +
                            "                            </div>\n" +
                            "                        </div>\n" +
                            "                        <div class=\"border\">\n" +
                            "                            <div class=\"mx-3 mt-4\">\n" +
                            "                                <label class=\"bold my-1\">Subtotal:</label>\n" +
                            "                                <input class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\""+subtotal+"\" aria-label=\"default input example\" disabled>    \n"
                            + "                             <input hidden name=\"subtotal\" class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\""+subtotal+"\" aria-label=\"default input example\">" +
                            "                            </div>\n" +
                            "                            <div class=\"mx-3\">\n" +
                            "                                <label class=\"bold my-1\">Precio envio:</label>\n" +
                            "                                <input class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\"15000\" aria-label=\"default input example\" disabled>\n"
                            + "                              <input hidden name=\"envio\" class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\"15000\" aria-label=\"default input example\">\n" +
                            "                            </div>\n" +
                            "                            <div class=\"mx-3 mb-4\">\n" +
                            "                                <label class=\"bold my-1\">Total a pagar:</label>\n" +
                            "                                <input class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\""+total+"\" aria-label=\"default input example\" disabled> \n"
                            + "                               <input hidden name=\"total\" class=\"form-control border text-center\" type=\"number\" placeholder=\"$0.0\" value=\""+total+"\" aria-label=\"default input example\"> \n" +
                            "                            </div>\n" +
                            "                            \n" +
                            "                            <div class=\"d-flex justify-content-center\">\n" +
                            "                                <button type=\"submit\" class=\"btn-continue-size btn btn-info text-white\">Continuar</button>\n" +
                            "                            </div>\n"
                            + "" +
                            "                        </div>\n"
                            + "</form>" +
                            "                    </div>\n" +
                            "\n" +
                            "            </div>\n" +
                            "\n" +
                            "            \n" +
                            "        </div>";
                    return rta;
    }

    public void guardarCarrito(String id_person, String[] idProductos, String[] cantidades) {

            CarritoDAO c = new CarritoDAO();
            PersonaDAO pers = new PersonaDAO();
            Persona per = pers.readPersona(id_person);
            List<Carrito> carrito = per.getCarritoList();
            ProductoDAO product = new ProductoDAO();
            
            boolean existe=false;
            for (Carrito carro: carrito) {
                
                for (int i = 0; i < idProductos.length; i++) {
                        int id = Integer.parseInt(idProductos[i]);
                        if(carro.getPersona().getCedula().equals(id_person) && carro.getProducto().getId()==id){
                            existe =true;
                            carro.setCantidad(Integer.parseInt(cantidades[i]));
                            c.update(carro);
                        }
                        if(!existe && i==idProductos.length-1){

                        Carrito carri = new Carrito(id_person,Integer.parseInt(idProductos[i]));
                        carri.setPersona(per);
                        carri.setProducto(product.readProducto(Integer.parseInt(idProductos[i])));
                        carri.setCantidad(Integer.parseInt(cantidades[i]));
                        carri.setCarritoPK(new CarritoPK(id_person,Integer.parseInt(idProductos[i])));
                        c.create(carri);

                    }
                }
                
        }
    }

    public void eliminarCarritoProducto(String producto, String id_person) {

            PersonaDAO p = new PersonaDAO();
            List<Carrito> ca = p.readPersona(id_person).getCarritoList();
            CarritoDAO c = new CarritoDAO();
            
            for (Carrito carro: ca) {
                if(carro.getProducto().getId()==Integer.parseInt(producto)){
                
                    try {
                        c.delete(carro.getCarritoPK());
                        
                    } catch (IllegalOrphanException ex) {
                        Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                    
        }
    }

    public String getDatosPersonales(String cedula) {

         PersonaDAO p = new PersonaDAO();
         Persona per = p.readPersona(cedula);
         return per.getNombre()+","+per.getApellido()+","+per.getCedula()+","+per.getTelefono()+","
                 +per.getCorreo()+","+per.getDireccion()+","+per.getContraseña();
    }

    public String getDireccionesUser(String cedula) {
        
        PersonaDAO p = new PersonaDAO();
        Persona per = p.readPersona(cedula);
        List<Domicilio> d = per.getDomicilioList();
        String domicilios = "";
        for (Domicilio dom : d) {
            domicilios+=dom.getId()+","+dom.getIdCiudad().getIdDepartamento().getNombre()+
                    ","+dom.getIdCiudad().getNombre()+","+dom.getBarrio()+","+dom.getDescripcion()+";";
        }
        return domicilios;
    }

    public String getTarjetasUser(String cedula) {

        PersonaDAO p = new PersonaDAO();
        Persona per = p.readPersona(cedula);
        List<MetodoPago> metodos = per.getMetodoPagoList();
        String metodo="";
        for (MetodoPago m : metodos) {
            String nombres[] = m.getNombrePropietario().split(" ");
            metodo+=m.getId()+","+m.getNombre()+","+m.getNumero()+","+nombres[0]+" "+nombres[1]+
                    ","+this.getSoloFecha(m.getFechaExpiracion())+","+m.getCvc()+","+m.getIdentificacion()+";";
        }
        return metodo;
    }

    public void actualizarPersona(String nombre, String apellido, String cedula, String telefono, String correo, String direccion,String pass) {
            
            System.out.println("este es el telefono "+telefono);
            PersonaDAO p = new PersonaDAO();
            Persona persona = p.readPersona(cedula);
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setTelefono(telefono);
            persona.setCorreo(correo);
            persona.setDireccion(direccion);
            persona.setContraseña(pass);
            p.update(persona);

    }

    public void agregarTarjeta(String cedula, String idTarjeta, String tipoTarjeta, String numTarjeta, String nombres, String fechaExp, String csv, String cedulaDue) {

            MetodoPagoDAO metodo = new MetodoPagoDAO();
            MetodoPago m;
            PersonaDAO p = new PersonaDAO();
            if(idTarjeta.isEmpty()){
                m = new MetodoPago(0, tipoTarjeta, numTarjeta, nombres, new Date(fechaExp), Short.parseShort(csv), "CC", cedulaDue);
                m.setIdCliente(p.readPersona(cedula));
                metodo.create(m);
            }else{
                m = metodo.readMetodoPago(Integer.parseInt(idTarjeta));
                m.setNombre(tipoTarjeta);
                m.setNumero(numTarjeta);
                m.setNombrePropietario(nombres);
                m.setFechaExpiracion(new Date(fechaExp));
                m.setCvc(Short.parseShort(csv));
                m.setIdentificacion(cedulaDue);
                metodo.update(m);
            }
            
    }

    public void agregarDomicilio(String d, String idDom, String departamento, String ciudad, String direccion) {

            DomicilioDAO domici = new DomicilioDAO();
            Domicilio domi;
            PersonaDAO p = new PersonaDAO();
            Departamento de = getDepart(departamento);
            Ciudad c = getCiudad(ciudad, de);
            if(idDom.isEmpty()){
                domi = new Domicilio(0,direccion,direccion);
                domi.setIdCiudad(c);
                domi.setIdCliente(p.readPersona(d));
                domi.setDescripcion(direccion);
                domici.create(domi);
            }else{
                domi = domici.readDomicilio(Integer.parseInt(idDom));
                domi.setIdCiudad(c);
                domi.setIdCliente(p.readPersona(d));
                domi.setDescripcion(direccion); 
                domici.update(domi); 
            }
    }

    private Ciudad getCiudad(String ciudad,Departamento departamento) {
        List<Ciudad> ciudades = departamento.getCiudadList();
        for (Ciudad c : ciudades) {
            if(c.getNombre().equals(ciudad))
                return c;
        }
        return null;
        
    }

    private Departamento getDepart(String departamento) {

        DepartamentoDAO d = new DepartamentoDAO();
        List<Departamento> departamentos = d.read();
        for (Departamento de: departamentos) {
            if(de.getNombre().equals(departamento)){
                return de;
            }
        }
        return null;
    }

    public void eliminarTarjeta(String idT) {

        try {
            MetodoPagoDAO m = new MetodoPagoDAO();
            m.delete(Integer.parseInt(idT));
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarDomicilio(String idD) {

        DomicilioDAO d = new DomicilioDAO();
        try {
            d.delete(Integer.parseInt(idD));
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(askshop.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}