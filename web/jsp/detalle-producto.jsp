<%-- 
    Document   : detalle-producto
    Created on : 4/12/2021, 11:56:01 p. m.
    Author     : Acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<html>
     <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                    + request.getServerPort() + path + "/";
            
                if(request.getSession().getAttribute("listaServiciosIndex")== null){
                  request.getRequestDispatcher(("MostrarRopaIndex.do")).forward(request, response);
                
                }
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
    
     <!-- CSS de Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" integrity="sha512-+4zCK9k+qNFUR5X+cKL9EIR+ZOhtIloNl9GIKS57V1MyNsYpYcUrUeQc9vNfzsWfV28IaLL3i96P9sdNyeRssA==" crossorigin="anonymous" />
        
        <!-- CSS restantes -->
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/detalle-producto.css"/>
        
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

        <div class = "card-wrapper">
      <div class = "card">
        <!-- card left -->
        <div class = "product-imgs">
          <div class = "img-display">
            <div class = "img-showcase">
              <img src = "<%=basePath%>img/single_1.jpg" alt = "shoe image">
              <img src = "<%=basePath%>img/single_2.jpg" alt = "shoe image">
              <img src = "<%=basePath%>img/single_3.jpg" alt = "shoe image">
              <img src = "<%=basePath%>img/single_1.jpg" alt = "shoe image">
            </div>
          </div>
          <div class = "img-select">
            <div class = "img-item">
              <a href = "#" data-id = "1">
                <img src = "<%=basePath%>img/single_1.jpg" alt = "shoe image">
              </a>
            </div>
            <div class = "img-item">
              <a href = "#" data-id = "2">
                <img src = "<%=basePath%>img/single_2.jpg" alt = "shoe image">
              </a>
            </div>
            <div class = "img-item">
              <a href = "#" data-id = "3">
                <img src = "<%=basePath%>img/single_3.jpg" alt = "shoe image">
              </a>
            </div>
            <div class = "img-item">
              <a href = "#" data-id = "4">
                <img src = "<%=basePath%>img/single_1.jpg" alt = "shoe image">
              </a>
            </div>
          </div>
        </div>
              
        <!-- card right -->
        <div class = "product-content">
          <h2 class = "product-title">Nombre producto</h2>
          <h3>REF: <span>5254</span></h3>
          
          <hr>

          <div class = "product-price">
            <p class = "last-price">Precio habitual: <span>$70.000</span></p>
            <p class = "new-price">Con descuento: <span>$50.000 (5%)</span></p>
            <p>Disponibles: <span>10</span></p>
          </div>
          
  
              <form action="action">
                  <div class="product-detail">
                   
                      <h4>Talla:</h4>
                      
                  <select class="select__1" name="tallas" required>
			<option>S</option>
			<option>M</option>
			<option>L</option>
                        <option>XL</option>
                  </select>
                      
                      <h4>Color:</h4>
                  
                  <select class="select__1" name="color" required>
			<option>Rojo</option>
			<option>Verde</option>
			<option>Azul</option>
                        <option>Negro</option>
                  </select>
                      
                      <h4>Cantidad</h4>
                      
                       <div class = "purchase-info">
                            <input type = "number" min = "0" value = "1">
                        </div>
                      
                      <div class="purchase-info2 d-flex justify-content-center">
                                 <button type = "submit" class = "btn">
                                 Añadir al carrito <i class = "fas fa-shopping-cart"></i>
                                 </button>
                               <button type = "submit" class = "btn">Comprar</button>
                      </div>
                      
                  </div>
              </form>
              
          <div class = "product-detail">
              <br>
            <h4>Descripción del producto </h4>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Illo eveniet veniam tempora fuga tenetur placeat sapiente architecto illum soluta consequuntur, aspernatur quidem at sequi ipsa!</p>
            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Consequatur, perferendis eius. Dignissimos, labore suscipit. Unde.</p>
          
          </div>

        </div>
      </div>
        
                                        
        <!-- JS de Bootstrap -->      
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>   

        <!-- JS -->      
        <script src="<%=basePath%>js/sesion.js"></script>
        <script src="<%=basePath%>js/detalle-producto.js"></script>

        
        
    </body>
</html>
