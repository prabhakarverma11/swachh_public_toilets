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
    <style>
        @media screen and (max-width: 992px) {
            .container {
                max-width: 990px;
            }
        }
    </style>
</head>

<body style="height: auto;">
<div>
    <%@include file="navigation.jsp" %>
    <div class="container">
        <div class="row">
            <form class="form-inline" style="font-size: medium;">
                <div class="col-md-3 form-group">
                    <label for="select_date">Select Range</label>
                    <select class="form-control" id="select_date">
                        <option value="today">Today</option>
                        <option value="yesterday">Yesterday</option>
                        <option value="lastthreedays">Last 3 Days</option>
                        <option value="lastweek">Last Week</option>
                        <option value="lasttwoweeks">Last 2 Weeks</option>
                        <option value="lastmonth">Last Month</option>
                        <option value="all">All</option>
                    </select>
                </div>
                <div class="col-md-3 form-group">
                    <label for="start_date">Start Date</label>
                    <input type="date" class="form-control" id="start_date">
                </div>
                <div class="col-md-3 form-group">
                    <label for="end_date">End Date</label>
                    <input type="date" class="form-control" id="end_date">
                </div>
                <div class="col-md-2 form-group">
                    <button type="submit" class="btn btn-success">Fetch Report</button>
                </div>

            </form>
        </div>
        <div class="row" style="max-height: 500px; overflow-y: scroll">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                    <tr>
                        <th>S.No.</th>
                        <th>Name</th>
                        <th>Address</th>
                        <th>Country</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="${0}"></c:set>
                    <c:forEach items="${locationsList}" var="location">
                        <c:set var="count" value="${count+1}"></c:set>
                        <tr>
                            <td>${count}</td>
                            <td>${location.name}</td>
                            <td>${location.address}</td>
                            <td>${location.country}</td>
                            <td>${location.latitude}</td>
                            <td>${location.longitude}</td>
                            <td>${location.type}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>