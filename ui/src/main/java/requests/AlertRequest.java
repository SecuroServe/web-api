package requests;

import interfaces.ConfirmationMessage;
import interfaces.IAlert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rest.RestClient;

/**
 * Created by Jandie on 2017-05-02.
 */
public class AlertRequest implements IAlert {
    private static final String REQUEST_PREFIX = "http://localhost:8080";
    private static final String GET_ALL_ALERTS = "/getallalerts";
    private static final String GET_ALERT = "/getalert";
    private static final String ADD_ALERT = "/addalert";
    private static final String UPDATE_ALERT = "/updatealert";
    private static final String REMOVE_ALERT = "/removealert";

    private RestClient restClient;

    public AlertRequest() {
        restClient = new RestClient();
    }

    /**
     * Returns a list with current alerts.
     *
     * @param token The authentication token.
     * @return A list with current alerts.
     */
    @Override
    public ConfirmationMessage getAllAlerts(String token) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);

        return restClient.request(REQUEST_PREFIX + GET_ALL_ALERTS, RestClient.RequestType.GET, parameters);
    }

    /**
     * Returns a single alert that matches the id.
     *
     * @param token The authentication token.
     * @param id    The id of the alert.
     * @return A single alert that matches the id.
     */
    @Override
    public ConfirmationMessage getAlert(String token, int id) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);

        return restClient.request(REQUEST_PREFIX + GET_ALERT, RestClient.RequestType.GET, parameters);
    }

    /**
     * Adds a new alert.
     *
     * @param token       The authentication token.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param urgency     The urgency of the alert.
     * @param lat         The latitude of the alert's location.
     * @param lon         The lontitude of the alert's location.
     * @param radius      The radius of the alert's location.    @return Confirmation message with feedback about the addition
     *                    also containing the new alert.
     */
    @Override
    public ConfirmationMessage addAlert(String token, String name, String description, int urgency, double lat, double lon, double radius) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("name", name);
        parameters.add("description", description);
        parameters.add("urgency", urgency);
        parameters.add("lat", lat);
        parameters.add("lon", lon);
        parameters.add("radius", radius);

        return restClient.request(REQUEST_PREFIX + ADD_ALERT, RestClient.RequestType.POST, parameters);
    }

    /**
     * Updates an alert.
     *
     * @param token       The authentication token.
     * @param id          The id of the alert.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param urgency     The urgency of the alert
     * @param lat         The latitude of the alert's location.
     * @param lon         The lontitude of the alert's location.
     * @param radius      The radius of the alert's location.    @return Confirmation message with feedback about the update.
     */
    @Override
    public ConfirmationMessage updateAlert(String token, int id, String name, String description, int urgency, double lat, double lon, double radius) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);
        parameters.add("name", name);
        parameters.add("description", description);
        parameters.add("urgency", urgency);
        parameters.add("lat", lat);
        parameters.add("lon", lon);
        parameters.add("radius", radius);

        return restClient.request(REQUEST_PREFIX + UPDATE_ALERT, RestClient.RequestType.POST, parameters);
    }

    /**
     * Deletes an alert.
     *
     * @param token The authentication token.
     * @param id    The id of the token.
     * @return Confirmation message with feedback about the deletion.
     */
    @Override
    public ConfirmationMessage removeAlert(String token, int id) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);

        return restClient.request(REQUEST_PREFIX + REMOVE_ALERT, RestClient.RequestType.POST, parameters);
    }
}
