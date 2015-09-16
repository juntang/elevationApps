package com.elevations.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;

public class Bounds
{
    private LatLng m_southWest;
    private LatLng m_northEast;

    public Bounds( LatLng southWest, LatLng northEast )
    {
        m_southWest = southWest;
        m_northEast = northEast;
    }

    public LatLng getSouthWest()
    {
        return m_southWest;
    }

    public LatLng getNorthEast()
    {
        return m_northEast;
    }

    public static Bounds parseJson( JsonElement bounds )
    {
        JsonElement jsonSouthWest = bounds.getAsJsonObject().get( "southWest" );
        JsonElement jsonNorthEast = bounds.getAsJsonObject().get( "northEast" );

        if ( jsonSouthWest != null && jsonNorthEast != null )
        {
            LatLng southWest = LatLng.parseJson( jsonSouthWest );
            LatLng northEast = LatLng.parseJson( jsonNorthEast );
            return new Bounds( southWest, northEast );
        }

        return null;
    }
}
