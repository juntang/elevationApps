package com.downhill.mappers;

import com.downhill.api.*;
import com.downhill.models.LngLat;
import com.downhill.models.Road;;
import com.downhill.models.RoadSegment;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.List;

public class SimpleRoadMapper extends AbstractRoadMapper
{
    protected void setAPIs()
    {
        setAPIs( new SRTMElevationAPI(), new SimpleGradientAPI(), new HaversineDistanceAPI() );
    }

    @Override
    protected Road buildRoad( JsonArray coordinates )
    {
        List<LngLat> points = new ArrayList<LngLat>();
        List<RoadSegment> segments = new ArrayList<RoadSegment>();

        double distance = 0;

        LngLat previousPoint = null;
        for ( int i = 0; i < coordinates.size(); i++ )
        {
            JsonArray coordinate = coordinates.get( i ).getAsJsonArray();
            LngLat currentPoint = buildPoint( coordinate.get( 0 ).getAsDouble(), coordinate.get( 1 ).getAsDouble() );
            points.add( currentPoint );

            if ( previousPoint != null )
            {
                RoadSegment segment = buildSegment( previousPoint, currentPoint );
                segments.add( segment );

                distance += segment.getDistance();
            }

            previousPoint = currentPoint;
        }

        if ( distance < 500 )
        {
            return new Road( new ArrayList<>(), new ArrayList<>() );
        }

        return new Road( points, segments );
    }

    protected LngLat buildPoint( double lng, double lat )
    {
        LngLat point = new LngLat( lng, lat );

        double elevation = m_elevationAPI.getElevation( point );
        point.setElevation( elevation );

        return point;
    }

    protected RoadSegment buildSegment( LngLat start, LngLat end )
    {
        RoadSegment segment = new RoadSegment( start, end );

        double distance = m_distanceAPI.getDistance( start, end );
        segment.setDistance( distance );

        double gradient = m_gradientAPI.getGradient( segment );
        segment.setGradient( gradient );

        return segment;
    }
}
