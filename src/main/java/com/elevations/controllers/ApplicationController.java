package com.elevations.controllers;

import com.elevations.models.LatLng;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController
{
    @RequestMapping( value = "/elevations", method = RequestMethod.GET )
    public String pageGet()
    {
        return "elevationMaps";
    }

    @RequestMapping( value = "/elevationData", method = RequestMethod.GET )
    @ResponseBody
    public LatLng mapGet( @RequestParam( "bounds" ) String bounds, @RequestParam( "diameter") String diameter )
    {
//        String[] formattedBounds = LatLngBounds.stripBounds( bounds );
//        LatLngBounds latLngBounds = LatLngBounds.parseBounds( formattedBounds );
//        Map map = new Map( latLngBounds );

        JsonParser parser = new JsonParser();
        JsonElement viewBounds = parser.parse( bounds );

        System.out.println( bounds );

        return new LatLng( 1, 0 );
    }
}
