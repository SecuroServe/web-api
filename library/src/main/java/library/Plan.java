package library;

/**
 * Created by Jandie on 2017-06-12.
 */
public class Plan {
    int id;
    String description;

    public Plan(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
