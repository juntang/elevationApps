package com.downhill.models;

public class RoadSegment
{
    private LngLat m_start;
    private LngLat m_end;
    private double m_gradient;
    private double m_distance;

    public RoadSegment( LngLat start, LngLat end )
    {
        m_start = start;
        m_end = end;
    }

    public LngLat getStart()
    {
        return m_start;
    }

    public LngLat getEnd()
    {
        return m_end;
    }

    public double getGradient()
    {
        return m_gradient;
    }

    public void setGradient( double gradient )
    {
        m_gradient = gradient;
    }

    public double getDistance()
    {
        return m_distance;
    }

    public void setDistance( double distance )
    {
        m_distance = distance;
    }
}
