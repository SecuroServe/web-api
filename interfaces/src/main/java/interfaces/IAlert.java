package interfaces;

import library.Location;

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
    ConfirmationMessage getAllAlerts(String token);

    /**
     * Returns a single alert that matches the id.
     *
     * @param token The authentication token.
     * @param id    The id of the alert.
     * @return A single alert that matches the id.
     */
    ConfirmationMessage getAlert(String token,
                                 int id);

    /**
     * Returns a list of nearby alerts based on the location.
     *
     * @param token    The authentication token.
     * @param location The location to check.
     * @param radius   The radius to check.
     * @return a list of nearby alerts based on the location.
     */
    ConfirmationMessage getNearbyAlerts(String token, Location location, int radius);

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
    ConfirmationMessage addAlert(String token,
                                 String name,
                                 String description,
                                 int urgency,
                                 double lat,
                                 double lon,
                                 double radius);


    /**
     * Add alert to calamity with media
     *
     * @param token       The authentication token.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param lat         The latitude of the alert's location.
     * @param lon         The lontitude of the alert's location.
     * @param radius      The radius of the alert's location.
     * @param calamityId  The calamity it is attached to.
     * @return Confirmation message with feedback about the addition
     */
    ConfirmationMessage addAlertToCalamity(String token,
                                           String name,
                                           String description,
                                           int urgency,
                                           double lat,
                                           double lon,
                                           double radius,
                                           int calamityId);

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
    ConfirmationMessage updateAlert(String token,
                                    int id,
                                    String name,
                                    String description,
                                    int urgency,
                                    double lat,
                                    double lon,
                                    double radius);

    /**
     * Deletes an alert.
     *
     * @param token The authentication token.
     * @param id    The id of the token.
     * @return Confirmation message with feedback about the deletion.
     */
    ConfirmationMessage removeAlert(String token, int id);
}
