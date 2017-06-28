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
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script async src="//code.jquery.com/ui/1.10.1/jquery-ui.min.js"></script>
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
        function fillModalData(locationName, locationAddress, locationId) {
            $(".modal-title").html(locationName + ", " + locationAddress);

            $.ajax({
                url: window.location.origin + "/fetch-rating-and-reviews/" + locationId,
                dataType: 'json',
                type: 'get',
                success: function (response) {
                    console.log("response", response);
                    var reviews = JSON.parse(response.reviews);
                    var modalBody = "<ul class='list-group'>";

                    reviews.forEach(function (review) {
                        modalBody += "<li class='list-group-item'>" +
                            "Author: <a href='" + review.authorURL + "' target='_blank'>" + review.authorName + "</a><br>" +
                            "Rating: " + review.rating + "<br>" +
                            "Review: " + review.reviewText +
                            "</li>";
                    });
                    modalBody += "</ul>";

                    $(".modal-body").html(modalBody);
                }
            });
        }

    </script>
</head>

<body style="height: auto;">
<%@include file="navigation.jsp" %>
<div class="row" style="height: 25%;margin-top: 50px;width: 100%;margin-bottom: 0;">
    <div class="row" style="margin: 0;">
        <h2 class="text-center" style="margin: 0">Welcome to the Swachhata Dashboard</h2>
    </div>
    <div class="row" style="margin: 0;">
        <h3 class="text-center">This platform shows you information on cities across the country as they are ranked on
            their performances.</h3>
    </div>
</div>
<div class="container">
    <div class="row">

        <div class="cols4">
            <div class="wrapBox">
                <div class="wrapTitle swachhProject">
                    <abc style="font-size: 5em;padding: 3vw 0vw 0vw 0vw;border-radius: 50%;background-color: white;height: 13vw;width: 13vw;float: left;margin: 3vw 0 0 7vw;color: #144887;">
                        1001
                    </abc>
                    <%--/* vertical-align: text-bottom; */--%>
                    <span>Toilets Reviewed Today</span>
                </div>
            </div>
        </div>
        <div class="cols4">
            <div class="wrapBox wrapGreen">
                <div class="wrapTitle csr">
                    <abc style="font-size: 5em;padding: 3vw 0vw 0vw 0vw;border-radius: 50%;background-color: white;height: 13vw;width: 13vw;float: left;margin: 3vw 0 0 7vw;color: #144887;">
                        500
                    </abc>
                    <span>Toilets with Rating 4-5</span>
                </div>
            </div>
        </div>
        <div class="cols4">
            <div class="wrapBox wrapPink">
                <div class="wrapTitle csr">
                    <abc style="font-size: 5em;padding: 3vw 0vw 0vw 0vw;border-radius: 50%;background-color: white;height: 13vw;width: 13vw;float: left;margin: 3vw 0 0 7vw;color: #144887;">
                        40
                    </abc>
                    <span>Toilets with Rating <=3</span>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container" style="margin: 10vw auto 0 auto;">
    <div class="row" style="height: 10%; margin: 0; margin-top: 1%">
        <form class="form-inline" onsubmit="alert('form-submitted!')">
            <div class="col-md-4 pull-left">
                <div class="form-group">
                    <label for="select_date">Rating</label>
                    <select class="form-control" id="select_date"
                            onchange="handleOnChangeSelectFilter(event.target.value)"
                            name="date_range">
                        <option value="today">&gt;</option>
                        <option value="yesterday">&lt;</option>
                        <option value="lasttwodays">=</option>
                    </select>
                </div>
                <div class="form-group">
                    <select class="form-control" onchange="handleOnChangeSelectFilter(event.target.value)"
                            name="date_range">
                        <option value="today">0</option>
                        <option value="today">1</option>
                        <option value="yesterday">2</option>
                        <option value="lasttwodays">3</option>
                        <option value="lastthreedays">4</option>
                        <option value="lastweek">5</option>
                    </select>
                </div>
            </div>
            <div class="col-md-4 pull-right">
                <div class="form-group">
                    <label for="select_date">Items Per Page</label>
                    <select class="form-control" id="" onchange="handleOnChangeSelectFilter(event.target.value)"
                            name="date_range">
                        <option value="today">10</option>
                        <option value="today">20</option>
                        <option value="today">30</option>
                        <option value="today">40</option>
                        <option value="today">50</option>
                        <option value="today">100</option>
                    </select>
                </div>
            </div>
            <%--<div class="col-md-2 form-group" style="margin: 0">--%>
            <%--<button type="submit" class="btn btn-success">Apply Filter</button>--%>
            <%--</div>--%>
            <%--<div class="col-md-1 form-group" style="margin: 0">--%>
            <%--<button type="submit" class="btn btn-info btn-sm" data-toggle="modal"--%>
            <%--data-target="#mailModal"--%>
            <%--onclick="event.preventDefault()"--%>
            <%-->Mail--%>
            <%--</button>--%>
            <%--</div>--%>

        </form>
    </div>
    <div class="table-responsive" style="height: 100%; overflow-y: auto">
        <table class="table table-hover">
            <thead>
            <tr>
                <th cla>S.No.</th>
                <th>Toilet Name</th>
                <th>Rating</th>
                <th>Reviews</th>
                <th>Type</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="${0}"></c:set>
            <c:forEach items="${reportsList}" var="report">
                <c:set var="count" value="${count+1}"></c:set>
                <tr>
                    <td>${count}</td>
                    <td>
                        <a href='/location-detail-<c:out value="${report.location.id}"></c:out>'>${report.location.name},${report.location.address} </a>
                    </td>
                    <td>${report.placeDetail.rating}</td>
                    <td>
                        <span class="badge">${report.reviewsCount}</span>
                        <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                                data-target="#myModal"
                                onclick="fillModalData('${report.location.name}','${report.location.address}','${report.location.id}')">
                            show
                        </button>
                    </td>
                    <td>${report.location.type}</td>
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
            <div class="modal-body" style="max-height: 500px;overflow-y: scroll;">
                <p>Some text in the modal.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>

<%@include file="footer.jsp" %>
</body>
</html>