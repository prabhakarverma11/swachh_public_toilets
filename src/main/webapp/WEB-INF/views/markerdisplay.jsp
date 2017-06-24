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
<div>
    <%@include file="navigation.jsp" %>
    <div class="container" style="margin-bottom: 10px;">
        <div class="col-md-6" style="height: 75%;">
            <div id="map" style="height: 100%;margin: 5px 0;"></div>
        </div>
        <div class="col-md-6" style="height: 75%;">
            <div class="row" id="overall_rating" style="height: 5%;margin: 0">
                <strong style="font-weight: bold">Rating:</strong><span>4.4</span>
            </div>
            <div class="row" id="reviews" style="height: 90%;margin: 0">
                <strong style="font-weight: bold">Reviews:</strong>
                <div style="overflow-y: scroll;max-height: 100%;">
                    <ul class="list-group" id="reviews_list">
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>
                        <li class="list-group-item" style="border: 1px solid grey">
                            <div><strong>Rating:</strong> 1.1</div>
                            <div>It needs improvement but I am happy that it is located on Google</div>
                        </li>


                    </ul>
                </div>
            </div>
        </div>
    </div>
    <%@include file="footer.jsp" %>
</body>
</html>