/**
 * Created by prabhakar on 24/6/17.
 */
var map;
var infowindow;
var delhiLat = 28.612308;
var delhiLng = 77.231054;
var data = [];

function createMarker(locationsList) {
    // console.log(locationsList);

    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 10,
        center: new google.maps.LatLng(delhiLat, delhiLng),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker;

    locationsList.forEach(function (location) {

        marker = new google.maps.Marker({
            position: new google.maps.LatLng(location.latitude, location.longitude),
            map: map
        });

        google.maps.event.addListener(marker, 'click', function () {
            infowindow.setContent(
                '<p style="font-size: medium;">' +
                '<strong style="font-weight: bold">' + 'Name: ' + '</strong>' +
                location.name +
                '<br>' +
                '<strong style="font-weight: bold">' + 'Address: ' + '</strong>' +
                location.address +
                '</p>'
            );
            infowindow.open(map, this);
            $("#div_location").html('<strong style="font-weight: bold">Location : </strong>' +
                location.name + ', ' + location.address);
            fillRatingAndReviewsOnClick(location.id);
        });

        google.maps.event.addListener(marker, 'mouseover', function () {
            infowindow.setContent(location.address);
            infowindow.open(map, this);
        });
    });
}

function fillRatingAndReviewsOnClick(locationId) {

    $.ajax({
        url: window.location.origin + "/fetch-rating-and-reviews/" + locationId,
        dataType: 'json',
        type: 'get',
        success: function (response) {
            console.log("response", response);
            var rating = JSON.parse(response.rating);
            var reviews = JSON.parse(response.reviews);
            if (rating)
                $("#overall_rating").find("span").text(rating);
            else
                $("#overall_rating").find("span").text("N.A.");

            var listOfReviewsToRender = "";

            reviews.forEach(function (review) {
                listOfReviewsToRender += '<li class="list-group-item" style="border: 1px solid grey">' +
                    '<div>' +
                    '<strong style="font-weight: bold">Author: </strong>' +
                    '<a href="' + review.authorURL + '" target="_blank">' + review.authorName + '</a>' +
                    '</div>' +
                    '<div><strong style="font-weight: bold">Rating: </strong>' + review.rating + '</div>' +
                    '<div><strong style="font-weight: bold">Review: </strong>' + review.reviewText + '</div>' +
                    '</li>';
            });
            if (reviews.length === 0) {
                listOfReviewsToRender += '<li class="list-group-item"><div>This toilet is not yet rated</div></li>'
            }
            $("#reviews").find("ul").html(listOfReviewsToRender);
        }
    });

}