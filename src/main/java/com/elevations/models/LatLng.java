package com.elevations.models;

public class LatLng
{
    private int id;

    private double m_lat;
    private double m_lng;

    public LatLng( LatLng latLng )
    {
        m_lat = latLng.m_lat;
        m_lng = latLng.m_lng;
    }

    public LatLng( double lat, double lng )
    {
        m_lat = lat;
        m_lng = lng;
    }

    public double getLat()
    {
        return m_lat;
    }

    public double getLng()
    {
        return m_lng;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        LatLng latLng = ( LatLng ) o;

        if ( id != latLng.id ) return false;
        if ( Double.compare( latLng.m_lat, m_lat ) != 0 ) return false;
        if ( Double.compare( latLng.m_lng, m_lng ) != 0 ) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits( m_lat );
        result = 31 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
        temp = Double.doubleToLongBits( m_lng );
        result = 31 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
        return result;
    }
}
