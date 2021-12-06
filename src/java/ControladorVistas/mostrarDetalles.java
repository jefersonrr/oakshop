/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.PublicacionDAO;
import DTO.Galeriaimg;
import DTO.Producto;
import DTO.Publicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Luis
 */
public class mostrarDetalles extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PublicacionDAO pDao = new PublicacionDAO();
        Publicacion pub = pDao.readPublicacion(Integer.parseInt(request.getParameter("id_publicacion")));
        List <Producto> listPDao = pub.getProductoList();
        List <Galeriaimg> galeria = pub.getGaleriaimgList();
        JSONObject json = new JSONObject();
        JSONObject out = new JSONObject();
        String img_display = "<div class = 'img-display'>"
                + "<div class = 'img-showcase'>";
        String img_item = "<div class = \"img-select\">";
        Map<Integer, String> colores = new HashMap<Integer, String>();
        Map<Integer, String> tallas = new HashMap<Integer, String>();
        int count = 0;
        for(Producto p : listPDao){
            Map<String, String> valores = new HashMap<String, String>();
            valores.put("precio_habitual", p.getCosto()+"");
            valores.put("precio_descuento", ((p.getCosto()*p.getDescuento())/100)+"");
            valores.put("cantidad", p.getCantidad()+"");
            valores.put("talla", p.getIdTalla().getValor());
            tallas.put(p.getIdTalla().getId(), p.getIdTalla().getValor());
            valores.put("talla_id", p.getIdTalla().getId()+"");
            valores.put("color", p.getIdColor().getNombre());
            colores.put(p.getIdColor().getId(), p.getIdColor().getNombre());
            valores.put("color_id", p.getIdColor().getId()+"");
            valores.put("referencia_producto", p.getReferencia());
            valores.put("producto_id", p.getId()+"");
            valores.put("descuento", p.getDescuento()+"");
            json.put(count, valores);
            count++;
            
        
        }
        count = 1;
        for(Galeriaimg imagen : galeria){
            img_display +="<img src = '"+imagen.getUrl()+"' alt = 'shoe image' width='300' height='500'> ";
            img_item+= "<div class = 'img-item'>"
                        +"<a href = '#' data-id = '"+count+"'>"
                        +"<img src = '"+imagen.getUrl()+"' alt = 'shoe image' width='120' height='150'>"
                        +"</a>"
                        +"</div>";
            count++;
        }
        img_item+= "</div>";
        img_display += "</div>"
                +"</div>";
        out.put("valores", json);
        request.getSession().setAttribute("descripcion_producto", pub.getDescripcion());
        out.put("publicacion_nombre", pub.getNombre());
        String outColores = "";
        for(Map.Entry<Integer, String> tmp: colores.entrySet()){
            outColores += "<option value = \""+tmp.getKey()+"\">"+tmp.getValue()+"</option>";
        }
        
        String outTallas = "";
        for(Map.Entry<Integer, String> tmp: tallas.entrySet()){
            outTallas += "<option value = \""+tmp.getKey()+"\">"+tmp.getValue()+"</option>";
        }
        System.out.println(img_display+img_item);
        request.getSession().setAttribute("imagenes", img_display+img_item);
        request.getSession().setAttribute("detalle_producto", out);
        request.getSession().setAttribute("tallas_disponibles", outTallas);
        request.getSession().setAttribute("colores_disponibles", outColores);
        request.getRequestDispatcher("jsp/detalle-producto.jsp").forward(request, response);
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
