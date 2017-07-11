<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="spt">
<head>
    <title>GTL</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <link rel="stylesheet" href="<c:url value='/static/css/styles.min.css' />">
    <script src="<c:url value='/static/js/main.min.js'/>" type="text/javascript"></script>
    <%--<script src="<c:url value='/static/js/app.min.js'/>" type="text/javascript"></script>--%>
    <script src="http://localhost:8081/build/js/app.min.js" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-default nav-dark affix-top" data-spy="affix" data-offset-top="60">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-nav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="../static/img/swacch-logo.png" class="img-responsive"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="main-nav">
            <sec:authorize access="!isAuthenticated()">
                <ul class="nav navbar-nav main-nav">
                    <li class="active"><a href="/">Home</a></li>
                    <li><a href="/dashboard">Dashboard</a></li>
                </ul>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav main-nav">
                    <li class="active"><a href="/">Home</a></li>
                    <li><a href="/admin/dashboard">Dashboard</a></li>
                </ul>
            </sec:authorize>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="!isAuthenticated()">
                    <li><a href="/login">Login <span class="glyphicon glyphicon-log-in"></span></a></li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Welecome ULB <span class="glyphicon glyphicon-user"></span> <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="/logout">Logout</a></li>
                        </ul>
                    </li>
                </sec:authorize>
                <!-- <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                  </ul>
                </li> -->
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>