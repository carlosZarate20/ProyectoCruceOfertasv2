<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="theme-color" content="#094FA4" />
    <title>Login BBVA</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap/bootstrap.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap/bootstrap-theme.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/font-awesome/font-awesome.min.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/frontend/main.css'/>">
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/frontend/login.css'/>">

    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta http-equiv="X-UA-Compatible" content="IE=7" />
    <meta http-equiv="X-UA-Compatible" content="IE=8" />
    <meta http-equiv="X-UA-Compatible" content="IE=9" />

</head>

<body class="blue-bg">

    <div class="main">

        <div class="container">

            <div class="middle">
                <div id="login">

                    <form action="application/json" id ="loginValues" method="post">

                        <fieldset class="clearfix">

                            <p>
                                <span class="fa fa-user"></span>
                                <input type="text" Placeholder="Usuario" id="username">
                            </p>
                            <!-- JS because of IE support; better: placeholder="Username" -->
                            <p>
                                <span class="fa fa-lock"></span>
                                <input type="password" Placeholder="Contraseña" id="password">
                            </p>
                            <!-- JS because of IE support; better: placeholder="Password" -->
                            <span id="alertMessage" class="alertMessage" style="display:none"></span>
                            <div>
<!--                                 <span style="width:48%; text-align:left;  display: inline-block;"> -->
<!--                                         <a class="small-text" href="#">Se olvido la contraseña? -->
<!--                                         </a> -->
<!--                                     </span> -->
                                <span style="width:50%; text-align:right; float: right;">
                                    <input type="submit" id="btnIngresar" value="Entrar">
                                </span>
                            </div>

                        </fieldset>
                        <div class="clearfix"></div>
                    </form>

                    <div class="clearfix"></div>

                </div>
                <!-- end login -->

                <div class="logo">
                    <img id="logo-bbvacf" height="150px" src="<c:url value='/resources/img2/logoBBVA.png'/>"/>


                    <div class="clearfix"></div>
                </div>

            </div>

        </div>

    </div>
    <script src="<c:url value='/resources/js/bootstrap/jquery-1.9.1.min.js'/>"></script>
    <script src="<c:url value='/resources/js/bootstrap/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resources/js/frontend/login.js?v=${timestamp}'/>"></script>
    <script type="text/javascript">
        $(function() {
           gPath = '${gPath}<c:if test="${empty gPath}">${pageContext.servletContext.contextPath}</c:if>';
        });
     </script>
</body>

</html>