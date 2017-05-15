package logic;

import constants.ApiConstants;
import datarepo.AlertRepo;
import datarepo.UserRepo;
import datarepo.database.Database;
import exceptions.NoPermissionException;
import interfaces.ConfirmationMessage;
import library.Alert;
import library.Location;
import library.User;
import library.UserType;
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
     * @param token       The authentication token.
     * @param id          The id of the alert.
     * @param name        The name of the alert.
     * @param description The description of the alert.
     * @param location    The location of the alert.
     * @return Confirmation message with feedback about the update.
     */
    public ConfirmationMessage updateAlert(String token, int id, String name, String description, int urgency, Location location) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.ALERT_UPDATE);

            Alert alert = alertRepo.getAlert(id);
            alert.setName(name);
            alert.setDescription(description);
            alert.setUrgency(urgency);
            alert.setLocation(location);

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
                    "Get all alerts", alertRepo.allAlert());
        } catch (SQLException | NoSuchAlgorithmException | ParseException e) {
            Logger.getLogger(AlertLogic.class.getName()).log(Level.SEVERE,
                    "Error while getting all alerts", e);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Error while getting all alerts", e);
        }
    }

    private void calculateAlertGroups() {
//        List<?> alerts = (List<?>) allAlert().getReturnObject();
//
//        List<Alert> alertGroup = new ArrayList<>();
//
//        for (Object x : alerts) {
//
//            Alert alertX = (Alert) x;
//
//            alertGroup.add(alertX);
//
//            for (Object y : alerts) {
//                Alert alertY = (Alert) y;
//
//                if (GeoUtil.measureGeoDistance(alertY.getLocation(), alertX.getLocation()) > ApiConstants.ALERT_GROUP_RADIUS) {
//                    alertGroup.add(alertY);
//                }
//            }
//
//            if (alertGroup.size() >= ApiConstants.ALERT_GROUP_AMOUNT) {
//                break;
//            }
//
//        }
    }
}
