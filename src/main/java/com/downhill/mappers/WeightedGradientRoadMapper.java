package com.downhill.mappers;

import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;
import com.google.gson.JsonArray;

public class WeightedGradientRoadMapper extends SimpleRoadMapper
{
    private double previousGradient = 0;
    private static final double GRADIENT_WEIGHT = 0.5;

    @Override
    protected Road buildRoad( JsonArray coordinates )
    {
        previousGradient = 0;
        return super.buildRoad( coordinates );
    }

    @Override
    protected RoadSegment buildSegment( LngLat start, LngLat end )
    {
        RoadSegment segment = super.buildSegment( start, end );

        double newGradient = ( GRADIENT_WEIGHT * previousGradient ) + ( 1 - GRADIENT_WEIGHT ) * segment.getGradient();
        segment.setGradient( newGradient );
        previousGradient = newGradient;

        return segment;
    }
}
