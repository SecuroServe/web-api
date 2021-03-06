package requests;

import interfaces.ConfirmationMessage;
import interfaces.IWeather;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import rest.RestClient;

/**
 * Created by Michel on 8-5-2017.
 */
public class WeatherRequest implements IWeather {
    
    private static final String REQUEST_PREFIX = "https://www.securoserve.nl/api";

    private static final String CURRENT_WEATHER = "/weather/current";
    private static final String HOURLY_FORECAST = "/weather/hourlyforecast";
    private static final String DAILY_FORECAST = "/weather/dailyforecast";


    RestClient restClient;

    public WeatherRequest() {
        restClient = new RestClient();
    }

    /**
     * Get the current weather at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @return ConfirmationMessage with the current weather at the library.Location
     */
    @Override
    public ConfirmationMessage getCurrentWeather(String token, double longitude, double latitude) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("token", token);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);

        return restClient.request(REQUEST_PREFIX + CURRENT_WEATHER, RestClient.RequestType.GET, parameters);
    }

    /**
     * Get the hourly forecast at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @return ConfirmationMessage with the hourly forecast at the library.Location.
     */
    @Override
    public ConfirmationMessage getHourlyForecast(String token, double longitude, double latitude) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();

        parameters.add("token", longitude);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);

        return restClient.request(REQUEST_PREFIX + HOURLY_FORECAST, RestClient.RequestType.GET, parameters);
    }

    /**
     * Get the daily forecast at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @param count     Number of days for the forecast
     * @return ConfirmationMessage with the daily forecast at the library.Location.
     */
    @Override
    public ConfirmationMessage getDailyForecast(String token, double longitude, double latitude, byte count) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", longitude);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);
        parameters.add("count", count);
        return restClient.request(REQUEST_PREFIX + DAILY_FORECAST, RestClient.RequestType.GET, parameters);
    }
}
