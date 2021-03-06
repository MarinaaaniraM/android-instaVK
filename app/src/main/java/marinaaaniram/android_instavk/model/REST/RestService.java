package marinaaaniram.android_instavk.model.REST;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import marinaaaniram.android_instavk.R;
import marinaaaniram.android_instavk.model.provider.MyContentProvider;
import marinaaaniram.android_instavk.model.utils.JsonParser;


public class RestService extends IntentService {
    public RestService() {
        super("restService");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getString(R.string.log_tag), "RestService starting...");
        String urlString = intent.getStringExtra("url");
        JSONObject response_json = null;

        try{
            response_json = JsonParser.getJsonFromUrl(urlString);

        } catch (IOException | JSONException e) {
            Log.e(JsonParser.log_json, "Can not load json");
        }

        if (response_json != null)
            try {
                JSONArray arr = null;

                String[] interestedObjects = intent.getStringArrayExtra("interestedObjectFromJSONResponse");
                if (intent.getStringExtra("no_items") == null) {
                    JSONObject response = response_json.getJSONObject("response");
                    arr = response.getJSONArray("items");
                }
                else {
                    arr = response_json.getJSONArray("response");
                }

                String table_name = intent.getStringExtra("table_name");

                for(int i = 0; i<arr.length();i++){

                    ContentValues cv = new ContentValues();
                    JSONObject current = arr.getJSONObject(i);

                    for(String s: interestedObjects){
                        cv.put(s, current.getString(s));
                    }
                    String uri_query = MyContentProvider.concat_strings("content://",
                            MyContentProvider.AUTHORITY, "/", table_name);
                    getContentResolver().insert(Uri.parse(uri_query), cv);
                }

            } catch (JSONException e) {
                Log.e(JsonParser.log_json, "Can not parse json");
            }
    }
}

