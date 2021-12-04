<%-- 
    Document   : index
    Created on : 27/11/2021, 9:49:02 a. m.
    Author     : Sebastian :v
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        
        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        <!-- CSS de Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <!-- CSS restantes -->
        <link rel="stylesheet" href="<%=basePath%>css/menu.css"/>
        <link rel="stylesheet" href="<%=basePath%>css/index.css"/>
        
        
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
        
        <!-- Carousel -->
        <div id="carouselExampleCaptions" class="carousel slide" data-bs-ride="carousel">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="2" aria-label="Slide 3"></button>
  </div>
  <div class="carousel-inner">
    <div class="carousel-item active">
        <img src="img/slide-01.jpg" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5 style="color: #000;">First slide label</h5>
        <p style="color: #000;">Some representative placeholder content for the first slide.</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="img/slide-02.jpg" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5 style="color: #000;">Second slide label</h5>
        <p style="color: #000;">Some representative placeholder content for the second slide.</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="img/slide-03.jpg" class="d-block w-100" alt="...">
      <div class="carousel-caption d-none d-md-block">
        <h5 style="color: #000;">Third slide label</h5>
        <p style="color: #000;">Some representative placeholder content for the third slide.</p>
      </div>
    </div>
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>
        <!-- Fin carousel -->
        
        <main>
            
            <!-- Hombres -->
            <section>
            <div class="container">
                <div class="titulo-secciones">
                        Hombres
                </div>
                <div class="row row-cols-3 row-cols-md-4 g-4">
                   
                    <div class="col">
                        <div class="card bg-danger">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Polos</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card bg-danger">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Camisetas</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card bg-danger">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Jeans</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card bg-danger">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Calzado</button>                          
                            </div>
                        </div>
                    </div>
            </div>
            </div>
            </section>
            
            <!-- Mujeres -->
            <section>
            <div class="container">
                <div class="titulo-secciones">
                        Mujeres
                </div>
                <div class="row row-cols-3 row-cols-md-4 g-4">
                   
                    <div class="col">
                        <div class="card" style="background: #FFD2F3;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Blusas</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #FFD2F3;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Vestidos</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #FFD2F3;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Jeans</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #FFD2F3;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Calzado</button>                          
                            </div>
                        </div>
                    </div>
            </div>
            </div>
            </section>
            
            <!-- Niños -->
        <section>
            <div class="container">
                <div class="titulo-secciones">
                        Kids
                </div>
                <div class="row row-cols-3 row-cols-md-4 g-4">
                   
                    <div class="col">
                        <div class="card" style="background: #B2ABFF;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Camisetas</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #B2ABFF;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Bermudas</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #B2ABFF;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Jeans</button>                          
                            </div>
                        </div>
                    </div>
                    
                    <div class="col">
                        <div class="card" style="background: #B2ABFF;">
                            <div class="card-body d-flex flex-column">
                                 <img src="img/polo-hombre.png" class="card-img-top" alt="...">
                                <button type="button" class="btn btn-light mt-auto align-self-center">Calzado</button>                          
                            </div>
                        </div>
                    </div>
            </div>
            </div>
            </section>
            
            
            
            
            
            
            
        </main>
        
        
        <!-- JS de Bootstrap -->      
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="./js/sesion.js"></script>
    </body>
</html>
