package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.ICalamity;
import library.Location;
import logic.CalamityLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yannic on 20/03/2017.
 */
@RestController
public class CalamityController implements ICalamity {

    private CalamityLogic calamityLogic;

    public CalamityController() {
        this.calamityLogic = new CalamityLogic();
    }

    public CalamityController(Database database) {
        this.calamityLogic = new CalamityLogic(database);
    }

    /**
     * Returns a list with all current calamities.
     *
     * @return A list with all current calamities.
     */
    @Override

    @RequestMapping("/allcalamity")
    public ConfirmationMessage allCalamity() {
        return calamityLogic.allCalamity();
    }

    /**
     * Returns a single calamity by id.
     *
     * @param id The id of the calamity.
     * @return A single calamity by id.
     */
    @Override

    @RequestMapping("/calamitybyid")
    public ConfirmationMessage calamityById(@RequestParam(value = "id") int id) {
        return calamityLogic.getCalamity(id);
    }

    /**
     * Adds a new calamity.
     *
     * @param token     The authentication token.
     * @param title     The name of the calamity.
     * @param message   The description of the calamity.
     * @param latitude  The latitude of the new calamity.
     * @param longitude The longitude of the new calamity.
     * @param radius    The radius of the new calamity.
     * @return Confirmation message with feedback about the addition
     * also containing the new calamity.
     */
    @Override
    @RequestMapping("/addcalamity")
    public ConfirmationMessage addCalamity(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "title") String title,
                                           @RequestParam(value = "message") String message,
                                           @RequestParam(value = "latitude") double latitude,
                                           @RequestParam(value = "longitude") double longitude,
                                           @RequestParam(value = "radius") double radius,
                                           @RequestParam(value = "confirmed") boolean confirmed,
                                           @RequestParam(value = "closed") boolean closed) {
        return calamityLogic.addCalamity(token, title, message, new Location(1, latitude, longitude, radius), confirmed, closed);
    }

    /**
     * Updates a calamity.
     *
     * @param token     The authentication token.
     * @param id        The id of the calamity.
     * @param title     The name of the calamity.
     * @param message   The description of the calamity.
     * @param latitude  The latitude of the new calamity.
     * @param longitude The longitude of the new calamity.
     * @param radius    The radius of the new calamity.
     * @return Confirmation message with feedback about the update.
     */
    @Override
    @RequestMapping("/updatecalamity")
    public ConfirmationMessage updateCalamity(@RequestParam(value = "token") String token,
                                              @RequestParam(value = "id") int id,
                                              @RequestParam(value = "title") String title,
                                              @RequestParam(value = "message") String message,
                                              @RequestParam(value = "locationid") int locId,
                                              @RequestParam(value = "latitude") double latitude,
                                              @RequestParam(value = "longitude") double longitude,
                                              @RequestParam(value = "radius") double radius,
                                              @RequestParam(value = "confirmed") boolean isConfirmed,
                                              @RequestParam(value = "closed") boolean isClosed) {
        return calamityLogic.updateCalamity(token, id, title, message, new Location(locId, latitude, longitude, radius), isConfirmed, isClosed);
    }

    @Override
    @RequestMapping("/deletecalamity")
    public ConfirmationMessage deleteCalamity(@RequestParam(value = "token") String token,
                                              @RequestParam(value = "id") int id) {
        return calamityLogic.deleteCalamity(token, id);
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
    @RequestMapping("/addcalamityassignee")
    public ConfirmationMessage addCalamityAssignee(@RequestParam(value = "token") String token,
                                                   @RequestParam(value = "calamityid") int calamityId,
                                                   @RequestParam(value = "userid") int userId) {
        return calamityLogic.addCalamityAssignee(token, calamityId, userId);
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
    @RequestMapping("/deletecalamityassignee")
    public ConfirmationMessage deleteCalamityAssignee(@RequestParam(value = "token") String token,
                                                      @RequestParam(value = "calamityid") int calamityId,
                                                      @RequestParam(value = "userid") int userId) {
        return calamityLogic.deleteCalamityAssignee(token, calamityId, userId);
    }
}
