package library;

/**
 * Created by Jandie on 2017-05-08.
 */
public class MediaFile extends Media {
    private String fileName;

    private FileType fileType;

    public MediaFile(int id, String name, String fileName, FileType fileType) {
        super(id, name);
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public enum FileType {
        PHOTO,
        VIDEO,
        SOUND
    }
}
