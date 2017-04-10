package securoserve.library;

import securoserve.api.enums.BuildingType;

public class Building {

    /**
     * The id of the Building
     */
    private int id;

    /**
     * The Location of the Building
     */
    private Location location;

    /**
     * The Type of the Building
     */
    private BuildingType type;

    /**
     * The Description of the Building
     */
    private String description;

    /**
     * The PostalCode of the Building
     */
    private String postalCode;

    /**
     * The Number of the Building
     */
    private int number;

    /**
     * The Addition of the Building
     */
    private String numberAddition;

    /**
     * Creates a new instance of Building based on the Location given
     * @param id the id of the Building
     * @param location the Location of the Building
     * @param type the Type of the Building
     * @param description the Description of the Building
     */
    public Building(int id, Location location, BuildingType type, String description){
        this.id = id;
        this.location = location;
        this.type = type;
        this.description = description;

        //TODO GET ADDRESS FROM DATABASE
    }

    /**
     * Creates a new instance of Calamity based on the address given.
     * @param id the id of the Building
     * @param postalCode the PostalCode of the Building
     * @param number the Number of the Building
     * @param numberAddition any addition to the Number of the Building
     * @param type the Type of the Building
     * @param description the Description of the Building
     */
    public Building(int id, String postalCode, int number, String numberAddition, BuildingType type, String description){
        this.id = id;
        this.postalCode = postalCode;
        this.number = number;
        this.numberAddition = numberAddition;
        this.type = type;
        this.description = description;

        //TODO GET LOCATION
    }

    /**
     * Get the Building ID
     * @return the ID of this Building
     */
    public int getId(){
        return this.id;
    }

    /**
     * Get the Location of this Building
     * @return the Location of this Building
     */
    public Location getLocation(){
        return this.location;
    }

    /**
     * Get the BuildingType of this Building
     * @return the BuildingType of this Building
     */
    public BuildingType getType(){
        return this.type;
    }

    /**
     * Get the Description of this Building
     * @return the Description of this Building
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Get the PostalCode of this Building
     * @return the PostalCode of this Building
     */
    public String getPostalCode(){
        return this.postalCode;
    }

    /**
     * Get the Number of this Building
     * @return the Number of this Building
     */
    public int getNumber(){
        return this.number;
    }

    /**
     * Get the NumberAddition of this Building
     * @return the NumberAddition of this Building
     */
    public String getNumberAddition(){
        return this.numberAddition;
    }

    /**
     * Set the Description of this Building
     * @param description the new Description of this Building
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Set the BuildingType of this Building
     * @param type the new BuildingType of this Building
     */
    public void setType(BuildingType type){
        this.type = type;
    }


}
