<%@include file="header.jsp" %>
<div class="container">
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" data-interval="8000">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
            <li data-target="#carousel-example-generic" data-slide-to="1"></li>
            <li data-target="#carousel-example-generic" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active">
                <img src="img/Google-Toilet-Locator-21-06-2017-b-07.jpg" alt="...">
                <!-- <div class="carousel-caption">
                  <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                  tempor incididunt ut labore et dolore magna aliqua.</p>
                  <button class="btn btn-color-red btn-lg">Know More</button>
                </div> -->
            </div>
            <div class="item">
                <img src="img/SBM_20x10-01.jpg" alt="...">
                <!-- <div class="carousel-caption">
                  <h3>Lorem ipsum dolor sit amet, consectetur adipisicing elit</h3>
                  <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                  tempor incididunt ut labore et dolore magna aliqua.</p>
                  <button class="btn btn-color-blue btn-lg">Know More</button>
                </div> -->
            </div>
            <div class="item">
                <img src="img/SBM_20x10-02.jpg" alt="...">
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
<div id="main-container">

    <div class="container">
        <div class="row">
            <div class="col-xs-12 col-md-6 col-md-offset-6 box-dark">
                <h2>About Us</h2>
                <h3>Lorem ipsum dolor</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                    tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
                    consequat. </p>

                <a class="btn btn-color-red" role="button" href="#">Know More</a>
            </div>
        </div>
    </div>
</div>

<%@include file="footer-new.jsp" %>
<div class="know-more" data-spy="affix" data-offset-top="100">
    <a href="#main-container">
        <span class="glyphicon glyphicon-menu-down"></span><br>
        <span class="glyphicon glyphicon-menu-down"></span>
    </a>
</div>
<%@include file="header-end.jsp" %>