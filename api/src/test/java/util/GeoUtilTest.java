package util;

import library.Location;
import org.junit.Test;
import utils.GeoUtil;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marc on 2-5-2017.
 */
public class GeoUtilTest {

    @Test
    public void measureGeoDistance() throws Exception {

        Location l1 = new Location(-1, 51.422332, 5.450672, 0);
        Location l2 = new Location(-1, 51.422989, 5.449774, 0);

        double meters = GeoUtil.measureGeoDistance(l1, l2);
        assertEquals(96.10, meters, 0.01);

    }

}