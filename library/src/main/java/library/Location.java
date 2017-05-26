package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * This class contains location information.
 * Created by Jandie on 13-3-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location implements Serializable {
    private double longitude;
    private double latitude;
    private double radius;
    private int id;

    /***
     * the constructor for a new location .
     * @param longitude the longitude of the location.
     * @param latitude the latutude of the location.
     * @param radius the radius of the location, can be 0.
     */

    public Location(int id, double latitude, double longitude, double radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
        this.id = id;
    }

    public Location() {

    }

    public Location(double lat, double lon, double radius) {
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "lon: " + this.longitude + " lat: " + this.latitude;
    }
}
