package com.elevations.dao;

import com.elevations.models.Bounds;
import com.elevations.models.LngLat;
import com.elevations.models.Road;
import com.elevations.mappers.RoadMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;


import java.util.Iterator;
import java.util.List;

@RegisterMapper( RoadMapper.class )
public abstract class RoadDAO
{
    @SqlQuery("SELECT * FROM planet_osm_roads")
    public abstract Iterator<Road> list();

    @SqlQuery(
            "SELECT ST_asGeoJson( way ) AS roads " +
                    "FROM planet_osm_line " +
                    "WHERE ST_Contains( " +
                    "ST_MakeEnvelope( :lng1, :lat1, :lng2, :lat2, 4326 ), " +
                    "way )" )
    public abstract List<Road> getRoadsInBounds(
            @Bind( "lng1" ) double lng1, @Bind( "lat1" ) double lat1,
            @Bind( "lng2" ) double lng2, @Bind( "lat2" ) double lat2 );

    public abstract void close();

    public List<Road> getRoadsInBounds( Bounds bounds )
    {
        LngLat southWest = bounds.getSouthWest();
        LngLat northEast = bounds.getNorthEast();
        return getRoadsInBounds( southWest.getLng(), southWest.getLat(), northEast.getLng(), northEast.getLat() );
    }

}
