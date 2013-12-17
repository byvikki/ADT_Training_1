package com.app.training;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends MyBaseActivity {

	
	public static final String _EDIT_FORM_ACTION = "edit.form.action";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		// Show the Up button in the action bar.
		sDbHelper = new SqlDbAdapter(this);		
		sDbHelper.open();
		setupActionBar();
	}
	
	
	public void saveNote(View view){
		EditText title = (EditText) findViewById(R.id.title);
		EditText body = (EditText) findViewById(R.id.body);
		
		sDbHelper.createNote(title.getText().toString(), body.getText().toString());
		
		finish();
		
	}

}
