<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0042)https://swachh.org.in/swachhapp/Login.aspx -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
        User Login
    </title>
    <meta http-equiv="X-UA-Compatible" content="IE=10; IE=9; IE=8; IE=EDGE">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=yes">
    <link id="lnkFavIcon" rel="shortcut icon" type="image/x-icon"
          href="https://swachh.blob.core.windows.net/assets/app_assets/Images/favicon.ico">
    <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="Styles/styles-ie.css"/>
    <![endif]-->
    <style type="text/css">
        .pagecontainer {
            display: none;
        }

        .noscriptmsg {
            color: Red;
        }

        input.zero-radius {
            border-radius: 0;
        }

        div.form-group.margin {
            margin: 0 10%;
        }

        form.form-horizontal.font {
            font-size: medium;
        }
    </style>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <link href="<c:url value='/static/css/login.css' />" rel="stylesheet"/>
</head>
<body class="noJS fullBg">

<div class="row" style="height: 15%; margin: 0">

</div>

<div class="row" style="height: 80%; margin: 0;overflow: auto">
    <div class="container" style="height: 80%">

        <div class="col-md-6" style="height: 100%; background-color: #1358ad">
            <ul class="list-group" style="height: 100%;padding: 0;margin: 0;">
                <li style="height: 30%">

                </li>
                <li style="height: 30%">
                    <div>

                        <img src="<c:url value='/static/Images/logo-login.png' />"
                             alt="Swachh Bharat Mission, Government of India"
                             title="Swachh Bharat Mission, Government of India"
                             style="height: 100%;width: 100%;padding: 2%;">
                    </div>
                </li>
            </ul>
        </div>

        <div class="col-md-6" style="height: 100%; background-color: #ffffff">
            <form method="post" action="/login" class="form-horizontal font">

                <div class="form-group margin" style="height: 20%;padding: 0;">
                </div>

                <div class="form-group margin" style="height: 15%;padding: 0;">
                    <label for="ssoId" class="">Login ID </label>

                    <input name="ssoId" type="text" maxlength="100" id="ssoId" tabindex="1"
                           class="form-control zero-radius"
                           required>

                    <span class="error" style="display:none;">Please enter login id</span>
                </div>

                <div class="form-group margin" style="height: 15%;padding: 0;">
                    <label for="password">Password </label>

                    <input name="password" type="password" maxlength="35" id="password" tabindex="2"
                           class="form-control zero-radius" required>

                    <%--style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAAAXNSR0IArs4c6QAAAPhJREFUOBHlU70KgzAQPlMhEvoQTg6OPoOjT+JWOnRqkUKHgqWP4OQbOPokTk6OTkVULNSLVc62oJmbIdzd95NcuGjX2/3YVI/Ts+t0WLE2ut5xsQ0O+90F6UxFjAI8qNcEGONia08e6MNONYwCS7EQAizLmtGUDEzTBNd1fxsYhjEBnHPQNG3KKTYV34F8ec/zwHEciOMYyrIE3/ehKAqIoggo9inGXKmFXwbyBkmSQJqmUNe15IRhCG3byphitm1/eUzDM4qR0TTNjEixGdAnSi3keS5vSk2UDKqqgizLqB4YzvassiKhGtZ/jDMtLOnHz7TE+yf8BaDZXA509yeBAAAAAElFTkSuQmCC&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;" --%>

                    <span class="error"
                          style="display:none;">Please enter password</span>
                </div>

                <div class="form-group margin" style="height: 15%;padding: 0;">
                    <span>&nbsp;</span>

                    <input type="checkbox" class="form-control zero-radius" id="rememberme" name="remember-me">

                    <label for="rememberme" class="">
                        Remember Me</label>
                </div>

                <div class="form-group margin" style="height: 0%;padding: 0;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                           class="form-control zero-radius"/>
                </div>

                <div class="form-group margin" style="height: 15%;padding: 0;">
                    <input type="submit" name="btnLogin" value="Login" class="form-control btn btn-default zero-radius">
                    <input id="Validation" name="Validation" type="hidden">
                </div>
            </form>
        </div>
    </div>

</div>

<%@include file="footer.jsp" %>

</body>
</html>