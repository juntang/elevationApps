package com.downhill.mappers;

import com.downhill.api.*;
import com.downhill.models.Road;;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoadMapper implements ResultSetMapper<Road>
{
    private static String COLUMN_NAME = "roads";
    private static String COORDINATES = "coordinates";
    private static APIController controller = new APIController();

    @Override
    public Road map( int i, ResultSet resultSet, StatementContext statementContext ) throws SQLException
    {
        JsonParser parser = new JsonParser();
        JsonElement viewBounds = parser.parse( resultSet.getString( COLUMN_NAME ) );
        JsonArray coordinates = viewBounds.getAsJsonObject().getAsJsonArray( COORDINATES );

        return controller.buildRoad( coordinates );
    }
}
