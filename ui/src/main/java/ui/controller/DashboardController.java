package ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import library.User;
import ui.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by guillaimejanssen on 30/04/2017.
 */
public class DashboardController implements Initializable {

    @FXML
    private Button logoutBtn;
    @FXML
    private VBox calamityBtn;
    @FXML
    private VBox sendRescuerBtn;

    private Main main;
    private User user;

    public DashboardController(Main main, User user) {
        this.main = main;
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logoutBtn.setOnAction(this::handleLogoutAction);
        calamityBtn.setOnMouseClicked(this::handleCalamityBtnAction);
        sendRescuerBtn.setOnMouseClicked(this::handleInformRescuerBtnAction);
    }

    private void handleCalamityBtnAction(MouseEvent mouseEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CalamityList.fxml"));
        CalamityListController calamityListController = new CalamityListController(user);
        calamityListController.user = this.user;
        fxmlLoader.setController(calamityListController);

        try {
            main.setStage(fxmlLoader.load(), stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleInformRescuerBtnAction(MouseEvent mouseEvent) {
        Stage stage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/SendRescuer.fxml"));
        SendRescuerController controller = new SendRescuerController(user);
        fxmlLoader.setController(controller);

        try {
            main.setStage(fxmlLoader.load(), stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogoutAction(ActionEvent actionEvent) {
        try {
            main.getPrimaryStage().close();
            main.loadLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
