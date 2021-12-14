/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CategoriaDAO;
import DAO.ColorDAO;
import DAO.ProductoDAO;
import DAO.PublicacionDAO;
import DAO.TallaDAO;
import DAO.TipoDAO;
import DTO.Producto;
import DTO.Publicacion;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Cristian
 */
public class main {
    public static void main(String[] args) {
        
        Date d = new Date("12/ 22 / 2021");
        System.out.println(d);
//        Date fecha = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
//        PublicacionDAO pu = new PublicacionDAO();
//       askshop a = new askshop();
//        ColorDAO c = new ColorDAO();
//        TallaDAO t = new TallaDAO();
//////        Publicacion p = pu.readPublicacion(2112042024);
//////        p.setDescripcion("hoalalalalala");
//////        p.setNombre("camisita");
//////        pu.update(p);
//          Producto p = new Producto(0, "CRTI", 20000, 0, 2, "");
//          p.setIdColor(c.readColor(3));
//          p.setIdTalla(t.readTalla(2));
//          ProductoDAO pro = new ProductoDAO();
//          p.setIdPublicacion(pu.readPublicacion(2112041610));
//          pro.create(p);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(fecha);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHss");
//        int h = Integer.parseInt(sdf.format(fecha));
//        System.out.println("hash "+h);
        //int r = Math.random();
//        Publicacion p 
//                = new Publicacion(123, "adidas", "ccamisa", fecha, "hola");
//        
//        CategoriaDAO ca = new CategoriaDAO();
//            TipoDAO ti = new TipoDAO();
//            p.setIdCategoria(ca.readCategoria(Integer.parseInt("2")));
//            p.setIdTipo(ti.readTipo(Integer.parseInt("1")));
//            pu.create(p);
//        a.crearPublicacion("xas", "lacos", "sfsdf", "camis", "lkfsdlkfj");
    }
    
    
}
