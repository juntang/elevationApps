package com.downhill.api;

import com.downhill.api.elevations.SelectionHeuristic;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;

import java.util.List;

public interface GradientAPI
{
    public double getGradient( RoadSegment segment );
}
