package com.downhill.api;

import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;

import java.util.List;

public interface ElevationAPI
{
    public double getElevation( LngLat lngLat );
}
