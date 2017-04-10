package securoserve.library;

/**
 * This class contains location information.
 * Created by Jandie on 13-3-2017.
 */
public class Location {
    private float longitude;
    private float latitude;
    private float radius;
    private int id;

    /***
     * the constructor for a new location .
     * @param longitude the longitude of the location.
     * @param latitude the latutude of the location.
     * @param radius the radius of the location, can be 0.
     */
    public Location(float latitude, float longitude, float radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
