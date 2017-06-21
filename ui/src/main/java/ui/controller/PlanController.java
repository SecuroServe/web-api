package ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import interfaces.ConfirmationMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import library.Calamity;
import library.Plan;
import library.User;
import library.Weather;
import netscape.javascript.JSObject;
import requests.CalamityRequest;
import requests.UserRequest;
import requests.WeatherRequest;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by guillaimejanssen on 20/03/2017.
 *
 * @author guillaime
 */
public class PlanController implements Initializable {

    /*
    * Connections to the fxml file, every button, label etc.
    * that will get filled are in here
    * */
    @FXML
    private Button saveButton;

    @FXML
    private TextArea descriptionTextArea;

    private User user;
    private Calamity selectedCalamity;

    public PlanController(User user, Calamity selectedCalamity) {
        this.user = user;
        this.selectedCalamity = selectedCalamity;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setOnAction(this::handleSaveAction);

        // Fill the textarea with the existing description
        if (selectedCalamity.getPlan() != null) {
            descriptionTextArea.setText(selectedCalamity.getPlan().getDescription());
        }
    }

    public void handleSaveAction(ActionEvent event) {

        if (!descriptionTextArea.getText().isEmpty()) {

            CalamityRequest calamityRequest = new CalamityRequest();

            if (selectedCalamity.getPlan() == null) {
                calamityRequest.addPlan(user.getToken(), selectedCalamity.getId(), descriptionTextArea.getText());
            } else {
                calamityRequest.updatePlan(user.getToken(), selectedCalamity.getPlan().getId(), descriptionTextArea.getText());
            }

            selectedCalamity.getPlan().setDescription(descriptionTextArea.getText());
        }
    }
}

