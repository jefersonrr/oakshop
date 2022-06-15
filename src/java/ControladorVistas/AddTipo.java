/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.CategoriaDAO;
import DAO.CategoriaTipoDAO;
import DAO.TallaDAO;
import DAO.TipoDAO;
import DAO.TipoTallaDAO;
import DTO.Categoria;
import DTO.CategoriaTipo;
import DTO.Talla;
import DTO.Tipo;
import DTO.TipoTalla;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jefersonrr
 */
public class AddTipo extends HttpServlet {

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
        TipoDAO tdao   = new TipoDAO();
        
        CategoriaDAO cadao = new CategoriaDAO();
        CategoriaTipoDAO catidao = new CategoriaTipoDAO();
        
              Tipo tipo = new Tipo(null,request.getParameter("nombre"), "ACTIVO",request.getParameter("url_foto"));
              tdao.create(tipo);
              Categoria categoria = cadao.readCategoria(Integer.parseInt(request.getParameter("categoria")));
              
              
              CategoriaTipo cate = new CategoriaTipo();
              cate.setIdCategoria(categoria);
              cate.setIdTipo(tipo);
              catidao.create(cate);
              TallaDAO ta = new TallaDAO();
              List<Talla> tallas = ta.read();
              TipoTallaDAO tita = new TipoTallaDAO();
              
              for(Talla t : tallas){
              
                  TipoTalla tip = new TipoTalla();
                  tip.setIdTalla(t);
                  tip.setIdTipo(tipo);
                  tita.create(tip);
              }
              
              System.out.println("--------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
              request.getRequestDispatcher("./MostrarCategorias.do").forward(request, response);
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