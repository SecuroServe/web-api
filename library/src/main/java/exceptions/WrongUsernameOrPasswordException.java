package exceptions;

/**
 * Is throwed when a wrong username or password is used
 * <p>
 * Created by Jandie on 2017-04-15.
 */
public class WrongUsernameOrPasswordException extends Exception {
    public WrongUsernameOrPasswordException(String s) {
        super(s);
    }
}
