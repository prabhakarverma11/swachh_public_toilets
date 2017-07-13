<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Dashboard</title>

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
    <script src="/static/js/common.util.js"></script>
    <script>

        function fillModalData(locationName, locationAddress, locationId) {
            $(".modal-title").html(locationName + ", " + toTitleCase(locationAddress));

            $.ajax({
                url: window.location.origin + "/fetch-rating-and-reviews/" + locationId,
                dataType: 'json',
                type: 'get',
                success: function (response) {
                    console.log("response", response);
                    var reviews = JSON.parse(response.reviews);
                    var modalBody = "<ul class='list-group'>";

                    var blackStar = '<span class="stars-container stars-0">&#9733;</span>';
                    var whiteStar = '<span class="stars-container stars-0">&#9734;</span>';

                    reviews.forEach(function (review) {
                        var stars = blackStar;
                        switch (parseInt(review.rating)) {
                            case 1: {
                                stars += whiteStar + whiteStar + whiteStar + whiteStar;
                                break;
                            }
                            case 2: {
                                stars += blackStar;
                                stars += whiteStar + whiteStar + whiteStar;
                                break;
                            }
                            case 3: {
                                stars += blackStar + blackStar;
                                stars += whiteStar + whiteStar;
                                break;
                            }
                            case 4: {
                                stars += blackStar + blackStar + blackStar;
                                stars += whiteStar;
                                break;
                            }
                            case 5: {
                                stars += blackStar + blackStar + blackStar + blackStar;
                                break;
                            }
                            default: {
                                break;
                            }
                        }

                        modalBody += "<li class='list-group-item' style='font-size: medium;border: none;height: 9vw;'>" +
                            "<div style='width: 25%;height: 100%;float: left;'>" +
                            "<img src='" + review.profilePhotoURL + "' style='font-size: 4.5em;border-radius: 50%;background-color: rgba(203, 120, 207, 0.55);height: 7vw;w;w;width: 7vw;float: left;margin: 0;'/>" +
                            "</div>" +
                            "<div style='width: 75%;height: 100%;float: right;'>" +
                            "<a href='" + review.authorURL + "' target='_blank'>" + review.authorName + "</a><br>" +
                            stars + "<br>" +
                            review.reviewText +
                            "</div>" +
                            "</li>";
                    });
                    modalBody += "</ul>";

                    $(".modal-body").html(modalBody);
                }
            });
        }

        function formatDate(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [year, month, day].join('-');
        }

        function handleOnChangeSelectFilter(value, startDateString = "", endDateString = "") {

            var currentTime = new Date().getTime();
            var startDate = null;
            var endDate = null;
            $("#start_date").attr("readOnly", true);
            $("#end_date").attr("readOnly", true);
            switch (value) {
                case "today": {
                    startDate = new Date(currentTime);
                    endDate = new Date(currentTime);
                    break;
                }
                case "yesterday": {
                    startDate = new Date(currentTime - 86400000);
                    endDate = new Date(currentTime - 86400000);
                    break;
                }
                case "lasttwodays": {
                    startDate = new Date(currentTime - 86400000);
                    endDate = new Date(currentTime);
                    break;
                }
                case "lastthreedays": {
                    startDate = new Date(currentTime - 86400000 * 2);
                    endDate = new Date(currentTime);
                    break;
                }
                case "lastweek": {
                    startDate = new Date(currentTime - 86400000 * 6);
                    endDate = new Date(currentTime);
                    break;
                }
                case "lasttwoweeks": {
                    startDate = new Date(currentTime - 86400000 * 13);
                    endDate = new Date(currentTime);
                    break;
                }
                case "lastmonth": {
                    startDate = new Date(currentTime - 86400000 * 29);
                    endDate = new Date(currentTime);
                    break;
                }
                case "custom": {
                    $("#start_date").removeAttr("readOnly");
                    $("#end_date").removeAttr("readOnly");

                    startDate = new Date(currentTime);
                    endDate = new Date(currentTime);
                    break;
                }
                default: {
                    startDate = new Date(currentTime);
                    endDate = new Date(currentTime);
                    console.log("invalid selection");
                    break;
                }
            }
            if (value === "custom") {
                $("#start_date").val(startDateString);
                $("#end_date").val(endDateString);
            } else {
                $("#start_date").val(formatDate(startDate));
                $("#end_date").val(formatDate(endDate));
            }
        }

        function setFilter(dateRange, startDate, endDate) {
            $("#select_date").val(dateRange);
            handleOnChangeSelectFilter(dateRange, startDate, endDate);
        }

    </script>
</head>

<body style="height: auto;" onload="setFilter('${dateRange}','${startDate}','${endDate}');">
<%@include file="navigation_old.jsp" %>
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
                    <abc style="font-size: 4.5em;padding: 3vw 0vw 0vw 0vw;border-radius: 50%;background-color: white;height: 13vw;width: 13vw;float: left;margin: 3vw 0 0 7vw;color: #144887;">
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
<div class="container" style="margin: 10vw auto 0 auto;font-size: medium">
    <div class="row" style="">
        <form class="form-inline" action="/dashboard">

            <div class="col-sm-12" style="padding: 0;">
                <div class="col-md-3">
                    <label for="select_date">Rating</label>
                    <select class="form-control" id="select_date"
                            onchange="handleOnChangeSelectFilter(event.target.value)" name="date_range">
                        <option value="today">&gt;</option>
                        <option value="yesterday">&lt;</option>
                        <option value="lasttwodays">=</option>
                    </select>
                    <select class="form-control" onchange="handleOnChangeSelectFilter(event.target.value)"
                            name="date_range">
                        <option value="today">0</option>
                        <option value="today">1</option>
                        <option value="yesterday">2</option>
                        <option value="lasttwodays">3</option>
                        <option value="lastthreedays">4</option>
                        <option value="lastweek">5</option>
                    </select></div>
                <div class="col-md-3">
                    <label for="select_date">Select Range</label>
                    <select class="form-control" onchange="handleOnChangeSelectFilter(event.target.value)"
                            name="date_range">
                        <option value="today">Today</option>
                        <option value="yesterday">Yesterday</option>
                        <option value="lasttwodays">Last 2 Days</option>
                        <option value="lastthreedays">Last 3 Days</option>
                        <option value="lastweek">Last Week</option>
                        <option value="lasttwoweeks">Last 2 Weeks</option>
                        <option value="lastmonth">Last Month</option>
                        <option value="custom">Custom Range</option>
                    </select>
                </div>
                <div class="col-sm-5" style="margin: 0">
                    <label for="start_date">From</label>
                    <input type="date" class="form-control" id="start_date" name="start_date" readonly="">
                    <%--</div>--%>
                    <%--<div class="col-sm-3" style="margin:0;">--%>
                    <label for="end_date">To</label>
                    <input type="date" class="form-control" id="end_date" name="end_date" readonly="">
                </div>
                <div class="col-sm-1" style="margin: 0">
                    <button type="submit" class="btn btn-primary">Apply</button>
                </div>
            </div>


        </form>
    </div>
    <div class="table-responsive" style="">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>S.No.</th>
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
                        <a href='/location-detail-<c:out value="${report.location.id}"></c:out>'>
                            <script>document.write(toTitleCase("${report.location.address}"))</script>
                        </a>
                    </td>
                    <td>${report.placeDetail.rating}</td>
                    <td style="min-width: 10vw;">
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
    <%--<div class="row" style="">--%>
    <%--<form class="form-inline">--%>

    <%--<div class="col-sm-12" style="padding: 0;">--%>
    <%--<div class="col-md-3">--%>
    <%--<label for="select_date">Items Per page</label>--%>
    <%--<select class="form-control"--%>
    <%--onchange="handleOnChangeSelectFilter(event.target.value)" name="date_range">--%>
    <%--<option value="today">10</option>--%>
    <%--<option value="today">20</option>--%>
    <%--<option value="today">30</option>--%>
    <%--<option value="today">40</option>--%>
    <%--<option value="today">50</option>--%>
    <%--<option value="today">100</option>--%>
    <%--<option value="today">200</option>--%>
    <%--</select>--%>
    <%--</div>--%>
    <%--</div>--%>


    <%--</form>--%>
    <%--</div>--%>
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
            <div class="modal-body" style="max-height: 500px;overflow-y: auto;">
                <p>Some text in the modal.</p>
            </div>
            <%--<div class="modal-footer">--%>
            <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
            <%--</div>--%>
        </div>

    </div>
</div>

<%@include file="footer_old.jsp" %>
</body>
</html>