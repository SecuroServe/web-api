package securoserve.api.controllers;

import securoserve.api.ConfirmationMessage;
import securoserve.api.ICalamity;
import securoserve.library.Calamity;
import securoserve.library.Location;
import securoserve.api.logic.CalamityLogic;
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
    public List<Calamity> allCalamity() {
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
    public Calamity calamityById(@RequestParam(value = "token") String token,
                             @RequestParam(value = "id") int id) {
        return (Calamity) calamityLogic.getCalamity(token, id).getReturnObject();
    }

    /**
     * Adds a new calamity.
     *
     * @param token         The authentication token.
     * @param title         The name of the calamity.
     * @param message       The description of the calamity.
     * @param location      The location object of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */
    @Override
    public ConfirmationMessage addCalamity(@RequestParam(value = "token") String token,
                                            @RequestParam(value = "title") String title,
                                            @RequestParam(value = "message") String message,
                                            @RequestParam(value = "location") Location location,
                                            @RequestParam(value = "confirmed") boolean isConfirmed,
                                            @RequestParam(value = "status") boolean isClosed) {
        return calamityLogic.addCalamity(token, title, message, location, isConfirmed, isClosed);
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
    public ConfirmationMessage updateCalamity(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "id") int id,
                                        @RequestParam(value = "name") String name,
                                        @RequestParam(value = "description") String description,
                                        @RequestParam(value = "location") Location location) {
        return calamityLogic.updateCalamity(token, id, name, description, location);
    }

    @Override
    public ConfirmationMessage deleteCalamity(String token, int id) {
        return null;
    }

    /**
     * Adds an assignee to a calamity
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @return Confirmation message with feedback about the addition.
     */
    @Override
    public ConfirmationMessage addCalamityAssignee(String token, int calamityId, int userId) {
        return null;
    }

    /**
     * Removes an assignee from a calamity
     *
     * @param token      The authentication token.
     * @param calamityId The id of the calamity.
     * @param userId     The id of the user.
     * @return Confirmation message with feedback about the deletion.
     */
    @Override
    public ConfirmationMessage deleteCalamityAssignee(String token, int calamityId, int userId) {
        return null;
    }
}
