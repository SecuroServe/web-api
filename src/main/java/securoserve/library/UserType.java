package securoserve.library;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandie on 13-3-2017.
 */
public class UserType {

    private String name;
    private List<Permission> permissions;

    public UserType(String name) {
        this.name = name;

        permissions =  new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
