package com.downhill.api.elevations.srtm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;

public class SRTMUtil
{
    public static final String EURASIA = "Eurasia";
    public static final String AFRICA = "Africa";
    public static final String ISLANDS = "Islands";
    public static final String NORTH_AMERICA = "North_America";
    public static final String SOUTH_AMERICA = "South_America";
    public static String SRTM3_URL = "http://dds.cr.usgs.gov/srtm/version2_1/SRTM3/";

    /**
     * Download the SRTM file from the dds.cr.usgs.gov to the specified Folder. The downloaded file
     * is returned
     *
     * @param lat Latitude
     * @param lon Longitude
     * @param region The region where the tile is contained
     * @param toFolder Folder where the file is downloaded
     * @return The downloaded File
     * @throws IllegalStateException
     * @throws IOException
     */
    public static File downloadSrtm(double lat, double lon, String region, File toFolder) throws IOException
    {
        String srtmFileName = getSRTMFileName( lat, lon ) + ".hgt.zip";
        URL url = new URL(SRTM3_URL + region + "/" + srtmFileName);
        File srtmFile = new File(toFolder, srtmFileName);

        InputStream is = null;
        FileOutputStream fos = null;
        try {
            is = Network.getInputStream(url);
            fos = new FileOutputStream(srtmFile);

            final byte[] buf = new byte[1024];

            for (int count = is.read(buf); count != -1; count = is.read(buf)) {
                fos.write(buf, 0, count);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }

        return srtmFile;
    }

    /**
     * Return the SRTM file name without the extension
     *
     * @param lat Latitude
     * @param lng Longitude
     * @return SRTM filename
     */
    public static String getSRTMFileName( double lng, double lat )
    {
        int nLat = Math.abs( ( int ) Math.floor( lat ) );
        int nLng = Math.abs( ( int ) Math.floor( lng ) );

        NumberFormat nf = NumberFormat.getInstance() ;
        String NS, WE;
        String fLat, fLng;

        if ( lat > 0 )
        {
            NS = "N";
        }
        else
        {
            NS = "S";
        }
        if ( lng > 0 )
        {
            WE = "E";
        }
        else
        {
            WE = "W";
        }

        nf.setMinimumIntegerDigits( 2 );
        fLat = nf.format( nLat );
        nf.setMinimumIntegerDigits( 3 );
        fLng = nf.format( nLng );

        return NS + fLat + WE + fLng;
    }
}
