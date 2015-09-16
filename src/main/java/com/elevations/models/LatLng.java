package com.elevations.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class LatLng
{
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

    public static LatLng parseJson( JsonElement jsonLatLng )
    {
        JsonObject jsonLatLngObject = jsonLatLng.getAsJsonObject();

        if ( !jsonLatLngObject.get( "lat" ).isJsonNull() && !jsonLatLngObject.get( "lng" ).isJsonNull() )
        {
            double lat = jsonLatLngObject.get( "lat" ).getAsDouble();
            double lng = jsonLatLngObject.get( "lng" ).getAsDouble();
            return new LatLng( lat, lng );
        }

        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        LatLng latLng = ( LatLng ) o;

        if ( Double.compare( latLng.m_lat, m_lat ) != 0 ) return false;
        if ( Double.compare( latLng.m_lng, m_lng ) != 0 ) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits( m_lat );
        result = ( int ) ( temp ^ ( temp >>> 32 ) );
        temp = Double.doubleToLongBits( m_lng );
        result = 31 * result + ( int ) ( temp ^ ( temp >>> 32 ) );
        return result;
    }
}
