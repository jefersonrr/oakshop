/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import DAO.CategoriaDAO;
import DAO.PublicacionDAO;
import DAO.TipoDAO;
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
//        Date fecha = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        PublicacionDAO pu = new PublicacionDAO();
       askshop a = new askshop();
        
        Publicacion p = pu.readPublicacion(2112042024);
        p.setDescripcion("hoalalalalala");
        p.setNombre("camisita");
        pu.update(p);
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
