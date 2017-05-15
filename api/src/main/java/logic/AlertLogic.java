package logic;

import constants.ApiConstants;
import datarepo.AlertRepo;
import datarepo.CalamityRepo;
import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import interfaces.ConfirmationMessage;
import library.*;
import org.eclipse.jetty.io.ssl.ALPNProcessor;
import utils.GeoUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jandie on 2017-05-01.
 */
public class AlertLogic {
    private Database database;
    private UserRepo userRepo;
    private AlertRepo alertRepo;

    public AlertLogic() {
        this.database = new Database();
        userRepo = new UserRepo(database);
        alertRepo = new AlertRepo(database);
    }

    public AlertLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
        alertRepo = new AlertRepo(database);
    }

    /**
     * Returns a single alert that matches the id.
     *
     * @param token The authentication token.
     * @param id    The id of the alert.
     * @return A single alert that matches the id.
     */
    public ConfirmationMessage getAlert(String token, int id) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.ALERT_GET);

            Alert alert = alertRepo.getAlert(id);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "got alert", alert);
        } catch (NoPermissionException | ParseException | NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Error while loading alert", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while loading alert", e);
        }
    }

    /**
     * Adds a new alert.
     *
     * @param token       The authentication token.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param location    The location.
     * @return Confirmation message with feedback about the addition
     * also containing the new alert.
     */
    public ConfirmationMessage addAlert(String token, String name, String description, Location location, int urgency) {
        try {
            User u = userRepo.getUser(token);
            u.getUserType().containsPermission(UserType.Permission.ALERT_ADD);

            Alert alert = new Alert(-1, location, u, new Date(), name, description, urgency);
            alertRepo.addAlert(alert);

            calculateAlertGroups();

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Added alert", alert);
        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Error while adding a alert", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while adding a alert", e);
        }
    }

    /**
     * Updates an alert.
     *
     * @param token The authentication token.
     * @param id The ID.
     * @param name The name.
     * @param description The description.
     * @param urgency The urgency.
     * @param lat The Location latitude.
     * @param lon The Location longitude.
     * @param radius The Location radius.
     * @return ConfirmationMessage Alert with feedback about the update.
     */
    public ConfirmationMessage updateAlert(String token, int id, String name, String description, int urgency, double lat, double lon, double radius) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.ALERT_UPDATE);

            Alert alert = alertRepo.getAlert(id);
            alert.setName(name);
            alert.setDescription(description);
            alert.setUrgency(urgency);
            alert.getLocation().setLatitude(lat);
            alert.getLocation().setLongitude(lon);
            alert.getLocation().setRadius(radius);

            alertRepo.updateAlert(alert);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "updated alert", alert);
        } catch (NoPermissionException | ParseException | NoSuchAlgorithmException | SQLException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Update alert failed!", e);

            return new ConfirmationMessage(
                    ConfirmationMessage.StatusType.ERROR, "Error while updating alert", e);
        }
    }

    /**
     * Deletes an alert.
     *
     * @param token The authentication token.
     * @param id    The id of the token.
     * @return Confirmation message with feedback about the deletion.
     */
    public ConfirmationMessage removeAlert(String token, int id) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.ALERT_DELETE);

            userRepo.getUser(token);
            alertRepo.deleteAlert(id);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Deleted alert", null);
        } catch (NoPermissionException | SQLException | ParseException | NoSuchAlgorithmException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Delete alert failed!", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Delete alert failed!", e);
        }
    }

    public ConfirmationMessage allAlert(String token) {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Get all alerts", alertRepo.allAlert(false));
        } catch (SQLException | NoSuchAlgorithmException | ParseException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Error while getting all alerts", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while getting all alerts", e);
        }
    }

    private void calculateAlertGroups() throws ParseException, NoSuchAlgorithmException, SQLException {
        List<Alert> alerts = alertRepo.allAlert(true);

        while (alerts.size() > 0) {
            Alert alertCentre = getNextAlert(alerts);

            List<Alert> alertGroup = findAlertGroup(alerts, alertCentre);

            if (alertGroup.size() >= ApiConstants.ALERT_GROUP_AMOUNT) {
                createCalamityForGroup(alertGroup);
            }
        }
    }

    private Alert getNextAlert(List<Alert> alerts){
        if (alerts.size() == 0) return null;

        return alerts.get(0);
    }

    private void createCalamityForGroup(List<Alert> alertGroup) throws SQLException {
        Calamity calamity = new Calamity(-1, calculateCentreLocation(alertGroup), null,
                false, false, new Date(), "generated", "generated");

        new CalamityRepo(database).addCalamity(calamity);

        for (Alert alert : alertGroup) {
            alert.setCalamityId(calamity.getId());

            alertRepo.updateAlert(alert);
        }
    }

    private Location calculateCentreLocation(List<Alert> alertGroup) {
        double latSum = 0;
        double longSum = 0;

        for (Alert alert : alertGroup) {
            latSum += alert.getLocation().getLatitude();
            longSum += alert.getLocation().getLongitude();
        }

        double latAvg = latSum / alertGroup.size();
        double longAvg = longSum / alertGroup.size();

        return new Location(latAvg, longAvg, 0);
    }

    private List<Alert> findAlertGroup(List<Alert> alerts, Alert alertCentre) {
        List<Alert> alertGroup = new ArrayList<>();
        List<Alert> newAlertNeighbours = new ArrayList<>();

        alertGroup.add(alertCentre);
        alerts.remove(alertCentre);
        newAlertNeighbours.add(alertCentre);

        while (newAlertNeighbours.size() > 0) {

            List<Alert> prevNeighbours = new ArrayList<>(newAlertNeighbours);
            newAlertNeighbours.clear();

            for (Alert alert : prevNeighbours) {
                newAlertNeighbours.addAll(findAlertNeighbours(alert, alerts));
            }

            alertGroup.addAll(newAlertNeighbours);
            alerts.removeAll(newAlertNeighbours);
        }

        return alertGroup;
    }


    private List<Alert> findAlertNeighbours(Alert alert, List<Alert> alerts) {

        List<Alert> alertGroup = new ArrayList<>();

        for (Alert alertRadius : alerts) {

            if (GeoUtil.measureGeoDistance(alertRadius.getLocation(), alert.getLocation()) <= ApiConstants.ALERT_GROUP_RADIUS) {
                alertGroup.add(alertRadius);
            }
        }

        return alertGroup;
    }
}
