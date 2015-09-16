package com.elevations.models;

import java.util.Set;

public class Road
{
    private Long id;

    private double m_gradient;

    private Set<LatLng > m_points;

    public Road( Set< LatLng > points )
    {
        m_points = points;
    }

    public Set<LatLng > getPoints()
    {
        return m_points;
    }

    public boolean isOnRoad( LatLng latLng )
    {
        return m_points.contains( latLng );
    }
}
