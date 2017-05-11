package ui.controller;

import interfaces.ConfirmationMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import library.Calamity;
import library.User;
import requests.CalamityRequest;
import requests.UserRequest;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by yannic on 12/04/2017.
 */
public class SendRescuerController implements Initializable {
    //fxml
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
    private TableView<Calamity> calamityTableView;
    @FXML
    private TableView<User> rescuerTableView;
    @FXML
    private ListView<User> selectedRescuerListView;
    @FXML
    private Label selectedCalamityLabel;
    //fields
    private User user;

    private ObservableList<User> selectedUsers;
    private List<User> availableUsers;
    private List<Calamity> allCalamities;
    private Calamity selectedCalamity;

    public SendRescuerController(User user) {
        this.user = user;
        selectedUsers = FXCollections.observableArrayList();

        CalamityRequest request = new CalamityRequest();
        ConfirmationMessage calamityMessage = request.allCalamity();//check for error
        if(calamityMessage.getStatus() != ConfirmationMessage.StatusType.ERROR){
            allCalamities = (List) calamityMessage.getReturnObject();
        } else{
            //todo display empty list (error)message
            allCalamities = new ArrayList<>();
        }

        UserRequest userRequest = new UserRequest();
        ConfirmationMessage userMessage = userRequest.allusers(user.getToken());//check for error
        if(userMessage.getStatus() != ConfirmationMessage.StatusType.ERROR){
            availableUsers = (List) userMessage.getReturnObject();
        } else{
            //todo display empty list (error)message
            availableUsers = new ArrayList<>();
        }
        //todo filter for only available rescuers.
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
        //set button actions
        setButtonActions();
        //init tables
        initTableViews();

        selectedCalamityLabel.setText("no calamity selected");
    }

    private void initTableViews() {
        //set tableView columns
        //todo set tableview collums
        //set tableView lists
        rescuerTableView.setItems(FXCollections.observableArrayList(availableUsers));
        calamityTableView.setItems(FXCollections.observableArrayList(allCalamities));
        rescuerTableView.setItems(selectedUsers);

    }

    private void setButtonActions() {
        backButton.setOnAction(this::handleBackAction);
        doneButton.setOnAction(this::finishAction);
        selectCalamityButton.setOnAction(this::selectCalamity);
        addRescuerButton.setOnAction(this::addRescuer);
        removeRescuerButton.setOnAction(this::removeRescuer);
    }

    /***
     * adds a rescuer to respond to the selected calamity
     * updates the listView
     * @param actionEvent
     */
    private void addRescuer(ActionEvent actionEvent) {
        User selUser = rescuerTableView.getSelectionModel().getSelectedItem();
        if(selUser != null){
            selectedUsers.add(selUser);
        }else{
            //todo popup a message saying what went wrong;
        }

    }

    /***
     * removes a rescuer from the list of selected rescuers.
     * updates the listview.
     * @param actionEvent
     */
    private void removeRescuer(ActionEvent actionEvent) {
        User selUser = selectedRescuerListView.getSelectionModel().getSelectedItem();
        if(selUser != null){
            selectedUsers.remove(selUser);
        }else {
            //todo popup another message with what went wrong.
        }

    }

    /***
     * selects a calamity to assign the rescuers to.
     * sets the calamity, and changes the label.
     * @param actionEvent
     */
    private void selectCalamity(ActionEvent actionEvent) {
        Calamity selCalamity = calamityTableView.getSelectionModel().getSelectedItem();
        if(selCalamity != null){
            setSelectedCalamity(selCalamity);
        }
    }

    private void setSelectedCalamity(Calamity calamity) {
        selectedCalamity = calamity;
        selectedCalamityLabel.setText(calamity.getTitle());
    }

    /***
     * this is a method handling the end of the action
     * informs the rescuers and sets the database
     * then closes the screen.
     * @param actionEvent
     */
    private void finishAction(ActionEvent actionEvent) {
        //todo check all inputs and send a notification to Rescuers;
    }

    /**
     * this method goes back to the dashboard.
     * it is called when the backbutton is clicked.
     * nothing is done with
     * @param actionEvent
     */
    private void handleBackAction(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
