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
    
    
    public String getFecha(Date fecha){
            SimpleDateFormat formateador = new SimpleDateFormat(
                 "dd '/' MM '/' yyyy", new Locale("es_ES"));
            SimpleDateFormat formateador2 = new SimpleDateFormat(
                 "hh:mm", new Locale("es_ES"));
            String fechad = formateador.format(fecha);
            String horas = formateador2.format(fecha);
        return "Dia: "+fechad.replace(" ", "")+"<br>Hora: "+horas;
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
                
                 Producto producto = new Producto(0, referencias[i],"", 
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]));
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
                    Producto producto = new Producto(0, referencias[i],"", 
                         Double.parseDouble(costos[i]) , Integer.parseInt(descuentos[i]),Integer.parseInt(cantidades[i]));
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

}
