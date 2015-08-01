package com.yali.USAPuzzle;

import java.io.IOException;
import java.util.ResourceBundle.Control;

import com.yali.USAPuzzle.R;

import myUtils.Controller;
import myUtils.MyAlertBox;
import myUtils.MyProgressBarMenu;
import myUtils.MyTextResizer;
import myUtils.MyWindowResizer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class Rating extends Activity implements OnClickListener{
	private SQLiteDatabase db;
	public static PuzzleBoard parent;
	Integer level;
	float rating;
	//=============================================//
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.rating);
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(6,0.4f);
		float size2=textResizer.calcSize(2,0.08f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(),"Fonts/HennyPenny.ttf");
		//=============== Text Size And Font ==========//
		
		//=========== Set The Rating Bar Value =======//
		Integer number =getIntent().getExtras().getInt("number");
		level =getIntent().getExtras().getInt("level");
		
		RatingBar rb=(RatingBar) findViewById(R.id.resultRatingBar);
		rating= (float)(number*5) /(float)100;
	    rb.setRating(rating); 
	    //=========== Set The Rating Bar Value =======//
	    
	    //======== Change The Stage Info For User ======//
	    changeStage();
	    //======== Change The Stage Info For User ======//
	    
	    //======== Add This Stage Rating For DB ========//
	    addRating(Controller.stage);
	    //======== Add This Stage Rating For DB ========//
	    
	    //========== Styles ===========//
	    TextView tv=(TextView) findViewById(R.id.textViewRating);
	    tv.setText("Your Score Is : " + number.toString()+ "%");
	    tv.setTypeface(face);
      	tv.setTextColor(Color.BLACK);
      	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
	    
	    Button b=(Button) findViewById(R.id.buttonRating);
	    b.setOnClickListener(this);
	    b.setTypeface(face2,Typeface.BOLD);
  		b.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
  		b.setTextColor(Color.BLACK);
  		
  		Button setWallPaper=(Button) findViewById(R.id.setWallPaper);
  		setWallPaper.setOnClickListener(this);
  		setWallPaper.setTypeface(face2,Typeface.BOLD);
  		setWallPaper.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
  		setWallPaper.setTextColor(Color.BLACK);
  		//========== Styles ===========//
  		
		//========= Resize The Activity On The Screen ====//
		MyWindowResizer.resizeWindow(this,0.6f,0.7f);
		//========= Resize The Activity On The Screen ====//

	}
	//=============================================//
	
	//======== Go Back To Choose Puzzle Menu =======//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//=========== Set WallPaper =========//
		if (v.getId()==R.id.setWallPaper)
		{
			boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
			WallpaperManager wm=WallpaperManager.getInstance(this);
			try {
				//======= Get Image ==========//
				Bitmap temp = BitmapFactory.decodeResource(getApplicationContext().getResources(),Controller.wallPaperImageId);
				 //====== Set WallPaper =======//
				DisplayMetrics displayMetrics = new DisplayMetrics();
			    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
				int width=displayMetrics.widthPixels;
				int height=displayMetrics.heightPixels;
				temp=Bitmap.createScaledBitmap(temp,width,height, true);
				wm.setBitmap(temp);		
				if(tabletSize)
					wm.suggestDesiredDimensions(width,height);
			    //========= Release Image ======//
				temp.recycle();
			    temp=null;
			    //======== Disable The Button ======//
			    Button b=(Button)v;
			    b.setEnabled(false);
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}
		//=========== Set WallPaper =========//
		
		//======== Next Stage ==========//
		Controller.stage++;
		parent.finishMe(true);
		new MyProgressBarMenu(getApplicationContext(),this, level).execute();
		//======== Next Stage ==========//
	}
	//======== Go Back To Choose Puzzle Menu =======//
	
	//=========== Change The User Stage ========//
	private void changeStage()
	{
		//========= Update Stage For User ======// 
		String userName=Controller.getUserName();
		try {
			db = openOrCreateDatabase("PuzzleDataBase", MODE_PRIVATE, null);
			String sqlComm="UPDATE UserStage SET Stage=Stage+1 WHERE User ='"+userName+"' AND Stage ="+Controller.stage;
			db.execSQL(sqlComm);
			db.close();
		} catch (Exception e) {
			MyAlertBox mab=new MyAlertBox(this,"3: " + e.getMessage());
		}
		//========= Update Stage For User ======//
	}
	//=========== Change The User Stage ========//
	
	//=========== Add User Rating To DB =======//
	private void addRating(int stage)
	{
		try{
			String name =Controller.getUserName();
			boolean found=true;
			String sqlCommand="";
			
			//====== Open DB  ======//
			db = openOrCreateDatabase("PuzzleDataBase",MODE_PRIVATE, null);
			//====== Open DB ======//
	
			//========== Check If This Stage IS Already Exists ==========//
			sqlCommand="SELECT * FROM UserRating WHERE Stage="+stage+" AND User='"+name+"';";
			Cursor iterator = db.rawQuery(sqlCommand, null);
			//========== Check If This Stage IS Already Exists ==========//
			
			//====== IF Not Exists ======//
			if(iterator.getCount()==0)
			{
				sqlCommand="INSERT INTO UserRating VALUES("+ stage +",'"+rating+ "','"+name+ "');";
			}
			//====== IF Not Exists ======//
			else
			{
				sqlCommand="UPDATE UserRating SET Rating='"+rating+"' WHERE Stage="+stage+" AND User='"+name+"';";
			}
			
			db.execSQL(sqlCommand);
			iterator.close();
			db.close();
		}catch (Exception e) {
			MyAlertBox mab=new MyAlertBox(this,"4: " + e.getMessage());
		}
	}
	//=========== Add User Rating To DB =======//
	

}
