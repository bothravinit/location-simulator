package com.vinit.utils;

import com.google.maps.model.LatLng;

/**
 * Created by IntelliJ IDEA.
 * User: bothravinit_old
 * Date: 2017/06/06
 * Time: 2:58
 */
public class UtilityMethods {

    /**
     * standard haversine formula
     * @param start
     * @param end
     * @return
     */
    public static double getStraightLineDistance(LatLng start, LatLng end) {

        double lat_a = start.lat, lng_a = start.lng;
        double lat_b = end.lat, lng_b = end.lng;
        {
            double earthRadius = 6371; // km
            double latDiff = Math.toRadians(lat_b - lat_a);
            double lngDiff = Math.toRadians(lng_b - lng_a);

            double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                    Math.cos(Math.toRadians(lat_a)) * Math.cos(Math.toRadians(lat_b)) *
                            Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            int meterConversion = 1000;

            return (distance * meterConversion);
        }
    }
}
