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
        for(Producto p : listPDao){
            Map<String, Map> valores = new HashMap<String, Map>();
            Map<String, String> valores1 = new HashMap<String, String>();
            valores1.put("precio_habitual", p.getCosto()+"");
            valores1.put("precio_descuento", ((p.getCosto()*p.getDescuento())/100)+"");
            valores.put(p.getIdTalla().getValor(), valores1);
            json.put(p.getReferencia(), valores);
        }
        
        System.out.println(json);
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
