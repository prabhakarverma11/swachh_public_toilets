<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>


    <div class="container" style="background:url('/static/Images/main-bg.jpg') no-repeat;">
        <div style="margin-top:10%;background-color:rgba(0,0,0,0.5);border-radius:5px;width:50%;margin:10% auto;color: #fff;padding-top: 30px;">


        <div class="row">
        <%--<div class="col-md-6" style="height: 100%; background-color: #1358ad">
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
        </div>--%>

        <div class="col-xs-12 col-md-6 col-md-offset-3">
            <div class="login-box">
            <form method="post" action="/login" class="form-horizontal">



                <div class="form-group">
                    <label for="ssoId" class="">Login ID </label>

                    <input name="ssoId" type="text" maxlength="100" id="ssoId" tabindex="1"
                           class="form-control"
                           required>

                    <span class="error" style="display:none;">Please enter login id</span>
                </div>

                <div class="form-group">
                    <label for="password">Password </label>

                    <input name="password" type="password" maxlength="35" id="password" tabindex="2"
                           class="form-control" required>

                    <%--style="background-image: url(&quot;data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAASCAYAAABSO15qAAAAAXNSR0IArs4c6QAAAPhJREFUOBHlU70KgzAQPlMhEvoQTg6OPoOjT+JWOnRqkUKHgqWP4OQbOPokTk6OTkVULNSLVc62oJmbIdzd95NcuGjX2/3YVI/Ts+t0WLE2ut5xsQ0O+90F6UxFjAI8qNcEGONia08e6MNONYwCS7EQAizLmtGUDEzTBNd1fxsYhjEBnHPQNG3KKTYV34F8ec/zwHEciOMYyrIE3/ehKAqIoggo9inGXKmFXwbyBkmSQJqmUNe15IRhCG3byphitm1/eUzDM4qR0TTNjEixGdAnSi3keS5vSk2UDKqqgizLqB4YzvassiKhGtZ/jDMtLOnHz7TE+yf8BaDZXA509yeBAAAAAElFTkSuQmCC&quot;); background-repeat: no-repeat; background-attachment: scroll; background-size: 16px 18px; background-position: 98% 50%;" --%>

                    <span class="error"
                          style="display:none;">Please enter password</span>
                </div>

                <div class="form-group" style="height: 15%;padding: 0;">
                    <span>&nbsp;</span>

                    <input type="checkbox" class="zero-radius" id="rememberme" name="remember-me">

                    <label for="rememberme" class="">
                        Remember Me</label>
                </div>

                <div class="form-group" style="height: 0%;padding: 0;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                           class="form-control zero-radius"/>
                </div>

                <div class="form-group" style="height: 15%;padding: 0;">
                    <button type="submit" name="btnLogin" value="Login" class="btn btn-color-blue">Login</button>
                    <input id="Validation" name="Validation" type="hidden">
                </div>
            </form>
            </div>
        </div>
    </div>
        </div>
</div>

<%@include file="footer-new.jsp" %>

<%@include file="header-end.jsp" %>