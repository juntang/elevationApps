package com.elevations.models;

import com.google.gson.JsonElement;

public class Bounds
{
    private LngLat m_southWest;
    private LngLat m_northEast;

    public Bounds( LngLat southWest, LngLat northEast )
    {
        m_southWest = southWest;
        m_northEast = northEast;
    }

    public LngLat getSouthWest()
    {
        return m_southWest;
    }

    public LngLat getNorthEast()
    {
        return m_northEast;
    }

    public static Bounds parseJson( JsonElement bounds )
    {
        JsonElement jsonSouthWest = bounds.getAsJsonObject().get( "southWest" );
        JsonElement jsonNorthEast = bounds.getAsJsonObject().get( "northEast" );

        if ( jsonSouthWest != null && jsonNorthEast != null )
        {
            LngLat southWest = LngLat.parseJson( jsonSouthWest );
            LngLat northEast = LngLat.parseJson( jsonNorthEast );
            return new Bounds( southWest, northEast );
        }

        return null;
    }
}
