<%@include file="header.jsp" %>

<div class="container" ng-controller="dashboardController" ng-init="init()">
    <div class="row">
        <div class="col-xs-12 col-md-7">
            <div id="map"></div>
        </div>
        <div class="col-xs-12 col-md-3 col-md-offset-1">
            <h2 class="text-center" id="area-name">Delhi</h2>
            <div class="panel panel-default color1">
                <div class="panel-body">
                    <div    class="number counter"
                            value="totalToilets.myValue"
                            to="totalToilets.myTarget"
                            duration="totalToilets.myDuration"
                            effect="totalToilets.myEffect"> {{ totalToilets.myValue | number:0 }}</div>
                    <h2>Total Toilets</h2>
                </div>
            </div>
            <div class="panel panel-default color2">
                <div class="panel-body">
                    <div    class="number counter"
                            value="fiveStarsRated.myValue"
                            to="fiveStarsRated.myTarget"
                            duration="fiveStarsRated.myDuration"
                            effect="fiveStarsRated.myEffect"> {{ fiveStarsRated.myValue | number:0 }}</div>
                    <h2>5 Stars Rated</h2>
                </div>
            </div>
            <div class="panel panel-default color3">
                <div class="panel-body">
                    <div    class="number counter"
                            value="threeOrLessStarsRated.myValue"
                            to="threeOrLessStarsRated.myTarget"
                            duration="threeOrLessStarsRated.myDuration"
                            effect="threeOrLessStarsRated.myEffect"> {{ threeOrLessStarsRated.myValue | number:0 }}</div>
                    <h2>3 or less Stars Rated</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 form-inline">
            <button class="btn btn-color-red opensearch animated flipInX"><span class="glyphicon glyphicon-search"></span></button>
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
                            <button class="btn btn-default open-modal" ng-click="getReviews(location.location.id, 0, 5);$event.stopPropagation();">View</button>
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
                                <slider ng-model="filterModel.ratings" min="filterModel.min" step="1" max="filterModel.max" value="[0,5]" range="true"></slider>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>Filter by Ward</label>
                            <select class="form-control" name="ward" ng-model="filterModel.ward" ng-options="ulb for ulb in ulbList">
                                <option value="">Select Ward</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Filter by Type</label>
                            <select class="form-control" name="type" ng-model="filterModel.type" ng-options="type for type in locationTypes">
                                <option value="">Select Type</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Filter by Period</label>
                            <select class="form-control" name="period" ng-model="filterModel.period" ng-change="changePeriod(filterModel.period)">
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
                            <input type="text" name="fromDate" class="form-control datepicker" ng-model="filterModel.fromDate" ng-disabled="filterModel.period != 'custom'">
                        </div>
                        <div class="form-group">
                            <label>To Date</label>
                            <input type="text" name="toDate" class="form-control datepicker" ng-model="filterModel.toDate" ng-disabled="filterModel.period != 'custom'">
                        </div>
                        <div class="form-group">
                            <div class="col-xs-12 col-md-6">
                                <button class="btn btn-color-blue btn-block" type="submit">Search</button>
                            </div>
                            <div class="col-xs-12 col-md-6">
                                <button class="btn btn-color-red btn-block" type="button" ng-click="clearFilters()">Clear</button>
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
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="gridSystemModalLabel">Reviews</h4>
                </div>
                <div class="modal-body toilet-details">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="list-group review-list" ng-repeat="review in reviews">
                                <a href="#" class="list-group-item">
                                    <h4 class="list-group-item-heading"><img ng-src="{{review.profilePhotoURL}}" class="img-circle" width="50"> {{review.authorName}}</h4>
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
            <img ng-src="{{toiletDetail.location.imageURL}}" class="img-circle" width="100">
            <h2>{{toiletDetail.location.name}}</h2>
            <p>{{toiletDetail.location.address}}</p>
            <p>{{toiletDetail.location.type}}</p>
            <p class="ratings"> {{toiletDetail.averageRating}} <span class="glyphicon glyphicon-star"></span></p>
            <!-- <p> Last Rated on 20 Jun, 2017</p> -->
            <p>{{toiletDetail.reviewsCount}} reviews</p>
            <!-- <p>Ram Sharma</p> -->
        </div>
        <div class="graph-col">

            <div class="tabs-blue">
                <!-- Nav tabs -->
                <ul class="nav nav-pills nav-justified" role="tablist">
                    <li role="presentation" class="active"><a href="#oRatings" aria-controls="oRatings" role="tab" data-toggle="tab">Overall Ratings</a></li>
                    <li role="presentation"><a href="#detailReview" aria-controls="detailReview" role="tab" data-toggle="tab">Reviews</a></li>
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
                                <h4 class="list-group-item-heading"><img ng-src="{{review.profilePhotoURL}}" class="img-circle" width="50"> {{review.authorName}}</h4>
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
</div>


<%@include file="footer-new.jsp" %>
<script type="text/javascript">

    Highcharts.chart('pie-chart', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie'
        },
        title: {
            text: 'Ratings'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            name: 'share',
            colorByPoint: true,
            data: [{
                name: '5 Stars',
                y: 56.33
            }, {
                name: '4 Stars',
                y: 24.03,
                sliced: true,
                selected: true
            }, {
                name: '3 Stars',
                y: 10.38
            }, {
                name: '2 Stars',
                y: 4.77
            }, {
                name: '1 Stars',
                y: 0.93
            }]
        }],
        credits : {
            enabled: false
        }
    });



    $(document).ready(function() {
        $('.opensearch').on('click', function() {
            $('.search-form').addClass('animated flipInX');
            $(this).removeClass('flipInX').addClass('animated flipOutX');
        });

        /*var rangeSlider = $(".rangeSlider").bootstrapSlider();*/

        $("#adv-search").on('click', function() {
            $("#filters").addClass('open');
        });
        $(".close").on('click', function() {
            $("#filters").removeClass('open');
        });

        $('.datepicker').datepicker({format:'dd-mm-yyyy'});

        /*$('#listing-table tr').each(function() {
         $(this).on('click', function() {
         window.location.href = 'toilet-details.html';
         })
         });*/

        //$('.counter').counter();

        $('.open-modal').on('click', function(e) {
            e.stopPropagation()
        });
    });

</script>
<%@include file="header-end.jsp" %>