package securoserve.api.logic;

import securoserve.api.datarepo.UserRepo;
import securoserve.api.datarepo.database.Database;

/**
 * Created by Jandie on 2017-05-01.
 */
public class AlertLogic {
    private Database database;
    private UserRepo userRepo;

    public AlertLogic() {
        this.database = new Database();
        userRepo = new UserRepo(database);
    }

    public AlertLogic(Database database) {
        this.database = database;
        userRepo = new UserRepo(database);
    }
}
