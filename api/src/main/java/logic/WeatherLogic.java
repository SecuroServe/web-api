package logic;

import datarepo.UserRepo;
import datarepo.database.Database;
import interfaces.ConfirmationMessage;
import library.UserType;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * Created by Michel on 1-5-2017.
 */
public class WeatherLogic {

    private Database database;
    private UserRepo userRepo;

    private OpenWeatherMap omw;

    public WeatherLogic() {
        database = new Database();
        userRepo = new UserRepo(database);
        this.omw = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "98d0f150f25d72ef30ec69301ef50f89");
    }

    public WeatherLogic(Database database) {
        database = database;
        userRepo = new UserRepo(database);
        this.omw = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, "98d0f150f25d72ef30ec69301ef50f89");
    }

    /**
     * Get current weather from the OpenWeatherMap API
     * @param token Token to validate the user
     * @param longitude
     * @param latitude
     * @return ConfirmationMessage with the raw data of the API
     */
    public ConfirmationMessage getCurrentWeather(String token, float longitude, float latitude) {

        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_GET);
            CurrentWeather cw = omw.currentWeatherByCoordinates(latitude, longitude);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Current Weather", cw);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to obtain current weather", e);
        }
    }

    /**
     * Get hourly forecast from the OpenWeatherMap API
     * @param token Token to validate the user
     * @param longitude
     * @param latitude
     * @return ConfirmationMessage with the raw data of the API
     */
    public ConfirmationMessage getHourlyForecast(String token, float longitude, float latitude) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_GET);
            HourlyForecast hf = omw.hourlyForecastByCoordinates(latitude, longitude);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Hourly Forecast", hf);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to obtain hourly forecast", e);
        }
    }

    /**
     * Get daily forecast from the OpenWeatherMap API
     * @param token Token to validate the user
     * @param longitude
     * @param latitude
     * @param count Amount of days to get the forecast of.
     * @return ConfirmationMessage with the raw data of the API
     */
    public ConfirmationMessage getDailyForecast(String token, float longitude, float latitude, byte count) {
        try {
            userRepo.getUser(token).getUserType().containsPermission(UserType.Permission.CALAMITY_GET);
            DailyForecast df = omw.dailyForecastByCoordinates(latitude, longitude, count);
            return new ConfirmationMessage(ConfirmationMessage.StatusType.SUCCES, "Daily Forecast", df);
        } catch (Exception e) {
            return new ConfirmationMessage(ConfirmationMessage.StatusType.ERROR, "Failed to obtain daily forecast", e);
        }
    }

}
