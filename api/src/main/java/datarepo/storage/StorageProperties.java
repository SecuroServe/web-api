package datarepo.storage;

public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "./MediaFiles";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}