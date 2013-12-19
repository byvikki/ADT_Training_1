package com.app.training;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class MainActivity extends MyBaseListActivity  {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sDbHelper = new SqlDbAdapter(this);
		sDbHelper.open();
		
		// Using the fragment to load the ListView fragment		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		MyListFragment listFragment = new MyListFragment();
		
		listFragment.setsDbHelper(sDbHelper);
		
		fragmentTransaction.add(R.id.fragment_container, listFragment);
		
		fragmentTransaction.commit();
		
	}
	
	
	
	// To refill the ListView
	@Override
	protected void onResume(){
		super.onResume();		
	}
	
	

}
