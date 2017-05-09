package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.IMedia;
import library.Media;
import logic.MediaLogic;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaController implements IMedia {
    private MediaLogic mediaLogic;

    public MediaController() {
        this.mediaLogic = new MediaLogic();
    }

    public MediaController(Database database) {
        this.mediaLogic = new MediaLogic(database);
    }

    /**
     * Gets Media object by id.
     *
     * @param token The authentication token.
     * @param id    The id of the media to get.
     * @return ConfirmationMessage with the Media object.
     */
    @Override
    public ConfirmationMessage getMedia(String token, int id) {
        return null;
    }

    /**
     * Adds new Media.
     *
     * @param token   The authentication token.
     * @param media   The media object to add.
     * @param alertId The id of the alert where media is stored.
     * @return Confirmation message with feedback about the addition
     * also containing the new media.
     */
    @Override
    public ConfirmationMessage addMedia(String token, Media media, int alertId) {
        return null;
    }

    /**
     * Uploads a new media file. Deletes existing media file if exists.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media the file belongs to.
     * @param file    The file to upload in MultipartFile object.
     * @return Confirmation message with feedback about the upload action
     */
    @Override
    public ConfirmationMessage uploadMedia(String token, int mediaId, MultipartFile file) {
        return null;
    }

    /**
     * Downloads a media file that belongs to a media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media the file belongs to.
     * @return Confirmation message with feedback about the download action
     * and also contains the downloaded file.
     */
    @Override
    public ConfirmationMessage downloadMedia(String token, int mediaId) {
        return null;
    }

    /**
     * Updates a media object.
     *
     * @param token The authentication token.
     * @param media The media to update.
     * @param file  Optional file to add to media, leave null if not necessary.
     * @return Confirmation message with feedback about the addition update
     * containing the new update.
     */
    @Override
    public ConfirmationMessage updateMedia(String token, Media media, MultipartFile file) {
        return null;
    }

    /**
     * Removes media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media object.
     * @return Confirmation message with feedback about the deletion
     */
    @Override
    public ConfirmationMessage removeMedia(String token, int mediaId) {
        return null;
    }
}
