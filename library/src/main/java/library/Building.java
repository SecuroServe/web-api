package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Building implements Serializable {

    /**
     * The id of the library.Building
     */
    private int id;

    /**
     * The library.Location of the library.Building
     */
    private Location location;

    /**
     * The Type of the library.Building
     */
    private BuildingType type;

    /**
     * The Description of the library.Building
     */
    private String description;

    /**
     * The PostalCode of the library.Building
     */
    private String postalCode;

    /**
     * The Number of the library.Building
     */
    private int number;

    /**
     * The Addition of the library.Building
     */
    private String numberAddition;

    /**
     * Creates a new instance of library.Building based on the library.Location given
     *
     * @param id          the id of the library.Building
     * @param location    the library.Location of the library.Building
     * @param type        the Type of the library.Building
     * @param description the Description of the library.Building
     */
    public Building(int id, Location location, BuildingType type, String description) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.description = description;

        //TODO GET ADDRESS FROM DATABASE
    }

    /**
     * Creates a new instance of library.Calamity based on the address given.
     *
     * @param id             the id of the library.Building
     * @param postalCode     the PostalCode of the library.Building
     * @param number         the Number of the library.Building
     * @param numberAddition any addition to the Number of the library.Building
     * @param type           the Type of the library.Building
     * @param description    the Description of the library.Building
     */
    public Building(int id, String postalCode, int number, String numberAddition, BuildingType type, String description) {
        this.id = id;
        this.postalCode = postalCode;
        this.number = number;
        this.numberAddition = numberAddition;
        this.type = type;
        this.description = description;

        //TODO GET LOCATION
    }

    public Building() { }

    public void setId(int id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setNumberAddition(String numberAddition) {
        this.numberAddition = numberAddition;
    }

    /**
     * Get the library.Building ID
     *
     * @return the ID of this library.Building
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get the library.Location of this library.Building
     *
     * @return the library.Location of this library.Building
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Get the BuildingType of this library.Building
     *
     * @return the BuildingType of this library.Building
     */
    public BuildingType getType() {
        return this.type;
    }

    /**
     * Get the Description of this library.Building
     *
     * @return the Description of this library.Building
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Get the PostalCode of this library.Building
     *
     * @return the PostalCode of this library.Building
     */
    public String getPostalCode() {
        return this.postalCode;
    }

    /**
     * Get the Number of this library.Building
     *
     * @return the Number of this library.Building
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Get the NumberAddition of this library.Building
     *
     * @return the NumberAddition of this library.Building
     */
    public String getNumberAddition() {
        return this.numberAddition;
    }

    /**
     * Set the Description of this library.Building
     *
     * @param description the new Description of this library.Building
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the BuildingType of this library.Building
     *
     * @param type the new BuildingType of this library.Building
     */
    public void setType(BuildingType type) {
        this.type = type;
    }

    public enum BuildingType {

        CONTROL_ROOM,
        FIRE_DEPARTMENT,
        POLICE_DEPARTMENT,
        HOSPITAL

    }
}
