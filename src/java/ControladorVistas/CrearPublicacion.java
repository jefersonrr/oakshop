/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import Negocio.askshop;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristian
 */
public class CrearPublicacion extends HttpServlet {

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
        
       askshop a = new askshop();
        //productos
        String referencias[] = request.getParameter("referencias").split(",");
        String costos[] = request.getParameter("costos").split(",");
        String descuentos[] = request.getParameter("descuentos").split(",");
        String tallas[] = request.getParameter("tallas").split(",");
        String imgs[] = request.getParameter("imgUrls").split(",");
        String colores[] = request.getParameter("colores").split(",");
        String cantidades[] = request.getParameter("cantidades").split(",");
        String pub = request.getSession().getAttribute("editar").toString();
        System.out.println("este es publicsacion        "+pub);
        //publicacion
        if(pub==null){
            String nombre = request.getSession().getAttribute("nombre").toString();
            String marca = request.getSession().getAttribute("marca").toString();
            String categoria = request.getSession().getAttribute("categoria").toString();
            String tipo = request.getSession().getAttribute("tipo").toString();
            String descripcion= request.getSession().getAttribute("descripcion").toString();

            a.crearPublicacion(nombre,marca,categoria,tipo,descripcion,referencias,costos,descuentos,tallas,imgs,colores,cantidades);
            request.getRequestDispatcher("jsp/adminPublicaciones.jsp").forward(request, response);
        }else{
        
            a.agregarProductosPublicacion(referencias,costos,descuentos,tallas,imgs,colores,cantidades,pub);
            request.getRequestDispatcher("jsp/editarPub.jsp").forward(request, response);
        }
        
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
