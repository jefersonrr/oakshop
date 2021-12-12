<%-- 
    Document   : productos
    Created on : 4/12/2021, 10:42:19 a. m.
    Author     : Acer
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

        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        <!-- Iconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">

        <!-- CSS de Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- CSS restantes -->
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/productos.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/footer.css"/>

    </head>
    <body oncontextmenu='return false' class='snippet-body' onload="sesion('<%=request.getSession().getAttribute("usuario")%>')">
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
                                <a class="nav-link" href="<%=basePath%>iniciarSesion.do">INICIAR SESIÓN</a>
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

        <%
            String productos = request.getSession().getAttribute("productos").toString();
            String tipoName = request.getSession().getAttribute("tipo").toString();
            String tallas = request.getSession().getAttribute("selectTalla").toString();
            String sTalla = "";
            String sPrecio = "";
            if (request.getSession().getAttribute("sTalla") != null) {
                sTalla = request.getSession().getAttribute("sTalla").toString();
                request.getSession().removeAttribute("sTalla");
            }

            if (request.getSession().getAttribute("sPrecio") != null) {
                sPrecio = request.getSession().getAttribute("sPrecio").toString();
                request.getSession().removeAttribute("sPrecio");
            }
            


        %>

        <!-- Title Page -->
        <section class="bg-title-page p-t-50 p-b-40 flex-col-c-m">

            <h2 class="l-text2 t-center">
                Categoría <%=tipoName%> 
            </h2>
        </section>

        <!-- Content page -->
        <section class="bgwhite p-t-55 p-b-65">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6 col-md-4 col-lg-3 p-b-50">
                        <div class="leftbar p-r-20 p-r-0-sm">

                            <h4 class="m-text14 p-b-7">
                                Categorias
                            </h4>
                            <!--
                                                        <ul class="p-b-54">
                                                            <li class="p-t-4">
                                                                <a href="#" class="s-text13 active1">
                                                                    All
                                                                </a>
                                                            </li>
                            
                                                            <li class="p-t-4">
                                                                <a href="#" class="s-text13">
                                                                    Women
                                                                </a>
                                                            </li>
                            
                                                            <li class="p-t-4">
                                                                <a href="#" class="s-text13">
                                                                    Men
                                                                </a>
                                                            </li>
                            
                                                            <li class="p-t-4">
                                                                <a href="#" class="s-text13">
                                                                    Kids
                                                                </a>
                                                            </li>
                            
                                                            <li class="p-t-4">
                                                                <a href="#" class="s-text13">
                                                                    Accesories
                                                                </a>
                                                            </li>
                                                    </ul>
                            -->



                            <form action="<%=basePath%>PublicacionesCategoria.do" method="POST"> 
                                <input type="hidden" name="tipo" value="<%=request.getSession().getAttribute("tipoF")%>" />
                                <input type="hidden" name="cate" value="<%=request.getSession().getAttribute("cate")%>" />
                                <div class="row">
                                    <h4 class="m-text14 p-b-32 col-4">
                                        Filtrar 
                                    </h4>
                                    <div class="filter-color  p-b-32  col-8" >
                                        <input type="hidden" name="cate" value="<%=request.getSession().getAttribute("cate")%>" />
                                        <button type="submit" class="btn btn-primary align-self-center">TODO</button>
                                    </div>  
                                </div>
                            </form> 


                            <form action="<%=basePath%>/FiltrarPublicaciones.do" method="GET">  

                                <input type="hidden" name="cate" value="<%=request.getSession().getAttribute("cate")%>" />
                                <div class="filter-color p-t-22 p-b-50 bo3">
                                    <div class="m-text15 p-b-12">
                                        Talla
                                    </div>

                                    <div class="d-flex flex-column">
                                        <select class="select__1 align-self-center" id="sTalla" name="talla" required>
                                            <option value="default" selected disabled>Seleccione una talla</option>
                                            <%=tallas%>
                                        </select>
                                    </div>

                                </div>

                                <div class="filter-color p-t-22 p-b-50 bo3">
                                    <div class="m-text15 p-b-12">
                                        Color
                                    </div>


                                    <ul class="flex-w" id="colores">
                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter1" type="checkbox" value="Blanco" name="color">
                                            <label class="color-filter color-filter1" for="color-filter1"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter2" type="checkbox" value="Azul" name="color">
                                            <label class="color-filter color-filter2" for="color-filter2"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter3" type="checkbox" value="Rojo" name="color">
                                            <label class="color-filter color-filter3" for="color-filter3"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter4" type="checkbox" value="Negro" name="color">
                                            <label class="color-filter color-filter4" for="color-filter4"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter5" type="checkbox" value="Verde" name="color">
                                            <label class="color-filter color-filter5" for="color-filter5"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter6" type="checkbox" value="Amarillo" name="color">
                                            <label class="color-filter color-filter6" for="color-filter6"></label>
                                        </li>

                                        <li class="m-r-10">
                                            <input class="checkbox-color-filter" id="color-filter7" type="checkbox" value="Gris" name="color">
                                            <label class="color-filter color-filter7" for="color-filter7"></label>
                                        </li>
                                    </ul>

                                </div>


                                <div class="rs2-select2 bo4 of-hidden filter-color p-t-22 p-b-50 bo3 w-size12 m-t-5 m-b-5 m-r-10">
                                    <div class="m-text15 p-b-12">
                                        Precio
                                    </div>
                                    <select class="select__1" id="sPrecio" name="precio">
                                        <option value="default" selected disabled>Rango de precio</option>
                                        <option value="0-40" >$0.00 - $40.000</option>
                                        <option value="40-50" >$40.000 - $50.000</option>
                                        <option value="50-60" >$50.000 - $60.000</option>
                                        <option value="60-70" >$60.000 - $70.000</option>
                                        <option value="70-+">$70.000+</option>
                                    </select>
                                </div>


                                <div class="filter-color p-t-22 p-b-50 bo3 d-flex flex-column " >
                                    <button type="submit" class="btn btn-danger align-self-center ">FILTRAR</button>
                                </div>                       

                            </form> 



                        </div>
                    </div>

                    <div class="col-sm-6 col-md-8 col-lg-9 p-b-50">
                        <!--  -->
                        <div class="flex-sb-m flex-w p-b-35">
                            <div class="flex-w">
                                <!-- <div class="rs2-select2 bo4 of-hidden w-size12 m-t-5 m-b-5 m-r-10">
    
                                     <select class="select__1" name="ordenar" required>
                                         <option value="default" selected disabled>Por defecto</option>
                                         <option>Más vendidos</option>
                                         <option>Fecha de lanzamiento</option>
                                     </select>
                                 </div> -->

                                <!-- <div class="rs2-select2 bo4 of-hidden w-size12 m-t-5 m-b-5 m-r-10">
                                     <select class="select__1" name="precio">
                                         <option value="default" selected disabled>Rango de precio</option>
                                         <option>$0.00 - $40.000</option>
                                         <option>$40.000 - $50.000</option>
                                         <option>$50.000 - $60.000</option>
                                         <option>$60.000 - $70.000</option>
                                         <option>$70.000+</option>
                                     </select>
                                 </div>-->
                            </div>
                        </div>

                        <!-- Product -->
                        <div class="row">  
                            <!-- Block2 -->
                            <%=productos%>

                        </div>

                    </div>

                </div>
            </div>
        </div>
    </section>      
    <script>

        document.body.onload = function () {

            if (<%=!sTalla.equals("")%>) {

                OptionTalla = document.querySelector('#sTalla').querySelectorAll('option');
                var i;
                for (i = 0; i < OptionTalla.length; i++) {

                    if (OptionTalla[i].value === "<%=sTalla%>") {

                        OptionTalla[i].setAttribute("selected", "");
                        // request.getSession().removeAttribute("sTalla");
                        break;
                    }

                }

            }

            if (<%=!sPrecio.equals("")%>) {

                OptionPrecio = document.querySelector('#sPrecio').querySelectorAll('option');
                var i;
                for (i = 0; i < OptionPrecio.length; i++) {

                    if (OptionPrecio[i].value === "<%=sPrecio%>") {

                        OptionPrecio[i].setAttribute("selected", "");


                        break;
                    }

                }

            }





        };

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
    <!-- JS de Bootstrap -->      
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="<%=basePath%>js/sesion.js"></script>
</body>
</html>
