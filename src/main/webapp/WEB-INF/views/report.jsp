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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        @media screen and (max-width: 992px) {
            .container {
                max-width: 990px;
            }
        }
    </style>
    <script>
        function fun(reviews) {
            console.log("reviews", reviews);
        }
    </script>
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
                        <th>Type</th>
                        <th>Rating</th>
                        <th>Reviews</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="${0}"></c:set>
                    <c:forEach items="${reportsList}" var="report">
                        <c:set var="count" value="${count+1}"></c:set>
                        <tr>
                            <td>${count}</td>
                            <td>${report.location.name}</td>
                            <td>${report.location.address}</td>
                            <td>${report.location.country}</td>
                            <td>${report.location.type}</td>
                            <td>${report.placeDetail.rating}</td>
                            <td>
                                <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                                        data-target="#myModal" onclick="fun(<c:out value='${report.reviews}'/>)">show
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Modal Header</h4>
                    </div>
                    <div class="modal-body">
                        <p>Some text in the modal.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</div>
</body>
</html>