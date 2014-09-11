package com.g19.find_my_sheep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {
  public static final String TABLE_SHEEP = "sheep";

  private static final String DATABASE_NAME = "sheepDatabase";
  private static final int DATABASE_VERSION = 2;

  private static final String DATABASE_CREATE = "CREATE TABLE sheep " +
      "(id integer primary key autoincrement, longitude real not null, " +
      "latitude real not null, name text not null, weight integer not null, " +
      "age integer not null, alive boolean default true, alarm boolean default false, " +
      "health text);";

  public Database(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(DATABASE_CREATE);
  }


  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(Database.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHEEP);
    onCreate(db);
  }

} 