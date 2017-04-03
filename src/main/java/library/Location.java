package library;

/**
 * This class contains location information.
 * Created by Jandie on 13-3-2017.
 */
public class Location {
    private long longitude;
    private long latitude;
    private long radius;

    /***
     * the constructor for a new location .
     * @param longitude the longitude of the location.
     * @param latitude the latutude of the location.
     * @param radius the radius of the location, can be 0.
     */
    public Location(long longitude, long latitude, long radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }
}
