package securoserve.api.datarepo.database;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses and runs a .sql file.
 * <p>
 * Created by Jandie on 2017-04-16.
 */
public class MySqlParser {
    private static final String DELIMITER = ";";

    private Database database;
    private String path;
    private List<String> queries;

    public MySqlParser(Database database, String path) {
        this.database = database;
        this.path = path;
    }

    static String readFile(String path, Charset encoding)
            throws Exception {

        InputStream is = new FileInputStream(path);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();

        byte[] encoded = buffer.toByteArray();
        return new String(encoded, encoding);
    }

    public void execute() throws Exception {
        queries = parseQueries(this.path);

        for (String query : queries) {
            database.executeQuery(query, new ArrayList<>(), Database.QueryType.NON_QUERY);
        }
    }

    private List<String> parseQueries(String path) throws Exception {
        String lines = readFile(path, StandardCharsets.UTF_8);
        lines = lines.replaceAll("[\r\n]", "");

        return Arrays.asList(lines.split(DELIMITER));
    }
}
