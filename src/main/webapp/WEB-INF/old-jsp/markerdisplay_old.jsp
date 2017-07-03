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
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDE6hMNFUb_MgX1aVvtnpAu52mS_9pZ_Zs&libraries=places&callback="
            async defer></script>
    <script src="<c:url value='/static/js/createMarker.js' />"></script>
</head>

<body style="height: auto;" onload="createMarker(<c:out value='${locationsListJson}'/>);">
<%@include file="navigation_old.jsp" %>
<div class="row" style="height: 80%;margin: 0; overflow: auto">
    <div class="col-md-6">
        <div id="map" style="height: 100%;margin: 5px 0;"></div>
    </div>
    <div class="col-md-6">
        <div class="row" id="div_location" style="height: 5%; margin: 0">

        </div>
        <div class="row" id="overall_rating" style="height: 5%;margin: 0">
            <strong style="font-weight: bold">Overall Rating: </strong><span>N.A.</span>
        </div>
        <div class="row" id="reviews" style="height: 85%;margin: 0">
            <strong style="font-weight: bold">Reviews: </strong>
            <div style="overflow-y: scroll;height: 100%;">
                <ul class="list-group" id="reviews_list">
                    <li class="list-group-item" style="border: 1px solid grey">
                        <div>Please click on the marker you wish to see the reviews of.</div>
                    </li>

                </ul>
            </div>
        </div>
    </div>
</div>
<%@include file="footer_old.jsp" %>
</body>
</html>