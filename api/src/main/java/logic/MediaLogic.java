package logic;

import datarepo.MediaRepo;
import datarepo.UserRepo;
import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import library.Media;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaLogic {
    private Database database;
    private UserRepo userRepo;
    private MediaRepo mediaRepo;

    public MediaLogic() {
        this.database = new Database();
        userRepo = new UserRepo(database);
        mediaRepo = new MediaRepo(database);
    }

    public MediaLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
        mediaRepo = new MediaRepo(database);
    }

    /**
     * Gets Media object by id.
     *
     * @param token The authentication token.
     * @param id    The id of the media to get.
     * @return ConfirmationMessage with the Media object.
     */
    public ConfirmationMessage getMedia(String token, int id) {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Get media succesfull!",
                    mediaRepo.getMedia(id));
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Get media failed!",
                    e);
        }
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
    public ConfirmationMessage addMedia(String token, Media media, int alertId) {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Media added",
                    mediaRepo.addMedia(media.getName(), alertId));
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Add media failed!",
                    e);
        }
    }

    /**
     * Uploads a new media file. Deletes existing media file if exists.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media the file belongs to.
     * @param file    The file to upload in MultipartFile object.
     * @return Confirmation message with feedback about the upload action
     */
    public ConfirmationMessage uploadMedia(String token, int mediaId, MultipartFile file) {
        try {
            mediaRepo.saveFile(file, buildCustomFileName(mediaId));

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "File uploaded",
                    null);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "File upload failed!",
                    e);
        }
    }

    /**
     * Downloads a media file that belongs to a media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media the file belongs to.
     * @return Confirmation message with feedback about the download action
     * and also contains the downloaded file.
     */
    public ConfirmationMessage downloadMedia(String token, int mediaId) {
        try {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "File gotten",
                    mediaRepo.loadFile(buildCustomFileName(mediaId)));
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "File download error!",
                    e);
        }
    }

    /**
     * Updates a media object.
     *
     * @param token The authentication token.
     * @param media The media to update.
     * @return Confirmation message with feedback about the addition update
     * containing the new update.
     */
    public ConfirmationMessage updateMedia(String token, Media media) {
        try {
            mediaRepo.updateMedia(media);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Media updated",
                    media);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Update media failed!",
                    e);
        }
    }

    /**
     * Removes media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media object.
     * @return Confirmation message with feedback about the deletion
     */
    public ConfirmationMessage removeMedia(String token, int mediaId) {
        try {
            mediaRepo.removeMedia(mediaId);

            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES,
                    "Media removed",
                    null);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR,
                    "Remove media failed!",
                    e);
        }
    }

    private String buildCustomFileName(int id) {
        return id + ".sec";
    }
}
