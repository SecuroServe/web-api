package requests;

import interfaces.ConfirmationMessage;
import interfaces.ILogin;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rest.RestClient;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class LoginRequest implements ILogin {

    private static final String REQUEST_PREFIX = "https://www.securoserve.nl/api";

    RestClient restClient;

    public LoginRequest() {
        restClient = new RestClient();
    }

    /**
     * Creates and returns a new token for a user if username and password
     * are correct.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The token of the user.
     */
    @Override
    public ConfirmationMessage login(String username, String password) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("username", username);
        parameters.add("password", password);

        return restClient.request(REQUEST_PREFIX + "/login", RestClient.RequestType.GET, parameters);
    }

    /**
     * Creates and returns a new user. Also checks for valid username and valid passwords.
     *
     * @param username  The username of the user to register.
     * @param password1 The password of the user.
     * @param password2 The retyped password.
     * @param email     The email of the User.
     * @param city      The city of the user
     * @return A ConfirmationMessage with the new User.
     */
    @Override
    public ConfirmationMessage register(String username, String password1, String password2, String email, String city) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("username", username);
        parameters.add("password1", password1);
        parameters.add("password2", password2);
        parameters.add("email", email);
        parameters.add("city", city);

        return restClient.request(REQUEST_PREFIX + "/register", RestClient.RequestType.GET, parameters);
    }
}
