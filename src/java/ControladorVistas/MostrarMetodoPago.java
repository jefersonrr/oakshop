/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.ProductoDAO;
import Negocio.askshop;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jefersonrr
 */
public class MostrarMetodoPago extends HttpServlet {

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
       
       
        if(request.getSession().getAttribute("usuario")==null){
            request.getRequestDispatcher("./jsp/iniciarsesion.jsp").forward(request, response);
        }
        askshop as = new askshop();
        if(request.getSession().getAttribute("esCarrito")!=null){
        
            request.getSession().setAttribute("subtotal",request.getParameter("subtotal"));
            request.getSession().setAttribute("envio", request.getParameter("envio"));
            request.getSession().setAttribute("total",request.getParameter("total"));
            request.getSession().setAttribute("esCarrito", request.getSession().getAttribute("esCarrito"));
        }else{
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        ProductoDAO pdao = new ProductoDAO();
        Double costo =  pdao.readProducto(idProducto).getCosto();
        request.getSession().setAttribute("subtotal",costo);
        request.getSession().setAttribute("envio", "15000");
        request.getSession().setAttribute("total", (costo+15000));
        request.getSession().setAttribute("idProducto", request.getParameter("idProducto"));
        
        }
        String [] metodos = as.metodoPago(request.getSession().getAttribute("usuario").toString());
        request.getSession().setAttribute("metodoCredito",metodos[0]);
        request.getSession().setAttribute("metodoDebito", metodos[1]);
        request.getRequestDispatcher("./jsp/medioPagoSeleccion.jsp").forward(request, response);
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
