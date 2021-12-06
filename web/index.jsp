<%-- 
    Document   : index
    Created on : 27/11/2021, 9:49:02 a. m.
    Author     : Sebastian :v
--%>

<%@page import="DTO.Tipo"%>
<%@page import="java.util.List"%>
<%@page import="DTO.Categoria"%>
<%@page import="DAO.CategoriaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";

        

        if (request.getSession().getAttribute("carrusel") == null) {
            request.getRequestDispatcher("/Index.do").forward(request, response);

        }
        String carrusel = request.getSession().getAttribute("carrusel").toString();
        CategoriaDAO cadao = new CategoriaDAO();
        List<Categoria> ca = cadao.readActivo();
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

        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        
        <!-- Iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
   
        <!-- CSS de Bootstrap -->
        <link href='https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css' rel='stylesheet'>

        <link href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css' rel='stylesheet'

              <!-- CSS restantes -->
              <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/index.css"/>
    <body oncontextmenu='return false' class='snippet-body' onload="sesion('<%=request.getSession().getAttribute("usuario")%>')">

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
                                    <li><a class="dropdown-item" href="<%=basePath%>MisVehiculos.do" >Carrito</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>MisServiciosUsu.do" >Mis Compras</a></li>
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

        <!-- Carousel -->
        <section class="banner">
            <div class="banner-content">
                <h1>Bienvenido a Oakshop Store</h1>
                <a href="#principal">Ver productos</a>	
            </div>
        </section>
        <!-- Fin carousel -->

        <main id="principal">

          <%=carrusel%>

        </main>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>   

        <!-- JS de Bootstrap -->      
        <script type='text/javascript' src='https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js'></script>
        <script type='text/javascript' src='https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/js/bootstrap.min.js'></script>
        <script src="./js/sesion.js"></script>
    </body>
</html>
