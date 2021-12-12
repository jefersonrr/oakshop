<%-- 
    Document   : editarProductoPublicacion
    Created on : 4/12/2021, 10:55:47 AM
    Author     : Cristian
--%>

<%@page import="DTO.Galeriaimg"%>
<%@page import="DAO.GaleriaimgDAO"%>
<%@page import="DTO.Producto"%>
<%@page import="java.util.List"%>
<%@page import="DTO.Publicacion"%>
<%@page import="DAO.PublicacionDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
      <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + path + "/";

        %>
        <base href="<%=basePath%>">
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Catalogo de prendas de vestir y calzado - Administración</title>

    <!-- Fuente de google: Open Sans - Regular 400 -->
    <link
      href="https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap"
      rel="stylesheet"
    />

    <!-- Boxicons CDN Link -->
    <link
      href="https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
    />

    <!-- DataTable -->
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/5.0.1/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdn.datatables.net/1.11.3/css/dataTables.bootstrap5.min.css"
    />

    <!--Importar CSS y script del menú -->
     <link rel="stylesheet" href="<%=basePath%>css/admClientes.css" />
     <link rel="stylesheet" href="<%=basePath%>css/menuAdministrador.css" />
  </head>
  <body>
    <div class="sidebar">
      <div class="logo-details">
        <i class="fas fa-tire icon"></i>
        <!-- Espacio entre mensaje Bienvenido-->
        <div class="logo_name">Bienvenido</div>
        <i class="bx bx-menu" id="btn"></i>
      </div>

      <ul class="nav-list">
        <li>
          <div class="image-admin">
            <div class="container-img">
              <img
                src="https://i.postimg.cc/50xpzL3N/user-admin.png"
                alt="Administrador"
              />
            </div>
            <div class="container-name">
              <p><span class="links_name">n</span></p>
            </div>
          </div>
        </li>

        <li>
          <a href="CitasAdmin.do">
            <i class="far fa-calendar-alt"></i>
            <span class="links_name">Publicaciones</span>
          </a>
          <span class="tooltip">Publicaciones</span>
        </li>
        <li>
          <a href="/jsp/adminClientes.jsp">
            <i class="icon fas fa-user"></i>
            <span class="links_name">Clientes</span>
          </a>
          <span class="tooltip">Clientes</span>
        </li>
        <li>
          <a href="MostrarServiciosAdmin.do">
            <i class="fas fa-user-cog"></i>
            <span class="links_name">Servicios</span>
          </a>
          <span class="tooltip">Servicios</span>
        </li>
        <li>
          <a href="MostrarProductosAdmin.do">
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
          <a href="cerrarSesion.do">
            <i class="bx bx-log-out"></i>
            <span class="links_name">Salir</span>
          </a>
        </li>
      </ul>
    </div>
      <section class="home-section">
            <div class="title">            
                <div class="titulo">
                    <h1>Productos de la publicacion</h1>
                </div>

                <div class="boton">
                    <form action="<%=basePath%>jsp/agregarProducto.jsp">
                    <button type="submit" class="btn btn-primary btn-lg" >Agregar producto</button>
                    </form>
                </div>
            </div>

            <%
               PublicacionDAO p = new PublicacionDAO();
               Publicacion pu = p.readPublicacion(Integer.parseInt(request.getSession().getAttribute("editar").toString()));
               List<Producto> productos = pu.getProductoList();
                if (productos.isEmpty()) {%>
            <div class = "container-fluid" style="display: flex; align-content: center; align-items: center;justify-content: center">
                <h1 style="color:#ff0000" align="center">En estos momentos no existen publicaciones</h1>
            </div>
            <%} else {%>
            <div class="table-responsive table-style">
                <table id="example" class="table table-bordered table-striped table-hover">
                    <thead class="table-secondary">
                        <tr>
                            <th class="enc" scope="col">ID</th>
                            <th class="enc" scope="col">Referencia</th>
                            <th class="enc" scope="col">Costo</th>
                            <th class="enc" scope="col">Descuento</th>
                            <th class="enc" scope="col">Color</th>
                            <th class="enc" scope="col">Talla</th>
                            <th class="enc" scope="col">Cantidad</th>
                            <th class="enc" scope="col">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <%  
                             List<Galeriaimg> ima = pu.getGaleriaimgList();
                             for(Producto pro: productos){
                                 if(pro.getEstado().equals("ACTIVO")){
                        %>
                        <tr>
                            <th class="enc" name="id" scope="row"><%=pro.getId()%></th>
                            
                            <td class="text-center"><%=pro.getReferencia() %></td>
                            <td class="text-center"><%=pro.getCosto() %></td>
                            <td class="text-center"><%=pro.getDescuento() %></td>
                            <td class="text-center" value="<%=pro.getIdColor().getId()%>"><%=pro.getIdColor().getNombre() %></td>
                            <td class="text-center" value="<%=pro.getIdTalla().getId()%>"><%=pro.getIdTalla().getValor() %></td>
                            
                            <td class="text-center"><%=pro.getCantidad() %></td>
                            <!-- Acciones: editar y cancelar. -->
                            <td>
                                <div class="icons-acciones">
                                    <div>
                                        <button type="button" style="background: transparent" class="fas fa-edit" data-bs-toggle="modal" data-bs-target="#modal2" data-bs-whatever = "<%=pro.getId()  %>"></button>
                                    </div>
                                    <div>
                                        <form>
                                            <i class="fas fa-trash-alt" onclick="eliminar('<%=pro.getId()%>')" data-bs-toggle="modal" data-bs-target="#modal3"></i>
                                        </form>
                                    </div>      
                                </div>
                                </div>
                            </td>
                        </tr>
                        <%
                            }
}%>
                    </tbody>
                </table>
            </div>
            <%}%>

        </section>
   
    
    <!-- ventana modal de editar -->
    <div
      class="modal fade"
      tabindex="-1"
      role="dialog"
      id="modal2"
      aria-labelledby="modal2example"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header justify-content-center align-items-center">
            <h2 class="modal-title">Editar Producto</h2>
          </div>
          <div class="modal-body">
            <form action="ActualizarProductoPublicacion.do">
              <div class="row text-center m-3">
                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputNombre" class="form-label"
                      >Referencia</label
                    >
                    <input
                      type="text"
                      class="form-control"
                      id="recipient-name"
                      name="referencia"
                      required
                    />
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputCed" class="form-label"
                      >Costo</label
                    >
                    <input
                      type="number"
                      class="form-control"
                      id="exampleInputCed"
                      name="costo"
                    />
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputEmail" class="form-label"
                      >Descuento</label
                    >
                    <input
                      type="number"
                      class="form-control"
                      id="exampleInputEmail"
                      name="descuento"
                      required
                    />
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputCel" class="form-label"
                      >Color</label
                    >
                    <select
                          class="custom-select"
                          style="height: 40px; width: 150px"
                          name="color"
                        >
                            <option selected value=""></option>
                          <%=request.getSession().getAttribute("colores").toString()%>
                        </select>
                    
                    <!--<input
                      type="number"
                      class="form-control"
                      id="exampleInputCel"
                      name="celular"
                      required
                    />-->
                  </div>
                </div>

                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputDirec" class="form-label"
                      >Talla</label
                    >
                    <select
                          class="custom-select"
                          style="height: 40px; width: 150px"
                          name="talla"
                        >
                          <option selected value="" ></option>
                          <%=request.getSession().getAttribute("tallas").toString()%>
                        </select>
                    <!--<input
                      type="text"
                      class="form-control"
                      id="exampleInputDirec"
                      name="direccion"
                      required
                    />-->
                  </div>
                </div>
                
                <div class="col-md-6">
                  <div class="mb-3">
                    <label for="exampleInputPass" class="form-label"
                      >Cantidad</label
                    >
                    <input
                      type="number"
                      class="form-control"
                      id="exampleInputPass"
                      name="cantidad"
                      required
                    />
                  </div>
                </div>
                <input hidden name="idProducto" />
              </div>
              <div class="modal-footer mt-2" id="foterM">
                <button type="button" class="boton2" data-bs-dismiss="modal">
                  Cancelar
                </button>
                <button type="submit" class="boton3">Guardar</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
                        
    <!-- Modal para el botón eliminar-->
        <div class="modal fade" id="modal3" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog ">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Eliminar Producto</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        ¿Estás seguro de eliminar el producto? <br>
                        Al eliminar el producto este cambiara su estado a inactivo
                    </div>
                    <div class="modal-footer">
                        <form action="<%=basePath%>/DesactivarProducto.do" name="formuEliminar">

                            <div class="mb-3" >

                                <input type="text" class="form-control " id="producto" name="proId" style="display: none ">

                            </div>

                            <button type="button" class="boton2" data-bs-dismiss="modal">Cancelar</button>
                            <button type="sumbit" class="boton3">Eliminar</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>

    <script src="../js/menuAdministrador.js"></script>
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
    <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
      crossorigin="anonymous"
    ></script>

    <script>
      $(document).ready(function () {
        $("#example").DataTable({
          language: {
            lengthMenu: "Mostrar_MENU_registros",
            zeroRecords: "No se encontraron resultados",
            info: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            infoEmpty:
              "Mostrando registros del 0 al 0 de un total de 0 registros",
            infoFiltered: "(Filtrado de un total de _MAX_ registros)",
            sSearch: "Buscar:",
            oPaginate: {
              sFirst: "Primero",
              sLast: "Último",
              sNext: "Siguiente",
              sPrevious: "Anterior",
            },
            sProcessing: "Procesando...",
          },
        });
      });

      var modalEditarCliente = document.getElementById("modal2");
      let selects = document.getElementsByTagName('select');
      modalEditarCliente.addEventListener("show.bs.modal", (e) => {
        var btn = e.relatedTarget.valueOf().parentNode;
        li = btn.parentNode;
        li = li.parentNode;
        li = li.parentNode;
        let id = li.querySelector("th").innerHTML;
        datos = li.querySelectorAll("td");
        console.log(datos);
        modalBodyInput = modalEditarCliente
          .querySelector(".modal-body")
          .querySelectorAll("input");
        modalBodyInput[0].value = datos[0].innerHTML; //referencia
        modalBodyInput[1].value = datos[1].innerHTML; //costo
        modalBodyInput[2].value = datos[2].innerHTML; //descuento
        modalBodyInput[3].value = datos[5].innerHTML; //cantidad
        modalBodyInput[4].value = id;
        selects[1].options[0].label = datos[3].innerHTML; //color
        selects[1].options[0].value = li.childNodes[9].getAttribute('value');//value
        selects[2].options[0].label = datos[4].innerHTML; //talla
        selects[2].options[0].value = li.childNodes[11].getAttribute('value');
        
      });
      
        function eliminar(id){
            let campoModal = document.getElementById('producto');
            campoModal.value = id;
        }
    </script>
  </body>
</html>

