package library;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Jandie on 2017-05-08.
 */
@Component
public class Text extends Media implements Serializable {

    private String text;

    public Text(int id, String name, String text) {
        super(id, name);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
