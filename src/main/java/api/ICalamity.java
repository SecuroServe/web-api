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
    List<Calamity> calamity ();

    /**
     * Returns a single calamity by id.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @return A single calamity by id.
     */
    Calamity calamity (@RequestParam(value = "token") String token,
                       @RequestParam(value = "id") int id);

    /**
     * Adds a new calamity.
     * @param token The authentication token.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location The location object of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */
    ConfirmationMessage calamity (@RequestParam(value = "token") String token,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "location") Location location);

    /**
     * Updates a calamity.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param location A location object of the calamity
     * @return Confirmation message with feedback about the update.
     */
    ConfirmationMessage calamity (@RequestParam(value = "token") String token,
                                  @RequestParam(value = "id") int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "location") Location location);
}
