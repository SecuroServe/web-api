package datarepo;

import datarepo.database.Database;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaRepo {
    private Database database;

    public MediaRepo(Database database) {
        this.database = database;
    }
}
