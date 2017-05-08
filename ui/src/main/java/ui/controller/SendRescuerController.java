package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import library.User;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by yannic on 12/04/2017.
 */
public class SendRescuerController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private Button doneButton;
    @FXML
    private Button selectCalamityButton;
    @FXML
    private Button addRescuerButton;
    @FXML
    private Button removeRescuerButton;
    @FXML
    private TableView calamityTableView;
    @FXML
    private TableView rescuerTableView;
    @FXML
    private ListView selectedRescuerListView;
    @FXML
    private Label selectedCalamityLabel;

    private User user;
    private Main main;

    public SendRescuerController(User user, Main main) {
        this.user = user;
        this.main = main;

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(this::handleBackAction);
        doneButton.setOnAction(this::finishAction);
        selectCalamityButton.setOnAction(this::selectCalamity);
        addRescuerButton.setOnAction(this::addRescuer);
        removeRescuerButton.setOnAction(this::removeRescuer);
        selectedCalamityLabel.setText("test");


    }

    /***
     * adds a rescuer to respond to the selected calamity
     * updates the listView
     * @param actionEvent
     */
    private void addRescuer(ActionEvent actionEvent) {

    }

    /***
     * removes a rescuer from the list of selected rescuers.
     * updates the listview.
     * @param actionEvent
     */
    private void removeRescuer(ActionEvent actionEvent) {


    }

    /***
     * selects a calamity to assign the rescuers to.
     * sets the calamity, and changes the label.
     * @param actionEvent
     */
    private void selectCalamity(ActionEvent actionEvent) {

    }

    /***
     * this is a method handling the end of the action
     * informs the rescuers and sets the database
     * then closes the screen.
     * @param actionEvent
     */
    private void finishAction(ActionEvent actionEvent) {

    }

    /**
     * this method goes back to the dashboard.
     * it is called when the backbutton is clicked.
     * nothing is done with
     * @param actionEvent
     */
    private void handleBackAction(ActionEvent actionEvent) {
        try {
            main.loadDashBoard(this.user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
