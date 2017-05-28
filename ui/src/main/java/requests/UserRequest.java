package requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ConfirmationMessage;
import interfaces.IUser;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rest.RestClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class UserRequest implements IUser {

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    private static final String LOGIN = "/getuser?usertoken={usertoken}";
    private static final String ALL = "/allusers";
    private static final String NOTIFY = "/notify";

    private RestTemplate restTemplate;
    private RestClient restClient;
    private ObjectMapper mapper;

    public UserRequest() {
        this.restTemplate = new RestTemplate();
        this.restClient = new RestClient();
        this.mapper = new ObjectMapper();
    }


    @Override
    public ConfirmationMessage allusers(String userToken) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", userToken);

        return restClient.request(REQUEST_PREFIX + ALL, RestClient.RequestType.GET, parameters);
    }

    @Override
    public ConfirmationMessage getUser(String userToken) {
        return restTemplate.getForObject(REQUEST_PREFIX + LOGIN, ConfirmationMessage.class, userToken);
    }

    @Override
    public ConfirmationMessage addUser(int userTypeId, int buildingId, String username, String password, String email, String city, String token) {
        throw new NotImplementedException();
    }

    @Override
    public ConfirmationMessage updateUser(String token, int id, String username, String password, String email, String city) {
        throw new NotImplementedException();
    }

    @Override
    public ConfirmationMessage deleteUser(String token, int id) {
        throw new NotImplementedException();
    }

    @Override
    public ConfirmationMessage giveUserToken(String userToken, String firebaseToken) {
        throw new NotImplementedException();
    }

    @Override
    public ConfirmationMessage askInformation(String userToken, int userId) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("usertoken", userToken);
        parameters.add("userid", userId);

        Object value = restClient.request(REQUEST_PREFIX + NOTIFY, RestClient.RequestType.GET, parameters);
        return mapper.convertValue(value, ConfirmationMessage.class);
    }
}
