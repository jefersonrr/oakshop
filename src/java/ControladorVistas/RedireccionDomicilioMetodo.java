/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.MetodoPagoDAO;
import DAO.PersonaDAO;
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
public class RedireccionDomicilioMetodo extends HttpServlet {

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
         if(request.getSession().getAttribute("esCarrito")!=null){
             
            request.getSession().setAttribute("subtotal", request.getParameter("subtotal"));
            request.getSession().setAttribute("envio", "15000");
            request.getSession().setAttribute("total", request.getParameter("total"));
            request.getSession().setAttribute("esCarrito", request.getSession().getAttribute("esCarrito"));
        }else{
             
           
        int idProducto = Integer.parseInt(request.getSession().getAttribute("idProducto").toString());
        ProductoDAO pdao = new ProductoDAO();
        Double costo =  pdao.readProducto(idProducto).getCosto();
        request.getSession().setAttribute("subtotal",costo);
        request.getSession().setAttribute("envio", "15000");
        request.getSession().setAttribute("total", costo+15000);
        request.getSession().setAttribute("idProducto", request.getSession().getAttribute("idProducto"));
        
        }
         
         askshop as = new askshop();
         MetodoPagoDAO mdao = new MetodoPagoDAO();
         
        if(request.getParameter("credi")!=null && !request.getParameter("credi").equals("") ){
        
            if(request.getParameter("credi").equals("-1")){
                request.getSession().setAttribute("tipoTarjeta", "Crédito");
                request.getRequestDispatcher("./jsp/medioPagoData.jsp").forward(request, response);
               
            }else{
           
            request.getSession().setAttribute("idTarjeta", request.getParameter("credi"));
            request.getSession().setAttribute("tarjetaOculta",as.ocultarTarjeta(mdao.readMetodoPago(Integer.parseInt(request.getParameter("credi"))).getNumero()) );
            request.getRequestDispatcher("./jsp/medioPagoConfirmar.jsp").forward(request, response);
            }
        }else if(request.getParameter("debi")!=null && !request.getParameter("debi").equals("")){
        
            if(request.getParameter("debi").equals("-1")){
                request.getSession().setAttribute("tipoTarjeta", "Débito");
                request.getRequestDispatcher("./jsp/medioPagoData.jsp").forward(request, response);
               
            }else{
            
          
            request.getSession().setAttribute("idTarjeta", request.getParameter("debi"));
            request.getSession().setAttribute("tarjetaOculta",as.ocultarTarjeta(mdao.readMetodoPago(Integer.parseInt(request.getParameter("debi"))).getNumero()) );
            request.getRequestDispatcher("./jsp/medioPagoConfirmar.jsp").forward(request, response);
            
            }
        }else{
        request.getRequestDispatcher("./jsp/medioPagoSeleccion.jsp").forward(request, response);
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
