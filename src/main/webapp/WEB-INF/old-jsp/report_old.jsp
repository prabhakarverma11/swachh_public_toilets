<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Report</title>

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
        function formatDate(date) {
            var d = new Date(date),
                month = '' + (d.getMonth() + 1),
                day = '' + d.getDate(),
                year = d.getFullYear();

            if (month.length < 2) month = '0' + month;
            if (day.length < 2) day = '0' + day;

            return [year, month, day].join('-');
        }

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

        function autoFillUsers() {
            var url = "${pageContext.request.contextPath}/search-users-list";

            $.ajax({
                url: url,
                dataType: 'json',
                success: function (response) {
                    console.log("response", response);

                    $("#email").autocomplete({
                        minLength: 2,
                        source: response,
                        autoFocus: true,
                        select: function (event, ui) {
                            var val1 = ui.item.id;
                            var val2 = ui.item.label;
                            console.log("ui.item", ui.item);
                            <%--$("users_email_search_form").attr("action","listcampaign?userId="+val1+"&rows=${rows}");--%>
                        }
                    });
                },
                type: "GET",
                error: function (e) {
                    alert("An error occurred!!! excepetion: " + e);
                }
            });
        }

    </script>
</head>

<body style="height: auto;"
      onload="setFilter('${dateRange}','${startDate}','${endDate}');">
<%@include file="navigation_old.jsp" %>
<div class="row" style="height: 80%; margin: 0">
    <div class="row" style="height: 10%; margin: 0; margin-top: 1%">
        <form class="form-inline" action="/report">
            <div class="col-md-3 form-group">
                <label for="select_date">Select Range</label>
                <select class="form-control" id="select_date" onchange="handleOnChangeSelectFilter(event.target.value)"
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
            <div class="col-md-3 form-group" style="margin: 0">
                <label for="start_date">Start Date</label>
                <input type="date" class="form-control" id="start_date" name="start_date"
                       readonly>
            </div>
            <div class="col-md-3 form-group" style="margin:0;">
                <label for="end_date">End Date</label>
                <input type="date" class="form-control" id="end_date" name="end_date"
                       readonly>
            </div>
            <div class="col-md-2 form-group" style="margin: 0">
                <button type="submit" class="btn btn-success">Apply Filter</button>
            </div>
            <div class="col-md-1 form-group" style="margin: 0">
                <button type="submit" class="btn btn-info btn-sm" data-toggle="modal"
                        data-target="#mailModal"
                        onclick="event.preventDefault()"
                >Mail
                </button>
            </div>

        </form>
    </div>
    <div class="row" style="height: 90%; overflow-y: scroll; margin: 0">
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
                        <td>
                            <a href='/location-detail-<c:out value="${report.location.id}"></c:out>'>${report.location.name}</a>
                        </td>
                        <td>${report.location.address}</td>
                        <td>${report.location.country}</td>
                        <td>${report.location.type}</td>
                        <td>${report.placeDetail.rating}</td>
                        <td>
                            <span class="badge">${report.reviewsCount}</span>
                            <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                                    data-target="#myModal"
                                    onclick="fillModalData('${report.location.name}','${report.location.address}','${report.location.id}')">
                                show
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
                <div class="modal-body" style="max-height: 500px;overflow-y: scroll;">
                    <p>Some text in the modal.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>

        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="mailModal" role="dialog">
        <div class="modal-dialog">
            <form class="form form-vertical" action="" role="form" name="usersemailsearchform"
                  id="users_email_search_form">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Email This Report</h4>
                    </div>
                    <div class="modal-body" style="max-height: 500px;overflow-y: scroll;">
                        <input type="text" id="email" placeholder="Search"
                               class="form-control" name="email" onkeyup="autoFillUsers()" onkeydown="autoFillUsers()">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-default">Send</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<%@include file="footer_old.jsp" %>
</body>
</html>