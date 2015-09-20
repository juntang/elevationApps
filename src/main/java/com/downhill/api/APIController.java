package com.downhill.api;


import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;


public class APIController
{
    private ElevationAPI m_elevationAPI = new SRTMElevationAPI();
    private GradientAPI m_gradientAPI = new SimpleGradientAPI();
    private DistanceAPI m_distanceAPI = new HaversineDistanceAPI();



    public Road buildRoad( JsonArray coordinates )
    {
        List<LngLat> points = new ArrayList<LngLat>();
        List<RoadSegment> segments = new ArrayList<RoadSegment>();

        LngLat previousPoint = null;
        for ( int i=0; i<coordinates.size(); i++ )
        {
            JsonArray coordinate = coordinates.get( i ).getAsJsonArray();
            LngLat currentPoint = buildPoint( coordinate.get( 0 ).getAsDouble(), coordinate.get( 1 ).getAsDouble() );
            points.add( currentPoint );

            if ( previousPoint != null )
            {
                RoadSegment segment = buildSegment( previousPoint, currentPoint );
                segments.add( segment );
            }

            previousPoint = currentPoint;
        }

        return new Road( points, segments );
    }

    private LngLat buildPoint( double lng, double lat )
    {
        LngLat point = new LngLat( lng, lat );

        double elevation = m_elevationAPI.getElevation( point );
        point.setElevation( elevation );

        return point;
    }

    private RoadSegment buildSegment( LngLat start, LngLat end )
    {
        RoadSegment segment = new RoadSegment( start, end );

        double distance = m_distanceAPI.getDistance( start, end );
        segment.setDistance( distance );

        double gradient = m_gradientAPI.getGradient( segment );
        segment.setGradient( gradient );

        return segment;
    }

}
