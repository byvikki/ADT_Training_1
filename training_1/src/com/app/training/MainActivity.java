package com.app.training;

import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends MyBaseListActivity  {

	public static final int INSERT_ID = Menu.FIRST;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sDbHelper = new SqlDbAdapter(this);		
		sDbHelper.open();
		fillData();
	}
	
	
	// To refill the ListView
	@Override
	protected void onResume(){
		super.onResume();
		fillData();
	}

}
