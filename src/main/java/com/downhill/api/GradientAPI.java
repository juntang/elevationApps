package com.downhill.api;

import com.downhill.api.elevations.SelectionHeuristic;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;

public interface GradientAPI
{
    public void setGradient( RoadSegment segment );

    public void setGradients( Road road, SelectionHeuristic heuristic );
}
