package requests;

import interfaces.ConfirmationMessage;
import interfaces.ICalamity;
import library.Location;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import rest.RestClient;

/**
 * Created by guillaimejanssen on 03/04/2017.
 */
public class CalamityRequest implements ICalamity {

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    private static final String GET_ALL = REQUEST_PREFIX +
            "/allcalamities";
    private static final String GET_CALAMITY_BY_ID = REQUEST_PREFIX +
            "/calamitybyid?token={token}&id={id}";

    private static final String POST_ADD_CALAMITY_ASSIGNEE = REQUEST_PREFIX +
            "/addcalamityassignee?token={token}&" +
            "calamityid={calamityid}&" +
            "userid={userid}";
    private static final String POST_ADD_CALAMITY = REQUEST_PREFIX +
            "/addcalamity?token={token}&" +
            "title={title}&" +
            "message={message}&" +
            "latitude={latitude}&" +
            "longitude={longitude}&" +
            "radius={radius}&" +
            "confirmed={confirmed}&" +
            "closed={closed}";

    private static final String UPDATE_CALAMITY = REQUEST_PREFIX +
            "/updatecalamity?token={token}&" +
            "id={id}&" +
            "title={title}&" +
            "message={message}&" +
            "locationid={locId}&" +
            "latitude={latitude}&" +
            "longitude={longitude}&" +
            "radius={location}&" +
            "confirmed={confirmed}&" +
            "closed={closed}";

    private static final String DELETE_CALAMITY = REQUEST_PREFIX +
            "/deletecalamity?token={token}&id={id}";
    private static final String DELETE_CALAMITY_ASSIGNEE = REQUEST_PREFIX +
            "/deletecalamityassignee?token={token}&" +
            "calamityid={calamityid}&" +
            "userid={userid}";

    RestTemplate restTemplate;
    RestClient restClient;

    public CalamityRequest() {
        restTemplate = new RestTemplate();
        restClient = new RestClient();
    }

    @Override
    public ConfirmationMessage allCalamity() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        return restClient.request(GET_ALL, RestClient.RequestType.GET, parameters);
    }

    @Override
    public ConfirmationMessage calamityById(String token, int id) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);
        return restClient.request(GET_CALAMITY_BY_ID, RestClient.RequestType.GET, parameters);
    }

    @Override
    public ConfirmationMessage addCalamity(String token, String title, String message,
                                           double latitude,
                                           double longitude,
                                           double radius, boolean confirmed, boolean closed) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("title", title);
        parameters.add("message", message);
        parameters.add("latitude", latitude);
        parameters.add("longitude", longitude);
        parameters.add("radius", radius);
        parameters.add("confirmed", confirmed);
        parameters.add("closed", closed);
        return restClient.post(POST_ADD_CALAMITY, parameters);
    }

    @Override
    public ConfirmationMessage updateCalamity(String token, int id, String title, String message,
                                              int locId,
                                              double latitude,
                                              double longitude,
                                              double radius, boolean confirmed, boolean closed) {

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);
        parameters.add("title", title);
        parameters.add("message", message);
        parameters.add("locationid", locId);
        parameters.add("latitude", latitude);
        parameters.add("longitude", longitude);
        parameters.add("radius", radius);
        parameters.add("confirmed", confirmed);
        parameters.add("closed", closed);
        return restClient.post(UPDATE_CALAMITY, parameters);
    }

    @Override
    public ConfirmationMessage deleteCalamity(String s, int i) {
        return null;
    }

    @Override
    public ConfirmationMessage addCalamityAssignee(String s, int i, int i1) {
        return null;
    }

    @Override
    public ConfirmationMessage deleteCalamityAssignee(String s, int i, int i1) {
        return null;
    }
}
