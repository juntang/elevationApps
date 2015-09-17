function placePoint( point, map, index )
{
    new google.maps.Marker({
        position: { lat : point.lat, lng : point.lng },
        map: map,
        title: index + "{" + point.lng + "," + point.lat + "}"
    });
}

function convertBounds( bounds )
{
    var southWest = { "lat" : bounds.getSouthWest().lat(), "lng" : bounds.getSouthWest().lng() };
    var northEast = { "lat" : bounds.getNorthEast().lat(), "lng" : bounds.getNorthEast().lng() };

    return { "southWest" : southWest, "northEast" : northEast };
}

function sendLongLat( map )
{
    var bounds = map.getBounds();
    var convertedBounds = convertBounds( bounds );
    $.ajax({
        url : "/elevationData",
        type: 'GET',
        data : { bounds : JSON.stringify( convertedBounds ), diameter : computeBoundsDistance( map ) },
        success : function( data )
        {
            drawRoads( data, map );
        }
    });
}

function printViewDetails( map )
{
    var bounds = map.getBounds();
    console.log( google.maps.geometry.spherical.computeDistanceBetween (bounds.getSouthWest(), bounds.getNorthEast() ) );
}

function computeBoundsDistance( map )
{
    var bounds = map.getBounds();
    return google.maps.geometry.spherical.computeDistanceBetween ( bounds.getSouthWest(), bounds.getNorthEast() )
}

function drawRoad( road, map )
{
    var points = road.points;
    var line = []
    for ( var index in points )
    {
        line.push( { lat : points[index].lat, lng : points[index].lng } )
    }

    var linePath = new google.maps.Polyline({
        path: line,
        geodesic: false,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 3
    });

    linePath.setMap(map);
}

function drawRoads( roads, map )
{
    for ( var index in roads )
    {
        drawRoad( roads[index], map )
    }
}