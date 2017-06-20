package ui.util;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import library.SocialPost;

import javafx.scene.control.Label;
import java.io.IOException;

/**
 * Created by yannic on 19-6-17.
 */
public class SocialCell {

    public static final String TEXT_FILL = "-fx-text-fill: black !important;";
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

        Font tweetFont = new Font("Cambria", 10);
        //author label
        labelAuthor.setText(post.getUsername());
        labelAuthor.setFont(new Font("Cambria", 15));
        labelAuthor.setStyle(TEXT_FILL);

        labelDate.setText(post.getCreatedDate().toString());
        labelDate.setFont(tweetFont);
        labelDate.setStyle(TEXT_FILL);

        labelMessage.setText(post.getTweetMessage());
        labelMessage.setFont(tweetFont);
        labelMessage.setStyle(TEXT_FILL);
        labelMessage.setWrapText(true);

        box.setStyle("-fx-border-color: black;");
    }

    public Node getBox() {
        return box;
    }
}
