package com.downhill.mappers;

import com.downhill.api.HaversineDistanceAPI;
import com.downhill.api.SRTMElevationAPI;
import com.downhill.api.SimpleGradientAPI;
import com.downhill.models.LngLat;
import com.downhill.models.Road;
import com.downhill.models.RoadSegment;
import com.google.gson.JsonArray;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class MovingGradientRoadMapper extends SimpleRoadMapper
{
    private int WINDOW_SIZE = 2;
    private DescriptiveStatistics m_stats = new DescriptiveStatistics();

    @Override
    protected Road buildRoad( JsonArray coordinates )
    {
        m_stats.clear();
        m_stats.setWindowSize( WINDOW_SIZE );
        return super.buildRoad( coordinates );
    }

    @Override
    protected RoadSegment buildSegment( LngLat start, LngLat end )
    {
        RoadSegment segment = super.buildSegment( start, end );

        double gradient = segment.getGradient();
        m_stats.addValue( gradient );
        double movingGradient = m_stats.getMean();

        segment.setGradient( movingGradient );

        return segment;
    }
}
