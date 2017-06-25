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
    </style>
    <link href="<c:url value='/static/css/login.css' />" rel="stylesheet"/>
</head>
<body class="noJS fullBg">
<div id="wrapper">
    <form method="post" action="/login">

        <div id="uplogin">

            <div id="divLogin" class="loginBox cf">
                <ul class="logoBlck">
                    <li class="logoText">
                        <div class="mainTitle">

                            <img src="<c:url value='/static/Images/logo-login.png' />"
                                 alt="Swachh Bharat Mission, Government of India"
                                 title="Swachh Bharat Mission, Government of India">
                        </div>
                    </li>
                </ul>
                <div class="formBlck">

                    <div class="v-middle">
                        <ul>

                            <li id="login">
                                <label for="ssoId" id="mailLabel" class="loginLabel">Login ID </label>
                                <input name="ssoId" type="text" maxlength="100" id="ssoId" tabindex="1"
                                       class="loginInput" required>
                                <span id="rfvtxtEmail" class="error" style="display:none;">Please enter login id</span>
                            </li>

                            <li id="password1">
                                <label for="password" id="pswdLabel" class="loginLabel">Password </label>
                                <input name="password" type="password" maxlength="35" id="password" tabindex="2"
                                       class="loginInput" required>
                                <%--style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAAAXNSR0IArs4c6QAAAPhJREFUOBHlU70KgzAQPlMhEvoQTg6OPoOjT+JWOnRqkUKHgqWP4OQbOPokTk6OTkVULNSLVc62oJmbIdzd95NcuGjX2/3YVI/Ts+t0WLE2ut5xsQ0O+90F6UxFjAI8qNcEGONia08e6MNONYwCS7EQAizLmtGUDEzTBNd1fxsYhjEBnHPQNG3KKTYV34F8ec/zwHEciOMYyrIE3/ehKAqIoggo9inGXKmFXwbyBkmSQJqmUNe15IRhCG3byphitm1/eUzDM4qR0TTNjEixGdAnSi3keS5vSk2UDKqqgizLqB4YzvassiKhGtZ/jDMtLOnHz7TE+yf8BaDZXA509yeBAAAAAElFTkSuQmCC&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;" --%>
                                <span id="rfvtxtPassword" class="error"
                                      style="display:none;">Please enter password</span>
                            </li>


                            <li id="reme" class="rememberBlck">
                                <span class="loginLabel">&nbsp;</span>
                                <input type="checkbox" class="inputChck iCheck" id="rememberme" name="remember-me">
                                <label for="rememberme" class="">
                                    Remember Me</label>
                            </li>
                            <li style="display: none">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </li>
                            <li id="loginbutton">
                                <input type="submit" name="btnLogin" value="Login" id="btnLogin" class="loginBtn">
                                <input id="Validation" name="Validation" type="hidden">
                            </li>

                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <div class="footer">
            <span id="lblFooter">© Content Owned by Swachhata Augmentation Through Corporate Helping Hands.</span>
        </div>
    </form>
</div>


</body>
</html>