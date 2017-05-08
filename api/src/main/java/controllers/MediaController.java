package controllers;

import interfaces.ConfirmationMessage;
import interfaces.IMedia;
import library.Media;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaController implements IMedia {
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
     * Updates a media object.
     *
     * @param token The authentication token.
     * @param media The media to update.
     * @return Confirmation message with feedback about the addition update
     * containing the new update.
     */
    @Override
    public ConfirmationMessage updateMedia(String token, Media media) {
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
