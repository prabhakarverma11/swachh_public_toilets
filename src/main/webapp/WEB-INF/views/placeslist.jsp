<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
    <%--<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>--%>
</head>

<body>
<div class="container">
    <%--<div class="row"><span class="lead">List of Locations </span></div>--%>
    <%--<div class="row">--%>
        <%--<!-- Default panel contents -->--%>
        <%--<table class="table table-responsive">--%>
            <%--<thead>--%>
            <%--<tr class="table-row-cell">--%>
                <%--<th>Name</th>--%>
                <%--<th>Address</th>--%>
                <%--<th>Country</th>--%>
                <%--<th>Latitude</th>--%>
                <%--<th>Longitude</th>--%>
                <%--<th>Type</th>--%>
            <%--</tr>--%>
            <%--</thead>--%>
            <%--<tbody>--%>
            <%--<c:forEach items="${locationsList}" var="location">--%>
                <%--<tr class="table-row-cell">--%>
                    <%--<td>${location.name}</td>--%>
                    <%--<td>${location.address}</td>--%>
                    <%--<td>${location.country}</td>--%>
                    <%--<td>${location.latitude}</td>--%>
                    <%--<td>${location.longitude}</td>--%>
                    <%--<td>${location.type}</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
            <%--</tbody>--%>
        <%--</table>--%>
    <%--</div>--%>
</div>
</body>
</html>