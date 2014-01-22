package com.app.training;


import java.io.File;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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


	public void onClick(View view){
		//Intent intent = new Intent("com.android.camera.action.CROP");
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);


		//Uri photoUri = Uri.parse("R.drawable.levis");
		File f = new File(Environment.getExternalStorageDirectory(),"tempPhoto.png");

		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);	
		intent.putExtra("outputY", 80);
		//intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("noFaceDetection",false); // lol, negative boolean noFaceDetection

		intent.putExtra("circleCrop", "true");


		startActivityForResult(intent, 0);

	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ViewGroup view = (ViewGroup)getWindow().getDecorView();
		ImageView photo = (ImageView) view.findViewById(R.id.show_pic);
		Uri photoUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"tempPhoto.png"));
		photo.setImageURI(photoUri);

	}


}
