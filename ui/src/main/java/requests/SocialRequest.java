package requests;

import interfaces.ConfirmationMessage;
import interfaces.ISocial;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rest.RestClient;

/**
 * Created by Jandie on 19-Jun-17.
 */
public class SocialRequest implements ISocial {
    private static final String REQUEST_PREFIX = "https://www.securoserve.nl/api";
    private static final String GET_SOCIAL_POSTS = "/getsocialposts";
    private RestClient restClient;

    public SocialRequest() {
        this.restClient = new RestClient();
    }

    /**
     * Find social posts based on key words.
     *
     * @param token    The authentication token.
     * @param keyWords String with different keywords separated by spaces.
     * @return List of found tweet objects.
     */
    @Override
    public ConfirmationMessage getSocialPosts(String token, String keyWords) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("token", token);
        parameters.add("keyWords", keyWords);

        return restClient.request(REQUEST_PREFIX + GET_SOCIAL_POSTS, RestClient.RequestType.GET, parameters);
    }
}
