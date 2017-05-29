package controllers;

import datarepo.database.Database;
import library.User;
import library.Weather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.HourlyForecast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.TestUtil;

/**
 * Created by Jandie on 2017-05-22.
 */
public class WeatherControllerTest {
    private Database database;
    private WeatherController wc;
    private User user;

    @Before
    public void setUp() throws Exception {
        database = TestUtil.cleanAndBuildTestDatabase();
        wc = new WeatherController(database);
        user = TestUtil.createTempUser(database);
    }

    @Test
    public void getCurrentWeather() throws Exception {
        Weather weather = (Weather) wc.getCurrentWeather(user.getToken(),
                5.481383f, 51.440082f)
                .getReturnObject();

        Assert.assertEquals(5.481383f, weather.getLatLong().getLongitude(), 0.01);
        Assert.assertEquals(51.440082f, weather.getLatLong().getLatitude(), 0.01);
        Assert.assertEquals("Eindhoven", weather.getCityName());
    }

    @Test
    public void getHourlyForecast() throws Exception {
        HourlyForecast hf = (HourlyForecast) wc.getHourlyForecast(user.getToken(),
                5.481383f, 51.440082f)
                .getReturnObject();

        Assert.assertEquals("Eindhoven", hf.getCityInstance().getCityName());
    }

    @Test
    public void getDailyForecast() throws Exception {
        DailyForecast df = (DailyForecast) wc.getDailyForecast(user.getToken(),
                5.481383f, 51.440082f, (byte) 5).getReturnObject();

        Assert.assertEquals("Eindhoven", df.getCityInstance().getCityName());
    }

}