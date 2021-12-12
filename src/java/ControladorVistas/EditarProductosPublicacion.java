/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.PublicacionDAO;
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
public class EditarProductosPublicacion extends HttpServlet {

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
        
        String nombre = request.getParameter("nombre");
        
        int editar = Integer.parseInt(request.getSession().getAttribute("editar").toString());//editar es el id de la publi
        PublicacionDAO p = new PublicacionDAO();
        String marca = request.getParameter("marca");
        int tipo = p.readPublicacion(editar).getIdTipo().getId();
        String descripcion = request.getParameter("descripcion");
        
        String colores = a.getColores();
        String tallas = a.tipo_talla(tipo);
        //para obtenerlos al agregar la publicacion
        request.getSession().setAttribute("nombre", nombre);
        request.getSession().setAttribute("marca",marca);
        request.getSession().setAttribute("descripcion", descripcion);
        //para el desplegable
        request.getSession().setAttribute("colores", colores);
        request.getSession().setAttribute("tallas",tallas);
        
        request.getRequestDispatcher("jsp/editarProductoPublicacion.jsp").forward(request, response);
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
