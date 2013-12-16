package com.app.training;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;


@SuppressLint("NewApi")
public class MainActivity extends ListActivity  {

	public static final int INSERT_ID = Menu.FIRST;	
	private SqlDbAdapter sDbHelper;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sDbHelper = new SqlDbAdapter(this);
		sDbHelper.open();
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;
		case R.id.empty_database:
			sDbHelper.dropTable();
			fillData();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	private void createNote(){
		SharedPreferences sharedPref = getSharedPrefs();
		SharedPreferences.Editor editor = sharedPref.edit();
		
		int sNoteNumber = sharedPref.getInt(getString(R.string.saved_note_no), 1);
		
		String noteName = "Note " + sNoteNumber++;
		sDbHelper.createNote(noteName, "hardcoded body");
		
		editor.putInt(getString(R.string.saved_note_no), sNoteNumber);
		editor.commit();
		fillData();
	}
	
	public SharedPreferences getSharedPrefs(){
		return getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	private void fillData(){
		Cursor c = sDbHelper.fetchAllNotes();
		
		String[] from = new String[] { SqlDbAdapter.KEY_TITLE };
		int[] to = new int[] { R.id.text1 };
		
		SimpleCursorAdapter sCursor = new SimpleCursorAdapter(this, R.layout.list_row, c, from, to, 0);
		
		setListAdapter(sCursor);

	}

}
