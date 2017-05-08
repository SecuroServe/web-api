package rest;

import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jandie on 2017-05-01.
 */
public class RestClientTest {
    @Test
    public void generateUrl() throws Exception {

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        map.add("test1", 1);
        map.add("test2", 2);
        map.add("test3", 3);

        RestClient rc = new RestClient();
        String output = rc.generateUrl("http://localhost:8080", map);

        // Order is not sorted because it's a hashmap, we expect the order 1, 2, 3.
        assertEquals("http://localhost:8080?test1=1&test2=2&test3=3", output);
    }

}