<%-- 
    Document   : agregarProducto
    Created on : 4/12/2021, 10:55:47 AM
    Author     : Cristian
--%>

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
    <body onload="agregarProducto()">
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
            <div class="col-md-10 container" role="document">
                <form action="CrearPublicacion.do" name="formulario">
                    <div id="productosCont" class="p-5">
                        <template id="tempProduct">
                            <div id="modelo" class="p-2">
                                <div class="row d-flex p-2">
                                    <div class="col-md-6"><h2>Producto</h2></div><div class="col-md-6 d-flex justify-content-end"><button class="btn btn-primary" id="eli" onclick="eliminarProducto()">Eliminar   <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash-fill" viewBox="0 0 16 16">
                                            <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                                            </svg></button></div></div>
                                <div class="row card p-4">
                                    <div class="row d-flex text-center">
                                        <div class="col-md-2"></div>
                                        <div class="col-md-3">
                                            <label for="exampleInputNombre" class="form-label"
                                                   >Referencia</label
                                            >
                                            <input
                                                type="text"
                                                class="form-control"
                                                id="exampleInputNombre"
                                                name="referencia"
                                                required
                                                />
                                        </div>
                                        <div class="col-md-3">
                                            <label for="exampleInputNombre" class="form-label"
                                                   >Costo</label
                                            >
                                            <input
                                                type="number"
                                                class="form-control"
                                                id="exampleInputNombre" min="0.0"
                                                name="costo"
                                                required
                                                />
                                        </div>
                                        <div class="col-md-3">
                                            <label for="exampleInputNombre" class="form-label"
                                                   >Descuento</label
                                            >
                                            <input
                                                type="number"
                                                class="form-control"
                                                id="exampleInputNombre"
                                                name="descuento" min="0.0"
                                                required
                                                />
                                        </div>
                                    </div>
                                    <br />
                                    <div class="row d-flex text-center">
                                        <div class="col-md-2"></div>
                                        <div class="col-md-3">
                                            <label for="exampleInputDuracion" class="form-label"
                                                   >Color</label
                                            ><br />
                                            <select
                                                class="custom-select"
                                                style="height: 40px; width: 150px"
                                                name="color"
                                                >
                                                <option selected>Color</option>
                                                <%=request.getSession().getAttribute("colores").toString()%>
                                            </select>
                                        </div>

                                        <div class="col-md-3">
                                            <label for="exampleInputDuracion" class="form-label"
                                                   >Talla</label
                                            ><br />
                                            <select
                                                class="custom-select"
                                                style="height: 40px; width: 150px"
                                                name="talla"
                                                >
                                                <option selected>Talla</option>
                                                <%=request.getSession().getAttribute("tallas").toString()%>
                                            </select>
                                        </div>
                                        <div class="col-md-3">
                                            <label for="exampleInputDuracion" class="form-label"
                                                   >Cantidad</label
                                            ><br />
                                            <input name="cantidad" type="number" min="0.0" class="form-control"/>

                                        </div>
                                    </div>
                                    <br>
                                    <div class="col-md-12 d-flex text-center justify-content-center">
                                        <div class="col-md-6">
                                            <label for="exampleDescripcion" class="form-label"
                                                   >Url Imagen</label
                                            >
                                            <input
                                                class="form-control"
                                                id="exampleInputDescripcion"
                                                type="text"
                                                name="imgUrl"
                                                required
                                                />
                                        </div>
                                    </div>
                                    <br />
                                </div>
                                <hr>
                            </div>

                        </template>
                    </div>
                    <hr />
                    <br />
                    <div class="row">
                        <div class="col-md-6">
                            <button class="btn btn-primary" onclick="agregarProducto()">
                                Agregar Producto
                            </button>
                        </div>
                        <div class="col-md-6 d-flex justify-content-end"> 
                            <button class="btn btn-primary" type="button" onclick="arreglos()">
                                Continuar
                            </button>
                        </div>
                        <input name="colores" value="" id="colores"hidden />
                        <input name="referencias" value="" id="referencias"hidden />
                        <input name="costos" value="" id="costos"hidden />
                        <input name="descuentos" value="" id="descuentos" hidden />
                        <input name="tallas" value="" id="tallas" hidden />
                        <input name="imgUrls" value="" id="imgUrls" hidden />
                        <input name="cantidades" value="" id="cantidades" hidden />
                    </div>
                </form>
                <br />
                <hr />
            </div>

        </section>
        <script>
            function arreglos() {

                let referencias = document.getElementsByName('referencia');
                let costos = document.getElementsByName('costo');
                let descuentos = document.getElementsByName('descuento');
                let colores = document.getElementsByName('color');
                let tallas = document.getElementsByName('talla');
                let img = document.getElementsByName('imgUrl');
                let cantidades = document.getElementsByName('cantidad');

                let referenciasF = document.getElementById('referencias');
                let costosF = document.getElementById('costos');
                let descuentosF = document.getElementById('descuentos');
                let coloresF = document.getElementById('colores');
                let tallasF = document.getElementById('tallas');
                let imgUrlF = document.getElementById('imgUrls');
                let cantidadesF = document.getElementById('cantidades');

                for (var i = 0; i < referencias.length; i++) {

                    referenciasF.value += referencias[i].value + ',';
                    costosF.value += costos[i].value + ',';
                    descuentosF.value += descuentos[i].value + ',';
                    coloresF.value += colores[i].value + ',';
                    tallasF.value += tallas[i].value + ',';
                    imgUrlF.value += img[i].value + ',';
                    cantidadesF.value += cantidades[i].value + ',';
                }
                document.formulario.submit();
            }

            function agregarProducto() {
                let padre = document.getElementById("productosCont");
                let temp = document.getElementById("tempProduct").content;
                let modelo = temp.querySelector('#modelo');
                let clone = modelo.cloneNode(true);
                let boton = clone.querySelector('#eli');

                boton.setAttribute('id', 'eli' + (padre.children.length));
                padre.appendChild(clone);
            }

            function eliminarProducto() {

                let id = window.event.target.id;
                let product = document.getElementById(id);
                let padre = document.getElementById("productosCont");
                padre.removeChild(product.parentNode.parentNode.parentNode);

            }
        </script>



        <script src="../js/menuAdministrador.js"></script>
        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.3/js/dataTables.bootstrap5.min.js"></script>
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"
        ></script>


    </body>
</html>

