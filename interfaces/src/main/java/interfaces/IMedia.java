package interfaces;

import library.Media;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Jandie on 2017-05-08.
 */
public interface IMedia {
    /**
     * Gets Media object by id.
     *
     * @param token The authentication token.
     * @param id    The id of the media to get.
     * @return ConfirmationMessage with the Media object.
     */
    ConfirmationMessage getMedia(String token, int id);

    /**
     * Adds new Media.
     *
     * @param token   The authentication token.
     * @param media   The media object to add.
     * @param file    Optional file to add to media, leave null if not necessary.
     * @param alertId The id of the alert where media is stored.
     * @return Confirmation message with feedback about the addition
     * also containing the new media.
     */
    ConfirmationMessage addMedia(String token, Media media, MultipartFile file, int alertId);

    /**
     * Updates a media object.
     *
     * @param token The authentication token.
     * @param media The media to update.
     * @param file Optional file to add to media, leave null if not necessary.
     * @return Confirmation message with feedback about the addition update
     * containing the new update.
     */
    ConfirmationMessage updateMedia(String token, Media media, MultipartFile file);

    /**
     * Removes media object.
     *
     * @param token   The authentication token.
     * @param mediaId The id of the media object.
     * @return Confirmation message with feedback about the deletion
     */
    ConfirmationMessage removeMedia(String token, int mediaId);
}
