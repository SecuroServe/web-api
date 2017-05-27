package interfaces;

/**
 * Created by Michel on 1-5-2017.
 */
public interface IWeather {

    /**
     * Get the current weather at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @return ConfirmationMessage with the current weather at the library.Location
     */
    ConfirmationMessage getCurrentWeather(String token, double longitude, double latitude);

    /**
     * Get the hourly forecast at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @return ConfirmationMessage with the hourly forecast at the library.Location.
     */
    ConfirmationMessage getHourlyForecast(String token, double longitude, double latitude);

    /**
     * Get the daily forecast at the given location by Longitude and Latitude
     *
     * @param token     Token to validate the user
     * @param longitude Longitude of the library.Location to get the weather from
     * @param latitude  Latitude of the library.Location to get the weather from
     * @param count     Number of days for the forecast
     * @return ConfirmationMessage with the daily forecast at the library.Location.
     */
    ConfirmationMessage getDailyForecast(String token, double longitude, double latitude, byte count);
}
