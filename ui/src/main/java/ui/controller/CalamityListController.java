package ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Calamity;
import library.Location;
import library.User;
import requests.CalamityRequest;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by guillaimejanssen on 20/03/2017.
 *
 * @author guillaime
 */
public class CalamityListController implements Initializable {

    @FXML
    public Button refreshButton;
    @FXML
    public Button backBtn;
    @FXML
    public TableView<Calamity> calamityTable;

    private Main main;
    private User user;

    private List<Calamity> calamities;
    private ObservableList<Calamity> obsList;

    private Timer timerToRefresh = new Timer();

    public CalamityListController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshButton.setOnAction(this::handleRefreshAction);
        backBtn.setOnAction(this::handleCloseAction);
        calamityTable.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                try {
                    main.loadCalamityDetails(user, calamityTable.getSelectionModel().getSelectedItem());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        initiateTableColumns();
        refreshCalamityTable();

        // Refreshing the table every 10 seconds
        timerToRefresh.schedule(new PostRequestTask(), 10 * 1000);
    }

    private void handleBackAction(ActionEvent actionEvent) {
        //TODO Go back to dashboard
    }

    private void handleRefreshAction(ActionEvent actionEvent) {
        refreshCalamityTable();
    }

    private void handleCloseAction(ActionEvent actionEvent) {
        try {
            main.loadDashBoard(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initiateTableColumns() {

        calamityTable.setColumnResizePolicy((param) -> true);

        TableColumn calamityName = new TableColumn("Calamity");
        calamityName.getStyleClass().add("foo");
        calamityName.setCellValueFactory(new PropertyValueFactory<Calamity, String>("title"));
        calamityTable.getColumns().add(calamityName);

        TableColumn calamityLocation = new TableColumn("Location");
        calamityLocation.getStyleClass().add("foo");
        calamityLocation.setCellValueFactory(new PropertyValueFactory<Calamity, Location>("location"));
        calamityTable.getColumns().add(calamityLocation);

        TableColumn calamityAlertedBy = new TableColumn("Alerted By");
        calamityAlertedBy.getStyleClass().add("foo");
        calamityAlertedBy.setCellValueFactory(new PropertyValueFactory<Calamity, User>("user"));
        calamityTable.getColumns().add(calamityAlertedBy);

        TableColumn calamityStatus = new TableColumn("State");
        calamityStatus.getStyleClass().add("foo");
        calamityStatus.setCellValueFactory(new PropertyValueFactory<Calamity, Calamity.CalamityState>("state"));
        calamityTable.getColumns().add(calamityStatus);

        TableColumn calamityDate = new TableColumn("Date");
        calamityDate.getStyleClass().add("foo");
        calamityDate.setCellValueFactory(new PropertyValueFactory<Calamity, Date>("date"));
        calamityTable.getColumns().add(calamityDate);
    }

    private void refreshCalamityTable() {
//        CalamityRequest calamityRequest = new CalamityRequest();
//        calamities = (List<Calamity>) calamityRequest.allCalamity().getReturnObject();
//
//        obsList = FXCollections.observableArrayList(calamities);
//        calamityTable.setItems(obsList);
    }

    class PostRequestTask extends TimerTask {
        @Override
        public void run() {
            refreshCalamityTable();
        }
    }
}

