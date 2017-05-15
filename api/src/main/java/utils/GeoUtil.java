package utils;

import library.Location;

/**
 * Created by Marc on 2-5-2017.
 */
public final class GeoUtil {

    /**
     * Measures the distance between to geographic coordinates in meters.
     *
     * @param lat1 Latitude Location 1
     * @param lon1 Longitude Location 1
     * @param lat2 Latitude Location 2
     * @param lon2 Longitude Location 2
     * @return
     */
    public static double measureGeoDistance(Location l1, Location l2) {

        double Radius = 6378.137; // Radius of earth in km.

        double lat1 = l1.getLatitude();
        double lon1 = l1.getLongitude();
        double lat2 = l2.getLatitude();
        double lon2 = l2.getLongitude();

        double lat1Rad = Math.toRadians(lat1); // Latitude 1 in radians.
        double lat2Rad = Math.toRadians(lat2); // Latitude 2 in radians.

        double diffLat = Math.toRadians(lat2 - lat1); // Latitude 2 - Latitude 2 in radians.
        double diffLon = Math.toRadians(lon2 - lon1); // Longitude 2 - Longitude 2 in radians.

        // Haversine formula for "a"
        double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                Math.sin(diffLon / 2) * Math.sin(diffLon / 2);

        // Haversine formula for "c"
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distance from radius
        double d = Radius * c;

        // Distance in meters
        return d * 1000;
    }
}


