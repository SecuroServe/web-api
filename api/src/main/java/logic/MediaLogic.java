package logic;

import datarepo.MediaRepo;
import datarepo.UserRepo;
import datarepo.database.Database;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaLogic {
    private Database database;
    private UserRepo userRepo;
    private MediaRepo mediaRepo;

    public MediaLogic() {
        this.database = new Database();
        userRepo = new UserRepo(database);
        mediaRepo = new MediaRepo(database);
    }

    public MediaLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
        mediaRepo = new MediaRepo(database);
    }
}
