package ui.controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import interfaces.ConfirmationMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import library.UserType;
import requests.CalamityRequest;
import requests.UserRequest;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private Label calamityTitle;
    @FXML
    private TextField titleTextField;
    @FXML
    private Label calamityCreator;
    @FXML
    private Label calamityDate;
    @FXML
    private Label calamityMessage;

    @FXML
    private GoogleMapView googleMapView;

    private GoogleMap map;

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

        titleTextField.setVisible(false);

        initiateTableColumns();
        initiateUserTable();
        refreshCalamityTable();
        refreshUserTable();

        // Refreshing the table every 10 seconds
        timerToRefresh.schedule(new PostRequestTask(), 10 * 1000);

        googleMapView.addMapInializedListener(() -> mapInitialized());
    }

    private void handleChangeAction(ActionEvent actionEvent) {
        titleTextField.setVisible(true);
        calamityTitle.setVisible(false);
        titleTextField.setText(calamityTitle.getText());
    }

    private void handleBackAction(ActionEvent actionEvent) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    private void handleRefreshAction(ActionEvent actionEvent) {
        refreshCalamityTable();
    }

    private void fillCalamityDetails(Calamity calamity) throws NullPointerException {
        calamityTitle.setText(calamity.getTitle());
        calamityCreator.setText(calamity.getUser().toString());
        calamityDate.setText(calamity.getDate().toString());
        calamityMessage.setText(createReadableText(calamity.getMessage()));
        updateMap(calamity.getLocation());
    }

    private String createReadableText(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        System.out.println(message.length());

        for(int i = 0; i < message.length(); i++) {
            stringBuilder.append(message.charAt(i));
            counter++;

            if(counter > 50) {
                stringBuilder.append("\n");
                System.out.println(counter);
                counter = 0;
            }
        }
        return stringBuilder.toString();
    }

    private void fillDefaultCalamityDetails() {
        calamityTitle.setText("Calamity title");
        calamityCreator.setText("Calamity creator");
        calamityDate.setText("Calamity date");
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
        List<User> users = (List<User>) userRequest.allusers(user.getToken()).getReturnObject();

        ObservableList<User> obsList = FXCollections.observableArrayList(users);
        userTable.setItems(obsList);
    }

    private void refreshCalamityTable() {
        //CalamityRequest calamityRequest = new CalamityRequest();
        //calamities = (List<Calamity>) calamityRequest.allCalamity().getReturnObject();

        List<Calamity> calamities = new ArrayList<>();
        calamities.add(new Calamity(1, new Location(1, 51.4477766, 5.4909617, 250.0),
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

    private void updateMap(Location loc){
        LatLong location = new LatLong(loc.getLatitude(), loc.getLongitude());
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(location)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(15);

        map = googleMapView.createMap(mapOptions);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);

        Marker marker = new Marker(markerOptions);
        map.addMarker(marker);

        CircleOptions circleO = new CircleOptions();
        circleO.center(location);
        circleO.radius(loc.getRadius());

        Circle circle = new Circle(circleO);
        map.addMapShape(circle);

        InfoWindowOptions iWO = new InfoWindowOptions();
        iWO.content("Bericht");

        InfoWindow iw = new InfoWindow(iWO);
        iw.open(map, marker);
    }

    public void mapInitialized() {
        LatLong fontys = new LatLong(51.4520890, 5.4819830);

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(51.4520890, 5.4819830))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(15);

        map = googleMapView.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(fontys);

        Marker fontysM = new Marker(markerOptions1);

        map.addMarker( fontysM );

    }
}

