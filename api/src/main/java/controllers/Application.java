package controllers;

import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import utils.FCMHelper;

import java.io.IOException;

/**
 * Created by Jandie on 13-3-2017.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(controllers.Application.class, args);
    }
}