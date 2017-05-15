package controllers;

import datarepo.CalamityRepo;
import datarepo.UserRepo;
import datarepo.database.Database;
import library.Calamity;
import library.Location;
import library.User;
import library.UserType;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

/**
 * this class adds entries to the database via the Repo classes.
 * Created by yannic on 08/05/2017.
 */
public class populateTestData {
    public static void main(String[] args) {
        CalamityRepo calamityRepo = new CalamityRepo(new Database());
        UserRepo userRepo = new UserRepo(new Database());

        Location testLocation = new Location(-1, 0, 0, 10);

        try{

            User testUser = userRepo.register(-1,-1, "testUser", "test123", "test@test.com", "Eindhoven");
            calamityRepo.addCalamity(new Calamity(-1, testLocation, testUser, true, false, new Date(1L), "Brand in het ziekenhuis", "hallo, er is hier vuur."));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}