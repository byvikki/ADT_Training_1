package com.app.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlDbAdapter {

	public static final String KEY_TITLE = "title";

	public static final String KEY_BODY = "body";
	public static final String KEY_ROWID = "_id";
	private static final String TAG = "SqlDbAdapter";
	private DatabaseHelper sDbHelper;
	private SQLiteDatabase sDb;
	private static final String DATABASE_NAME = "data";

	private static final String DATABASE_TABLE = "notes";
	private static final int DATABASE_VERSION = 2;

	private final Context ctx;

	// Database creation sql statement

	private static final String DATABASE_CREATE_QUERY = 
			"create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement, "+ KEY_TITLE
			+" text not null, "+KEY_BODY+" text not null);";


	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_QUERY);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version "+ oldVersion + "to"
					+newVersion+ ", which will destroy old data");
			
			db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
			
			onCreate(db);
			
		}
		
	}

	public SqlDbAdapter(Context context){
		this.ctx = context;
	}
	
	
	public SqlDbAdapter open() throws SQLException{
		sDbHelper = new DatabaseHelper(ctx);
		sDb = sDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		sDbHelper.close();
	}
	
	public void dropTable() throws SQLException{
		sDbHelper = new DatabaseHelper(ctx);
		sDb = sDbHelper.getWritableDatabase();
		sDb.execSQL("DELETE from "+ DATABASE_TABLE );
	}
	
	//To Create a note 
	public long createNote(String title, String body){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_TITLE, title);
		initialValues.put(KEY_BODY, body);
		return sDb.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// To delete a note having n rowId
	public boolean deleteNote(long rowId){
		return sDb.delete(DATABASE_TABLE, KEY_ROWID+" = "+ rowId, null) > 0;
	}
	
	public Cursor fetchAllNotes(){
		return sDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_TITLE, KEY_BODY}, 
				null, null, null, null, null);
	}

}
