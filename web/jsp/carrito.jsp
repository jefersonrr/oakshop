<%-- 
    Document   : carrito
    Created on : 5/12/2021, 7:49:39 p. m.
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";


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
        <link rel="stylesheet" href="<%=basePath%>css/styles.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/footer.css"/>

    </head>
    <body onload="sesion('<%=request.getSession().getAttribute("usuario")%>')">
        
        <!-- MENÚ DE NAVEGACIÓN-->
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
                            <a class="nav-link active" aria-current="page" href="index.jsp">INICIO</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                HOMBRE
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">POLOS</a></li>
                                <li><a class="dropdown-item" href="#">CAMISETAS</a></li>
                                <li><a class="dropdown-item" href="#">JEANS</a></li>
                                <li><a class="dropdown-item" href="#">CALZADO</a></li>
                             </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                MUJERES
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">BLUSAS</a></li>
                                <li><a class="dropdown-item" href="#">VESTIDOS</a></li>
                                <li><a class="dropdown-item" href="#">JEANS</a></li>
                                <li><a class="dropdown-item" href="#">CALZADO</a></li>
                             </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                KIDS
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">CAMISETAS</a></li>
                                <li><a class="dropdown-item" href="#">BERMUDAS</a></li>
                                <li><a class="dropdown-item" href="#">JEANS</a></li>
                                <li><a class="dropdown-item" href="#">CALZADO</a></li>
                             </ul>
                        </li>
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
                    <!-- USUARIO LOGUEADO -->
                     <template id="SiSesion">
                        <ul class="navbar-nav ml-auto m-4">
                            <li class="nav-item dropdown" style="list-style-type: none;">
                                <a  class="nav-link dropdown-toggle link-dark" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" >
                                    <%=request.getSession().getAttribute("nameUser")%>
                                </a>
                                <ul class="dropdown-menu text-small "aria-labelledby="dropdownUser2"  >
                                    <li><a class="dropdown-item" href="#" >Mi Cuenta</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>MisVehiculos.do" >Mis Vehiculos</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>MisServiciosUsu.do" >Mis Servicios</a></li>
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
                                
    <div class="container-fluid cont-padre">
        <div class="row">
            <div class="col start-title">
                <div class="text-center start-text p-2">
                    Oakshop Store
                </div>
            </div>
        </div>
        <%=request.getSession().getAttribute("carro")%>
        <%request.getSession().setAttribute("esCarrito", "si");%>           
    </div>
                                    
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
        <script src="./js/sesion.js"></script>
        <script>
            function eliminar(){
                
                let id = window.event.target.id;
                let padre = document.getElementById(id).parentNode.parentNode;
                padre.parentNode.removeChild(padre);
            }
            
            function enviarDatos(){
                let tabla = document.getElementById("tabla");
                let filas = tabla.childNodes[3].querySelectorAll('tr');
                let idProducts = document.getElementById('idProducts');
                let idCantidadesP = document.getElementById('cantidadesP');
                for(let i=0; i<filas.length; i++){
                    
                    idProducts.value+=filas[i].childNodes[1].innerText+",";
                    idCantidadesP.value+=filas[i].childNodes[11].querySelector('input').value+",";
                }
                document.carritoTablaForm.submit();
            }
            
        </script>
    </body>
</html>
