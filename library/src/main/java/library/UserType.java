package library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import exceptions.NoPermissionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandie on 13-3-2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserType implements Serializable{

    private String name;
    private List<Permission> permissions;

    public UserType(String name) {
        this.name = name;
        permissions = new ArrayList<>();
    }

    public UserType() { }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void addPermission(Permission permission) {
        permissions.add(permission);
    }

    public void containsPermission(Permission permission) throws NoPermissionException {
        for (Permission p : permissions) {
            if (permission.equals(p)) {
                return;
            }
        }

        throw new NoPermissionException("No permission!");
    }

    public enum Permission {
        CALAMITY_ADD,
        CALAMITY_UPDATE,
        CALAMITY_GET,
        CALAMITY_DELETE,
        CALAMITY_ADD_ASSIGNEE,
        CALAMITY_DELETE_ASSIGNEE,
        USER_REGISTER,
        USER_DELETE,
        USER_UPDATE
    }
}