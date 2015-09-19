package com.downhill.api;

import com.downhill.api.elevations.SelectionHeuristic;
import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;
import com.downhill.util.LngLatUtil;

import java.util.List;

/**
 * Simple gradient api goes through every point to calculate the gradients on a road
 */
public class SimpleGradientAPI extends AbstractGradientAPI
{
    public SimpleGradientAPI( ElevationAPI elevationAPI )
    {
        super( elevationAPI );
    }

    @Override
    public void setGradient( RoadSegment segment )
    {
        LngLat start = segment.getStart();
        LngLat end = segment.getEnd();

        double startElevation = start.getElevation();
        double endElevation = end.getElevation();

        double distance = LngLatUtil.distance( start.getLat(), end.getLat(), start.getLng(), end.getLng(),
                startElevation, endElevation );

        double gradient = ( startElevation - endElevation )/distance;

        segment.setGradient( gradient );
        segment.setDistance( distance );
    }

    @Override
    public void setGradients( Road road, SelectionHeuristic heuristic )
    {
        heuristic.setRoad( road );
        List<RoadSegment> segments = road.getSegments();
        for ( RoadSegment segment : segments )
        {
            if ( heuristic.isNextSegment( segment ) )
            {
                m_elevationAPI.setElevations( segment );
                setGradient( segment );
            }
        }
    }
}
