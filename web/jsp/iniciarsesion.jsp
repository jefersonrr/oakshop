<%-- 
    Document   : iniciosesion
    Created on : 27/11/2021, 5:57:38 p. m.
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
         <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" +     
        request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Oakshop</title>

        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap" rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">


        <!--Normallize css: proyecto que corrige estilos predeterminados de los diferentes navegadores, para evitar usar el selector universal
    en la hoja de estilos CSS. -->
        <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
        
        <!-- Iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
        <!--Importar CSS -->
        <link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css"/>
        <!--Importar CSS -->
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/iniciarsesion.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/footer.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>
        

    </head>
    <body onload="sesion('<%=request.getSession().getAttribute("usuario")%>')">
        <!--menú -->
        <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
            <div class="container-fluid">

                <a class="navbar-brand" href="<%=basePath%>/index.jsp">
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
                                <a class="nav-link" href="#">INICIAR SESIÓN</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">REGISTRARSE</a>
                            </li>
                        </ul>
                    </template>
                    <!-- Usuario logueado
                    <template id="SiSesion">
                        <ul class="navbar-nav ml-auto m-4">
                            <li class="nav-item dropdown" style="list-style-type: none;">
                                <a  class="nav-link dropdown-toggle link-dark" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false" >
                    <%=request.getSession().getAttribute("nameUser")%>
                </a>
                <ul class="dropdown-menu text-small "aria-labelledby="dropdownUser2"  >
                    <li><a class="dropdown-item" href="#" >Mi Cuenta</a></li>
                    <li><a class="dropdown-item" href="./MisVehiculos.do" >Mis Vehiculos</a></li>
                    <li><a class="dropdown-item" href="#" >Mis Servicios</a></li>
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
                    -->
                </div>
            </div>
        </nav>
        <!--Fin menú -->

        <main>
            <div class="wrapper">
                <div class="encabezado">
                    <h1>Iniciar Sesión</h1>
                </div>
                <form action='<%=basePath%>IniciarSesion.do' method="POST" class="formulario" id="formulario">
                    <div class="field cedula">
                        <div class="input-area">
                            <input type="text" name="cedula" placeholder="Número de cédula">
                            <i class="icon fas fa-user"></i>
                            <i class="error error-icon fas fa-exclamation-circle"></i>
                        </div>
                        <div class="error error-txt">La cedula no puede estar en blanco</div>
                    </div>
                    <div class="field password">
                        <div class="input-area">
                            <input type="password" name="clave" placeholder="Contraseña">
                            <i class="icon fas fa-lock"></i>
                            <template id="errorClave" ><i class="error error-icon fas fa-exclamation-circle">Digito mal su clave</i></template>
                        </div>

                        <div class="error error-txt">La contraseña no puede estar en blanco</div>
                    </div>
                    <div class="pass-txt"><a href="#">Olvidaste tu contraseña?</a></div>
                    <input type="submit" value="Ingresar" >

                </form>
                <div class="sign-txt">¿No eres miembro? <a href="<%=basePath%>/jsp/registrarse.jsp">Registrate</a></div>
            </div>

        </main>

        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="statc" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-danger" id="Mymodal">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel" style="color:red">¡¡Error!!</h5>

                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class = "row">
                            Sus datos son incorrectos, por favor vuelva a digitarlos!!
                        </div>
                        <div class = "row" class = "display: flex; align-content: center;">
                            <svg xmlns="http://www.w3.org/2000/svg" width="80" height="80" style="color:#dc3545; " fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
                            <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                            </svg>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>


        <script>
            document.body.onload = function validarCampos() {
                var campo = '<%=request.getSession().getAttribute("mensaje")%>';
                if (campo === "err") {
                    var myModal = new bootstrap.Modal(document.getElementById('staticBackdrop'), {
                        keyboard: false,
                        focus: true
                    })
                    myModal.show();
                }
            }
        </script>
        
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


        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="<%=basePath%>js/sesion.js"></script>
    </body>
</html>
