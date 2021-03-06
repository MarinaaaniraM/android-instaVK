package marinaaaniram.android_instavk.model.utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by kic on 4/25/15.
 */
public class JsonParser {
    public static final String log_json = "JsonParserLogs";
    private static String reader_to_string(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject getJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = null;
        JSONObject json = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonString = reader_to_string(rd);
            json = new JSONObject(jsonString);
        } catch (IOException e) {
            Log.e(log_json, "Could not connect to server");
        }
        finally {
            if (is!=null)
                is.close();
        }
        return json;
    }
}
