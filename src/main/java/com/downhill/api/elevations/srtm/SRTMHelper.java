package com.downhill.api.elevations.srtm;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

public class SRTMHelper
{
    private File localDir;
    //TODO use a proper caching library rather then soft referenced data structures.
    private Map<File, SoftReference<BufferedInputStream>> srtmMap;
    private Logger m_logger = Logger.getLogger( SRTMHelper.class );

    public SRTMHelper( File localDir )
    {
        this.localDir = localDir;
        srtmMap = new HashMap<File, SoftReference<BufferedInputStream>>();
    }

    /**
     * Determine the filename of the srtm file corresponding to the lat and lon coordinates of the
     * actual node
     *
     * @param lat latitue
     * @param lng longitude
     * @return srtm height or Double.NaN if something is wrong
     * @throws IOException
     */
    public double getElevation( double lng, double lat )
    {
        double val = Double.NaN;
        String srtmFileName = SRTMUtil.getSRTMFileName( lng, lat );

        File file = new File( srtmFileName + ".hgt" );

        double ilat = getILat( lat );
        double ilon = getILon( lng );
        int rowmin = ( int ) Math.floor( ilon );
        int colmin = ( int ) Math.floor( ilat );
        double[] values = new double[4];
        try
        {
            values[0] = getValues( file, rowmin, colmin );
            values[1] = getValues( file, rowmin + 1, colmin );
            values[2] = getValues( file, rowmin, colmin + 1 );
            values[3] = getValues( file, rowmin + 1, colmin + 1 );
            double coefrowmin = ( rowmin + 1 ) - ilon;
            double coefcolmin = ( colmin + 1 ) - ilat;
            double val1 = ( values[0] * coefrowmin ) + ( values[1] * ( 1 - coefrowmin ) );
            double val2 = ( values[2] * coefrowmin ) + ( values[3] * ( 1 - coefrowmin ) );
            val = ( val1 * coefcolmin ) + ( val2 * ( 1 - coefcolmin ) );
        }
        catch ( IOException e )
        {
            m_logger.log( Level.ERROR, "HGT File not found" );
        }

        return val;
    }

    private static double getILat( double lat )
    {
        double dlat = lat - Math.floor( lat );
        double ilat = dlat * 1200;
        return ilat;
    }

    private static double getILon( double lon )
    {
        double dlon = lon - Math.floor( lon );
        double ilon = dlon * 1200;
        return ilon;
    }

    private short readShort( BufferedInputStream in ) throws IOException
    {
        int ch1 = in.read();
        int ch2 = in.read();
        return ( short ) ( ( ch1 << 8 ) + ( ch2 ) );
    }

    //TODO store in db? read times are horrendously slow, problem only lies in initial query until cache kicks in
    private double getValues( File file, int rowmin, int colmin ) throws IOException
    {
        file = new File( localDir, file.getName() );
        if ( !file.exists() )
        {
            File zipped = new File( localDir, file.getName() + ".zip" );

            if ( !zipped.exists() )
            {
                throw new FileNotFoundException( zipped.getAbsolutePath() + " not found!" );
            }

            ZipFile zipfile = new ZipFile( zipped, ZipFile.OPEN_READ );
            InputStream inp = zipfile.getInputStream( zipfile.getEntry( file.getName() ) );
            BufferedOutputStream outp = new BufferedOutputStream( new FileOutputStream( file ), 1024 );

            copyInputStream( inp, outp );
            outp.flush();
            zipfile.close();
        }

        SoftReference<BufferedInputStream> inRef = srtmMap.get( file );
        BufferedInputStream in = ( inRef != null ) ? inRef.get() : null;
        if ( in == null )
        {
            int srtmbuffer = 1201 * 1201 * 2;
            in = new BufferedInputStream( new FileInputStream( file ), srtmbuffer );
            srtmMap.put( file, new SoftReference<BufferedInputStream>( in ) );
            in.mark( srtmbuffer );
        }
        in.reset();

        long starti = ( ( 1200 - colmin ) * 2402 ) + ( rowmin * 2 );
        in.skip( starti );
        short readShort = readShort( in );
        return readShort;
    }

    private static void copyInputStream( InputStream in, BufferedOutputStream out ) throws IOException
    {
        byte[] buffer = new byte[1024 * 1024];
        int len = in.read( buffer );
        while ( len >= 0 )
        {
            out.write( buffer, 0, len );
            len = in.read( buffer );
        }
        in.close();
        out.close();
    }
}
