package com.downhill.models;

import java.util.Iterator;
import java.util.List;

public class Road
{
    private List<LngLat> m_points;

    private List<RoadSegment> m_segments;

    public Road( List<LngLat> points, List<RoadSegment> segments )
    {
        m_points = points;
        m_segments = segments;
    }

    public List<LngLat> getPoints()
    {
        return m_points;
    }

    public List<RoadSegment> getSegments()
    {
        return m_segments;
    }

    public boolean isOnRoad( LngLat lngLat )
    {
        return m_points.contains( lngLat );
    }

    public boolean isOnRoad( RoadSegment segment )
    {
        return m_segments.contains( segment );
    }

    public String toString()
    {
        String road = "{";
        Iterator<LngLat> iterator = m_points.iterator();
        while ( iterator.hasNext() )
        {
            road += iterator.next().toString();
            if ( iterator.hasNext() )
            {
                road += ", ";
            }
        }
        road += "}";
        return road;
    }
}
