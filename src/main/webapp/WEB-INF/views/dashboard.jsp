<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="spt">
<head>
    <title>GTL</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,500,600,700" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/static/css/styles.min.css' />">
    <script src="<c:url value='/static/js/main.min.js'/>" type="text/javascript"></script>
    <script src="<c:url value='/static/js/app.min.js'/>" type="text/javascript"></script>
    <%--<script src="http://localhost:8081/build/js/app.min.js" type="text/javascript"></script>--%>
</head>
<body>
<nav class="navbar navbar-default nav-dark" data-spy="affix" data-offset-top="60">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#main-nav"
                    aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"><img src="/static/img/swacch-logo.png" class="img-responsive"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="main-nav">
            <ul class="nav navbar-nav main-nav">
                <li><a href="/">Home</a></li>
                <li class="active"><a href="/dashboard">Dashboard</a></li>
                <li><a href="/admin/dashboard">ULB Dashboard</a></li>
                <li><a href="/admin/review-dashboard">Admin</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <%--<li><a href="/login">Login <span class="glyphicon glyphicon-log-in"></span></a></li>--%>
                <!-- <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                  <ul class="dropdown-menu">
                    <li><a href="#">Action</a></li>
                    <li><a href="#">Another action</a></li>
                    <li><a href="#">Something else here</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="#">Separated link</a></li>
                  </ul>
                </li> -->
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container" ng-controller="dashboardController" ng-init="init()" ng-cloak>
    <div class="row">
        <div class="col-xs-12 col-md-12 text-center">
            <h2 class="text-center" id="area-name">{{selectedUlb == 'All Wards' ? 'Delhi' : selectedUlb}}</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12 col-md-6">
            <div id="map" ng-show="!gmap"></div>
            <div id="map1" ng-show="gmap">
                <div map-lazy-load="https://maps.google.com/maps/api/js"
                     map-lazy-load-params="{{googleMapsUrl}}" style="height: 100%;">
                    <map center="[{{latcenter}}, {{longcenter}}]" zoom="11" style="height: 100%;">
                        <custom-control id="home" position="TOP_RIGHT" index="1" ng-click="gmap = !gmap">
                            <div style="background-color: white;padding:10px;margin-top:10px; cursor:pointer;">
                                <span class="glyphicon glyphicon-chevron-left"></span><b>Back</b>
                            </div>
                        </custom-control>
                        <marker position="[{{location.location.latitude}}, {{location.location.longitude}}]" ng-repeat="location in locationData"></marker>
                    </map>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-md-3">
            <h3 class="text-center">Overall</h3>
            <div class="panel panel-default color1">
                <div class="panel-body">
                    <div class="number counter"
                         value="totalToilets.myValue"
                         to="totalToilets.myTarget"
                         duration="totalToilets.myDuration"
                         effect="totalToilets.myEffect"> {{ totalToilets.myValue | number:0 }}
                    </div>
                    <h2>Total Toilets</h2>
                </div>
            </div>
            <div class="panel panel-default color2">
                <div class="panel-body">
                    <div class="number counter"
                         value="fourToFiveStarsRated.myValue"
                         to="fourToFiveStarsRated.myTarget"
                         duration="fourToFiveStarsRated.myDuration"
                         effect="fourToFiveStarsRated.myEffect"> {{ fourToFiveStarsRated.myValue | number:0 }}
                    </div>
                    <h2>Most Rated (4-5 <i class="glyphicon glyphicon-star" aria-hidden="true"></i>)</h2>
                </div>
            </div>
            <div class="panel panel-default color3">
                <div class="panel-body">
                    <div class="number counter"
                         value="threeOrLessStarsRated.myValue"
                         to="threeOrLessStarsRated.myTarget"
                         duration="threeOrLessStarsRated.myDuration"
                         effect="threeOrLessStarsRated.myEffect"> {{ threeOrLessStarsRated.myValue | number:0 }}
                    </div>
                    <h2>Least Rated (0-3 <i class="glyphicon glyphicon-star" aria-hidden="true"></i>)</h2>
                </div>
            </div>
        </div>
        <div class="col-xs-12 col-md-3">
            <h3 class="text-center">Yesterday</h3>
            <div class="panel panel-default color1">
                <div class="panel-body">
                    <div class="number counter"
                         value="totalToiletsYesterday.myValue"
                         to="totalToiletsYesterday.myTarget"
                         duration="totalToiletsYesterday.myDuration"
                         effect="totalToiletsYesterday.myEffect"> {{ totalToiletsYesterday.myValue | number:0 }}
                    </div>
                    <h2>Total Toilets </h2>
                </div>
            </div>
            <div class="panel panel-default color2">
                <div class="panel-body">
                    <div class="number counter"
                         value="fourToFiveStarsRatedYesterday.myValue"
                         to="fourToFiveStarsRatedYesterday.myTarget"
                         duration="fourToFiveStarsRatedYesterday.myDuration"
                         effect="fourToFiveStarsRatedYesterday.myEffect"> {{ fourToFiveStarsRatedYesterday.myValue |
                        number:0 }}
                    </div>
                    <h2>Most Rated (4-5 <i class="glyphicon glyphicon-star" aria-hidden="true"></i>)</h2>
                </div>
            </div>
            <div class="panel panel-default color3">
                <div class="panel-body">
                    <div class="number counter"
                         value="threeOrLessStarsRatedYesterday.myValue"
                         to="threeOrLessStarsRatedYesterday.myTarget"
                         duration="threeOrLessStarsRatedYesterday.myDuration"
                         effect="threeOrLessStarsRatedYesterday.myEffect"> {{ threeOrLessStarsRatedYesterday.myValue |
                        number:0 }}
                    </div>
                    <h2>Least Rated (0-3 <i class="glyphicon glyphicon-star" aria-hidden="true"></i>)</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 form-inline">
            <button class="btn btn-color-red opensearch animated flipInX"><span
                    class="glyphicon glyphicon-search"></span></button>
            <div class="search-form animated flipOutX">
                <div class="input-group">
                      <span class="input-group-btn">
                        <button class="btn btn-color-red" type="button"><span class="glyphicon glyphicon-search"></span></button>
                      </span>
                    <input type="text" class="form-control" placeholder="Search for address">

                </div><!-- /input-group -->
                <button class="btn btn-color-blue" id="adv-search">Advanced Search</button>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <div class="table-responsive">
                <table class="table table-bordered numeric-table table-striped table-hover" id="listing-table">
                    <thead>
                    <tr>
                        <th>Sr. No.</th>
                        <!-- <th>Name</th> -->
                        <th>Address</th>
                        <th>Type</th>
                        <th>Ratings</th><!--
                                <th>Last Rated on</th>
                                <th>Assigned To</th> -->
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="location in locationData" ng-click="showToiletDetails(location)">
                        <td>
                            {{((metaData.page-1)*10) + $index+1}}
                        </td>
                        <!-- <td>
                            {{location.location.name}}
                        </td> -->
                        <td>
                            {{location.location.address}}
                        </td>
                        <td>
                            {{location.location.type}}
                        </td>
                        <td>
                            {{location.averageRating || 'NA'}}
                            <button class="btn btn-default open-modal"
                                    ng-click="getReviews(location.location.id, 0, 5);$event.stopPropagation();">View
                            </button>
                        </td><!--
                                <td>
                                    20 Jun, 2017
                                </td>
                                <td>
                                    Ram Sharma
                                </td> -->
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <nav aria-label="" class="text-center">
                <ul class="pagination pagination-lg pagination-base">
                    <!-- <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                    <li class="active"><a href="#">1 <span class="sr-only">(current)</span></a></li>
                    <li><a href="#">2 <span class="sr-only">2</span></a></li>
                    <li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li> -->

                    <!-- first -->
                    <li ng-class="{'disabled':currentPage == 1}" ng-click="getData(1)">
                        <a href="javascript:void(0);" aria-label="First"><span aria-hidden="true">&laquo;</span></a>
                    </li>

                    <!-- prev -->
                    <li ng-class="{'disabled':currentPage == 1}" ng-click="getData(currentPage-1)">
                        <a href="javascript:void(0);" aria-label="Previous"><span aria-hidden="true">&lsaquo;</span></a>
                    </li>

                    <!-- numbers -->
                    <li ng-repeat="i in pages" ng-class="{'active': i == currentPage}" ng-click="getData(i)">
                        <a href="javascript:void(0);">
                            {{ i}}<span class="sr-only">2</span>
                        </a>
                    </li>

                    <!-- next -->
                    <li ng-class="{'disabled':currentPage == totalPages}" ng-click="getData(currentPage+1)">
                        <a href="javascript:void(0);" aria-label="Next"><span aria-hidden="true">&rsaquo;</span></a>
                    </li>
                    <!-- last -->
                    <li ng-class="{'disabled':currentPage == totalPages}" ng-click="getData(totalPages)">
                        <a href="javascript:void(0);" aria-label="Last"><span aria-hidden="true">&raquo;</span></a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>


    <div id="filters">
        <div class="row">
            <div class="col-xs-12">
                <div class="close"><span class="glyphicon glyphicon-remove"></span></div>
                <div class="filters">
                    <h3>Advanced Search</h3>
                    <form ng-submit="filterData(filterForm)" name="filterForm" novalidate>
                        <div class="form-group">
                            <label>Ratings</label>
                            <div>
                                <slider ng-model="filterModel.ratings" min="filterModel.min" step="1"
                                        max="filterModel.max" value="[0,5]" range="true"></slider>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Filter by ULB</label>
                            <select class="form-control" name="ward" ng-model="filterModel.ward"
                                    ng-options="ulb for ulb in ulbList">
                                <option value="">Select ULB</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Filter by Type</label>
                            <select class="form-control" name="type" ng-model="filterModel.type"
                                    ng-options="type for type in locationTypes">
                                <option value="">Select Type</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Filter by Period</label>
                            <select class="form-control" name="period" ng-model="filterModel.period"
                                    ng-change="changePeriod(filterModel.period)">
                                <option value="">Select Period</option>
                                <option value="today">Today</option>
                                <option value="yesterday">Yesterday</option>
                                <option value="lastWeek">Past Week</option>
                                <option value="lastMonth">Past Month</option>
                                <option value="custom">Select Dates</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>From Date</label>
                            <input type="text" name="fromDate" class="form-control datepicker"
                                   ng-model="filterModel.fromDate" ng-disabled="filterModel.period != 'custom'">
                        </div>
                        <div class="form-group">
                            <label>To Date</label>
                            <input type="text" name="toDate" class="form-control datepicker"
                                   ng-model="filterModel.toDate" ng-disabled="filterModel.period != 'custom'">
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12 col-md-6">
                                <button class="btn btn-color-blue btn-block" type="submit">Search</button>
                            </div>
                            <div class="col-xs-12 col-md-6">
                                <button class="btn btn-color-red btn-block" type="button" ng-click="clearFilters()">
                                    Clear
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel" id="reviews">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">Reviews</h4>
                </div>
                <div class="modal-body toilet-details">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="list-group review-list" ng-repeat="review in reviews">
                                <a href="#" class="list-group-item">
                                    <h4 class="list-group-item-heading"><img ng-src="{{review.profilePhotoURL}}"
                                                                             class="img-circle" width="50">
                                        {{review.authorName}}</h4>
                                    <p class="list-group-item-text">{{review.reviewText}}</p>
                                    <div class="ratings">
                                        {{review.rating}} <span class="glyphicon glyphicon-star"></span>
                                    </div>
                                </a>
                            </div>
                            <div ng-if="!reviews.length" class="text-center">
                                <h4>No Reviews</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" ng-if="reviews.length > 5">
                    <button type="button" class="btn btn-default" data-dismiss="modal">&lsaquo;</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">&rsaquo;</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <div class="details-popup" ng-hide="!showDetails">
        <div class="close" ng-click="closeDetails()">
            <span class="glyphicon glyphicon-remove"></span>
        </div>
        <div class="info-col">
            <img ng-src="{{toiletDetail.location.imageURL}}" class="img-circle" width="100" style="width: 70%;">
            <h2>{{toiletDetail.location.name}}</h2>
            <p>{{toiletDetail.location.address}}</p>
            <p>{{toiletDetail.location.type}} <a data-toggle="modal" data-target="#editLocationType"
                                                 style="cursor: pointer">Change Type</a></p>

            <p>{{toiletDetail.placeULBMap.ULBName}} <a data-toggle="modal" data-target="#editULB"
                                                       style="cursor: pointer">Change ULB</a></p>

            <p class="ratings"> {{toiletDetail.averageRating}} <span class="glyphicon glyphicon-star"></span></p>
            <!-- <p> Last Rated on 20 Jun, 2017</p> -->
            <p>{{toiletDetail.reviewsCount}} reviews</p>
            <!-- <p>Ram Sharma</p> -->
        </div>
        <div class="graph-col">

            <div class="tabs-blue">
                <!-- Nav tabs -->
                <ul class="nav nav-pills nav-justified" role="tablist">
                    <li role="presentation" class="active"><a href="#oRatings" aria-controls="oRatings" role="tab"
                                                              data-toggle="tab">Overall Ratings</a></li>
                    <li role="presentation"><a href="#detailReview" aria-controls="detailReview" role="tab"
                                               data-toggle="tab">Reviews</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="oRatings">
                        <div id="pie-chart">

                        </div>
                    </div>
                    <div role="tabpanel toilet-details" class="tab-pane" id="detailReview">
                        <div class="list-group review-list" ng-repeat="review in reviews">
                            <a href="#" class="list-group-item">
                                <h4 class="list-group-item-heading"><img ng-src="{{review.profilePhotoURL}}"
                                                                         class="img-circle" width="50">
                                    {{review.authorName}}</h4>
                                <p class="list-group-item-text">{{review.reviewText}}</p>
                                <div class="ratings">
                                    {{review.rating}} <span class="glyphicon glyphicon-star"></span>
                                </div>
                            </a>
                        </div>
                        <div ng-if="!reviews.length" class="text-center">
                            <h4>No Reviews</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" role="dialog" tabindex="-1" id="editLocationType">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Edit Location Type</h4>
                </div>
                <div class="modal-body toilet-details">
                    <div class="">
                        <form class="form-horizontal" onsubmit="saveLocationType();">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                                   class="form-control zero-radius"/>
                            <input type="hidden" name="locationId" id="locationId"
                                   value="{{toiletDetail.location.id}}">

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="locationType">Correct Type:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="locationType" name="locationType"
                                            ng-model="filterModel.type"
                                            ng-options="type for type in locationTypes" required>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="category">You are:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="category" name="category" required>
                                        <option value="ULB">ULB</option>
                                        <option value="Citizen">Citizen</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="name">Name:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="name" placeholder="Enter name"
                                           name="name" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-sm-2" for="phone">Phone:</label>
                                <div class="col-sm-10">
                                    <input type="number" class="form-control" id="phone"
                                           placeholder="Enter phone number" name="phone" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="email">Email:</label>
                                <div class="col-sm-10">
                                    <input type="email" class="form-control" id="email" placeholder="Enter email"
                                           name="email">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-sm-offset-2 col-sm-4">
                                    <button type="submit" class="form-control btn btn-success">
                                        Save
                                    </button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" role="dialog" tabindex="-1" id="editULB">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Edit ULB</h4>
                </div>
                <div class="modal-body toilet-details">
                    <div class="">
                        <form class="form-horizontal" onsubmit="saveULBType();">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                                   class="form-control zero-radius"/>
                            <input type="hidden" name="locationId" id="locationId2"
                                   value="{{toiletDetail.location.id}}">

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="locationType">Correct ULB:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" name="ulbName" ng-model="filterModel.ward"
                                            ng-options="ulb for ulb in ulbList" id="ulbName">
                                        <option value="">Select ULB</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="category">You are:</label>
                                <div class="col-sm-10">
                                    <select class="form-control" id="category2" name="category" required>
                                        <option value="ULB">ULB</option>
                                        <option value="Citizen">Citizen</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="name">Name:</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="name2" placeholder="Enter name"
                                           name="name" required>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-sm-2" for="phone">Phone:</label>
                                <div class="col-sm-10">
                                    <input type="number" class="form-control" id="phone2"
                                           placeholder="Enter phone number" name="phone" required>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="email">Email:</label>
                                <div class="col-sm-10">
                                    <input type="email" class="form-control" id="email2" placeholder="Enter email"
                                           name="email">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-sm-offset-2 col-sm-4">
                                    <button type="submit" class="form-control btn btn-success">
                                        Save
                                    </button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>


<%@include file="footer-new.jsp" %>
<script>
    $("select#category").on("change", function () {
        $("input#name").val("");
        $("input#phone").val("");
        $("input#email").val("");
        if ($(this).val() === "ULB") {
            $("input#name").removeAttr("readOnly");
            $("input#phone").removeAttr("readOnly");
            $("input#email").removeAttr("readOnly");
        } else {
            $("input#name").attr("readOnly", true);
            $("input#phone").attr("readOnly", true);
            $("input#email").attr("readOnly", true);
        }
    });
    $("select#category2").on("change", function () {
        $("input#name2").val("");
        $("input#phone2").val("");
        $("input#email2").val("");
        if ($(this).val() === "ULB") {
            $("input#name2").removeAttr("readOnly");
            $("input#phone2").removeAttr("readOnly");
            $("input#email2").removeAttr("readOnly");
        } else {
            $("input#name2").attr("readOnly", true);
            $("input#phone2").attr("readOnly", true);
            $("input#email2").attr("readOnly", true);
        }
    });

    function saveLocationType() {
        var data = {};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        data["locationId"] = $("#locationId").val();
        data["locationType"] = $("#locationType").val();
        data["category"] = $("#category").val();
        data["name"] = $("#name").val();
        data["phone"] = $("#phone").val();
        data["email"] = $("#email").val();
        $.ajax({
            url: "/api/save-location-type/",
            dataType: 'json',
            type: 'POST',
            data: data,
            success: function (response) {
                console.log("response", response);
                $('#editLocationType').modal('toggle');
                alert("Thanks for you help, the admin panel will review it.")
            }
        });
    }
    function saveULBType() {
        var data = {};
        data["${_csrf.parameterName}"] = "${_csrf.token}";
        data["locationId"] = $("#locationId2").val();
        data["ulbName"] = $("#ulbName").val();
        data["category"] = $("#category2").val();
        data["name"] = $("#name2").val();
        data["phone"] = $("#phone2").val();
        data["email"] = $("#email2").val();
        $.ajax({
            url: "/api/save-ulb-name/",
            dataType: 'json',
            type: 'POST',
            data: data,
            success: function (response) {
                console.log("response", response);
                $('#editULB').modal('toggle');
                alert("Thanks for you help, the admin panel will review it.")
            }
        });
    }
</script>
<script src="https://code.highcharts.com/mapdata/countries/in/custom/in-all-disputed.js"></script>
<script type="text/javascript">


    $(document).ready(function () {
        $('.opensearch').on('click', function () {
            $('.search-form').addClass('animated flipInX');
            $(this).removeClass('flipInX').addClass('animated flipOutX');
        });

        /*var rangeSlider = $(".rangeSlider").bootstrapSlider();*/

        $("#adv-search").on('click', function () {
            $("#filters").addClass('open');
        });
        $(".close").on('click', function () {
            $("#filters").removeClass('open');
        });

        $('.datepicker').datepicker({format: 'dd-mm-yyyy'});

        /*$('#listing-table tr').each(function() {
         $(this).on('click', function() {
         window.location.href = 'toilet-details.html';
         })
         });*/

        //$('.counter').counter();

        $('.open-modal').on('click', function (e) {
            e.stopPropagation()
        });
    });

</script>
<%@include file="header-end.jsp" %>