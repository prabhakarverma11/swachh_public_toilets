<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Users List</title>
    <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/1.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/2.css' />" rel="stylesheet"/>
    <link href="<c:url value='/static/css/3.css' />" rel="stylesheet"/>
</head>

<body>
<%@include file="navigation.jsp" %>
<div class="row" style="height: 80%; margin-top: 0;margin-bottom: 0">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading"><span class="lead">List of Users </span></div>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Firstname</th>
                <th>Lastname</th>
                <th>Email</th>
                <th>User Id</th>
                <%--<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">--%>
                <%--<th width="100"></th>--%>
                <%--</sec:authorize>--%>
                <%--<sec:authorize access="hasRole('ADMIN')">--%>
                <%--<th width="100"></th>--%>
                <%--</sec:authorize>--%>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${user.ssoId}</td>
                        <%--<sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">--%>
                        <%--<td><a href="<c:url value='/edit-user-${user.ssoId}' />"--%>
                        <%--class="btn btn-success custom-width">edit</a></td>--%>
                        <%--</sec:authorize>--%>
                        <%--<sec:authorize access="hasRole('ADMIN')">--%>
                        <%--<td><a href="<c:url value='/delete-user-${user.ssoId}' />"--%>
                        <%--class="btn btn-danger custom-width">delete</a></td>--%>
                        <%--</sec:authorize>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>