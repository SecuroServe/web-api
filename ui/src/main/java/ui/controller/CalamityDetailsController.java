package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import library.Calamity;
import library.User;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class CalamityDetailsController implements Initializable {

    private Main main;
    private Calamity calamity;
    private User user;

    @FXML
    public Button backBtn;
    @FXML
    public Label calamityTitle;
    @FXML
    public Label calamityCreator;
    @FXML
    public Label calamityState;
    @FXML
    public Label calamityDate;
    @FXML
    public TextArea calamityDescription;

    public CalamityDetailsController(User user, Calamity calamity, Main main) {
        this.calamity = calamity;
        this.main = main;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBtn.setOnAction(this::handleBackAction);

        calamityTitle.setText(calamity.getTitle());
        calamityCreator.setText("Alerted by: " + calamity.getUser().getUsername() + " te " + calamity.getUser().getCity());
        calamityState.setText("State: " + calamity.getState());
        calamityDate.setText("Date: " + calamity.getDate().toString());

        String s = calamity.getMessage();
        s = s.replace(". ", ".\n");

        calamityDescription.setText(s);
        calamityDescription.setEditable(false);
    }

    private void handleBackAction(ActionEvent actionEvent) {
        try {
            main.loadCalamityList(this.user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
