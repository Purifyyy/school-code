var map;

function myMap() {
    const directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers: true});
    const directionsService = new google.maps.DirectionsService();
    const distanceService = new google.maps.DistanceMatrixService();
    
    var mapProp = {
        center:new google.maps.LatLng(48.153565, 17.073218),
        zoom:17,
    };

    const markerplace = { lat: 48.153903, lng: 17.073852 };
    map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
    directionsDisplay.setMap(map);
    const contentString =
    '<div id="content">' +
    '<img id="infoimg" src="infoimg.png" alt="fei">' +
    '<h6>Fakulta elektrotechniky a informatiky Slovenskej Technickej Univerzity</h3>' +
    '<div id="bodyContent">' +
    "<p>Zemepisná šírka: 48.153903<br>Zemepisná dĺžka: 17.073852<p>" +
    "</div>" +
    "</div>";

    var windowLatLng = new google.maps.LatLng(48.154203, 17.073852);
    const infoWindow = new google.maps.InfoWindow({
        content: contentString,
        position: windowLatLng,
        maxWidth: 300,
    });
    infoWindow.open(map); 

    const marker = new google.maps.Marker({
        position: markerplace,
        map,
        icon: "fei.png",
    });

    marker.addListener("click", () => {
        infoWindow.open({
            anchor: marker,
            map,
            shouldFocus: false,
       });
    });

    source = document.getElementById("origin-input");
    var sourceAuto = new google.maps.places.Autocomplete(source);

    var rad = document.getElementsByName("form");
    var prev = null;
    for (var i = 0; i < rad.length; i++) {
        rad[i].addEventListener('change', function() {
            if (this !== prev) {
                prev = this;
            }
            var place = sourceAuto.getPlace();
            if(place){
                var from = new google.maps.LatLng(place.geometry.location.lat(), place.geometry.location.lng());
                dest(from,directionsDisplay,directionsService,markerplace,distanceService);
            }
        });
    }
    
    sourceAuto.addListener('place_changed', function() {
        var place = sourceAuto.getPlace();
        var from = new google.maps.LatLng(place.geometry.location.lat(), place.geometry.location.lng());
        dest(from,directionsDisplay,directionsService,markerplace,distanceService);
    });
    
    var placesService = new google.maps.places.PlacesService(map);
    document.getElementById("transit").addEventListener("click", function() {
        var x = new google.maps.LatLng(48.153903,17.073852);
        display_transit(placesService,x);
        document.getElementById("transit").style.visibility = "hidden"; 
    });
    
    const panorama = new google.maps.StreetViewPanorama(
        document.getElementById("pano"),
        {
            position: { lat: 48.153898, lng: 17.074317 },
            pov: {
                heading: -80,
                pitch: 20,
                zoom: 0,
            },
        }
    );
    //map.setStreetView(panorama);
}

function dest(from,directionsDisplay,directionsService,destination,distanceService){
    
    if(document.getElementById('walk').checked) {
        var mode = google.maps.TravelMode.WALKING;
    } else if(document.getElementById('drive').checked){
        var mode = google.maps.TravelMode.DRIVING;
    }
    if(from) {
        var request = {
            origin: from,
            destination: destination,
            travelMode: mode
        };
        directionsService.route(request, function (response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            }
        });

        distanceService.getDistanceMatrix({
            origins: [from],
            destinations: [destination],
            travelMode: mode,
            unitSystem: google.maps.UnitSystem.METRIC,
            avoidHighways: false,
            avoidTolls: false
        }, function (response, status) {
            if (status == google.maps.DistanceMatrixStatus.OK && response.rows[0].elements[0].status != "ZERO_RESULTS") {
                var distance = response.rows[0].elements[0].distance.text;
                var dvDistance = document.getElementById("dvDistance");
                dvDistance.style.visibility = "visible";
                dvDistance.innerHTML = "";
                dvDistance.innerHTML += "Vzdialenosť: " + distance;
            }
        }); 
    }
}

function display_transit(service,place){
    var request = {
        location: place,
        radius: '750',
        type: ['transit_station']
    };
    service.nearbySearch(request, callback);
}

function callback(results, status) {
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            if(results[i].name != "Šafranová") {
                createMarker(results[i]);
            }
        }
    }
}

function createMarker(place) {

    new google.maps.Marker({
        position: place.geometry.location,
        map: map,
        icon: 'https://maps.gstatic.com/mapfiles/transit/iw2/b/bus2.png'
    });
}
