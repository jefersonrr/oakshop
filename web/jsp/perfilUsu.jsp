
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
        <link rel="stylesheet" href="<%=basePath%>css/perfilUsu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/footer.css"/>
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
                            <li><a class="dropdown-item" href="<%=basePath%>PublicacionesCategoria.do">VESTIDOS</a></li>
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
                <!-- Usuario logueado-->
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
    <!--Fin menú -->

    <main>
        <div class="container-fluid">
            <div class="row header-cliente">
                <div class="col h-c">
                    <div class="imagen-cliente">
                        <img src="https://i.postimg.cc/9Qys23M0/585e4bf3cb11b227491c339a.png" alt="">
                    </div>
                    <div class="nombre-cliente">
                        <h5>Hola! <span>Nombre usuario</span></h5>
                    </div>
                </div>
            </div>

            <div class="body-cliente">
                <div class="row mis-datos">
                    <div class="col-md-2"></div>
                    <div class="col-md-8 t-datos bg-primary">
                        <h3>Mis datos</h3>
                    </div>
                    <div class="col-md-2"></div>
                </div>

                <div class="row f-datos" id="form-datos">

                    <div class="col-md-3"></div>
                    <div class="col-md-3 datos">
                        <label for="nombre" class="form-label">Nombres</label>
                        <h6 id="datos">Johan Sebastian</h6>
                    </div>
                    <div class="col-md-3 datos">
                        <label for="apellido" class="form-label">Apellidos</label>
                        <h6 id="datos">Casadiegos Gomez</h6>
                    </div>
                    <div class="col-md-3"></div>

                    <div class="col-md-3"></div>
                    <div class="col-md-3 datos">
                        <label for="cedula" class="form-label">Cedula</label>
                        <h6 id="datos">1191892619</h6>
                    </div>
                    <div class="col-md-3 datos">
                        <label for="telefono" class="form-label">Telefono</label>
                        <h6 id="datos">3204827926</h6>
                    </div>
                    <div class="col-md-3"></div>

                    <div class="col-md-3"></div>
                    <div class="col-md-3 datos">
                        <label for="correo" class="form-label">Correo electrónico</label>
                        <h6 id="datos">scasadiego4@gmail.com</h6>
                    </div>
                    <div class="col-md-3 datos">
                        <label for="direccion" class="form-label">Dirección residencia</label>
                        <h6 id="datos">Cll 22#13-43 lopez</h6>
                    </div>
                    <div class="col-md-3"></div>

                </div>

                <div class="row m-t">
                    <div class="col boton-act">
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editarDatModal">
                            Actualizar
                        </button>
                    </div>
                </div>

                <!-- ************************************************ -->
                
                <div class="row mis-datos">
                    <div class="col-md-2"></div>
                    <div class="col-md-8 t-datos bg-primary">
                        <h3>Mis tarjetas</h3>
                    </div>
                    <div class="col-md-2"></div>
                </div>

                <div class="row">
                    <div class="col-md-3"></div>
                    <div class="col-md-3">
                        <h6>Tarjeta termina en <span>***456</span></h6>
                    </div>
                    <div class="col-md-3 botones">
                        <button class="btn btn-primary">
                            Editar
                        </button>
                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#eliminarTarModal">
                            Eliminar
                        </button>
                    </div>
                    <div class="col-md-3"></div>

                    <div class="col-md-3"></div>
                    <div class="col-md-3">
                        <h6>Tarjeta termina en <span>***456</span></h6>
                    </div>
                    <div class="col-md-3 botones">
                        <button class="btn btn-primary">
                            Editar
                        </button>
                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#eliminarTarModal">
                            Eliminar
                        </button>
                    </div>
                    <div class="col-md-3"></div>
                </div>

                <div class="row m-t">
                    <div class="col boton-act">
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addTarModal">
                            Agregar
                        </button>
                    </div>
                </div>
                
                <!-- ************************************************ -->
                
                <div class="row mis-datos">
                    <div class="col-md-2"></div>
                    <div class="col-md-8 t-datos bg-primary">
                        <h3>Mis direcciones</h3>
                    </div>
                    <div class="col-md-2"></div>
                </div>

                <div class="row">
                    <div class="col-md-3"></div>
                    <div class="col-md-3">
                        <h6>Calle 22 #14-43 Alfonso lopez</h6>
                    </div>
                    <div class="col-md-3 botones">
                        <button class="btn btn-primary">
                            Editar
                        </button>
                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#eliminarDirModal">
                            Eliminar
                        </button>
                    </div>
                    <div class="col-md-3"></div>

                    <div class="col-md-3"></div>
                    <div class="col-md-3">
                        <h6>Calle 22 #14-43 Alfonso lopez</h6>
                    </div>
                    <div class="col-md-3 botones">
                        <button class="btn btn-primary">
                            Editar
                        </button>
                        <button class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#eliminarDirModal">
                            Eliminar
                        </button>
                    </div>
                    <div class="col-md-3"></div>
                </div>

                <div class="row m-t">
                    <div class="col boton-act">
                        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addDomModal">
                            Agregar
                        </button>
                    </div>
                </div>
                
            </div>
        </div>

        <!-- ventana modal de editar -->
        <div class="modal fade" tabindex="-1" role="dialog" id="editarDatModal" aria-labelledby="editarDatModal" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">

                        <h2 class="modal-title">Editar datos</h2>

                    </div>
                    <div class="modal-body ">

                        <form action="#" method="post">
                            <div class="row text-center m-3">

                                <div class="col-md-6">

                                    <div class="mb-3 ">
                                        <label for="exampleInputNombre" class="form-label">Nombre</label>
                                        <input type="text" class="form-control " id="exampleInputNombre" value="Johan Sebastian" required>
                                    </div>

                                </div>

                                
                                <div class="col-md-6">

                                    <div class="mb-3 ">
                                        <label for="exampleInputApellido" class="form-label">Apellido</label>
                                        <input type="text" class="form-control " id="exampleInputNombre" value="Casadiegos Gomez" required>
                                    </div>

                                </div>
                    

                                <div class="col-md-6">

                                    <div class="mb-3">
                                        <label for="exampleInputCed" class="form-label">Cédula</label>
                                        <input type="number" class="form-control" id="exampleInputCed" value="1005879654" required>
                                    </div>

                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="exampleInputTel" class="form-label">Telefono</label>
                                        <input type="number" class="form-control" id="exampleInputCel" value="3152546875" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <label for="exampleInputEmail" class="form-label">Correo electrónico</label>
                                        <input type="email" class="form-control" id="exampleInputEmail" value="ricar@gmail.com" required>
                                    </div>
                                </div>

                                <div class="col-md-6">

                                    <div class="mb-3">
                                        <label for="exampleInputDirec" class="form-label">Dirección</label>
                                        <input type="text" class="form-control" id="exampleInputDirec" value="Calle 8 9-9194545656" required>
                                    </div>

                                </div>
                                
                                 <div class="col-md-6">

                                    <div class="mb-3">
                                        <label for="exampleInputPass" class="form-label">Contraseña</label>
                                         <input type="password" class="form-control" id="exampleInputPass" value="juanito757" required>
                                    </div>

                                </div>

                            </div>

                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>
        </div>

        <!-- ventana agregar tarjeta -->
        <div class="modal fade" tabindex="-1" role="dialog" id="addTarModal" aria-labelledby="addTarModal" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">

                        <h2 class="modal-title">Agregar tarjeta</h2>

                    </div>
                    <div class="modal-body ">

                        <form action="#" method="post">
                            <div class="row text-center m-3">

                                <div class="col">
                                    <input type="radio" name="tarjetas" class="inp_radio" id="inp_cred" required>
                                    <label for="inp_cred" class="form-label">Tarjeta de crédito</label>
                                </div>

                                <div class="col">
                                    <input type="radio" name="tarjetas" class="inp_radio" id="inp_deb" required>
                                    <label for="inp_deb" class="form-label">Tarjeta de débito</label>
                                </div>

                            </div>

                                <div class="row text-center m-3">
                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <input class="form-control form-control-lg border-bottom w-100" type="number" placeholder="Número de tarjeta" aria-label="default input example" required>
                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Nombres y apellidos" aria-label="default input example" required>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <!-- <label for="fecha_exp" class="form-label">Fecha de expiración</label>
                                            <input class="form-control form-control-lg border-bottom w-100" type="date" id="fecha_exp" aria-label="default input example" required>
                                         -->
                                         <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Fecha de expiración" aria-label="default input example" required>
                                        </div>
                                    </div>
                                
                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Codigo de seguridad" aria-label="default input example" required>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="mb-3">
                                        <input class="form-control form-control-lg border-bottom w-100" type="number" placeholder="Cedula" aria-label="default input example" required>
                                    </div>
                                </div>

                            </div>              

                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>
        </div>

        <!-- ventana agregar domicilio -->
        <div class="modal fade" tabindex="-1" role="dialog" id="addDomModal" aria-labelledby="addDomModal" aria-hidden="true">
            <div class="modal-dialog modal-lg modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">

                        <h2 class="modal-title">Agregar direccion residencia</h2>

                    </div>
                    <div class="modal-body ">

                        <form action="#" method="post">

                            <div class="row text-center m-3">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Departamento" aria-label="default input example" required>
                                        </div>
                                    </div>

                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Ciudad" aria-label="default input example" required>
                                        </div>
                                    </div>

                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <input class="form-control form-control-lg border-bottom w-100" type="text" placeholder="Dirección" aria-label="default input example" required>
                                        </div>
                                    </div>                            

                            </div>              

                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="btn btn-primary">Guardar</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>
        </div>

  <!-- Modal eliminar tarjetas -->
  <div class="modal fade" id="eliminarTarModal" tabindex="-1" aria-labelledby="eliminarTarModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title" id="exampleModalLabel">Eliminar tarjeta</h3>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <h5>¿Estás seguro de eliminar esta tarjeta?</h5>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
          <button type="button" class="btn btn-primary">Aceptar</button>
        </div>
      </div>
    </div>
  </div>

    <!-- Modal direcciones -->
    <div class="modal fade" id="eliminarDirModal" tabindex="-1" aria-labelledby="eliminarDirModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title" id="exampleModalLabel">Eliminar dirección</h3>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <h5>¿Estás seguro de eliminar esta dirección de residencia?</h5>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancelar</button>
              <button type="button" class="btn btn-primary">Aceptar</button>
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
    <script src="./js/sesion.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
