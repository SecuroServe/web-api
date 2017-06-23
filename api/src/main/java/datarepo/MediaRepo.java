package datarepo;

import datarepo.database.Database;
import datarepo.storage.FileSystemStorageService;
import library.Media;
import library.MediaFile;
import library.Text;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaRepo {
    private final FileSystemStorageService storageService;
    private Database database;

    public MediaRepo(Database database) {
        this.database = database;
        this.storageService = new FileSystemStorageService();
    }

    /**
     * Gets a Media object from the database based on its id.
     *
     * @param mediaId The id of the Media object.
     * @return The Media object from the database.
     * @throws SQLException Database error.
     */
    public Media getMedia(int mediaId) throws SQLException {
        Media media = null;
        String mediaName = null;

        String query = "SELECT `Name` FROM Media WHERE ID = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                mediaName = rs.getString(1);
            }
        }

        if (isText(mediaId)) {
            media = getText(mediaId, mediaName);
        } else if (isMediaFile(mediaId)) {
            media = getMediaFile(mediaId, mediaName);
        }

        return media;
    }

    /**
     * Gets a Media object from the database that belongs to a specific alert object.
     *
     * @param alertId The id of the Alert object.
     * @return The Media object from the database.
     * @throws SQLException Database error.
     */
    public Media getMediaByAlert(int alertId) throws SQLException {
        Media media = null;
        int mediaId = -1;
        String mediaName = null;

        String query = "SELECT `ID`, `Name` FROM Media WHERE AlertID = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(alertId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                mediaId = rs.getInt(1);
                mediaName = rs.getString(2);
            }
        }

        if (isText(mediaId)) {
            media = getText(mediaId, mediaName);
        } else if (isMediaFile(mediaId)) {
            media = getMediaFile(mediaId, mediaName);
        }

        return media;
    }

    /**
     * Gets a Text object from the database.
     *
     * @param mediaId   The id of the media record.
     * @param mediaName The name of the media.
     * @return Text object from the database.
     * @throws SQLException Database error.
     */
    private Text getText(int mediaId, String mediaName) throws SQLException {
        Text text = null;

        String query = "SELECT `Text` FROM Text WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                text = new Text(mediaId, mediaName, rs.getString(1));
            }
        }

        return text;
    }

    /**
     * Gets a MediaFile object from the database.
     *
     * @param mediaId   The id of the media record.
     * @param mediaName The name of the media.
     * @return MediaFile object from the database.
     * @throws SQLException Database error.
     */
    private MediaFile getMediaFile(int mediaId, String mediaName) throws SQLException {
        MediaFile mediaFile = null;

        String query = "SELECT `FileName`, `FileType` FROM `File` WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                String fileName = rs.getString(1);
                MediaFile.FileType fileType = MediaFile.FileType.valueOf(rs.getString(2));

                mediaFile = new MediaFile(mediaId, mediaName, fileName, fileType);
            }
        }

        return mediaFile;
    }

    /**
     * Checks whether or not the Media is a Text.
     *
     * @param mediaId The id of the Media object to check.
     * @return Whether or not the Media is a Text.
     * @throws SQLException Database error.
     */
    private boolean isText(int mediaId) throws SQLException {
        String query = "SELECT ID FROM Text WHERE MediaID = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether or not the Media is a MediaFile.
     *
     * @param mediaId The id of the Media object to check.
     * @return Whether or not the Media is a MediaFile.
     * @throws SQLException Database error.
     */
    private boolean isMediaFile(int mediaId) throws SQLException {
        String query = "SELECT ID FROM File WHERE MediaID = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.QUERY)) {
            if (rs.next()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new text to the database.
     *
     * @param text    The text object to persist.
     * @param alertId The id of the alert the text object belongs to.
     * @return The newly persisted text object.
     * @throws SQLException Database error.
     */
    public Text addText(Text text, int alertId) throws SQLException {
        int mediaId = addMedia(text.getName(), alertId);

        String query = "INSERT INTO `Text` (`MediaID`, `Text`) " +
                "VALUES (?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);
        parameters.add(text.getText());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        text.setId(mediaId);

        return text;
    }

    /**
     * Adds a new mediaFile object to the database.
     *
     * @param mediaFile The MediaFile object to persist.
     * @param alertId   The id of the alert the MediaFile object belongs to.
     * @return The newly persisted MediaFile object.
     * @throws SQLException Database error.
     */
    public MediaFile addMediaFile(MediaFile mediaFile, int alertId) throws SQLException {
        int mediaId = addMedia(mediaFile.getName(), alertId);

        String query = "INSERT INTO `File` (`MediaID`, `FileName`, `FileType`) " +
                "VALUES (?, ?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);
        parameters.add(mediaFile.getFileName());
        parameters.add(mediaFile.getFileType().toString());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);

        mediaFile.setId(mediaId);

        return mediaFile;
    }

    /**
     * Adds a new Media object to the database.
     *
     * @param name    Name of the media.
     * @param alertId The id of the alert the Media object belongs to.
     * @return The id of the newly persisted Media object.
     * @throws SQLException Database error.
     */
    public int addMedia(String name, int alertId) throws SQLException {
        String query = "INSERT INTO `Media` (`AlertID`, `Name`) VALUES (?, ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(alertId);
        parameters.add(name);

        try (ResultSet rs = database.executeQuery(query, parameters, Database.QueryType.INSERT)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("Add media failed!");
    }

    /**
     * Deletes Media object from database by id.
     *
     * @param mediaId The id of the Media object to remove.
     * @throws SQLException Database error.
     */
    public void removeMedia(int mediaId) throws SQLException {
        if (isText(mediaId)) {
            removeText(mediaId);
        }

        if (isMediaFile(mediaId)) {
            removeMediaFile(mediaId);
        }

        String query = "DELETE FROM `Media` WHERE `ID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Deletes Text object from database by id.
     *
     * @param mediaId The id of the Text object to remove.
     * @throws SQLException Database error.
     */
    private void removeText(int mediaId) throws SQLException {
        String query = "DELETE FROM `Text` WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Deletes MediaFile object from database by id.
     *
     * @param mediaId The id of the MediaFile object to remove.
     * @throws SQLException Database error.
     */
    private void removeMediaFile(int mediaId) throws SQLException {
        String query = "DELETE FROM `File` WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaId);

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Updates a Media object in the database.
     *
     * @param media The media object.
     */
    public void updateMedia(Media media) throws SQLException {
        if (isText(media.getId())) {
            updateText((Text) media);
        } else if (isMediaFile(media.getId())) {
            updateMediaFile((MediaFile) media);
        }
    }

    /**
     * Updates a Text object in the database.
     *
     * @param text The text object.
     */
    private void updateText(Text text) throws SQLException {
        String query = "UPDATE `Text` SET `Text` = ? WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(text.getText());
        parameters.add(text.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Updates a MediaFile object in the database.
     *
     * @param mediaFile The mediaFile object.
     */
    private void updateMediaFile(MediaFile mediaFile) throws SQLException {
        String query = "UPDATE `File` SET `FileName` = ?, `FileType` = ? WHERE `MediaID` = ?";

        List<Object> parameters = new ArrayList<>();
        parameters.add(mediaFile.getFileName());
        parameters.add(mediaFile.getFileType().toString());
        parameters.add(mediaFile.getId());

        database.executeQuery(query, parameters, Database.QueryType.NON_QUERY);
    }

    /**
     * Saves a file to the filesystem.
     *
     * @param file           The file to save as a MultipartFile.
     * @param customFileName The custom file name.
     */
    public void saveFile(MultipartFile file, String customFileName) {
        storageService.init();
        storageService.store(file, customFileName);
    }

    /**
     * Loads a file as a resource.
     *
     * @param customFileName The name of the file.
     * @return The file as a Rescource.
     */
    public Resource loadFile(String customFileName) {
        storageService.init();

        return storageService.loadAsResource(customFileName);
    }
}
