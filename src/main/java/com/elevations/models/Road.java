package com.elevations.models;

import java.util.Iterator;
import java.util.List;

public class Road
{
    private Long id;

    private double m_gradient;

    private List<LngLat > m_points;

    public Road( List< LngLat > points )
    {
        m_points = points;
    }

    public List<LngLat > getPoints()
    {
        return m_points;
    }

    public boolean isOnRoad( LngLat lngLat )
    {
        return m_points.contains( lngLat );
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
