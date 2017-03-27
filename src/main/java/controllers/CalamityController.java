package controllers;

import api.ConfirmationMessage;
import api.ICalamity;
import library.Calamity;
import library.Location;
import logic.CalamityLogic;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by yannic on 20/03/2017.
 */
public class CalamityController implements ICalamity {

    private CalamityLogic calamityLogic;

    public CalamityController() {
        this.calamityLogic = new CalamityLogic();
    }

    /**
     * Returns a list with all current calamities.
     *
     * @return A list with all current calamities.
     */
    @Override
    public List<Calamity> calamity() {
        return null;
    }

    /**
     * Returns a single calamity by id.
     *
     * @param token The authentication token.
     * @param id    The id of the calamity.
     * @return A single calamity by id.
     */
    @Override
    public Calamity calamity(@RequestParam(value = "token") String token,
                             @RequestParam(value = "id") int id) {
        return (Calamity) calamityLogic.getCalamity(token, id).getReturnObject();
    }

    /**
     * Adds a new calamity.
     *
     * @param token       The authentication token.
     * @param name        The name of the calamity.
     * @param description The description of the calamity.
     * @param location    The location object of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */
    @Override
    public ConfirmationMessage calamity(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "name") String name,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "location") Location location) {
        return calamityLogic.addCalamity(token, name, description, location);
    }

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
    @Override
    public ConfirmationMessage calamity(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "id") int id,
                                        @RequestParam(value = "name") String name,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "location") Location location) {
        return calamityLogic.updateCalamity(token, id, name, description, location);
    }
}
