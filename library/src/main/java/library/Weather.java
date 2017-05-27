package library;

import net.aksingh.owmjapis.CurrentWeather;

import java.io.Serializable;

/**
 * Created by guillaimejanssen on 27/05/2017.
 */
public class Weather implements Serializable {

    private String countryCode;

    private String cityName;
    private long cityCode;

    private LatLong latLong;
    private Temperature temperature;

    private float windSpeed;
    private float clouds;

    public Weather(CurrentWeather currentWeather) {
        this.cityName = currentWeather.getCityName();
        this.cityCode = currentWeather.getCityCode();
        this.countryCode = currentWeather.getSysInstance().getCountryCode();
        this.latLong = new LatLong(currentWeather.getCoordInstance().getLatitude(),
                currentWeather.getCoordInstance().getLongitude());
        this.temperature = new Temperature(currentWeather.getMainInstance().getTemperature(),
                currentWeather.getMainInstance().getMinTemperature(),
                currentWeather.getMainInstance().getMaxTemperature(),
                currentWeather.getMainInstance().getHumidity());
        this.windSpeed = currentWeather.getWindInstance().getWindSpeed();
        this.clouds = currentWeather.getCloudsInstance().getPercentageOfClouds();
    }

    public Weather() {

    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getCityCode() {
        return cityCode;
    }

    public void setCityCode(long cityCode) {
        this.cityCode = cityCode;
    }

    public LatLong getLatLong() {
        return latLong;
    }

    public void setLatLong(LatLong latLong) {
        this.latLong = latLong;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public class LatLong implements Serializable {

        private float latitude;
        private float longitude;

        public LatLong() { }

        public LatLong(float lat, float lon) {
            this.latitude = lat;
            this.longitude = lon;
        }

        public float getLatitude() {
            return latitude;
        }

        public void setLatitude(float latitude) {
            this.latitude = latitude;
        }

        public float getLongitude() {
            return longitude;
        }

        public void setLongitude(float longitude) {
            this.longitude = longitude;
        }
    }

    public class Temperature implements Serializable {

        private float temperature;
        private float minTemperature;
        private float maxTemperature;
        private float humidity;

        public Temperature() { }

        public Temperature(float temp, float min, float max, float hum) {
            this.temperature = temp;
            this.minTemperature = min;
            this.maxTemperature = max;
            this.humidity = hum;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getMinTemperature() {
            return minTemperature;
        }

        public void setMinTemperature(float minTemperature) {
            this.minTemperature = minTemperature;
        }

        public float getMaxTemperature() {
            return maxTemperature;
        }

        public void setMaxTemperature(float maxTemperature) {
            this.maxTemperature = maxTemperature;
        }

        public float getHumidity() {
            return humidity;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }
    }
}
