package requests;

import interfaces.ConfirmationMessage;
import interfaces.ICalamity;
import library.Plan;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rest.RestClient;

/**
 * Created by guillaimejanssen on 03/04/2017.
 */
public class CalamityRequest implements ICalamity {

    private static final String REQUEST_PREFIX = "https://www.securoserve.nl/api";

    private static final String GET_ALL = "/allcalamity";
    private static final String GET_CALAMITY_BY_ID = "/calamitybyid";

    private static final String POST_ADD_CALAMITY_ASSIGNEE = "/addcalamityassignee";
    private static final String POST_ADD_CALAMITY = "/addcalamity";

    private static final String UPDATE_CALAMITY = "/updatecalamity";

    private static final String DELETE_CALAMITY = "/deletecalamity";
    private static final String DELETE_CALAMITY_ASSIGNEE = "/deletecalamityassignee";

    private static final String ADD_POST = "/addpost";
    private static final String ADD_PLAN = "/addplan";

    RestClient restClient;

    public CalamityRequest() {
        restClient = new RestClient();
    }

    @Override
    public ConfirmationMessage allCalamity() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        return restClient.request(REQUEST_PREFIX + "/allcalamity", RestClient.RequestType.GET, parameters);
    }

    @Override
    public ConfirmationMessage calamityById(int id) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("id", id);
        return restClient.request(REQUEST_PREFIX + GET_CALAMITY_BY_ID, RestClient.RequestType.GET, parameters);
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
        return restClient.request(REQUEST_PREFIX + POST_ADD_CALAMITY, RestClient.RequestType.POST, parameters);
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
        return restClient.request(REQUEST_PREFIX + UPDATE_CALAMITY, RestClient.RequestType.POST, parameters);
    }

    @Override
    public ConfirmationMessage deleteCalamity(String token, int id) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);
        return restClient.request(REQUEST_PREFIX + DELETE_CALAMITY, RestClient.RequestType.DELETE, parameters);
    }

    @Override
    public ConfirmationMessage addCalamityAssignee(String token, int calamityId, int userId) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("calamityid", calamityId);
        parameters.add("userid", userId);
        return restClient.request(REQUEST_PREFIX + POST_ADD_CALAMITY_ASSIGNEE, RestClient.RequestType.POST, parameters);
    }

    @Override
    public ConfirmationMessage deleteCalamityAssignee(String token, int calamityId, int userId) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("calamityid", calamityId);
        parameters.add("userid", userId);
        return restClient.request(REQUEST_PREFIX + DELETE_CALAMITY_ASSIGNEE, RestClient.RequestType.DELETE, parameters);
    }

    @Override
    public ConfirmationMessage addPost(String token, int userId, int calamityId, String text) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("userId", userId);
        parameters.add("calamityId", calamityId);
        parameters.add("text", text);
        return restClient.request(REQUEST_PREFIX + ADD_POST, RestClient.RequestType.POST, parameters);
    }

    @Override
    public ConfirmationMessage addPlan(String token, int calamityId, Plan plan) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("calamityId", calamityId);
        parameters.add("plan", plan);
        return restClient.request(REQUEST_PREFIX + ADD_PLAN, RestClient.RequestType.POST, parameters);
    }
}
