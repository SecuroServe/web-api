package constants;

/**
 * Created by Marc on 8-5-2017.
 */
public final class ApiConstants {

    // Automatically generates a calamity from alerts if these settings apply.
    public static final int ALERT_GROUP_RADIUS = 100; // Radius to search for alerts, in meters.
    public static final int ALERT_GROUP_AMOUNT = 10; // Amount of alerts that are found within the radius

    private ApiConstants() {
    }
}
