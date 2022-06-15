<%-- 
    Document   : detalleCompra
    Created on : 14/06/2022, 9:12:48 p. m.
    Author     : Acer
--%>

<%@page import="Negocio.askshop"%>
<%@page import="DTO.Producto"%>
<%@page import="DAO.GaleriaimgDAO"%>
<%@page import="DTO.DetalleCompra"%>
<%@page import="DTO.Tipo"%>
<%@page import="DTO.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="DAO.CategoriaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";

        CategoriaDAO cadao = new CategoriaDAO();
        List<Categoria> ca = cadao.readActivo();
        String compras = request.getSession().getAttribute("compras").toString();
    %>
    <base href="<%=basePath%>">
    
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Oakshop</title>

        <!--Normallize css: proyecto que corrige estilos predeterminados de los diferentes navegadores, para evitar usar el selector universal
        en la hoja de estilos CSS. -->
        <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
        <!-- Iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">

        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        <!-- CSS de Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- CSS restantes -->
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/comprasUsu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/footer.css"/>
    </head>
    <body onload="sesion('<%=request.getSession().getAttribute("usuario")%>')" id="impri">
        <!--menú -->
           <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
            <div class="container-fluid">

                <a class="navbar-brand" href="index.jsp">
                    <!-- <img src="#" alt="" width="140px" height="120px" /> -->
                    Oakshop
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">INICIO</a>
                        </li>
                        <% int k;
                            if (ca.size() > 5) {
                                k = 5;

                            } else {
                                k = ca.size();
                            }
                            for (int i = 0; i < k; i++) {%>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <%=ca.get(i).getNombre()%>
                            </a>
                            <%List<Tipo> tipos = ca.get(i).getTipoList();%>

                            
                            
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <%for (Tipo t : tipos) {%>


                                <li><a class="dropdown-item" href="<%=basePath%>/PublicacionesCategoria.do?tipo=<%=t.getId() %>&cate=<%=ca.get(i).getId()%>"><%=t.getNombre()%> </a></li>


                                <%};%>
                            </ul>
                        </li>
                        <%};%>


                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="#">CONTACTO</a>
                        </li>
                    </ul>

                    <template id="NoSesion">
                        <ul class="navbar-nav ml-auto m-4">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=basePath%>/jsp/iniciarsesion.jsp">INICIAR SESIÓN</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=basePath%>jsp/registrarse.jsp">REGISTRARSE</a>
                            </li>
                        </ul>
                    </template>
                    <!-- Usuario logueado-->
                    <template id="SiSesion">
                        <ul class="navbar-nav ml-auto m-4">
                            <li class="nav-item dropdown" style="list-style-type: none;">
                                <a  class="nav-link dropdown-toggle link-dark" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" >
                                    <%=request.getSession().getAttribute("nameUser")%>
                                </a>
                                <ul class="dropdown-menu text-small "aria-labelledby="dropdownUser2"  >
                                    <li><a class="dropdown-item" href="EditarPerfil.do" >Mi Cuenta</a></li>
                                    <li><a class="dropdown-item" href="IrAcarrito.do" >Carrito</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>MostrarCompras.do" >Mis Compras</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="./cerrarSesion.do">Salir</a></li>
                                </ul>
                            </li>

                            <svg xmlns="http://www.w3.org/2000/svg" style="color:#fff" width="50" height="50" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                            </svg>

                        </ul>
                    </template>

                </div>
            </div>
        </nav>
    <!--Fin menú -->
    
    <form action="#">

    <main>
        <div class="container-fluid">
            <%
                List<DetalleCompra> det = (List<DetalleCompra>)request.getSession().getAttribute("detalles");
                GaleriaimgDAO g = new GaleriaimgDAO();
                DetalleCompra deta = det.get(0);
                askshop a = new askshop();
            %>
            <div class="header-compras">
                <div class="row">
                    <div class="col">
                        <div class="tit-principal">
                            <h2 class="text-primary">Detalle compra</h2>
                        </div>
                    </div>
                </div>
            </div>

            <div class="body-compras">

                <div class="row">
                    <div class="titulo">
                        <h4>Productos comprados:</h4>
                    </div>

                    <!-- Parte 1 -->
                    <div class="col-md-7">
                        <% for (DetalleCompra d: det) {
                        Producto p = d.getProducto();
                        %>
                               
                            <div class="compra">

                                <div class="row nomb">
                                    
                                    <div class="col nombre-p">
                                        <div>
                                            <h6><%=p.getIdPublicacion().getNombre()%> <%=p.getIdColor().getNombre()%> | Talla: <%=p.getIdTalla().getValor() %> </h6>
                                        </div>
                                        <div class="nombre-p">
                                            <p><span><%=d.getCantidad()%></span> Unidades</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <%}%>
                        <div class="estado estado-pad">
                            <div class="row">
                                <div class="col">
                                    <div class="titulo-e">
                                        <h5>Entregado</h5>
                                        <hr>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col">
                                    <div class="fecha-e">
                                        <h3>Fecha de entrega: <span><%=deta.getCompra().getFecha()%> </span></h3>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col descripcion-m">
                                    <div class="descripcion-e">
                                        <p>Su paquete se entregó a las <span>10:20 A.M</span> en <span>Dirección <%=deta.getCompra().getIdCliente().getDireccion() %></span>
                                         Cúcuta, Norte de Santander
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    

                    <!-- Parte 2 | Detalle compra -->
                    <div class="col-md-5">

                        <div class="resumen">
                            <div class="row">
                                <div class="col head-dc">
                                    <div>
                                        <h3>Detalle de la compra</h3>
                                    </div>
                                    <div>
                                        <p><span>Fecha compra  <%=deta.getCompra().getFecha() %></span> / #<span><%=deta.getCompra().getId() %></span></p>
                                    </div>
                                    <hr>
                                </div>
                            </div>

                            <div class="row body-dc">
                                <%for (DetalleCompra d: det) {
                                    Producto p = d.getProducto();  
                                %>
                               
                                   <div class="col">
                                    <div class="contenedor d-flex justify-content-between">
                                        <div class="productos">
                                            <h5><%=p.getIdPublicacion().getNombre() %></h5> 
                                        </div>
                                        <div class="precio">
                                            <p>$<%=p.getCosto()%></p>
                                        </div>
                                    </div>
                                <%}%>
                                    
                                </div>
                            </div>

                            <div class="row body-dc">
                                <div class="col">
                                    <div class="contenedor d-flex justify-content-between">
                                        <div class="envio">
                                            <h5>Envio</h5>
                                        </div>
                                        <div class="precio-e">
                                            <p>$15.000</p>
                                        </div>
                                    </div>
                                    <hr>
                                </div>
                            </div>

                            <div class="row body-dc">
                                <div class="col">
                                    <div class="contenedor d-flex justify-content-between">
                                        <div class="total">
                                            <h4>Total</h4>
                                        </div>
                                        <div class="precio-t">
                                            <p>$<%= deta.getCompra().getCosto() %></p>
                                        </div>
                                    </div>
                                    <hr>
                                </div>
                            </div>

                            <!-- Desplegable detalles -->
                            <div class="row">
                                <div class="col">
                                    <div class="titulo-c">
                                        <h4>Datos personales</h4>
                                    </div>
                                    <div class="datos-p">
                                        <div class="row">
                                            <div class="col datos">
                                                <h5>Aquí el <%=deta.getCompra().getIdCliente().getNombre() %> <%=deta.getCompra().getIdCliente().getApellido() %></h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col datos">
                                                <h5>Cédula: <span><%=deta.getCompra().getIdCliente().getCedula() %></span></h5>
                                            </div>
                                        </div>
                                       
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col">
                                    <div class="titulo-c">
                                        <h4>Datos pago</h4>
                                    </div>
                                    <div class="datos-p">
                                        <div class="row">
                                            <div class="col datos">
                                                <h5>Tipo de tarjeta <%=deta.getCompra().getIdMetodoPago().getNombre() %></h5>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col datos">
                                                <h5>Tarjeta: <span>****<%=a.ocultarTarjeta(deta.getCompra().getIdMetodoPago().getNumero()) %></span></h5>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>

                        <div class="d-flex justify-content-center">
                            <input class="btn botonsito btn-danger" onclick="imp()" value="Generar Factura">
                        </div>

                    </div>


                </div>
                
                
            </div>
        </div>

      
    </main>

</form>
     <div class="footer-dark">
        <footer>
            <div class="container">
                <div class="row">
                    
                    <div class="col-sm-6 col-md-3 item">
                        <h3>Acerca de</h3>
                        <ul>
                            <li><a href="#">Empresa</a></li>
                            <li><a href="#">Equipo</a></li>
                            <li><a href="#">Corporativo</a></li>
                        </ul>
                    </div>
                    <div class="col-md-6 item text">
                        <h3>Oakshop Store</h3>
                        <p>Praesent sed lobortis mi. Suspendisse vel placerat ligula. Vivamus ac sem lacus. Ut vehicula rhoncus elementum. Etiam quis tristique lectus. Aliquam in arcu eget velit pulvinar dictum vel in justo.</p>
                    </div>
                </div>
                <div class="row">
                    <div class="item social">
                        <a href="#"><i class="icon ion-social-facebook"></i></a>
                        <a href="#"><i class="icon ion-social-twitter"></i></a>
                        <a href="#"><i class="icon ion-social-instagram"></i></a>
                    </div>
                </div>
                <p class="copyright">Oakshop Store © 2021</p>
            </div>
        </footer>
    </div> 
    <!-- jQuery library -->
    

    <!-- JS de Bootstrap -->
    <script>
        
        function imp() {
           window.print();
        }
    </script>
    <script src="./js/sesion.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    
</body>
</html>

