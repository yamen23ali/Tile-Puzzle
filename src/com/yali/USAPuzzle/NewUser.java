package com.yali.USAPuzzle;

import com.yali.USAPuzzle.R;

import myUtils.Controller;
import myUtils.MyAlertBox;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewUser extends Activity implements OnClickListener,DialogInterface.OnClickListener{

	private SQLiteDatabase db;
	private int level=1;
	private String name="";
	//=============================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.new_user);
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(6,0.4f);
		float size2=textResizer.calcSize(3,0.08f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(),"Fonts/HennyPenny.ttf");
		//=============== Text Size And Font ==========//
		
		//========== Styles ===========//
		TextView tv=(TextView) findViewById(R.id.newUserText);
		tv.setTypeface(face);
    	tv.setTextColor(Color.BLACK);
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	
		Button newUserDone=(Button) findViewById(R.id.newUserDone);
		newUserDone.setOnClickListener(this);
		newUserDone.setTypeface(face2,Typeface.BOLD);
		newUserDone.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
		newUserDone.setTextColor(Color.BLACK);
		
		EditText et=(EditText) findViewById(R.id.newUserName);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		ViewGroup.LayoutParams lp= et.getLayoutParams();
		lp.width=(int)(0.4*dm.widthPixels);
		
		//========== Styles ===========//
		
		//========= Resize The Activity On The Screen ====//
		MyWindowResizer.resizeWindow(this,0.5f,0.5f);
		//========= Resize The Activity On The Screen ====//
	}

	
	//=============== Create New User =============//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			EditText et = (EditText) findViewById(R.id.newUserName);
			name = et.getText().toString();
			boolean found = false;
	
			db = openOrCreateDatabase("PuzzleDataBase",
					MODE_PRIVATE, null);
			db.execSQL("CREATE TABLE IF NOT EXISTS UserStage"
					+ " (Stage INT(3),Level INT(3),User VARCHAR );");
	
			Cursor iterator = db.rawQuery("SELECT * FROM UserStage", null);
			int index = iterator.getColumnIndex("User");
			while (iterator.moveToNext()) {
				if (iterator.getString(index).equals( name)) {
					found = true;
					break;
				}
			}
			iterator.close();
			if (!found) 
			{
				db.execSQL("INSERT INTO UserStage VALUES(1,"+level+",'"+name+"');");
				db.close();
				
				Controller.setUserName(name);
				//===== Choose Level ======//
				Intent in=new Intent(this,ChooseLevel.class);
				startActivity(in);
				//===== Choose Level ======//
				
				this.finish();
			}
			else
			{
				AlertDialog alert = new AlertDialog.Builder(this).create();
				alert.setMessage("User Already Exist , Do You Want To Overwrite It ?!");
				alert.setButton(DialogInterface.BUTTON_POSITIVE,"Yes",this);
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",this);
				alert.show();
			}
		}
		catch(Exception e)
		{
			MyAlertBox mab=new MyAlertBox(this,"0: " + e.getMessage());
		}
		
	}
	//=============== Create New User =============//


	//========= Dialog click Listener =====//
	@Override
	public void onClick(DialogInterface arg0, int which) {
		// TODO Auto-generated method stub
		if(which==DialogInterface.BUTTON_POSITIVE)
		{
			level=2;
			try{
				db.execSQL("UPDATE UserStage SET Stage=1 , Level = " + level +
						" WHERE User='"+name+"';");
				db.execSQL("DELETE FROM UserRating WHERE User='"+name+"';");
				db.close();
				
				Controller.setUserName(name);
				//===== Choose Level ======//
				Intent in=new Intent(this,ChooseLevel.class);
				startActivity(in);
				//===== Choose Level ======//
				
				this.finish(); 
			}
			catch(Exception e)
			{
				MyAlertBox mab=new MyAlertBox(this,"00: " + e.getMessage());
			}
		}
		
	}
	//========= Dialog click Listener =====//

}
