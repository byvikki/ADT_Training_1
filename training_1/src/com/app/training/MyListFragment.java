package com.app.training;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyListFragment extends ListFragment {
	
	private SqlDbAdapter sDbHelper;
	
	public SqlDbAdapter getsDbHelper() {
		return sDbHelper;
	}

	public void setsDbHelper(SqlDbAdapter sDbHelper) {
		this.sDbHelper = sDbHelper;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.list_view, container, false);
		
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		fillData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		fillData();
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
		SimpleCursorAdapter sCursor = new SimpleCursorAdapter(getActivity(), R.layout.list_row, cursor, from, to, 0);
		setListAdapter(sCursor);
	}

}
