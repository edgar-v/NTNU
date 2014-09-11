package com.g19.find_my_sheep;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseSuperpower {

  // Database fields
  private SQLiteDatabase database;
  private Database dbHelper;

  public DatabaseSuperpower(Context context) {
    dbHelper = new Database(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }
  
  public void sheepUpdate(ContentValues values, int id){
    database.update(Database.TABLE_SHEEP,values,"id="+id,null);
  }
  
  public void sheepAdd(List<NameValuePair> values){
    ContentValues contentValues = new ContentValues();
    for (NameValuePair value : values) {
      contentValues.put(value.getName(), value.getValue());
    }
    contentValues.put("alive", true);
    contentValues.put("alarm", false);
    contentValues.put("longitude", 10.3933);
    contentValues.put("latitude", 63.4297);
    database.insert(Database.TABLE_SHEEP, null, contentValues);
  }
  
  public void startUp(ContentValues values){
    if(getSheepIdFromDatabase().contains(values.get("id"))){
      sheepUpdate(values, values.getAsInteger("id"));
    } else{
      database.insert(Database.TABLE_SHEEP, null, values);
    }
  }
  
  public void delete(int id){
    database.delete(Database.TABLE_SHEEP, "id="+id, null);
  }
  
  public ContentValues getSheepInfoFromDatabase(int id){
    ContentValues values = new ContentValues();
    String[] cols = {"name","weight","age","alive","health"};
    Cursor cursor = database.query(Database.TABLE_SHEEP, cols, "id="+id, null, null, null, null);
    cursor.moveToFirst();
    for(int i = 0; i < cursor.getColumnCount(); i++){
      if(cursor.getString(i) != null)
        values.put(cursor.getColumnName(i), cursor.getString(i));
      else{
        values.put(cursor.getColumnName(i), "");
        values.putNull(cursor.getColumnName(i));
      }
    }
    cursor.close();
    return values;
  }
  
  public ArrayList<Integer> getSheepIdFromDatabase(){
    ArrayList<Integer> idlist = new ArrayList<Integer>();
    String[] names = {"id"};
    Cursor cursor = database.query(Database.TABLE_SHEEP, names, null, null, null, null, null);
    cursor.moveToFirst();
    for(int i = 0; i < cursor.getCount(); i++){
      idlist.add(cursor.getInt(0));
      cursor.moveToNext();
    }
    cursor.close();
    return idlist;
  }
  
  public ArrayList<String> getSheepNamesFromDatabase(){
    ArrayList<String> namelist = new ArrayList<String>();
    String[] names = {"name"};
    Cursor cursor = database.query(Database.TABLE_SHEEP, names, null, null, null, null, null);
    cursor.moveToFirst();
    for(int i = 0; i < cursor.getCount(); i++){
      namelist.add(cursor.getString(0));
      cursor.moveToNext();
    }
    cursor.close();
    return namelist;
  }

}  