<%-- 
    Document   : registrarse
    Created on : 27/11/2021, 12:03:50 p. m.
    Author     : Acer
--%>

<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
         <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" +     
        request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Oakshop</title>
        <!-- Fuente de google: Open Sans - Regular 400 -->
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans&display=swap" rel="stylesheet">
        <!-- CSS de Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

        <!--Normallize css: proyecto que corrige estilos predeterminados de los diferentes navegadores, para evitar usar el selector universal
        en la hoja de estilos CSS. -->
        <link rel="stylesheet" href="https://necolas.github.io/normalize.css/8.0.1/normalize.css">
        <!--Importar CSS -->
        <link href="<%=basePath%>css/menu.css" rel="stylesheet" type="text/css"/>
        <link href="<%=basePath%>css/registro.css" rel="stylesheet" type="text/css"/>
    </head>
    <body onload="exist()" >
        
        <!--menú -->
        <nav class="navbar navbar-expand-lg sticky-top navbar-dark bg-dark">
            <div class="container-fluid">

                <a class="navbar-brand" href="<%=basePath%>index.jsp">
                   <!-- <img src="#" alt="" width="140px" height="120px" /> -->
                   Oakshop
                </a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">

                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="<%=basePath%>index.jsp">INICIO</a>
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
                                <a class="nav-link" href="<%=basePath%>/jsp/registrarse.jsp">REGISTRARSE</a>
                            </li>
                        </ul>
                    </template>
                    
                </div>
            </div>
        </nav>
        <!--Fin menú -->
        
        <main>
            <!-- Inicio formulario-->
        <section class="registro">

            <div class="encabezado">
                <h1>Registro</h1>
                <br>
                <h2>Completa tus datos personales</h2>
                <br>
            </div>
            
            <form action="Registro.do" class="formulario" id="formulario">
            <!--Grupo: Nombre -->
			<div class="formulario__grupo" id="grupo__nombre">
				<label for="nombre" class="formulario__label">Nombres</label>
				<div class="formulario__grupo-input">
					<input type="text" class="formulario__input" name="nombre" id="nombre" placeholder="Nombres completos" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">El nombre solo puede contener letras y espacios.</p>
			</div>

            <!--Apellido: -->
			<div class="formulario__grupo" id="grupo__apellido">
				<label for="apellido" class="formulario__label">Apellidos</label>
				<div class="formulario__grupo-input">
					<input type="text" class="formulario__input" name="apellido" id="apellido" placeholder="Apellidos completos" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">El apellido solo puede contener letras y espacios.</p>
			</div>

            <!-- Grupo: Contraseña -->
			<div class="formulario__grupo" id="grupo__password">
				<label for="password" class="formulario__label">Contraseña</label>
				<div class="formulario__grupo-input">
					<input type="password" class="formulario__input" name="password" id="password" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">La contraseña tiene que ser de 4 a 12 dígitos. No puede contener espacios</p>
			</div>

			<!-- Grupo: Contraseña 2 -->
			<div class="formulario__grupo" id="grupo__password2">
				<label for="password2" class="formulario__label" name="password2">Repetir Contraseña</label>
				<div class="formulario__grupo-input">
					<input type="password" class="formulario__input" name="password2" id="password2" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">Ambas contraseñas deben ser iguales.</p>
			</div>
            <!--Grupo: Cédula-->
			<div class="formulario__grupo" id="grupo__cedula">
				<label for="cedula" class="formulario__label">Cedula</label>
				<div class="formulario__grupo-input">
					<input type="text" class="formulario__input" name="cedula" id="cedula" placeholder="Número de identificación" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">La cédula solo puede contener números y debe ser de 8 a 10 dígitos.</p>
			</div>
		<!--document.getElementById('formulario__mensaje-exito').classList.add('formulario__mensaje-exito-activo');-->

			<!-- Grupo: Correo Electronico -->
			<div class="formulario__grupo" id="grupo__correo">
				<label for="correo" class="formulario__label">Correo Electrónico</label>
					<div class="formulario__grupo-input">
						<input type="email" class="formulario__input" name="correo" id="correo" placeholder="correo@correo.com" required>
						<i class="formulario__validacion-estado fas fa-times-circle"></i>
					</div>
					<p class="formulario__input-error">El correo solo puede contener letras, numeros, puntos, guiones y guion bajo.</p>
			</div>
                        <% String valor = request.getSession().getAttribute("existe")==null?"no existe":request.getSession().getAttribute("existe").toString();
                          
                        %>
            <!-- Grupo: Teléfono -->
			<div class="formulario__grupo" id="grupo__telefono">
				<label for="telefono" class="formulario__label">Teléfono</label>
				<div class="formulario__grupo-input">
					<input type="text" class="formulario__input" name="telefono" id="telefono" placeholder="Número de telefono celular" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">El número de celular solo puede contener numeros y son 10 dígitos.</p>
			</div>

			<!--Grupo: Dirección-->
			<div class="formulario__grupo" id="grupo__direccion">
				<label for="direccion" class="formulario__label">Dirección</label>
				<div class="formulario__grupo-input">
					<input type="text" class="formulario__input" name="direccion" id="direccion" placeholder="Dirección de residencia" required>
					<i class="formulario__validacion-estado fas fa-times-circle"></i>
				</div>
				<p class="formulario__input-error">La dirección puede contener numeros, letras, guiones, númeral.</p>
			</div>

			<!-- Grupo: Terminos y Condiciones -->
			<div class="formulario__grupo" id="grupo__terminos">
				<label class="formulario__label">
                                    <input class="formulario__checkbox" type="checkbox" name="terminos" id="terminos" required>
					Acepto los Terminos y Condiciones
				</label>
			</div>

			<div class="formulario__mensaje" id="formulario__mensaje">
				<p><i class="fas fa-exclamation-triangle"></i> <b>Error:</b> Por favor rellena el formulario correctamente. </p>
			</div>

            <div class="formulario__grupo formulario__grupo-btn-enviar">
				<button id="boton" type="submit" class="btn formulario__btn" disabled>Continuar</button>
				<p class="formulario__mensaje-exito" id="formulario__mensaje-exito">Formulario enviado exitosamente!</p>
			</div>

            </form>
        </section>
        <!-- Fin formulario-->
        </main>
        
        <!-- JS formulario -->
         <script src="<%=basePath%>js/formulario.js" type="text/javascript"></script>
         <script>
    
            function exist(){            
                let existe = '<%=valor%>';
                            if(existe==="existe Usuario"){
                                alert('El usuario ya existe!');
                            }else if(existe === "existe Correo"){
                                alert('El correo digitado, ya exite!');
                            }
                        }
            </script>
        <!-- JS de Bootstrap -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <!-- Enlace a los iconos que se trabajan. -->
	<script src="https://kit.fontawesome.com/2c36e9b7b1.js" crossorigin="anonymous"></script>
        
    </body>
</html>
