package interfaces;

import library.Location;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface ICalamity {

    /**
     * Returns a list with all current calamities.
     *
     * @return A list with all current calamities.
     */
    ConfirmationMessage allCalamity();

    /**
     * Returns a single calamity by id.
     *
     * @param token The authentication token.
     * @param id    The id of the calamity.
     * @return A single calamity by id.
     */

    ConfirmationMessage calamityById(String token,
                                     int id);

    /**
     * Adds a new calamity.
     *
     * @param token    The authentication token.
     * @param title    The name of the calamity.
     * @param message  The description of the calamity.
     * @param location The location object of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */

    ConfirmationMessage addCalamity(String token,
                                    String title,
                                    String message,
                                    Location location, boolean isConfirmed, boolean isClosed);

    /**
     * Updates a calamity.
     *
     * @param token       The authentication token.
     * @param id          The id of the calamity.
     * @param name        The name of the calamity.
     * @param description The description of the calamity.
     * @param location    A location object of the calamity
     * @return Confirmation message with feedback about the update.
     */

    ConfirmationMessage updateCalamity(String token,
                                       int id,
                                       String name,
                                       String description,
                                       Location location, boolean isConfirmed, boolean isClosed);

    /**
     * Deletes a calamity.
     *
     * @param token The authentication token.
     * @param id    The id of the calamity.
     * @return Confirmation message with feedback about the deletion.
     */
    ConfirmationMessage deleteCalamity(String token, int id);

    /**
     * Adds an assignee to a calamity
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @return Confirmation message with feedback about the addition.
     */
    ConfirmationMessage addCalamityAssignee(String token, int calamityId, int userId);

    /**
     * Removes an assignee from a calamity
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @return Confirmation message with feedback about the deletion.
     */
    ConfirmationMessage deleteCalamityAssignee(String token, int calamityId, int userId);
}
