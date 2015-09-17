package com.elevations.controllers;

import com.elevations.dao.RoadDAO;
import com.elevations.models.Bounds;
import com.elevations.models.LngLat;
import com.elevations.models.Road;
import com.google.gson.JsonParser;
import org.skife.jdbi.v2.DBI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.util.List;

@Controller
public class ApplicationController
{
    private RoadDAO m_roadDAO;

    @Autowired
    public void setDataSource( DataSource dataSource )
    {
        m_roadDAO = new DBI( dataSource ).onDemand( RoadDAO.class );
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

        List<Road> roads = m_roadDAO.getRoadsInBounds( bounds );

        System.out.println( jsonBounds );

        return roads;
    }
}
