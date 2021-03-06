<%-- 
    Document   : detalle-producto
    Created on : 4/12/2021, 11:56:01 p. m.
    Author     : Acer
--%>

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

        %>
        <base href="<%=basePath%>">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Oakshop</title>

            <!-- Fuente de google: Open Sans - Regular 400 -->
            <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">

            <!--Normallize css: proyecto que corrige estilos predeterminados de los diferentes navegadores, para evitar usar el selector universal
            en la hoja de estilos CSS. -->
            <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">

            <!-- Iconos -->
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">

            <!-- CSS de Bootstrap -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />

            <!-- CSS restantes -->
            <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
            <link rel="stylesheet" href="<%=basePath%>css/detalle-producto.css"/>
            <script>
                const datos = <%= request.getSession().getAttribute("detalle_producto")%>;
                let direccion;
            </script>
        </head>
        <body onload="sesion('<%=request.getSession().getAttribute("usuario")%>')">

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
                                    <li><a class="dropdown-item" href="#" >Mi Cuenta</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>AgregarACarrito.do" >Carrito</a></li>
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
            <main>
                <div class = "card-wrapper">
                    <div class = "card">
                        <!-- card left -->
                        <div class = "product-imgs">
                            <%= request.getSession().getAttribute("imagenes")%>
                        </div>

                        <!-- card right -->
                        <div class = "product-content">
                            <h2 class = "product-title"><%= request.getSession().getAttribute("publicacion_nombre")%></h2>
                            <h3>REF: <span id="nombre_producto"></span></h3>

                            <hr>

                            <div class = "product-price">
                                <p class = "last-price">Precio habitual: <span id="precio_habitual"></span></p>
                                <p class = "new-price">Con descuento: <span id="descuento"></span></p>
                                <p>Disponibles: <span id="disponibles"></span></p>
                            </div>


                            <form action="AgregarACarrito.do">
                                <div class="product-detail">

                                    <h4>Talla:</h4>

                                    <select class="select__1" name="tallas" id="tallas" required onchange="selectActive()">
                                        <%= request.getSession().getAttribute("tallas_disponibles")%>
                                    </select>

                                    <h4>Color:</h4>

                                    <select class="select__1" name="color" id="color" required onchange="selectActive()">
                                        <%= request.getSession().getAttribute("colores_disponibles")%>
                                    </select>

                                    <h4>Cantidad</h4>

                                    <div class = "purchase-info">
                                        <input type = "number" id="cantidad_dato" name="cantidad" min = "1" value="1" onchange="agregarCantidadItem()">
                                    </div>

                                    <div class="purchase-info2 d-flex justify-content-center">
                                        <button type = "submit" class = "btn" id="carrito">
                                            Añadir al carrito <i class = "fas fa-shopping-cart"></i>
                                        </button>
                                        <input type="hidden" id="productoId" value=".">
                                        <a  id="idProducto" href="#" type = "submit" class = "btn" id="submit">Comprar</a>
                                    </div>

                                </div>
                            </form>

                            <div class = "product-detail">
                                <br>
                                <h4>Descripción del producto </h4>
                                <div>
                                    <%= request.getSession().getAttribute("descripcion_producto")%>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </main>

            <div class="footer-dark">
                <footer>
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-6 col-md-3 item">
                                <h3>Categorías</h3>
                                <ul>
                                    <li><a href="#">Hombres</a></li>
                                    <li><a href="#">Mujer</a></li>
                                    <li><a href="#">Kids</a></li>
                                </ul>
                            </div>
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


            <!-- JS de Bootstrap -->      
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>   

            <!-- JS -->      
            <script src="<%=basePath%>js/sesion.js"></script>
            <script src="<%=basePath%>js/detalle-producto.js"></script>



        </body>
    </html>
