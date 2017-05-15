package ui.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import library.Location;
import library.User;
import requests.UserRequest;
import java.net.URL;
import java.util.*;

/**
 * Created by guillaimejanssen on 20/03/2017.
 *
 * @author guillaime
 */
public class CalamityListController implements Initializable {

    /*
    * Connections to the fxml file, every button, label etc.
    * that will get filled are in here
    * */
    @FXML
    private Button refreshButton;
    @FXML
    private Button backButton;
    @FXML
    private Button changeButton;

    @FXML
    private TableView<Calamity> calamityTable;
    @FXML
    public TableView<User> userTable;

    // Calamity
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField creatorTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextArea informationTextArea;

    public User user;

    private Timer timerToRefresh = new Timer();

    public CalamityListController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshButton.setOnAction(this::handleRefreshAction);
        backButton.setOnAction(this::handleBackAction);
        calamityTable.setOnMouseClicked((MouseEvent event) -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                try {
                    fillCalamityDetails(calamityTable.getSelectionModel().getSelectedItem());
                } catch (NullPointerException ex) {
                    fillDefaultCalamityDetails();
                }
            }
        });
        changeButton.setOnAction(this::handleChangeAction);

        titleTextField.setEditable(false);
        creatorTextField.setEditable(false);
        dateTextField.setEditable(false);
        informationTextArea.setEditable(false);

        initiateTableColumns();
        initiateUserTable();
        refreshCalamityTable();
        refreshUserTable();

        // Refreshing the table every 10 seconds
        timerToRefresh.schedule(new PostRequestTask(), 10 * 1000);
    }

    private void handleChangeAction(ActionEvent actionEvent) {
        titleTextField.setEditable(true);
        creatorTextField.setEditable(true);
        dateTextField.setEditable(true);
        informationTextArea.setEditable(true);
    }

    private void handleBackAction(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void handleRefreshAction(ActionEvent actionEvent) {
        refreshCalamityTable();
        refreshUserTable();
    }

    private void fillCalamityDetails(Calamity calamity) throws NullPointerException {
        titleTextField.setText(calamity.getTitle());
        creatorTextField.setText(calamity.getUser().toString());
        dateTextField.setText(calamity.getDate().toString());
        informationTextArea.setText(createReadableText(calamity.getMessage()));
    }

    private String createReadableText(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        System.out.println(message.length());

        for(int i = 0; i < message.length(); i++) {
            stringBuilder.append(message.charAt(i));
            String c = Character.toString(message.charAt(i));
            counter++;

            if(counter > 40 && c.equals(" ")) {
                stringBuilder.append("\n");
                System.out.println(counter);
                counter = 0;
            }
        }
        return stringBuilder.toString();
    }

    private void fillDefaultCalamityDetails() {
        titleTextField.setText("Calamity title");
        creatorTextField.setText("Calamity creator");
        dateTextField.setText("Calamity date");
    }

    private void initiateTableColumns() {

        calamityTable.setColumnResizePolicy((param) -> true);

        TableColumn calamityName = new TableColumn("Calamity");
        calamityName.getStyleClass().add("foo");
        calamityName.setMinWidth(150);
        calamityName.setCellValueFactory(new PropertyValueFactory<Calamity, String>("title"));
        calamityTable.getColumns().add(calamityName);

        TableColumn calamityDate = new TableColumn("Date");
        calamityDate.getStyleClass().add("foo");
        calamityDate.setMinWidth(250);
        calamityDate.setCellValueFactory(new PropertyValueFactory<Calamity, Date>("date"));
        calamityTable.getColumns().add(calamityDate);

//        TableColumn calamityLocation = new TableColumn("Location");
//        calamityLocation.getStyleClass().add("foo");
//        calamityLocation.setCellValueFactory(new PropertyValueFactory<Calamity, Location>("location"));
//        calamityTable.getColumns().add(calamityLocation);
//
//        TableColumn calamityAlertedBy = new TableColumn("Alerted By");
//        calamityAlertedBy.getStyleClass().add("foo");
//        calamityAlertedBy.setCellValueFactory(new PropertyValueFactory<Calamity, User>("user"));
//        calamityTable.getColumns().add(calamityAlertedBy);
//
//        TableColumn calamityStatus = new TableColumn("State");
//        calamityStatus.getStyleClass().add("foo");
//        calamityStatus.setCellValueFactory(new PropertyValueFactory<Calamity, Calamity.CalamityState>("state"));
//        calamityTable.getColumns().add(calamityStatus);

    }

    private void initiateUserTable() {

        userTable.setColumnResizePolicy((param) -> true);

        TableColumn userName = new TableColumn("Name");
        userName.getStyleClass().add("foo");
        userName.setMinWidth(150);
        userName.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        userTable.getColumns().add(userName);


        TableColumn userCity = new TableColumn("City");
        userCity.getStyleClass().add("foo");
        userCity.setMinWidth(250);
        userCity.setCellValueFactory(new PropertyValueFactory<User, String>("city"));
        userTable.getColumns().add(userCity);
    }

    private void refreshUserTable() {
        UserRequest userRequest = new UserRequest();
        Object value = userRequest.allusers(user.getToken()).getReturnObject();

        // Cast object to list of objects
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.convertValue(value, new TypeReference<List<User>>() { });

        ObservableList<User> obsList = FXCollections.observableArrayList(users);
        userTable.setItems(obsList);
    }

    private void refreshCalamityTable() {
        //CalamityRequest calamityRequest = new CalamityRequest();
        //calamities = (List<Calamity>) calamityRequest.allCalamity().getReturnObject();

        List<Calamity> calamities = new ArrayList<>();
        calamities.add(new Calamity(1, new Location(1, 20.45345, 20.54375, 1.0),
                new User(1, null, null, null, "Henk", "henk@gmail.com", "Eindhoven", "abcd"),
                true, false, new Date(System.currentTimeMillis()), "Aanslag TU", "Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen. Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw, toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken. Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting. Het is in de jaren '60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten."));

        ObservableList<Calamity> obsList = FXCollections.observableArrayList(calamities);
        calamityTable.setItems(obsList);
    }

    class PostRequestTask extends TimerTask {
        @Override
        public void run() {
            refreshCalamityTable();
        }
    }
}

