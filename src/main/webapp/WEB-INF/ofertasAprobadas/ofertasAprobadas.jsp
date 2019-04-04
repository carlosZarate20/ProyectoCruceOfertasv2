<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html> 
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Ofertas Aprobadas</title>


    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap/bootstrap-theme.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/font-awesome/font-awesome.min.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/frontend/menu/menu.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/frontend/cruceOfertas.css'/>">
    <link rel="stylesheet" href="<c:url value='/resources/css/frontend/main.css'/>">
</head>

<body>
<%
	//permite acceso si existe sesion
	String user = null;
	if(session.getAttribute("username") == null){
		response.sendRedirect(request.getContextPath() + "/loginBBVA");
	}

%>
    <div class="fa fa-user" style="float:right; font-size: 25px; padding-top: 20px; padding-right: 50px" aria-hidden="true">
        <span id="user" ></span>
    </div>
    <div id="wrapper" class=""><!--toggled-->
        <div class="nav-side-menu">
            <div class="brand"><img src="<c:url value='/resources/img2/imgBannerCF.png'/>"></div>
            <div class="menu-list">
                <ul id="menu-content" class="menu-content collapse out">
                    <li>
                        <a href="#">
                            <i class="fa fa-book fa-lg"></i> Acerca de
                        </a>
                    </li>
                    <li data-toggle="collapse" data-target="#products" class="collapsed active" aria-controls="products">
                        <a href="#"><i class="fa fa-exchange fa-lg"></i> Cruce de Datos <span class="arrow"></span></a>
                    </li>
                    <ul class="sub-menu collapse" id="products">
                        <li class="active"><a href="<c:url  value='/ofertasAprobadas/ofertasAprobadas'/>">Ofertas Aprobadas</a></li>
                    </ul>
                    <li>
                        <a href="#">
                            <i class="fa fa-user fa-lg"></i> Perfil
                        </a>
                    </li>
                    <li>
                        <a href="<c:url value='/logout'/>">
                            <i class="fa fa-sign-out fa-lg"></i> Cerrar Sesión
                        </a>
                    </li>
                </ul>
            </div>
        </div>
       
    <div id="page-content-wrapper">
            <i href="#menu-toggle" class="fa fa-bars fa-2x" id="menu-toggle"></i>
            <div id="ContenidoPaginaPrincipal">
            <div class="container">
                    <div>
                        <h1> <span class="fa fa-exchange" aria-hidden="true"></span>
                            Ofertas Aprobadas
                        </h1>
                        <p>El modulo de Ofertas Aprobadas, permite cruzar datos a partir de un archivo Excel que
                            contiene los numeros de documento de clientes. </p>
                    </div>
                    <div>
                        <form enctype="multipart/form-data" id="uploadFile" method="post">
                            <fieldset class="container">
                                <legend> <span class="fa fa-cloud-upload" aria-hidden="true"></span>
                                    Carga de Archivo
                                </legend>
                                <p>Seleccione un archivo Excel y pulse el boton <b>"Enviar"</b>.</p>
                                <div style="overflow: hidden;">
                                    <div style="float: left;">
                                        <label for="file-upload" class="subir btn">
                                            <i class="fa fa-file-excel-o"></i> <b>Seleccionar Archivo</b>
                                        </label>
                                        <input id="file-upload" onchange="cambiar()" type="file" style='display: none;' />
                                        <span id="info">Ningún Archivo Selecionado...</span>
                                    </div>
                                    <div style="float: right;">
                                        <input type="submit" value="Enviar" name="enviar_archivo" id="enviar_archivo" class="btn btn-success">
                                    </div>
                                    
                                </div>
                                <!-- ALERTA -->
                                <div class="alert alert-danger hover_img" role="alert" style="display: none" id="file_error">
                                    <strong>¡Error!</strong> El archivo que ha subido tiene un formato incorrecto. Revise
                                    la <a href="#" class="alert-link">estructura
                                        del archivo. <span><img src="<c:url value='/resources/img2/modeloExcel.PNG'/>" alt="image" height="150" /></span></a>
                                </div><!-- END_ALERTA -->
                				<!-- ALERTA -->
                                <div class="alert alert-danger hover_img" role="alert" style="display: none" id="file_warning">
                                    <strong>¡Advertencia!</strong> Debe seleccionar un archivo Excel. <a class="alert-link"> <span></span></a>
                                </div><!-- END_ALERTA -->
                            </fieldset>
                        </form>            
                    </div>
                    <div>
                        <fieldset class="container">
                            <legend> <span class="fa fa-table" aria-hidden="true"></span>
                                Resultado
                            </legend>
                            <div class="table-responsive" style="height: 200px" id="resultado-ofertas"></div>
                            <div style="float:right">
                                <button id="btnDescargar" class="btn btn-default">Descargar</button>
                            </div>
                        </fieldset>
                    </div>
            
                </div> <!-- END_Contenedor-->
            </div>
    </div>

    <!-- Contenedor-->
    
</div>
    
    <script src="<c:url value='/resources/js/bootstrap/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap/notify.min.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/ofertasAprobadas/ofertasAprobadas.js'/>"></script>
    <script src="<c:url value='/resources/js/frontend/cruceOfertas.js'/>"></script>
    <script src="<c:url value='/resources/js/frontend/menu/menu.js'/>"></script>
    <script type="text/javascript">
        $(function() {
           gPath = '${gPath}<c:if test="${empty gPath}">${pageContext.servletContext.contextPath}</c:if>';
        });
    </script>
</body>

</html>