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

    private static final String REQUEST_PREFIX = "http://localhost:8080";

    private static final String CURRENT_WEATHER = "/weather/current";
    private static final String HOURLY_FORECAST = "/weather/hourlyforecast";
    private static final String DAILY_FORECAST = "/weather/dailyforecast";


    RestClient restClient;

    public WeatherRequest(){
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
    public ConfirmationMessage getCurrentWeather(String token, float longitude, float latitude) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", longitude);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);
        return restClient.post(REQUEST_PREFIX + CURRENT_WEATHER, parameters);
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
    public ConfirmationMessage getHourlyForecast(String token, float longitude, float latitude) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", longitude);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);
        return restClient.post(REQUEST_PREFIX + HOURLY_FORECAST, parameters);
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
    public ConfirmationMessage getDailyForecast(String token, float longitude, float latitude, byte count) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("token", longitude);
        parameters.add("longitude", longitude);
        parameters.add("latitude", latitude);
        parameters.add("count", count);
        return restClient.post(REQUEST_PREFIX + DAILY_FORECAST, parameters);
    }
}
