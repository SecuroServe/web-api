package requests;

import interfaces.ConfirmationMessage;
import interfaces.IMedia;
import library.Media;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import rest.RestClient;

/**
 * Created by Jandie on 2017-05-22.
 */
public class MediaRequest implements IMedia {
    private static final String REQUEST_PREFIX = "https://www.securoserve.nl/api";
    private static final String GET_MEDIA = "/getmedia";
    private static final String ADD_MEDIA = "/addmedia";
    private static final String UPLOAD_MEDIA = "/uploadmedia";
    private static final String DOWNLOAD_MEDIA = "/downloadmedia";
    private static final String UPDATE_MEDIA = "/updatemedia";
    private static final String REMOVE_MEDIA = "removemedia";


    private RestClient restClient;

    public MediaRequest() {
        this.restClient = new RestClient();
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("id", id);

        return restClient.request(REQUEST_PREFIX + GET_MEDIA, RestClient.RequestType.GET, parameters);
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("media", media);
        parameters.add("alertId", alertId);

        return restClient.request(REQUEST_PREFIX + ADD_MEDIA, RestClient.RequestType.POST, parameters);
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("mediaId", mediaId);
        parameters.add("file", file);

        return restClient.request(REQUEST_PREFIX + UPLOAD_MEDIA, RestClient.RequestType.POST, parameters);
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("mediaId", mediaId);

        return restClient.request(REQUEST_PREFIX + DOWNLOAD_MEDIA, RestClient.RequestType.GET, parameters);
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("media", media);

        return restClient.request(REQUEST_PREFIX + UPDATE_MEDIA, RestClient.RequestType.POST, parameters);
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
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", token);
        parameters.add("mediaId", mediaId);

        return restClient.request(REQUEST_PREFIX + REMOVE_MEDIA, RestClient.RequestType.POST, parameters);
    }
}
