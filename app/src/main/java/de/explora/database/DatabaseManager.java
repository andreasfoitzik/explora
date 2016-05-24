package de.explora.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.StringBuilderPrinter;

/**
 * Created by Marash on 24.05.2016.
 */
public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DB_NAME = "explora.db";
    public static int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COMMA_SEP = ",";
    private static final String VARCHAR = " varchar";

    public static final String SQL_CREATE_ENTRIES =
            CREATE_TABLE + UserEntry.TABLE_NAME + " (" + UserEntry._ID + " PRIMARY KEY " + COMMA_SEP + UserEntry.COLUMN_NAME_MAIL + VARCHAR + COMMA_SEP + UserEntry.COLUMN_NAME_PASSWORD + VARCHAR + ");";


    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ENTRIES);
        insertUsers(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void insertUsers(SQLiteDatabase db){
        ContentValues values = new ContentValues();

        values.put(UserEntry.COLUMN_NAME_MAIL, "admin@admin.de");
        values.put(UserEntry.COLUMN_NAME_PASSWORD, "padmin");
        long newRowId = db.insert(UserEntry.TABLE_NAME,null,values);
    }

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "User";
        public static final String COLUMN_NAME_MAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
