/**
 * Created by prabhakar on 24/6/17.
 */
var map;
var infowindow;
var delhiLat = 28.612308;
var delhiLng = 77.231054;
var data = [];

function createMarker(locationsList) {
    console.log(locationsList);

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
        });

        google.maps.event.addListener(marker, 'mouseover', function () {
            infowindow.setContent(location.address);
            infowindow.open(map, this);
        });
    });
}