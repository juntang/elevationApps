package com.downhill.api.elevations;

import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;

//Defines how to select points to compute gradients of a road
public interface SelectionHeuristic
{
    public void setRoad( Road road );

    public boolean isNextSegment( RoadSegment segment );
}
