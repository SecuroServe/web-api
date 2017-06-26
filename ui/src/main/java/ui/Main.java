package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import library.User;
import ui.controller.DashboardController;
import ui.controller.LoginController;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        loadLogin();
    }

    public void loadLogin() throws IOException {

        if (primaryStage != null) {
            primaryStage = new Stage();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        LoginController controller = new LoginController(this);
        fxmlLoader.setController(controller);
        setStage(fxmlLoader.load(), primaryStage, "Login");
    }

    public void loadDashBoard(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
        DashboardController controller = new DashboardController(this, user);
        fxmlLoader.setController(controller);
        setStage(fxmlLoader.load(), primaryStage, "Dashboard");
    }

    public void setStage(Parent root, Stage stage) throws IOException {
        this.setStage(root, stage, "SecuroServe");
    }

    public static void setStage(Parent root, Stage stage, String title) throws IOException {

        // Prepare stage
        stage.setResizable(false);
        stage.setTitle("SecuroServe - " + title);
        stage.setScene(new Scene(root));
        stage.show();

        // Center it
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
