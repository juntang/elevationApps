package com.downhill.api;

import com.downhill.models.LngLat;
import com.downhill.models.RoadSegment;



import java.util.List;

/**
 * Simple gradient api goes through every point to calculate the gradients on a road
 */
public class SimpleGradientAPI implements GradientAPI
{
    @Override
    public double getGradient( RoadSegment segment )
    {
        LngLat start = segment.getStart();
        LngLat end = segment.getEnd();

        double startElevation = start.getElevation();
        double endElevation = end.getElevation();

        double distance = segment.getDistance();

        return ( startElevation - endElevation )/distance;
    }
}
