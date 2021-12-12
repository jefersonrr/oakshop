
<%@page import="java.util.List"%>
<%@page import="DTO.Persona"%>
<%@page import="DAO.PersonaDAO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + path + "/";

        %>
        <base href="<%=basePath%>">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Askshop - Administración</title>

        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap" rel="stylesheet">

        <!-- Boxicons CDN Link -->
        <link href='https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"/>

        <!-- DataTable -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css">

        <!--Importar CSS y script del menú -->
        <link rel="stylesheet" href="<%=basePath%>css/admClientes.css" />
        <link rel="stylesheet" href="<%=basePath%>css/menuAdministrador.css" />
    </head>
    <body onload="validarSesion('<%=request.getSession().getAttribute("msg")%>')">

        <%

            String categorias = request.getSession().getAttribute("categorias").toString();
            String tipos = request.getSession().getAttribute("tipos").toString();
        %>
        <div class="sidebar">
            <div class="logo-details">
                <i class="fas fa-tire icon"></i> 
                <!-- Espacio entre mensaje Bienvenido-->
                <div class="logo_name">Bienvenido</div>
                <i class='bx bx-menu' id="btn" ></i>
            </div>

             <div class="sidebar">
            <div class="logo-details">
                <i class="fas fa-tire icon"></i> 
                <!-- Espacio entre mensaje Bienvenido-->
                <div class="logo_name">Bienvenido</div>
                <i class='bx bx-menu' id="btn" ></i>
            </div>

            <ul class="nav-list">
                <li>
                    <div class="image-admin">
                        <div class="container-img">
                            <img src="<%=basePath%>img/user-admin.png" alt="Administrador">
                        </div>
                        <div class="container-name">
                            <p><span class="links_name"><%=request.getSession().getAttribute("nameUser")%></span></p>
                        </div>
                    </div>
                </li>

                <li>
                    <a href="<%=basePath%>jsp/adminPublicaciones.jsp">
                        <i class="far fa-calendar-alt"></i>
                        <span class="links_name">Publicaciones</span>
                    </a>
                    <span class="tooltip">Publicaciones</span>
                </li>
                <li>
                    <a href="<%=basePath%>./jsp/adminClientes.jsp">
                        <i class="icon fas fa-user"></i>
                        <span class="links_name">Clientes</span>
                    </a>
                    <span class="tooltip">Clientes</span>
                </li>
                <li>
                    <a href="<%=basePath%>MostrarCategorias.do">
                        <i class="fas fa-user-cog"></i>
                        <span class="links_name">Categorias</span>
                    </a>
                    <span class="tooltip">Categorias</span>
                </li>
                   <li>
                    <a href="#">
                        <i class="fas fa-shopping-cart"></i>
                        <span class="links_name">Pedidos</span>
                    </a>
                    <span class="tooltip">Pedidos</span>
                </li>
                <li>
                    <a href="#">
                        <i class="fas fa-shopping-cart"></i>
                        <span class="links_name">Productos</span>
                    </a>
                    <span class="tooltip">Productos</span>
                </li>
                <!-- <li>
                  <a href="#">
                    <i class="fas fa-chart-pie"></i>
                    <span class="links_name">Reportes</span>
                  </a>
                  <span class="tooltip">Reportes</span>
                </li> -->
                <li class="profile">
                    <a href="<%=basePath%>cerrarSesion.do">
                        <i class='bx bx-log-out'></i>
                        <span class="links_name">Salir</span>
                    </a>
                </li>
            </ul>
        </div>
        </div>
        <div class="container-fluid">
            <div class="row  ">
                <div class="col-md-6">
                    <section class="home-section ">
                        <div class="title">            
                            <div class="titulo">
                                <h1>Categorías</h1>
                            </div>

                            <div class="boton">
                                <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#modal4">Añadir Categoria</button>
                            </div>
                        </div>




                        <div class="table-responsive table-style">
                            <table id="example" class="table table-bordered table-striped table-hover">
                                <thead class="table-secondary">
                                    <tr>
                                        <th class="enc" scope="col">Id Categoria</th>
                                        <th class="enc" scope="col">Nombre</th>
                                        <th class="enc" scope="col">Estado</th>
                                        <th class="enc" scope="col">Acciones</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    <%=categorias%>

                                </tbody>
                            </table>
                        </div>


                    </section>
                </div>
                <div class="col-md-6  ">
                    <section class="home-section ">
                        <div class="title">            
                            <div class="titulo">
                                <h1>Tipos</h1>
                            </div>

                            <div class="boton">
                                <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#modal5">Añadir Tipo</button>
                            </div>
                        </div>




                        <div class="table-responsive table-style">
                            <table id="example2" class="table table-bordered table-striped table-hover">
                                <thead class="table-secondary">
                                    <tr>
                                        <th class="enc" scope="col">Id Tipo</th>
                                        <th class="enc" scope="col">Nombre</th>
                                        <th class="enc" scope="col">Estado</th>
                                        <th class="enc" scope="col">Acciones</th>
                                    </tr>
                                </thead>

                                <tbody>

                                    <%=tipos%>


                                </tbody>
                            </table>
                        </div>


                    </section>
                </div>
            </div>
        </div>
        <!-- ventana modal Añadir cliente-->
        <div class="modal fade" tabindex="-1" role="dialog" id="modal4" aria-labelledby="modal1" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">
                        <h2 class="modal-title">Añadir Categoria</h2>
                    </div>
                    <div class="modal-body ">
                        <form action = "<%=basePath%>/AddCategoria.do" method="POST" >
                            <div class="row text-center m-3">
                                <div class="col-md-6">

                                    <div class="mb-3 ">
                                        <label for="exampleInputNombre" class="form-label">Nombre</label>
                                        <input type="text" class="form-control " id="recipient-name" name="nombre"  required>

                                    </div>


                                </div>




                            </div>
                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="boton3">Guardar</button>
                            </div>
                        </form>
                    </div>
                    <!--  <div class="modal-footer" id="foterM">
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       <a href="#" class="btn" id="boton" type="button">Calificar servicio</a>
                     </div> -->
                </div>
            </div>
        </div>


        <!-- ventana modal Añadir Tipo-->
        <div class="modal fade" tabindex="-1" role="dialog" id="modal5" aria-labelledby="modal1" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">
                        <h2 class="modal-title">Añadir Tipo</h2>
                    </div>
                    <div class="modal-body ">
                        <form action = "<%=basePath%>/AddTipo.do" method="POST" >
                            <div class="row text-center m-3">
                                <div class="col-md-6">

                                    <div class="mb-3 ">
                                        <label for="exampleInputNombre" class="form-label">Nombre</label>
                                        <input type="text" class="form-control " id="recipient-name" name="nombre"  required>

                                    </div>


                                </div>


                           


                            </div>
                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="boton3">Guardar</button>
                            </div>
                        </form>
                    </div>
                    <!--  <div class="modal-footer" id="foterM">
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       <a href="#" class="btn" id="boton" type="button">Calificar servicio</a>
                     </div> -->
                </div>
            </div>
        </div>

        <!-- ventana modal de editar Categoria -->
        <div class="modal fade" tabindex="-1" role="dialog" id="modal2" aria-labelledby="modal2example" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">

                        <h2 class="modal-title">Editar Categoria</h2>
                        <!--       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                              </button> -->
                    </div>
                    <div class="modal-body ">
                        <form action="<%=basePath%>UpdateCategoria.do" method="POST">
                            <div class="row text-center m-3">
                                <div class="col-md-6">

                                    <input style="display: none" type="text" name ="idCategoria" class="form-control " id="recipient-name" name="nombre"  required>
                                    <div class="mb-3 ">
                                        <label for="exampleInputNombre" class="form-label">Nombre</label>
                                        <input type="text" class="form-control " id="recipient-name" name="nombre"  required>

                                    </div>


                                </div>


                                <div class="col-md-6">


                                    <div class="mb-3">
                                        <label for="clase"  class="form-label">Estado</label>
                                        <select id="select1" name="estado" class="form-select" aria-label="Default select example">
                                            <option value="1">ACTIVO</option>
                                            <option value="2">INACTIVO</option>      
                                        </select>

                                    </div>

                                </div>


                            </div>
                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="boton3">Guardar</button>
                            </div>

                        </form>


                    </div>
                    <!--  <div class="modal-footer" id="foterM">
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       <a href="#" class="btn" id="boton" type="button">Calificar servicio</a>
                     </div> -->
                </div>
            </div>
        </div>


        <!-- ventana modal de editar Tipo -->
        <div class="modal fade" tabindex="-1" role="dialog" id="modal3" aria-labelledby="modal2example" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header justify-content-center align-items-center">

                        <h2 class="modal-title">Editar Tipo</h2>
                        <!--       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
                              </button> -->
                    </div>
                    <div class="modal-body ">
                        <form action="<%=basePath%>UpdateTipo.do" method="POST">
                            <div class="row text-center m-3">
                                <div class="col-md-6">
                                    <input  style="display: none" type="text" name ="idTipo" class="form-control " id="recipient-namategoriae" name="nombre"  required>
                                    <div class="mb-3 ">
                                        <label for="exampleInputNombre" class="form-label">Nombre</label>
                                        <input type="text" class="form-control " id="recipient-name" name="nombre"  required>

                                    </div>


                                </div>


                                <div class="col-md-6">


                                    <div class="mb-3">
                                        <label for="clase"  class="form-label">Estado</label>
                                        <select id="select2" name="estado" class="form-select" aria-label="Default select example">
                                            <option value="1">ACTIVO</option>
                                            <option value="2">INACTIVO</option>      
                                        </select>

                                    </div>

                                </div>


                            </div>
                            <div class="modal-footer mt-2 " id="foterM">
                                <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                                <button type="submit" class="boton3">Guardar</button>
                            </div>

                        </form>


                    </div>
                    <!--  <div class="modal-footer" id="foterM">
                         <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                       <a href="#" class="btn" id="boton" type="button">Calificar servicio</a>
                     </div> -->
                </div>
            </div>
        </div>

        <script src="<%=basePath%>js/menuAdministrador.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>


        <script>
        $(document).ready(function () {
            $('#example').DataTable({

                "language": {
                    "lengthMenu": "Mostrar_MENU_registros",
                    "zeroRecords": "No se encontraron resultados",
                    "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "infoFiltered": "(Filtrado de un total de _MAX_ registros)",
                    "sSearch": "Buscar:",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "sProcessing": "Procesando...",
                }

            }
            );
        });

        $(document).ready(function () {
            $('#example2').DataTable({

                "language": {
                    "lengthMenu": "Mostrar_MENU_registros",
                    "zeroRecords": "No se encontraron resultados",
                    "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                    "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                    "infoFiltered": "(Filtrado de un total de _MAX_ registros)",
                    "sSearch": "Buscar:",
                    "oPaginate": {
                        "sFirst": "Primero",
                        "sLast": "Último",
                        "sNext": "Siguiente",
                        "sPrevious": "Anterior"
                    },
                    "sProcessing": "Procesando...",
                }

            }
            );
        });

        var modalEditarCategoria = document.getElementById('modal2');
        modalEditarCategoria.addEventListener('show.bs.modal', (e) => {
            var btn = e.relatedTarget.valueOf().parentNode;
            li = btn.parentNode;
            li = li.parentNode
            li = li.parentNode

            datos = li.querySelectorAll("td");
            console.log(datos);
            modalBodyInput = modalEditarCategoria.querySelector('.modal-body').querySelectorAll('input');
            modalBodyOptionCategoria = modalEditarCategoria.querySelector('select').querySelectorAll('option');
            modalBodyInput[0].value = datos[0].innerHTML;//id
            modalBodyInput[1].value = datos[1].innerHTML;//nombre


            var i = 0;
            for (i = 0; i < modalBodyOptionCategoria.length; i++) {


                if (modalBodyOptionCategoria[i].innerHTML === datos[2].innerHTML) {
                    modalBodyOptionCategoria[i].setAttribute("selected", "");


                } else {
                    modalBodyOptionCategoria[i].removeAttribute("selected");

                }

            }
        });

        var modalEditarTipo = document.getElementById('modal3');
        modalEditarTipo.addEventListener('show.bs.modal', (e) => {
            var btn = e.relatedTarget.valueOf().parentNode;
            li = btn.parentNode;
            li = li.parentNode
            li = li.parentNode

            datos = li.querySelectorAll("td");
            console.log(datos);
            modalBody = modalEditarTipo.querySelector('.modal-body').querySelectorAll('input');
            modalBodyOptionTipo = modalEditarTipo.querySelector('select').querySelectorAll('option');
            modalBody[0].value = datos[0].innerHTML;//id
            modalBody[1].value = datos[1].innerHTML;//nombre



            var i = 0;
            for (i = 0; i < modalBodyOptionCategoria.length; i++) {


                if (modalBodyOptionTipo[i].innerHTML === datos[2].innerHTML) {
                    modalBodyOptionTipo[i].setAttribute("selected", "");


                } else {
                    modalBodyOptionTipo[i].removeAttribute("selected");

                }

            }

        });


        </script>

    </body>
</html>