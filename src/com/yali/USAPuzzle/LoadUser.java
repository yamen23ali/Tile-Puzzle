package com.yali.USAPuzzle;

import java.util.ArrayList;
import java.util.List;

import com.yali.USAPuzzle.R;

import myUtils.Controller;
import myUtils.MyAlertBox;
import myUtils.MyProgressBarMenu;
import myUtils.MySpinnerAdapter;
import myUtils.MyTextResizer;
import myUtils.MyWindowResizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class LoadUser extends Activity implements OnClickListener{

	// =================================//
	private Spinner sp;
	private Cursor iterator;
	private SQLiteDatabase db;

	// =================================//

	// =================================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.load_user);
		// =========================================//
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
    	float size=textResizer.calcSize(6,0.4f);
    	float size2=textResizer.calcSize(3,0.08f);
    	Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
    	Typeface face2 = Typeface.createFromAsset(getAssets(),"Fonts/HennyPenny.ttf");
    	//=============== Text Size And Font ==========//
    	
    	//============== Customize The Fonts On Elements =======//
    	TextView tv=(TextView) findViewById(R.id.loadUserText);
    	tv.setTypeface(face);
    	tv.setTextColor(Color.BLACK);
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
		
		Button b = (Button) findViewById(R.id.loadUserButton);
		b.setTypeface(face2,Typeface.BOLD);
		b.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
		b.setTextColor(Color.BLACK);
		b.setOnClickListener(this);
		b.setText("Load");

    	
		sp = (Spinner) findViewById(R.id.loadUserSpinner);
		List<String> items = new ArrayList<String>();
		//============== Customize The Fonts On Elements =======//
		
		// ================== Fill The Spinner ===================//

		// =========== Get List Of Users======//
		try {
			db = openOrCreateDatabase("PuzzleDataBase", MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS UserStage"
					+ " (Stage INT(3),Level INT(3),User VARCHAR );");

			iterator = db.rawQuery("SELECT * FROM UserStage", null);
		

			int index = iterator.getColumnIndex("User");
			while (iterator.moveToNext()) {
				String s = iterator.getString(index);
				items.add(s);
			}
			// =========== Get List Of Users======//
	
			iterator.close();
		}
		catch (Exception e) {
				MyAlertBox mab=new MyAlertBox(this,"1: " + e.getMessage());
		}
		
		// ========== Fill IT ======//
		
		//======== Change Spinner Style And Size =======//
		String[] itemsString=items.toArray(new String[items.size()]);
		MySpinnerAdapter msa= new MySpinnerAdapter(getApplicationContext(),R.layout.spinner_item,itemsString,this);
		sp.setAdapter(msa);
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ViewGroup.LayoutParams lp= sp.getLayoutParams();
		lp.width=(int)(0.4*dm.widthPixels);
		//======== Change Spinner Style And Size =======//
		
		
		if (items.size() == 0) {
			b.setEnabled(false);
		}
		// ========== Fill IT ======//

		// ================== Fill The Spinner ===================//
		
		//========= Resize The Activity On The Screen ====// 
		MyWindowResizer.resizeWindow(this,0.5f,0.5f);
		//========= Resize The Activity On The Screen ====//
	}

	// =================================//

	// ================Load The Selected User=================//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			//===== Get Selected user values======//
			String userName = (String) sp.getSelectedItem();
			iterator = db.rawQuery("SELECT * FROM UserStage WHERE User='"
					+ userName + "';", null);
			iterator.moveToFirst();
			int stage = iterator.getInt(iterator.getColumnIndex("Stage"));
			int level = iterator.getInt(iterator.getColumnIndex("Level"));
			iterator.close();
			db.close();
			//===== Get Selected user values======//
			
			//===== Start The Game ======//
			Controller.stage=stage;
			Controller.setUserName(userName);
			new MyProgressBarMenu(getApplicationContext(),this, level).execute();
			//===== Start The Game ======//
		} catch (Exception e) {
			MyAlertBox mab=new MyAlertBox(this,"2: " + e.getMessage());
		}
	}

	// ================Load The Selected User=================//
	
	//============= On Back Click =============//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	db.close();
	    	this.finish();
	    }	    
	    return true;
	}
	//============= On Back Click =============//

}
