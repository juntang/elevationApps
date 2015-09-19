package com.downhill.api;

import com.downhill.api.ElevationAPI;
import com.downhill.api.GradientAPI;

public abstract class AbstractGradientAPI implements GradientAPI
{
    protected ElevationAPI m_elevationAPI;

    public AbstractGradientAPI( ElevationAPI elevationAPI )
    {
        m_elevationAPI = elevationAPI;
    }
}
