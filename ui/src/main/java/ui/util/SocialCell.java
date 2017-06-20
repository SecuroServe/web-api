package ui.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import library.SocialPost;

import javafx.scene.control.Label;
import java.io.IOException;

/**
 * Created by yannic on 19-6-17.
 */
public class SocialCell {

    @FXML
    private VBox box;

    @FXML
    private Label labelAuthor;

    @FXML
    private Label labelDate;

    @FXML
    private Label labelMessage;

    public SocialCell(SocialPost post) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TweetCellItem.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        labelAuthor.setText(post.getUsername());
        labelDate.setText(post.getCreatedDate().toString());
        labelMessage.setText(post.getTweetMessage());

        box.setStyle("-fx-border-color: red;");
    }

    public Node getBox() {
        return box;
    }
}
