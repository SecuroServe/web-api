package securoserve.api.interfaces;

import securoserve.library.Alert;

import java.util.List;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface IAlert {

    /**
     * Returns a list with current alerts.
     *
     * @param token The authentication token.
     * @return A list with current alerts.
     */
    List<Alert> alert(String token);

    /**
     * Returns a single alert that matches the id.
     *
     * @param token The authentication token.
     * @param id    The id of the alert.
     * @return A single alert that matches the id.
     */
    Alert alert(String token,
                int id);

    /**
     * Adds a new alert.
     *
     * @param token       The authentication token.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param lat         The latitude of the alert's location.
     * @param lon         The lontitude of the alert's location.
     * @param radius      The radius of the alert's location.
     * @return Confirmation message with feedback about the addition
     * also containing the new alert.
     */
    ConfirmationMessage alert(String token,
                              String name,
                              String description,
                              long lat,
                              long lon,
                              long radius);

    /**
     * Updates an alert.
     *
     * @param token       The authentication token.
     * @param id          The id of the alert.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param lat         The latitude of the alert's location.
     * @param lon         The lontitude of the alert's location.
     * @param radius      The radius of the alert's location.
     * @return Confirmation message with feedback about the update.
     */
    ConfirmationMessage alert(String token,
                              int id,
                              String name,
                              String description,
                              long lat,
                              long lon,
                              long radius);
}
