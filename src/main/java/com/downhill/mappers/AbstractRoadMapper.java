package com.downhill.mappers;

import com.downhill.api.DistanceAPI;
import com.downhill.api.ElevationAPI;
import com.downhill.api.GradientAPI;
import com.downhill.models.Road;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractRoadMapper implements ResultSetMapper<Road>
{
    protected ElevationAPI m_elevationAPI;
    protected GradientAPI m_gradientAPI;
    protected DistanceAPI m_distanceAPI;

    private static String COLUMN_NAME = "roads";
    private static String COORDINATES = "coordinates";

    {
        setAPIs();
    }

    protected abstract void setAPIs();

    protected void setAPIs( ElevationAPI elevationAPI, GradientAPI gradientAPI, DistanceAPI distanceAPI )
    {
        m_elevationAPI = elevationAPI;
        m_gradientAPI = gradientAPI;
        m_distanceAPI = distanceAPI;
    }

    @Override
    public Road map( int i, ResultSet resultSet, StatementContext statementContext ) throws SQLException
    {
        JsonParser parser = new JsonParser();
        JsonElement viewBounds = parser.parse( resultSet.getString( COLUMN_NAME ) );
        JsonArray coordinates = viewBounds.getAsJsonObject().getAsJsonArray( COORDINATES );

        return buildRoad( coordinates );
    }

    protected abstract Road buildRoad( JsonArray coordinates );
}
