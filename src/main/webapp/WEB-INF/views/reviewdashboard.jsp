<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="spt">
<head>
    <title>GTL</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,500,600,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/static/css/styles.min.css' />">
    <script src="<c:url value='/static/js/main.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/static/js/app.min.js'/>" type="text/javascript"></script>
    <%--<script src="http://localhost:8081/build/js/app.min.js" type="text/javascript"></script>--%>
</head>
<body>
<nav class="navbar navbar-default nav-dark" data-spy="affix" data-offset-top="60">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-nav"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="/static/img/swacch-logo.png" class="img-responsive"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="main-nav">
            <ul class="nav navbar-nav main-nav">
                <li><a href="/">Home</a></li>
                <li><a href="/dashboard">Dashboard</a></li>
                <li><a href="/admin/dashboard">ULB Dashboard</a></li>
                <li class="active"><a href="/admin/review-dashboard">Admin</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%--<li><a href="/login">Login <span class="glyphicon glyphicon-log-in"></span></a></li>--%>
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

<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="table-responsive">
                <table class="table table-bordered numeric-table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Location</th>
                        <th>Suggested Type</th>
                        <th>Suggested ULB</th>
                        <th>Suggested By</th>
                        <th>Name</th>
                        <th>Phone</th>
                        <th>Email</th>
                        <th style="width: 10%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${adminVerifications}" var="adminVerification">
                        <tr>
                            <td>${adminVerification.location.address}</td>
                            <td>${adminVerification.locationType}</td>
                            <td>${adminVerification.ULBName}</td>
                            <td>${adminVerification.category}</td>
                            <td>${adminVerification.name}</td>
                            <td>${adminVerification.phone}</td>
                            <td>${adminVerification.email}</td>
                            <td>
                                <button class="btn-success" onclick="update(event);"><i
                                        class="glyphicon glyphicon-ok" id="${adminVerification.id}"></i></button>
                                <button class="btn-danger" onclick="reject(event);"><i
                                        class="glyphicon glyphicon-remove" id="${adminVerification.id}"></i></button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="footer-new.jsp" %>
<%@include file="header-end.jsp" %>
<script>
    function update(event) {
        var data = {id: event.target.id};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        $.ajax({
            url: "/api/admin/accept",
            type: 'POST',
            data: data,
            success: function (response) {
                alert("accepted");
            }
        });
    }
    function reject(event) {
        var data = {id: event.target.id};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        $.ajax({
            url: "/api/admin/reject",
            type: 'POST',
            data: data,
            success: function (response) {
                alert("rejected");
            }
        });
    }
</script>