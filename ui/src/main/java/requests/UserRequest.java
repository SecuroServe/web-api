package requests;

import interfaces.ConfirmationMessage;
import interfaces.IUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rest.RestClient;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class UserRequest implements IUser {

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    private static final String LOGIN = "/getuser?usertoken={usertoken}";
    private static final String ALL = "/allusers";

    private RestTemplate restTemplate;
    private RestClient restClient;

    public UserRequest() {
        this.restTemplate = new RestTemplate();
        this.restClient = new RestClient();
    }



    @Override
    public ConfirmationMessage allusers(String userToken) {
        //todo fix this, gets stuck here
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", userToken);
        return restClient.request(REQUEST_PREFIX+ALL, RestClient.RequestType.GET, parameters);
    }

    @Override
    public ConfirmationMessage getUser(String userToken) {
        return restTemplate.getForObject(REQUEST_PREFIX + LOGIN, ConfirmationMessage.class, userToken);
    }

    @Override
    public ConfirmationMessage addUser(int userTypeId, int buildingId, String username, String password, String email, String city, String token) {
        return null;
    }

    @Override
    public ConfirmationMessage updateUser(String token, int id, String username, String password, String email, String city) {
        return null;
    }

    @Override
    public ConfirmationMessage deleteUser(String token, int id) {
        return null;
    }
}
