<%-- 
    Document   : medioPago
    Created on : 5/12/2021, 02:15:52 AM
    Author     : Luis
--%>
<%@page import="DTO.Tipo"%>
<%@page import="DTO.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="DAO.CategoriaDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + path + "/";
        CategoriaDAO cadao = new CategoriaDAO();
        List<Categoria> ca = cadao.readActivo();
        String metodosCredito = request.getSession().getAttribute("metodoCredito").toString();
        String metodosDebito = request.getSession().getAttribute("metodoDebito").toString();
        String subtotal = request.getSession().getAttribute("subtotal").toString();
        String envio = request.getSession().getAttribute("envio").toString();
        String total = request.getSession().getAttribute("total").toString();

        if (request.getSession().getAttribute("esCarrito") != null) {
            request.getSession().setAttribute("esCarrito", request.getSession().getAttribute("esCarrito"));
        } else {
            request.getSession().setAttribute("idProdcuto", request.getSession().getAttribute("idProducto"));
        }

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
                                    <li><a class="dropdown-item" href="EditarPerfil.do" >Mi Cuenta</a></li>
                                    <li><a class="dropdown-item" href="IrAcarrito.do" >Carrito</a></li>
                                    <li><a class="dropdown-item" href="<%=basePath%>MostrarCompras.do" >Mis Compras</a></li>
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


        <div class="row">
            <div class="col start-title">
                <div class="text-center start-text p-2">
                    Askshop
                </div>
            </div>
        </div>

        <form action="<%=basePath%>/RedireccionDomicilioMetodo.do" method="post">
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
                <div class="contenedor-inicial mt-5 p-3 me-1">
                    <div class="contenedor">
                        <div class="w-100">
                            <div class="accordion" id="accordionExample">
                                <div class="accordion-item">
                                    <h2 class="accordion-header " id="headingOne">
                                        <button class="accordion-button collapsed elemento-data texto-contenido" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                            Tarjeta Credito
                                        </button>
                                    </h2>
                                    <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                        <div class="accordion-body elemento-data">

                                            <div class="form-check">
                                                <input class="form-check-input" value = "-1" type="radio" name="credi" id="flexRadioDefault1">
                                                <label class="form-check-label" for="flexRadioDefault1">
                                                    Nueva Tarjeta
                                                </label>
                                            </div>
                                            <%=metodosCredito%>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="accordion" id="accordionExample">
                                <div class="accordion-item">
                                    <h2 class="accordion-header " id="headingTwo">
                                        <button class="accordion-button collapsed elemento-data texto-contenido" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                            Tarjeta Debito
                                        </button>
                                    </h2>
                                    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
                                        <div class="accordion-body elemento-data">

                                            <div class="form-check">
                                                <input class="form-check-input"  value = "-1" type="radio" name="debi" id="flexRadioDefault1">
                                                <label class="form-check-label" for="flexRadioDefault1">
                                                    Nueva Tarjeta
                                                </label>
                                            </div>
                                            <%=metodosDebito%>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div> 

                    <div class="row mt-4 mod-pos btn-2">
                        <div class="col">
                            <div class="d-flex justify-content-end">
                                <button  type="submit" class="btn-continue-size btn btn-info text-white">Continuar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="contenedor-confirmar">

                    <div class="mt-5">
                        <div class="titulo-compra mt-4">
                            <div class="text-center">
                                Resumen Compra
                            </div>
                        </div>

                        <div class="border">
                            <div class="mx-3 mt-4">
                                <label class="bold my-1">Subtotal $: </label>
                                <input class="form-control border text-center" type="number" name="subtotal"  value="<%=subtotal%>" readonly="false" aria-label="default input example">    
                            </div>
                            <div class="mx-3">
                                <label class="bold my-1">Precio envio $: </label>
                                <input class="form-control border text-center" type="number" name="envio"  value="<%=envio%>" readonly="false" aria-label="default input example">
                            </div>
                            <div class="mx-3 mb-4">
                                <label class="bold my-1">Total a pagar $:  </label>
                                <input class="form-control border text-center" type="number"name="total"  value="<%=total%>"readonly="false" aria-label="default input example">
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
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script>

            <%
                String correcto = "No";
                if (request.getSession().getAttribute("correcto") != null) {

                    correcto = request.getSession().getAttribute("correcto").toString();
                    request.getSession().setAttribute("correcto", null);
                }
            %>
if (<%=correcto.equals("si")%>) {
swal("Excelente!", "Tarjeta Agregada Correctamente!", "success");

}
        </script>
        <!-- JS de Bootstrap -->      
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="<%=basePath%>./js/sesion.js"></script>
    </body>
</html>

