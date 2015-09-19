package com.downhill.api.elevations;

import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;

//Simple heuristic that uses all points on a road to compute gradients
public class BruteForceHeuristic implements SelectionHeuristic
{
    private Road m_road;

    public void setRoad( Road road )
    {
        m_road = road;
    }

    @Override
    public boolean isNextSegment( RoadSegment point )
    {
        if ( m_road.isOnRoad( point ) )
        {
            return true;
        }
        return false;
    }
}
