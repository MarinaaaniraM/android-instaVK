package marinaaaniram.android_instavk.model.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by My on 15.04.15.
 */
public class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "test_DB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {

            Log.d("VkWebViewClient", "new database");

            db.execSQL("CREATE TABLE albums (" +
                    "_id integer primary key autoincrement," +
                    "title text," +
                    "thumb_src text," +
                    "id integer," +
                    "owner_id integer," +
                    "UNIQUE(owner_id, id) ON CONFLICT REPLACE," +
                    "FOREIGN KEY(owner_id) REFERENCES users(id));"); // ALBUB ID IN VK

            db.execSQL("CREATE TABLE photos (" +
                    "_id integer primary key autoincrement," +
                    "photo_75 text unique," +
                    "photo_604 text unique," +
                    "owner_id integer," +
                    "album_id integer," +
                    "id integer," +
                    "FOREIGN KEY(owner_id) REFERENCES users(id)," +
                    "FOREIGN KEY(album_id) REFERENCES albums(id));");

            db.execSQL("CREATE TABLE users (" +
                    " _id integer primary key autoincrement, " +
                    " id integer unique, " + // id user in vk
                    " first_name text, " +
                    " last_name  text, " +
                    " photo_50 text" +
                    ");");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
}
