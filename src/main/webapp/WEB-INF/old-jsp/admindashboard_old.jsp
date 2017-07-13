<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin Dashboard</title>

    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/1.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/2.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/3.css' />" rel="stylesheet"/>
    <style>
        @media screen and (max-width: 992px) {
            .container {
                max-width: 990px;
            }
        }
    </style>
</head>

<body style="height: auto;">
<%@include file="navigation_old.jsp" %>
<div class="row" style="height: 80%; margin:0;overflow: auto">
    <div class="col-md-6" style="height: 100%;margin: 0">
        <div class="form-group">
            <button class="form-block">Fetch Place Ids</button>
        </div>
    </div>
    <div class="col-md-6" style="height: 100%;margin: 0">
        <div class="form-group">
            <button class="form-block">Fetch Place Details</button>
        </div>
    </div>
</div>

<%@include file="footer_old.jsp" %>
</body>
</html>