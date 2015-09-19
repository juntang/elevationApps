package com.downhill.models;

import java.util.List;

/**
 * Represents a map of the current view defined by a bounds. The roads/elevation of the roads are computed from the
 * bounds using a database query to find all roads, and google's ElevationAPI for the elevations.
 */
public class MapView
{
    private List<Road> m_roads;
}
