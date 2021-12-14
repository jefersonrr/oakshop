/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ControladorVistas;

import DAO.CarritoDAO;
import DAO.CompraDAO;
import DAO.DetalleCompraDAO;
import DAO.DomicilioDAO;
import DAO.EnvioDAO;
import DAO.MetodoPagoDAO;
import DAO.PersonaDAO;
import DAO.ProductoDAO;
import DTO.Carrito;
import static DTO.Categoria_.estado;
import DTO.Compra;
import DTO.DetalleCompra;
import DTO.DetalleCompraPK;
import static DTO.Domicilio_.descripcion;
import DTO.Envio;
import DTO.MetodoPago;
import DTO.Persona;
import DTO.Producto;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jefersonrr
 */
public class Facturar extends HttpServlet {

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
            throws ServletException, IOException, IllegalOrphanException, NonexistentEntityException {

        MetodoPagoDAO mdao = new MetodoPagoDAO();
        MetodoPago metodo = mdao.readMetodoPago(Integer.parseInt(request.getSession().getAttribute("idTarjeta").toString()));
        PersonaDAO pdao = new PersonaDAO();
        Persona per = pdao.readPersona(request.getSession().getAttribute("usuario").toString());
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC-5"));
        Date fechaActual = calendar.getTime();
        CompraDAO cdao = new CompraDAO();
        Compra compra = new Compra(null, Double.parseDouble(request.getParameter("total")), fechaActual);
        compra.setIdCliente(per);
        compra.setIdMetodoPago(metodo);
        cdao.create(compra);
        EnvioDAO endao = new EnvioDAO();
        Envio en = new Envio(null, "El vendedor esta preparando tu paquete", "EN PREPARACION");
        en.setIdCompra(compra);
        DomicilioDAO domidao = new DomicilioDAO();
        en.setIdDomicilio(domidao.readDomicilio(Integer.parseInt(request.getParameter("direccion"))));
        endao.create(en);
        DetalleCompraDAO dcompradao = new DetalleCompraDAO();

        ProductoDAO prodao = new ProductoDAO();
        if (request.getSession().getAttribute("esCarrito") != null) {
            CarritoDAO cadao = new CarritoDAO();
            List<Carrito> carrito = per.getCarritoList();

            for (Carrito c : carrito) {

                Producto p = c.getProducto();
                DetalleCompra dcompra = new DetalleCompra(new DetalleCompraPK(compra.getId(), p.getId()), c.getCantidad());
                dcompra.setCompra(compra);
                dcompra.setProducto(p);
                p.setCantidad(p.getCantidad() - c.getCantidad());
                dcompradao.create(dcompra);
                prodao.update(p);
                cadao.delete(c.getCarritoPK());

            }
        } else {

            Producto p = prodao.readProducto(Integer.parseInt(request.getSession().getAttribute("idProducto").toString()));
            DetalleCompra dcompra = new DetalleCompra(new DetalleCompraPK(compra.getId(), p.getId()), 1);
            dcompra.setCompra(compra);
            dcompra.setProducto(p);
            p.setCantidad(p.getCantidad() - 1);
            prodao.update(p);
            dcompradao.create(dcompra);
        }

        request.getRequestDispatcher("./MostrarCompras.do").forward(request, response);

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
        try {
            processRequest(request, response);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(Facturar.class.getName()).log(Level.SEVERE, null, ex);
        }
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
