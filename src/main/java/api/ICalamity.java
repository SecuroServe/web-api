package api;

import library.Calamity;
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
    @RequestMapping(value = "/api/calamity", method = RequestMethod.GET)
    List<Calamity> calamity ();

    /**
     * Returns a single calamity by id.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @return A single calamity by id.
     */
    @RequestMapping(value = "/api/calamity", method = RequestMethod.GET)
    Calamity calamity (@RequestParam(value = "token") String token,
                       @RequestParam(value = "id") int id);

    /**
     * Adds a new calamity.
     * @param token The authentication token.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param lat The latitude of the calamity's location.
     * @param lon The longtitude of the calamity's location.
     * @param radius The radius of the calamity's location.
     * @return Confirmation message with feedback about the addition.
     */
    @RequestMapping(value = "/api/calamity", method = RequestMethod.POST)
    ConfirmationMessage calamity (@RequestParam(value = "token") String token,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "lat") long lat,
                                  @RequestParam(value = "lon") long lon,
                                  @RequestParam(value = "radius") long radius);

    /**
     * Updates a calamity.
     * @param token The authentication token.
     * @param id The id of the calamity.
     * @param name The name of the calamity.
     * @param description The description of the calamity.
     * @param lat The latitude of the calamity's location.
     * @param lon The longtitude of the calamity's location.
     * @param radius The radius of the calamity's location.
     * @return Confirmation message with feedback about the update.
     */
    @RequestMapping(value = "/api/calamity", method = RequestMethod.PUT)
    ConfirmationMessage calamity (@RequestParam(value = "token") String token,
                                  @RequestParam(value = "id") int id,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "description") String description,
                                  @RequestParam(value = "lat") long lat,
                                  @RequestParam(value = "lon") long lon,
                                  @RequestParam(value = "radius") long radius);
}
