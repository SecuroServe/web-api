package ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ConfirmationMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import library.Calamity;
import library.Location;
import library.User;
import requests.CalamityRequest;
import requests.UserRequest;

import java.net.URL;
import java.util.ArrayList;
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

    private CalamityRequest calamityRequest;
    private UserRequest userRequest;

    public SendRescuerController(User user) {
        this.user = user;
        selectedUsers = FXCollections.observableArrayList();
        calamityRequest = new CalamityRequest();
        userRequest = new UserRequest();

        ObjectMapper objMapper = new ObjectMapper();

        ConfirmationMessage calamityMessage = calamityRequest.allCalamity();//check for error
        if (calamityMessage.getStatus() != ConfirmationMessage.StatusType.ERROR) {
            Object val = calamityMessage.getReturnObject();
            //convert list to list of calamities using mapper
            allCalamities = objMapper.convertValue(val, new TypeReference<List<Calamity>>(){});
        } else{
            calamityTableView.setPlaceholder(new Label("No open calamities"));
            allCalamities = new ArrayList<>();
        }

        ConfirmationMessage userMessage = userRequest.allusers(user.getToken());//check for error
        if (userMessage.getStatus() != ConfirmationMessage.StatusType.ERROR) {
            Object val = userMessage.getReturnObject();
            //convert list to list of users
            availableUsers = objMapper.convertValue(val, new TypeReference<List<User>>(){});
        } else{
            rescuerTableView.setPlaceholder(new Label("No available rescuers"));
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
        //rescuerTableView
        TableColumn rescuerNameCol = new TableColumn("Name");
        rescuerNameCol.setCellValueFactory(new PropertyValueFactory<User, String>("username"));

        TableColumn rescuerLocationCol = new TableColumn("Location");
        rescuerLocationCol.setCellValueFactory(new PropertyValueFactory<User, String>("city"));

        rescuerTableView.getColumns().addAll(rescuerNameCol, rescuerLocationCol);
        //calamityTableView
        TableColumn calamityTitleCol = new TableColumn("Title");
        calamityTitleCol.setCellValueFactory(new PropertyValueFactory<Calamity, String>("title"));

        TableColumn calamityLocationCol = new TableColumn("Location");
        calamityLocationCol.setCellValueFactory(new PropertyValueFactory<Calamity, Location>("location"));

        calamityTableView.getColumns().addAll(calamityTitleCol, calamityLocationCol);

        //set tableView lists
        rescuerTableView.setItems(FXCollections.observableArrayList(availableUsers));
        calamityTableView.setItems(FXCollections.observableArrayList(allCalamities));
        selectedRescuerListView.setItems(selectedUsers);


    }

    private void setButtonActions() {
        backButton.setOnAction(this::handleBackAction);
        doneButton.setOnAction(this::finishAction);
        selectCalamityButton.setOnAction(this::selectCalamity);
        addRescuerButton.setOnAction(this::addRescuer);
        removeRescuerButton.setOnAction(this::removeRescuer);
    }

    private void refreshTableViews() {
        rescuerTableView.setItems(FXCollections.observableList(availableUsers));
        calamityTableView.setItems(FXCollections.observableList(allCalamities));
    }

    /***
     * adds a rescuer to respond to the selected calamity
     * updates the listView
     * @param actionEvent
     */
    private void addRescuer(ActionEvent actionEvent) {
        User selUser = rescuerTableView.getSelectionModel().getSelectedItem();
        if (selUser != null) {
            selectedUsers.add(selUser);
            availableUsers.remove(selUser);
        }else{
            showMessage("No user selected");
        }
        refreshTableViews();
    }

    /***
     * removes a rescuer from the list of selected rescuers.
     * updates the listview.
     * @param actionEvent
     */
    private void removeRescuer(ActionEvent actionEvent) {
        User selUser = selectedRescuerListView.getSelectionModel().getSelectedItem();
        if (selUser != null) {
            selectedUsers.remove(selUser);
            availableUsers.add(selUser);
        }else {
            showMessage("No user selected");
        }
        refreshTableViews();
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
        } else {
            showMessage("No calamity selected");
        }
    }

    private void setSelectedCalamity(Calamity calamity) {
        allCalamities.remove(calamity);
        if (selectedCalamity != null) {
            allCalamities.add(selectedCalamity);
        }
        selectedCalamity = calamity;
        selectedCalamityLabel.setText(calamity.getTitle());
        refreshTableViews();
        //todo set selected users
    }

    /***
     * this is a method handling the end of the action
     * informs the rescuers and sets the database
     * then closes the screen.
     * @param actionEvent
     */
    private void finishAction(ActionEvent actionEvent) {
        //todo send a notification to Rescuers;
        if(selectedCalamity == null || selectedUsers.size() == 0){
            String s = "Please select a calamity, and at least 1 rescuer";
            showMessage(s);
        }else {
          
            calamityRequest.updateCalamity(user.getToken(),
                    selectedCalamity.getId(),
                    selectedCalamity.getTitle(),
                    selectedCalamity.getMessage(),
                    selectedCalamity.getLocation().getId(),
                    selectedCalamity.getLocation().getLatitude(),
                    selectedCalamity.getLocation().getLongitude(),
                    selectedCalamity.getLocation().getRadius(),
                    selectedCalamity.isConfirmed(),
                    selectedCalamity.isClosed());

            for(User u : selectedUsers){
                calamityRequest.addCalamityAssignee(user.getToken(), selectedCalamity.getId(), u.getId());
            }
            showMessage("Succesfully added users to the calamity");

        }
    }

    /**
     * shows a message containing the passed String.
     * @param s the string to show
     */
    private void showMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message!");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    /**
     * this method goes back to the dashboard.
     * it is called when the backbutton is clicked.
     * nothing is done with
     *
     * @param actionEvent
     */
    private void handleBackAction(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
