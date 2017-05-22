package controllers;

import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import interfaces.IWeather;
import logic.WeatherLogic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Michel on 1-5-2017.
 */
@RestController
public class WeatherController implements IWeather {

    private WeatherLogic weatherLogic;

    public WeatherController() {
        this.weatherLogic = new WeatherLogic();
    }

    public WeatherController(Database database) {
        this.weatherLogic = new WeatherLogic(database);
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
    @RequestMapping("/weather/current")
    public ConfirmationMessage getCurrentWeather(@RequestParam(value = "token") String token,
                                                 @RequestParam(value = "longitude") float longitude,
                                                 @RequestParam(value = "latitude") float latitude) {
        return this.weatherLogic.getCurrentWeather(token, longitude, latitude);
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
    @RequestMapping("/weather/hourlyforecast")
    public ConfirmationMessage getHourlyForecast(@RequestParam(value = "token") String token,
                                                 @RequestParam(value = "longitude") float longitude,
                                                 @RequestParam(value = "latitude") float latitude) {
        return this.weatherLogic.getHourlyForecast(token, longitude, latitude);
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
    @RequestMapping("/weather/dailyforecast")
    public ConfirmationMessage getDailyForecast(@RequestParam(value = "token") String token,
                                                @RequestParam(value = "longitude") float longitude,
                                                @RequestParam(value = "latitude") float latitude,
                                                @RequestParam(value = "count") byte count) {
        return this.weatherLogic.getDailyForecast(token, longitude, latitude, count);
    }
}
