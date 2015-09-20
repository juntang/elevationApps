package com.downhill.api;

import com.downhill.api.elevations.srtm.SRTMHelper;
import com.downhill.models.LngLat;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

//API that uses SRTM data for elevations
public class SRTMElevationAPI implements ElevationAPI
{
    private SRTMHelper m_SRTMHelper;
    private static String LOCAL_DIR = "/hgt";
    private Logger m_logger = Logger.getLogger( SRTMElevationAPI.class );

    public SRTMElevationAPI()
    {
        try
        {
            File localDir = new File( getClass().getResource( LOCAL_DIR ).getFile() );
            if ( !localDir.exists() )
            {
                throw new FileNotFoundException( "HGT Directory not found" );
            }
            m_SRTMHelper = new SRTMHelper( localDir );
        }
        catch ( FileNotFoundException e )
        {
            m_logger.log( Level.ERROR, "HGT directory not found" );
        }
    }

    @Override
    public double getElevation( LngLat lngLat )
    {
        return m_SRTMHelper.getElevation( lngLat.getLng(), lngLat.getLat() );
    }
}
