function placePoint( point, map )
{
    new google.maps.Marker({
        position: { lat : point.lat, lng : point.lng },
        map: map,
        title: "hello"
    });
}

function convertBounds( bounds )
{
    var southWest = { "lat" : bounds.getSouthWest().lat(), "lng" : bounds.getSouthWest().lng() };
    var northEast = { "lat" : bounds.getNorthEast().lat(), "lng" : bounds.getNorthEast().lng() };
    var res = []
    res[0] = southWest;
    res[1] = northEast;

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
            placePoint( data, map );
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