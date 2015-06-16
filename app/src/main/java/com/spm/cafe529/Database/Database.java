package com.spm.cafe529.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spm.cafe529.Struct.Menu;

import java.util.ArrayList;

/**
 * Created by 성호 on 2015-04-10.
 */
public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CAFE529";
    private static final int DATABASE_VERSION = 2;

    private final String TABLE_NAME = "menu";
    private final String COLUMN_MENU_NAME = "name";
    private final String COLUMN_MENU_IMAGEPATH = "path";
    private final String COLUMN_MENU_PRICE = "price";
    private final String COLUMN_MENU_TYPE = "type";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_MENU_NAME + " TEXT PRIMARY KEY, "
                + COLUMN_MENU_PRICE + " INTEGER NOT NULL, "
                + COLUMN_MENU_IMAGEPATH + " VARCHAR(100) NOT NULL, "
                + COLUMN_MENU_TYPE + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void putMenu (SQLiteDatabase db, String name, int price, String path, String type) {
        if (db == null || name == null || type == null)
            return;

        db.execSQL("INSERT OR REPLACE INTO " + TABLE_NAME + " VALUES ('" + name + "', " + price + ", '" + path + "', '"+ type +"');");
    }

    public ArrayList<Menu> getMenu (SQLiteDatabase db, String type) {
        if (type == null || db == null)
            return null;

        ArrayList<Menu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " +
                COLUMN_MENU_NAME + "," +
                COLUMN_MENU_PRICE + "," +
                COLUMN_MENU_IMAGEPATH +
                " FROM " + TABLE_NAME + " WHERE type='" + type + "';", null);
        //cursor.moveToNext();

        while (cursor.moveToNext()) {
            list.add(new Menu (cursor.getString(0), cursor.getInt(1),cursor.getString(2)));
        }

        return list;
    }

    public int getPrice (SQLiteDatabase db, String name) {
        Cursor cursor = db.rawQuery("SELECT " +
                COLUMN_MENU_PRICE +
                " FROM " + TABLE_NAME + " WHERE name='" + name + "';", null);
        cursor.moveToNext();

        if (cursor.getString(0) != null)
            return cursor.getInt(0);
        else
            return -1;
    }
}
