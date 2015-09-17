package com.elevations.models;

import java.util.Set;

public class Road
{
    private Long id;

    private double m_gradient;

    private Set<LngLat > m_points;

    public Road( Set< LngLat > points )
    {
        m_points = points;
    }

    public Set<LngLat > getPoints()
    {
        return m_points;
    }

    public boolean isOnRoad( LngLat lngLat )
    {
        return m_points.contains( lngLat );
    }
}
