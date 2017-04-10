package api;

import library.Calamity;
import library.Location;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Jandie on 13-3-2017.
 */
public interface ICalamity {

    /**
     * Returns a list with all current calamities.
     * @return A list with all current calamities.
     */
    List<Calamity> allCalamity ();

    /**
     * Returns a single calamity by id.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @return A single calamity by id.
     */

    Calamity calamityById (String token,
                       int id);

    /**
     * Adds a new calamity.
     * @param token The authentication token.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location The location object of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */

    ConfirmationMessage addCalamity (String token,
                                  String name,
                                  String description,
                                  Location location);

    /**
     * Updates a calamity.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location A location object of the calamity
     * @return Confirmation message with feedback about the update.
     */

    ConfirmationMessage updateCalamity (String token,
                                  int id,
                                  String name,
                                  String description,
                                  Location location);

    ConfirmationMessage deleteCalamity (String token, int id);
}
