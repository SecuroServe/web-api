package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by Jandie on 13-3-2017.
 */

public abstract class Media implements Serializable {

    @Autowired
    private int id;

    @Autowired
    private String name;

    public Media(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Media() {
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
