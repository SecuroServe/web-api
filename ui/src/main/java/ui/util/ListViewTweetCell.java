package ui.util;

import interfaces.ISocial;
import javafx.scene.control.ListCell;
import library.SocialPost;

/**
 * Created by yannic on 19-6-17.
 */
public class ListViewTweetCell extends ListCell<SocialPost> {
    public ListViewTweetCell() {

    }

    @Override
    public void updateItem(SocialPost socialPost, boolean empty)
    {
        super.updateItem(socialPost,empty);
        if(socialPost != null)
        {
            SocialCell cell = new SocialCell(socialPost);
            setGraphic(cell.getBox());
        }
    }

}
