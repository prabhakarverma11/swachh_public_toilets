<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
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
</head>

<body>
<div>
    <%@include file="navigation.jsp" %>
    <div class="container">
        <div class="row">
            <h1 class="text-center">${location.name}, ${location.address}</h1>
        </div>
        <div class="row">
            <form class="form-inline" action='/location-detail-<c:out value="${location.id}"></c:out>'>
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
                    <input type="date" class="form-control" id="start_date" name="start_date">
                </div>
                <div class="col-md-3 form-group">
                    <label for="end_date">End Date</label>
                    <input type="date" class="form-control" id="end_date" name="end_date">
                </div>
                <div class="col-md-2 form-group">
                    <button type="submit" class="btn btn-success">Apply Filter</button>
                </div>

            </form>
        </div>
        <div class="row">
            <div class="container">
                <div class="col-md-6">
                    <div class="row">
                        <img src="<c:out value="${location.imageURL}"></c:out>"
                             style="height: 50%;width: 80%;/* min-width: 500px; */">
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="row">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <strong style="font-weight: bold">Overall Rating: </strong>${overallRating}
                            </li>
                            <li class="list-group-item">
                                <strong style="font-weight: bold">Average Rating: </strong>${averageRating}
                            </li>
                            <li class="list-group-item">
                                <strong style="font-weight: bold">Reviews Count: </strong>${reviewsCount}
                            </li>
                            <li class="list-group-item">
                                <strong style="font-weight: bold">Total Reviews: </strong>${totalReviews}
                            </li>
                            <li class="list-group-item">
                                <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                                        data-target="#myModal1">Show reviews
                                </button>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="myModal1" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">${location.name}, ${location.address}</h4>
                    </div>
                    <div class="modal-body" style="max-height: 500px;overflow-y: scroll;">
                        <ul class="list-group">
                            <c:forEach items="${reviews}" var="review">
                                <li class='list-group-item'>
                                    Author: <a href='<c:out value="${review.authorURL}"></c:out>' target='_blank'>${review.authorName}</a>
                                    <br>
                                    Rating: ${review.rating}
                                    <br>
                                    Review: ${review.reviewText}
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
</body>
</html>