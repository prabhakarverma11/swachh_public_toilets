<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html ng-app="spt">
<head>
    <title>Swachh Public Toilet</title>
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
            <a class="navbar-brand" href="/admin/review-dashboard"><img src="/static/img/swacch-logo.png" class="img-responsive"></a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="main-nav">
            <ul class="nav navbar-nav main-nav">
                <li class="active"><a href="/">Home</a></li>
                <li><a href="/dashboard">Dashboard</a></li>
                <li><a href="/admin/dashboard">ULB Dashboard</a></li>
                <%--<li><a href="/admin/review-dashboard">Admin</a></li>--%>
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
<div class="">
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="5000">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="/static/img/Main_Page_Image.jpg" alt="...">

            </div>

            <div class="item">
                <img src="/static/img/Google-Toilet-Locator-21-06-2017-b-07.jpg" alt="...">
                <!-- <div class="carousel-caption">
                  <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                  tempor incididunt ut labore et dolore magna aliqua.</p>
                  <button class="btn btn-color-red btn-lg">Know More</button>
                </div> -->
            </div>
            <div class="item">
                <img src="/static/img/SBM_20x10-01.jpg" alt="...">
                <!-- <div class="carousel-caption">
                  <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                  tempor incididunt ut labore et dolore magna aliqua.</p>
                  <button class="btn btn-color-blue btn-lg">Know More</button>
                </div> -->
            </div>
            <div class="item">
                <img src="/static/img/SBM_20x10-02.jpg" alt="...">
                <!-- <div class="carousel-caption">
                  <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                  tempor incididunt ut labore et dolore magna aliqua.</p>
                  <button class="btn btn-color-pink btn-lg">Know More</button>
                </div> -->
            </div>

        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

</div>
<div class="video">
    <img src="/static/img/cancel.png" class="Cancel"/>
    <video width="260px" height="150px" controls autoplay>
        <source src="/static/video/ToiletLocator.mp4" type="video/mp4">
        <source src="/static/video/ToiletLocator.ogg" type="video/ogg">
        <source src="/static/video/ToiletLocator.avi" type="video/avi">
        Your browser does not support the video tag.
    </video>
</div>
<section id="middle-container" class="m-50">
    <!--<div class="container">
    <h1 class="text-center middle-heading">Locating/ Reviewing Toilets on Google Maps</h1>
    </div> end con-->

    <div class="bg-Campaign">
        <h2>About the Campaign</h2>
        <p>In order to increase the usage of public toilets, it is essential for citizens to be able to locate the
            nearest toilet. Further to this, it was envisaged that if the data can be made available on a publically
            available online maps platform, it will provide ease of access to citizens. To facilitate this, MoUD has
            partnered with Google to provide location of toilets on Google Maps Platform and also has collated community
            and public toilet data in 5 cities of NCR (Delhi, Gurgaon, Faridabad, Ghaziabad and Noida. "When you search
            for 'public toilet' on Google Maps in an area where the service is available, you'll see a list of restrooms
            near you, including the respective address and opening hours</p>
        <p class="xs-text"> Campaign Shall start for public from 12th July, 2017 Users will get exciting surprises if
            their feedback is found to be genuine* Cleanest Toilet will be awarded by MoUD at the end of the campaign
            per ULB*</p>
    </div> <!--end left-->

    <div class="bg-Campaign-c">

        <p> Campaign Shall start for public from 12th July, 2017 Users will get exciting surprises if their feedback is
            found to be genuine* Cleanest Toilet will be awarded by MoUD at the end of the campaign per ULB*</p>

    </div>
    <div class="clearfix"></div>
</section>
<section class="m-50">
    <div class="bg-about">
        <h2>About Google Toilet Locator</h2>
        <p>In alignment with National Urban Sanitation Policy, 2008, the Government of India has developed a vision to
            transform Urban India into totally sanitized, healthy and livable towns.
            To achieve this vision, Swachh Bharat Mission (SBM - Urban) has been launched by the Ministry of Urban
            Development (MoUD) on 2nd October 2014 for all 4,041 statutory towns of India.
            One of the objectives of the Swachh Bharat Mission (SBM - Urban) is to provide sanitation through the
            construction of Individual Household/Community & Public toilet facilities across India, to drive towards
            100% Open Defecation Free Cities.
            The Google Toilet Locator was launched by the Hon'ble Minister for Urban Development on 22 December 2016 at
            the National Media Centre, New Delhi.</p>
        <p class="xs-text">The initiative is part of a government plan to improve sanitation and reduce urination and
            defecation in the streets, which is extremely common since more than half of India's 1.2 billion people do
            not have toilets at home and more than five million people in Delhi have to relieve themselves outside.</p>
    </div>

    <div class="bg-about-c">
        <p>The initiative is part of a government plan to improve sanitation and reduce urination and defecation in the
            streets, which is extremely common since more than half of India's 1.2 billion people do not have toilets at
            home and more than five million people in Delhi have to relieve themselves outside.</p>
    </div>
    <div class="clearfix">
</section>
<section id="bottom-section">
    <div class="container">
        <h1 class="text-center" style="clear:both;">How to Locate a Toilet on Google Maps</h1>

        <div class="row">
            <div class="col-md-4 col-sm-4 col-xs-12">
                <div class="Locate">
                    <h5><a href="https://www.google.co.in/maps" style="color:#fff;" target="_blank">Locate Nearest
                        Toilet on <br/> Google Map</a></h5>
                </div>
            </div><!-- end span div-->
            <div class="col-md-4 col-sm-4 col-xs-12">
                <div class="Locate">
                    <h5><a href="https://www.google.co.in/maps" style="color:#fff;" target="_blank"> Rate & <br/> Review
                        it </a></h5>
                </div>
            </div><!-- end span div-->
            <div class="col-md-4 col-sm-4 col-xs-12">
                <div class="Locate">
                    <h5><a href="https://www.google.co.in/maps" style="color:#fff;" target="_blank">Suggest an Edit in
                        Toilet's Details </a></h5>
                </div>
            </div><!-- end span div-->

        </div> <!-- End row div-->

    </div> <!--end con-->

</section>
<!--End Section-->


<%@include file="footer-new.jsp" %>
<div class="know-more" data-spy="affix" data-offset-top="100">
    <a href="#middle-container">
        <span class="glyphicon glyphicon-menu-down"></span><br>
        <span class="glyphicon glyphicon-menu-down"></span>
    </a>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        // Add smooth scrolling to all links
        $("a.know-more").on('click', function (event) {

            // Make sure this.hash has a value before overriding default behavior
            if (this.hash !== "") {
                // Prevent default anchor click behavior
                event.preventDefault();

                // Store hash
                var hash = this.hash;

                // Using jQuery's animate() method to add smooth page scroll
                // The optional number (800) specifies the number of milliseconds it takes to scroll to the specified area
                $('html, body').animate({
                    scrollTop: $(hash).offset().top
                }, 800, function () {

                    // Add hash (#) to URL when done scrolling (default click behavior)
                    //window.location.hash = hash;
                });
            } // End if
        });

        $('.Cancel').click(function () {
            // alert('hello');
            $(".video").css("display", "none");
            $('video').get(0).pause();
        });
    });

</script>
<%@include file="header-end.jsp" %>
