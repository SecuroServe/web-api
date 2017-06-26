package exceptions;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class PasswordsDontMatchException extends Exception {
    public PasswordsDontMatchException(String s) {
        super(s);
    }
}
