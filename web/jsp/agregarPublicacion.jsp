<%-- 
    Document   : agregarPublicacion
    Created on : 3/12/2021, 08:30:32 PM
    Author     : Cristian
--%>

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
                            <img src="https://i.postimg.cc/50xpzL3N/user-admin.png" alt="Administrador">
                        </div>
                        <div class="container-name">
                            <p><span class="links_name"><%=request.getSession().getAttribute("nameUser")%></span></p>
                        </div>
                    </div>
                </li>

                <li>
                    <a href="<%=basePath%>CitasAdmin.do">
                        <i class="far fa-calendar-alt"></i>
                        <span class="links_name">Agendamientos</span>
                    </a>
                    <span class="tooltip">Agendamientos</span>
                </li>
                <li>
                    <a href="<%=basePath%>./jsp/adminClientes.jsp">
                        <i class="icon fas fa-user"></i>
                        <span class="links_name">Clientes</span>
                    </a>
                    <span class="tooltip">Clientes</span>
                </li>
                <li>
                    <a href="<%=basePath%>MostrarServiciosAdmin.do">
                        <i class="fas fa-user-cog"></i>
                        <span class="links_name">Servicios</span>
                    </a>
                    <span class="tooltip">Servicios</span>
                </li>
                <li>
                    <a href="<%=basePath%>MostrarProductosAdmin.do">
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
            <div class="col-md-8 container" role="document">
                <div class="">
                  <div class="modal-header justify-content-center align-items-center">
                    <h2 class="modal-title">Agregar Publicacion</h2>
                  </div>
                  <div class="modal-body">
                    <form action="AgregarProductos.do">
                      <div class="row d-flex text-center">
                        <div class="col-md-6">
                          <label for="exampleInputNombre" class="form-label"
                            >Nombre</label
                          >
                          <input
                            type="text"
                            class="form-control"
                            id="exampleInputNombre"
                            name="nombre"
                            required
                          />
                        </div>
                        <div class="col-md-6">
                          <label for="exampleInputNombre" class="form-label"
                            >Marca</label
                          >
                          <input
                            type="text"
                            class="form-control"
                            id="exampleInputNombre"
                            name="marca"
                            required
                          />
                        </div>
                      </div>
                      <br />
                      <br><br>
                      <div class="row d-flex text-center">
                        <div class="col-md-6">
                          <label for="exampleInputDuracion" class="form-label"
                            >Tipo</label
                          ><br />
                            
                                <select
                                class="custom-select"
                                style="height: 40px; width: 250px" name="tipo"
                                >
                                <option selected>Tipo</option>
                                <%=request.getSession().getAttribute("tipos").toString()%>
                            
                            
                          </select>
                        </div>
        
                        <div class="col-md-6">
                          <label for="exampleInputDuracion" class="form-label"
                            >Categoria</label
                          ><br />
                          <select
                            class="custom-select"
                            style="height: 40px; width: 250px" name="categoria"
                          >
                            <option selected>Categoria</option>
                            <%=request.getSession().getAttribute("categorias").toString()%>
                          </select>
                        </div>
                        
                      </div>
                      <br><br>
                      <div class="row d-flex text-center">
                      <div class="col-md-12">
                        <label for="exampleDescripcion" class="form-label"
                          >Descripción</label
                        ><br>
                        <textarea
                          class="form-control"
                          id="exampleInputDescripcion"
                          rows="3"
                          name="descripcion"
                          required
                        ></textarea>
                      </div>
                      </div>
                      <hr />
                     
                      <br />
                      <div class="d-flex justify-content-end">
                      <button class="btn btn-primary" type="submit">
                        Continuar
                      </button>
                        </div>
                      <br />
                      <hr />
                    </form>
                  </div>
                </div>
              </div>
        </section>
       



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
           
            var modalEditarCliente = document.getElementById('modal2');
            modalEditarCliente.addEventListener('show.bs.modal', (e) => {
                var btn = e.relatedTarget.valueOf().parentNode;
                li = btn.parentNode;
                li = li.parentNode;
                li = li.parentNode;
                datos = li.querySelectorAll("td");
                console.log(datos);
                modalBodyInput = modalEditarCliente.querySelector('.modal-body').querySelectorAll('input');
                modalBodyInput[0].value = datos[0].innerHTML;//nombre
                modalBodyInput[1].value = datos[1].innerHTML;//cc
                modalBodyInput[2].value = datos[3].innerHTML;//email
                modalBodyInput[3].value = datos[2].innerHTML;//celular
                modalBodyInput[4].value = datos[4].innerHTML;//direccion
                modalBodyInput[5].value = e.relatedTarget.getAttribute('data-bs-whatever');//clave

            });
        
        </script>

    </body>
</html>