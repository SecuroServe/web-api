package requests;

import interfaces.ConfirmationMessage;
import interfaces.IUser;
import org.springframework.web.client.RestTemplate;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class UserRequest implements IUser {

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    private static final String LOGIN = "/getuser?usertoken={usertoken}";
    private static final String ALL = "/allusers";

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public ConfirmationMessage allusers(String userToken) {
        //todo fix this, gets stuck here
        return restTemplate.getForObject(REQUEST_PREFIX + ALL, ConfirmationMessage.class);
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
