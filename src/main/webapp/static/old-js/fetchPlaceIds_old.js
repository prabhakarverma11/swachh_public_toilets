/**
 * Created by prabhakar on 23/6/17.
 */
function fetchPlaceIds(locationsList, googleApiKey) {
    alert("asddsa");
    console.log(locationsList);
    var placeIds = [];
    locationsList.map(function (location) {

            var url = "https://maps.googleapis.com/maps/api/place/radarsearch/json?location=" +
                location.latitude +
                "," +
                location.longitude +
                "&radius=" +
                "5" +
                "&type=" +
                "Public+Bathroom" +
                "&key=" +
                googleApiKey;

            $.ajax({
                url: url,
                crossDomain: true,
                dataType: 'jsonp',
                type: 'get',
                success: function (response) {
                    console.log(location.id, response.results);
                    // placeIds.push({id: item.id, placeId: placeId});
                }
            });


        }
    )
}