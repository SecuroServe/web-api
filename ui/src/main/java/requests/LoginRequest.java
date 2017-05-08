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

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    RestClient restClient;

    public LoginRequest() {
        restClient = new RestClient();
    }

    @Override
    public ConfirmationMessage login(String username, String password) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("username", username);
        parameters.add("password", password);

        return restClient.request(REQUEST_PREFIX + "/login", RestClient.RequestType.GET, parameters);
    }
}
