package dataRepo;

import library.Calamity;
import library.Location;

/**
 * This class defines the database operations for calamities.
 * Created by yannic on 20/03/2017.
 */
public class CalamityRepo {

    //todo create querries and implement this.
    private Database database;

    public CalamityRepo(Database database) {
        this.database = database;
    }


    public void updateCalamity(int id, String name, String description, Location location) {

    }

    public Calamity getCalamity(int id) {

        return null;
    }
}
