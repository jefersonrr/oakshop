<%-- 
    Document   : medioPago
    Created on : 5/12/2021, 02:15:52 AM
    Author     : Luis
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
        <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
        <!-- Iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/productos.css"/>
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

        <!-- CONTENIDO -->

        <form action="" method="post">
            <div class="row">
                <div class="col start-title">
                    <div class="text-center start-text p-2">
                        Nombre de tienda
                    </div>
                </div>
            </div>
            <div class="row m-10 mt-3">
                <div class="row my-2">
                    <div class="col d-flex">
                        <div>
                            <img src="img/carrito.png" width="50" height="50"/>    
                        </div>
                        <div class="titulo-contenido mt-2 ms-5 d-flex">
                            ¿ Como Quieres Pagar ?
                        </div>
                    </div>
                </div>
                <div class="contenedor-inicial mt-5">
                    <div class="contenedor">
                        <div class="w-100">
                            <ul class="nav rounded elemento-data texto-contenido">
                                <li class="w-100">
                                    <div class="row">
                                        <div class="col-2">
                                            <div class="d-flex justify-content-center my-3">
                                                <img src="img/card.png" width="30" height="30"/>    
                                            </div>

                                        </div>
                                        <div class="col-7">
                                            <div class="my-3 bold">
                                                Tarjeta de Crédito    
                                            </div>

                                        </div>
                                        <div class="col-3 align-items-center">
                                            <button type="button" class="btn-content btn-size btn align-items-center">Modificar</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>

                            <div class="container">
                                <div class="titulo-contenido my-5">
                                    Ingresa los datos de tu tarjeta
                                </div>
                                <div class="container border p-5">
                                    <div class="row">
                                        <div class="col-8">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Número de tarjeta" aria-label="default input example">         
                                        </div>
                                    </div>
                                    <div class="row my-2">
                                        <div class="col-8">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Nombre y Apellido" aria-label="default input example">
                                        </div>
                                    </div>

                                    <div class="row my-2">
                                        <div class="col-4">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Fecha de Expiración" aria-label="default input example">
                                        </div>
                                        <div class="col-4">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Codigo de Seguridad" aria-label="default input example">
                                        </div>
                                    </div>
                                    <div class="row mt-2 mb-5">
                                        <div class="col-4">
                                            <label class="texto-contenido bold">Tipo</label>
                                            <select class="mt-2 form-select border-0 border-bottom text-start" aria-label="Default select example">
                                                <option selected>CC</option>
                                                <option value="1">TI</option>
                                                <option value="2">---</option>
                                                <option value="3">---</option>
                                            </select>
                                        </div>
                                        <div class="col-4">
                                            <label></label>
                                            <input class="form-control form-control-lg border-bottom w-100" type="number" placeholder="Número" aria-label="default input example">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> 

                    <div class="row mt-4 mod-pos btn-2">
                        <div class="col">
                            <div class="d-flex justify-content-end">
                                <a href="<%=basePath%>./jsp/medioPagoConfirmar.jsp"type="button" class="btn-continue-size btn btn-info  text-white">Continuar</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="contenedor-confirmar">
                    <div class="mt-5">
                        <div class="titulo-compra rounded mt-4">
                            <div class="text-center">
                                Resumen Compra
                            </div>
                        </div>
                        <div class="border">
                            <div class="mx-3 mt-4">
                                <label class="bold my-1">Subtotal:</label>
                                <input class="form-control border text-center" type="number" placeholder="$0.0" aria-label="default input example">    
                            </div>
                            <div class="mx-3">
                                <label class="bold my-1">Precio envio:</label>
                                <input class="form-control border text-center" type="number" placeholder="$0.0" aria-label="default input example">
                            </div>
                            <div class="mx-3">
                                <label class="bold my-1">Descuento:</label>
                                <input class="form-control border text-center" type="number" placeholder="$0.0" aria-label="default input example">
                            </div>
                            <div class="mx-3 mb-4">
                                <label class="bold my-1">Total a pagar:</label>
                                <input class="form-control border text-center" type="number" placeholder="$0.0" aria-label="default input example">
                            </div>
                        </div>
                    </div>
                    <div class="row mt-4 mod-pos btn-1">
                        <div class="col">
                            <div class="d-flex justify-content-end">
                                <a href="<%=basePath%>./jsp/medioPagoConfirmar.jsp"type="button" class="btn-continue-size btn btn-info  text-white">Continuar</a>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </form>


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
    </body>
</html>
