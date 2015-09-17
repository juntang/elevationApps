package com.elevations.mappers;

import com.elevations.models.LngLat;
import com.elevations.models.Road;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class RoadMapper implements ResultSetMapper<Road >
{
    private static String COLUMN_NAME = "roads";
    private static String COORDINATES = "coordinates";

    @Override
    public Road map( int i, ResultSet resultSet, StatementContext statementContext ) throws SQLException
    {
        JsonParser parser = new JsonParser();
        JsonElement viewBounds = parser.parse( resultSet.getString( COLUMN_NAME ) );
        JsonArray coordinates = viewBounds.getAsJsonObject().getAsJsonArray( COORDINATES );
        Set<LngLat > points = new HashSet<LngLat >();
        for ( int j=0; j<coordinates.size(); j++ )
        {
            JsonArray coord = coordinates.get( j ).getAsJsonArray();
            points.add( new LngLat( coord.get( 0 ).getAsDouble(), coord.get( 1 ).getAsDouble() ) );
        }
        return new Road( points );
    }
}
