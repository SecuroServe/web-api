package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Jandie on 13-3-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Media implements Serializable {
    private int id;
    private String name;

    public Media(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
