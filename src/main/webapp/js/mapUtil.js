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

function drawSegments( road, map, roadIndex )
{
    var segments = road.segments
    for ( var index in segments )
    {
        var segment = segments[index];
        var start = segment.start
        var end = segment.end
        var line = [
            { lat : start.lat, lng : start.lng },
            { lat : end.lat, lng : end.lng }
        ]

        var gradient = segment.gradient

        var details = "End: " + "{" + end.lng + "," + end.lat + "}" + "\n" +
                        "Road: " + roadIndex + "\n" +
                        "Segment: " + index + "\n" +
                        "Start Elevation: " + start.elevation + "\n" +
                        "End Elevation: " + end.elevation + "\n" +
                        "Gradient: " + gradient + "\n" +
                        "Distance: " + segment.distance;

//        drawPoint( start, map, details )

        var color = gradient > 0 ? '#FF0000' : '#0000FF'

        var linePath = new google.maps.Polyline({
            path: line,
            geodesic: true,
            strokeColor: color,
            strokeOpacity: 2.5 * segment.gradient,
            strokeWeight: 3
        });

        linePath.setMap(map);
    }
}

function drawRoads( roads, map )
{
    for ( var index in roads )
    {
        drawSegments( roads[index], map, index )
    }
}

function drawPoint( point, map, description )
{
    new google.maps.Marker({
        position: { lat : point.lat, lng : point.lng },
        map: map,
        title: "{" + point.lng + "," + point.lat + "}" + "\n" + description
    });
}
