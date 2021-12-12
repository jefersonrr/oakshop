

<%@page import="Negocio.askshop"%>
<%@page import="java.util.List"%>
<%@page import="DTO.Publicacion"%>
<%@page import="DAO.PublicacionDAO"%>
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
        <title>Catalogo de prendas de vestir y calzado - Administración</title>

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
        <section class="home-section">
            <div class="title">            
                <div class="titulo">
                    <h1>Lista de Publicaciones</h1>
                </div>

                <div class="boton">
                    <form action="AgregarPublicacion.do">
                    <button type="submit" class="btn btn-primary btn-lg" >Añadir Publicacion</button>
                    </form>
                </div>
            </div>

            <%
                PublicacionDAO p = new PublicacionDAO();
                List<Publicacion> lista = p.read();
                if (lista.isEmpty()) {%>
            <div class = "container-fluid" style="display: flex; align-content: center; align-items: center;justify-content: center">
                <h1 style="color:#ff0000" align="center">En estos momentos no existen publicaciones</h1>
            </div>
            <%} else {%>
            <div class="table-responsive table-style">
                <table id="example" class="table table-bordered table-striped table-hover">
                    <thead class="table-secondary">
                        <tr>
                            <th class="enc" scope="col">ID</th>
                            <th class="enc" scope="col">Nombre</th>
                            <th class="enc" scope="col">Marca</th>
                            <th class="enc" scope="col">Fecha</th>
                            <th class="enc" scope="col">Categoria</th>
                            <th class="enc" scope="col">Tipo</th>
                            <th class="enc" scope="col">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%  askshop a = new askshop();
                            for (Publicacion pu : lista) {
                              if(pu.getEstado().equals("ACTIVO")){
                        %>
                        <tr>
                            <th class="enc" name="id" scope="row"><%=pu.getId()%></th>
                            
                            <td class="text-center"><%=pu.getNombre()%></td>
                            <td class="text-center"><%=pu.getMarca()%></td>
                            <td class="text-center"><%=a.getFecha(pu.getFecha())%></td>
                            <td class="text-center"><%=pu.getIdCategoria().getNombre()%></td>
                            <td class="text-center"><%=pu.getIdTipo().getNombre()%></td>
                            <!-- Acciones: editar y cancelar. -->
                            <td>
                                <div class="icons-acciones">
                                    <div>
                                        <form action="EditarPublicacion.do">
                                            <input hidden name="editar" value="<%=pu.getId()%>"/>
                                            <button class="fas fa-edit" type="submit"></button>
                                        </form>
                                    </div>
                                    <div>
                                        <form>
                                            <i class="fas fa-trash-alt" onclick="eliminar('<%=pu.getId()%>')" data-bs-toggle="modal" data-bs-target="#modal3"></i>
                                        </form>
                                    </div>      
                                </div>
                                </div>
                            </td>
                        </tr>
                        <%
                            }}%>
                    </tbody>
                </table>
            </div>
            <%}%>

        </section>

       

        <!-- Modal para el botón eliminar-->
        <div class="modal fade" id="modal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog ">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminar Publicacion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        ¿Estás seguro de eliminar la publicación? <br>
                        Al eliminar la publicacion se eliminaran todos los productos que pertenezcan a esta
                    </div>
                    <div class="modal-footer">
                        <form action="<%=basePath%>/EliminarPublicacion.do" name="formuEliminar">

                            <div class="mb-3" >

                                <input type="text" class="form-control " id="publicacion" name="publicId" style="display: none ">

                            </div>

                            <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                            <button type="sumbit" class="boton3">Eliminar</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>

                        <script>
                            function eliminar(id){
                                let campoModal = document.getElementById('publicacion');
                                campoModal.value = id;
                            }
                        </script>

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
           
        
        </script>

    </body>
</html>