package com.elevations.dao;

import com.elevations.models.Bounds;
import com.elevations.models.LatLng;
import com.elevations.models.Road;
import com.elevations.mappers.RoadMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Define;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;


import java.util.Iterator;
import java.util.List;

@UseStringTemplate3StatementLocator
@RegisterMapper( RoadMapper.class )
public abstract class RoadDAO
{
    @SqlQuery("SELECT * FROM planet_osm_roads")
    public abstract Iterator<Road> list();

    @SqlQuery(
        "SELECT ST_asGeoJson( way ) AS roads " +
        "FROM planet_osm_roads " +
        "WHERE ST_DWithin( way, ST_GeomFromText( \'POINT( <lat> <lng> )\', ST_SRID( way ) ), :radius )" )
    public abstract List<Road> getRoadsNearPoint( @Define( "lat" ) double lat, @Define( "lng" ) double lng, @Bind(
            "radius" ) int radius );

    @SqlQuery(
            "SELECT ST_asGeoJson( way ) AS roads " +
                    "FROM planet_osm_roads " +
                    "WHERE ST_Within( " +
                    "ST_MakeEnvelope( :lat1, :lng1, :lat2, :lng2), " +
                    "way )" )
    public abstract List<Road> getRoadsInBounds(
            @Bind( "lat1" ) double lat1, @Bind( "lng1" ) double lng1,
            @Bind( "lat2" ) double lat2, @Bind( "lng2" ) double lng2 );

    public abstract void close();

    public List<Road> getRoadsInBounds( Bounds bounds )
    {
        LatLng southWest = bounds.getSouthWest();
        LatLng northEast = bounds.getNorthEast();
        return getRoadsInBounds( southWest.getLat(), southWest.getLng(), northEast.getLat(), northEast.getLng() );
    }

}
