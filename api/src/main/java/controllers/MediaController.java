package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.IMedia;
import library.Media;
import logic.MediaLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @RequestMapping("/getmedia")
    public ConfirmationMessage getMedia(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "id") int id) {
        return mediaLogic.getMedia(token, id);
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
    @RequestMapping("/addmedia")
    public ConfirmationMessage addMedia(@RequestParam(value = "token") String token,
                                        @RequestParam(value = "media") Media media,
                                        @RequestParam(value = "alertId") int alertId) {
        return mediaLogic.addMedia(token, media, alertId);
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
    @RequestMapping("/uploadmedia")
    public ConfirmationMessage uploadMedia(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "mediaId") int mediaId,
                                           @RequestParam(value = "file") MultipartFile file) {
        return mediaLogic.uploadMedia(token, mediaId, file);
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
    @RequestMapping("/downloadmedia")
    public ConfirmationMessage downloadMedia(@RequestParam(value = "token") String token,
                                             @RequestParam(value = "mediaId") int mediaId) {
        return mediaLogic.downloadMedia(token, mediaId);
    }

    /**
     * Updates a media object.
     *
     * @param token The authentication token.
     * @param media The media to update.
     * @return Confirmation message with feedback about the addition update
     * containing the new update.
     */
    @Override
    @RequestMapping("/updatemedia")
    public ConfirmationMessage updateMedia(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "media") Media media) {
        return mediaLogic.updateMedia(token, media);
    }

    /**
     * Removes media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media object.
     * @return Confirmation message with feedback about the deletion
     */
    @Override
    @RequestMapping("/removemedia")
    public ConfirmationMessage removeMedia(@RequestParam(value = "token") String token,
                                           @RequestParam(value = "mediaId") int mediaId) {
        return mediaLogic.removeMedia(token, mediaId);
    }
}
