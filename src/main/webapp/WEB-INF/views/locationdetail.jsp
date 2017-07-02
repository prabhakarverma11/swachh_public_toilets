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

    <%--Amcharts--%>
    <script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
    <script src="https://www.amcharts.com/lib/3/pie.js"></script>
    <script src="https://www.amcharts.com/lib/3/plugins/animate/animate.min.js"></script>
    <script src="https://www.amcharts.com/lib/3/plugins/export/export.min.js"></script>
    <link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all"/>
    <script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
    <script>
        /**
         * Define data for each year
         */
        var chartData = {
            "1995": [
                {"sector": "Agriculture", "size": 6.6},
                {"sector": "Mining and Quarrying", "size": 0.6},
                {"sector": "Manufacturing", "size": 23.2},
                {"sector": "Electricity and Water", "size": 2.2},
                {"sector": "Construction", "size": 4.5},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 14.6},
                {"sector": "Transport and Communication", "size": 9.3},
                {"sector": "Finance, real estate and business services", "size": 22.5}],
            "1996": [
                {"sector": "Agriculture", "size": 6.4},
                {"sector": "Mining and Quarrying", "size": 0.5},
                {"sector": "Manufacturing", "size": 22.4},
                {"sector": "Electricity and Water", "size": 2},
                {"sector": "Construction", "size": 4.2},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 14.8},
                {"sector": "Transport and Communication", "size": 9.7},
                {"sector": "Finance, real estate and business services", "size": 22}],
            "1997": [
                {"sector": "Agriculture", "size": 6.1},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 20.9},
                {"sector": "Electricity and Water", "size": 1.8},
                {"sector": "Construction", "size": 4.2},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 13.7},
                {"sector": "Transport and Communication", "size": 9.4},
                {"sector": "Finance, real estate and business services", "size": 22.1}],
            "1998": [
                {"sector": "Agriculture", "size": 6.2},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 21.4},
                {"sector": "Electricity and Water", "size": 1.9},
                {"sector": "Construction", "size": 4.2},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 14.5},
                {"sector": "Transport and Communication", "size": 10.6},
                {"sector": "Finance, real estate and business services", "size": 23}],
            "1999": [
                {"sector": "Agriculture", "size": 5.7},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 20},
                {"sector": "Electricity and Water", "size": 1.8},
                {"sector": "Construction", "size": 4.4},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.2},
                {"sector": "Transport and Communication", "size": 10.5},
                {"sector": "Finance, real estate and business services", "size": 24.7}],
            "2000": [
                {"sector": "Agriculture", "size": 5.1},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 20.4},
                {"sector": "Electricity and Water", "size": 1.7},
                {"sector": "Construction", "size": 4},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.3},
                {"sector": "Transport and Communication", "size": 10.7},
                {"sector": "Finance, real estate and business services", "size": 24.6}],
            "2001": [
                {"sector": "Agriculture", "size": 5.5},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 20.3},
                {"sector": "Electricity and Water", "size": 1.6},
                {"sector": "Construction", "size": 3.1},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.3},
                {"sector": "Transport and Communication", "size": 10.7},
                {"sector": "Finance, real estate and business services", "size": 25.8}],
            "2002": [
                {"sector": "Agriculture", "size": 5.7},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 20.5},
                {"sector": "Electricity and Water", "size": 1.6},
                {"sector": "Construction", "size": 3.6},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.1},
                {"sector": "Transport and Communication", "size": 10.7},
                {"sector": "Finance, real estate and business services", "size": 26}],
            "2003": [
                {"sector": "Agriculture", "size": 4.9},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 19.4},
                {"sector": "Electricity and Water", "size": 1.5},
                {"sector": "Construction", "size": 3.3},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.2},
                {"sector": "Transport and Communication", "size": 11},
                {"sector": "Finance, real estate and business services", "size": 27.5}],
            "2004": [
                {"sector": "Agriculture", "size": 4.7},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 18.4},
                {"sector": "Electricity and Water", "size": 1.4},
                {"sector": "Construction", "size": 3.3},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.9},
                {"sector": "Transport and Communication", "size": 10.6},
                {"sector": "Finance, real estate and business services", "size": 28.1}],
            "2005": [
                {"sector": "Agriculture", "size": 4.3},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 18.1},
                {"sector": "Electricity and Water", "size": 1.4},
                {"sector": "Construction", "size": 3.9},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.7},
                {"sector": "Transport and Communication", "size": 10.6},
                {"sector": "Finance, real estate and business services", "size": 29.1}],
            "2006": [
                {"sector": "Agriculture", "size": 4},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 16.5},
                {"sector": "Electricity and Water", "size": 1.3},
                {"sector": "Construction", "size": 3.7},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 14.2},
                {"sector": "Transport and Communication", "size": 12.1},
                {"sector": "Finance, real estate and business services", "size": 29.1}],
            "2007": [
                {"sector": "Agriculture", "size": 4.7},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 16.2},
                {"sector": "Electricity and Water", "size": 1.2},
                {"sector": "Construction", "size": 4.1},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.6},
                {"sector": "Transport and Communication", "size": 11.2},
                {"sector": "Finance, real estate and business services", "size": 30.4}],
            "2008": [
                {"sector": "Agriculture", "size": 4.9},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 17.2},
                {"sector": "Electricity and Water", "size": 1.4},
                {"sector": "Construction", "size": 5.1},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.4},
                {"sector": "Transport and Communication", "size": 11.1},
                {"sector": "Finance, real estate and business services", "size": 28.4}],
            "2009": [
                {"sector": "Agriculture", "size": 4.7},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 16.4},
                {"sector": "Electricity and Water", "size": 1.9},
                {"sector": "Construction", "size": 4.9},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.5},
                {"sector": "Transport and Communication", "size": 10.9},
                {"sector": "Finance, real estate and business services", "size": 27.9}],
            "2010": [
                {"sector": "Agriculture", "size": 4.2},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 16.2},
                {"sector": "Electricity and Water", "size": 2.2},
                {"sector": "Construction", "size": 4.3},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.7},
                {"sector": "Transport and Communication", "size": 10.2},
                {"sector": "Finance, real estate and business services", "size": 28.8}],
            "2011": [
                {"sector": "Agriculture", "size": 4.1},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 14.9},
                {"sector": "Electricity and Water", "size": 2.3},
                {"sector": "Construction", "size": 5},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 17.3},
                {"sector": "Transport and Communication", "size": 10.2},
                {"sector": "Finance, real estate and business services", "size": 27.2}],
            "2012": [
                {"sector": "Agriculture", "size": 3.8},
                {"sector": "Mining and Quarrying", "size": 0.3},
                {"sector": "Manufacturing", "size": 14.9},
                {"sector": "Electricity and Water", "size": 2.6},
                {"sector": "Construction", "size": 5.1},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 15.8},
                {"sector": "Transport and Communication", "size": 10.7},
                {"sector": "Finance, real estate and business services", "size": 28}],
            "2013": [
                {"sector": "Agriculture", "size": 3.7},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 14.9},
                {"sector": "Electricity and Water", "size": 2.7},
                {"sector": "Construction", "size": 5.7},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.5},
                {"sector": "Transport and Communication", "size": 10.5},
                {"sector": "Finance, real estate and business services", "size": 26.6}],
            "2014": [
                {"sector": "Agriculture", "size": 3.9},
                {"sector": "Mining and Quarrying", "size": 0.2},
                {"sector": "Manufacturing", "size": 14.5},
                {"sector": "Electricity and Water", "size": 2.7},
                {"sector": "Construction", "size": 5.6},
                {"sector": "Trade (Wholesale, Retail, Motor)", "size": 16.6},
                {"sector": "Transport and Communication", "size": 10.5},
                {"sector": "Finance, real estate and business services", "size": 26.5}]
        };


        /**
         * Create the chart
         */
        var currentYear = 1995;
        var chart = AmCharts.makeChart("chartdiv", {
            "type": "pie",
            "theme": "light",
            "dataProvider": [],
            "valueField": "size",
            "titleField": "sector",
            "startDuration": 0,
            "innerRadius": 80,
            "pullOutRadius": 20,
            "marginTop": 30,
            "titles": [{
                "text": "South African Economy"
            }],
            "allLabels": [{
                "y": "54%",
                "align": "center",
                "size": 25,
                "bold": true,
                "text": "1995",
                "color": "#555"
            }, {
                "y": "49%",
                "align": "center",
                "size": 15,
                "text": "Year",
                "color": "#555"
            }],
            "listeners": [{
                "event": "init",
                "method": function (e) {
                    var chart = e.chart;

//                    function getCurrentData() {
//                        var data = chartData[currentYear];
//                        currentYear++;
//                        if (currentYear > 2014)
//                            currentYear = 1995;
//                        return data;
//                    }
//
//                    function loop() {
//                        chart.allLabels[0].text = currentYear;
//                        var data = getCurrentData();
//                        chart.animateData( data, {
//                            duration: 1000,
//                            complete: function() {
//                                setTimeout( loop, 3000 );
//                            }
//                        } );
//                    }
//
//                    loop();
                }
            }],
            "export": {
                "enabled": true
            }
        });
    </script>

    <style>
        @media screen and (max-width: 992px) {
            .container {
                max-width: 990px;
            }
        }
    </style>
    <script src="/static/js/common.util.js"></script>

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

<body onload="setFilter('${dateRange}','${startDate}','${endDate}');">
<%@include file="navigation.jsp" %>
<div class="row" style="height: 80%; margin: 0;overflow: auto">
    <div class="row" style="height:15%; margin: 0;">
        <h1 class="text-center">${location.name},
            <script>document.write(toTitleCase("${location.address}"))</script>
        </h1>
    </div>
    <div class="row" style="height: 10%; margin: 0; margin-top: 1%">
        <form class="form-inline" action='/location-detail-<c:out value="${location.id}"></c:out>'>
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
            <div class="col-md-3 form-group">
                <label for="start_date">Start Date</label>
                <input type="date" class="form-control" id="start_date" name="start_date"
                       value="<%= request.getParameter("start_date") %>">
            </div>
            <div class="col-md-3 form-group">
                <label for="end_date">End Date</label>
                <input type="date" class="form-control" id="end_date" name="end_date"
                       value="<%= request.getParameter("end_date") %>">
            </div>
            <div class="col-md-2 form-group">
                <button type="submit" class="btn btn-success">Apply Filter</button>
            </div>

        </form>
    </div>
    <div class="row" style="height: 70%; margin:0;">
        <div class="col-md-5" style="height: auto">
            <div class="row" style="height: 100%;margin: 0">
                <img src="<c:out value="${location.imageURL}"></c:out>"
                     style="height: 100%;width: 100%;/* min-width: 500px; */">
            </div>
        </div>
        <div class="col-md-3" style="height: auto">
            <div class="row" style="margin: 0">
                <ul class="list-group">
                    <li class="list-group-item">
                        <strong style="font-weight: bold">Overall Rating: </strong>${overallRating}
                    </li>
                    <li class="list-group-item">
                        <strong style="font-weight: bold">Total Reviews: </strong>${totalReviews}
                    </li>
                    <li class="list-group-item">
                        <strong style="font-weight: bold">Rating in Selected Date Range: </strong>${averageRating}
                    </li>
                    <li class="list-group-item">
                        <strong style="font-weight: bold">Reviews Count in Selected Date Range: </strong>${reviewsCount}
                    </li>
                </ul>
            </div>
            <div class="row" style="margin: 0">
                <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                        data-target="#myModal1">Show reviews
                </button>
            </div>
        </div>
        <div class="col-md-4" style="height: auto">
            <div class="row" style="margin: 0">
                <div id="chartdiv" style="width: 100%;height: 500px;"></div>
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
                                Author: <a href='<c:out value="${review.authorURL}"></c:out>'
                                           target='_blank'>${review.authorName}</a>
                                <br>
                                Rating: ${review.rating}
                                <br>
                                Review: ${review.reviewText}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <%--<div class="modal-footer">--%>
                <%--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--%>
                <%--</div>--%>
            </div>

        </div>
    </div>
</div>
<%@include file="footer.jsp" %>
</body>
</html>