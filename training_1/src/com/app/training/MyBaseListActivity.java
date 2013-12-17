package com.app.training;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;

public class MyBaseListActivity extends ListActivity {

	public SqlDbAdapter sDbHelper;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//menu.add(0, INSERT_ID, 0, R.string.menu_insert);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.create_note:
			createNoteWithForm("new");
			return true;
		case R.id.empty_database:
			sDbHelper.dropTable();
			fillData();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public void createNoteWithForm(String formAction){
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra(EditActivity._EDIT_FORM_ACTION, formAction);
		startActivity(intent);
	}
	
/*	public void createNote(){
		SharedPreferences sharedPref = getSharedPrefs();
		SharedPreferences.Editor editor = sharedPref.edit();
		
		int sNoteNumber = sharedPref.getInt(getString(R.string.saved_note_no), 1);
		
		String noteName = "Note " + sNoteNumber++;
		sDbHelper.createNote(noteName, "hardcoded body");
		
		editor.putInt(getString(R.string.saved_note_no), sNoteNumber);
		editor.commit();
		fillData();
	}*/
	
	public SharedPreferences getSharedPrefs(){
		return getSharedPreferences(MyBaseListActivity.class.getSimpleName(), Context.MODE_PRIVATE);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fillData(){
		
		new AsyncTask(){

			@Override
			protected Cursor doInBackground(Object... params) {
				// Will start a progress bar on here				
				return sDbHelper.fetchAllNotes();
			}
			
			@Override
			protected void onPostExecute(Object cursor){
				
				// will stop the progress bar started at doInBackground
				fillListWithData((Cursor)cursor);
			}
			
		}.execute(null, null, null);
		

	}
	
	public void fillListWithData(Cursor cursor){
		String[] from = new String[] { SqlDbAdapter.KEY_TITLE };
		int[] to = new int[] { R.id.text1 };
		SimpleCursorAdapter sCursor = new SimpleCursorAdapter(this, R.layout.list_row, cursor, from, to, 0);
		setListAdapter(sCursor);
	}
}
