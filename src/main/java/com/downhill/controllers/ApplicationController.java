package com.downhill.controllers;

import com.downhill.api.*;
import com.downhill.api.elevations.BruteForceHeuristic;
import com.downhill.api.elevations.SelectionHeuristic;
import com.downhill.models.Bounds;
import com.downhill.models.Road;
import com.google.gson.JsonParser;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController
{
    private RoadAPI m_roadAPI;
    private Logger m_logger = Logger.getLogger( ApplicationController.class );

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        m_roadAPI = new DBI( dataSource ).onDemand( RoadAPI.class );
    }

    @RequestMapping( value = "/elevations", method = RequestMethod.GET )
    public String pageGet()
    {
        return "elevationMaps";
    }

    @RequestMapping( value = "/elevationData", method = RequestMethod.GET )
    @ResponseBody
    public List<Road> mapGet( @RequestParam( "bounds" ) String jsonBounds, @RequestParam( "diameter") String diameter )
    {
        JsonParser parser = new JsonParser();

        Bounds bounds = Bounds.parseJson( parser.parse( jsonBounds ) );

        List<Road> roads = new ArrayList<>();

        //TODO Need to prevent HUGE QUERIES on server side rather then client side!
        if ( Double.parseDouble( diameter ) < 16000 )
        {
            roads.addAll( m_roadAPI.getRoadsInBounds( bounds ) );
        }

        m_logger.log( Level.INFO, roads.size() );

        return roads;
    }
}
