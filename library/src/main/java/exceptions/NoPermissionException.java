package exceptions;

/**
 * Is throwed when a user has no permission for a specific action.
 * <p>
 * Created by Jandie on 2017-04-15.
 */
public class NoPermissionException extends Exception {
    public NoPermissionException(String s) {
        super(s);
    }
}
