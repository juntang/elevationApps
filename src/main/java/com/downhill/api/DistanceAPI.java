package com.downhill.api;


import com.downhill.models.LngLat;

public interface DistanceAPI
{
    public double getDistance( LngLat latLng1, LngLat  latLng2 );
}
