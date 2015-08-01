package com.yali.USAPuzzle;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle.Control;
import java.util.Timer;
import java.util.TimerTask;

import com.yali.USAPuzzle.R;

import myUtils.*;
import puzzleCreator.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PuzzleBoard extends Activity implements OnClickListener,DialogInterface.OnClickListener{

	int puzzle = 0;
	int level=0;
	boolean started = false;
	int maskHId, maskVId;
	Controller controller;
	Activity current;
	int currentStage;
	// ================//

	// =========================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.puzzleboard);
		
		current=this;
		// ================//

		// ====== Get The Picture Id to puzzle it ======//
		puzzle = Integer.parseInt(getIntent().getExtras().get("puzzleId")
				.toString());
		// ====== Get The Picture Id to puzzle it ======//
		
		// ====== Get The Place Name ======//
		String place = getIntent().getExtras().get("place").toString();
		Typeface face=Typeface.createFromAsset(getAssets(), "Fonts/CevicheOne.ttf");
		TextView tv=(TextView) findViewById(R.id.placeName);
		tv.setTextColor(Color.BLACK);
		tv.setTypeface(face,Typeface.NORMAL); 
		tv.setText(place);
		// ====== Get The Place Name ======//
		
		// ====== Get The Image Credits ======//
		String imageCredits = getIntent().getExtras().get("imageCredits").toString();
		Typeface face2=Typeface.createFromAsset(getAssets(), "Fonts/MarckScript.ttf");
		TextView icTv=(TextView) findViewById(R.id.imageCredits);
		icTv.setTextColor(Color.BLACK);
		icTv.setTypeface(face2,Typeface.BOLD); 
		icTv.setText("Image Source : \n" + imageCredits +"\n - CC BY -");
		if(imageCredits.equals(""))
			icTv.setText("");
		// ====== Get The Image Credits ======//
				
		
		//============ Set Size On Text Views ======//
		MyTextResizer textResizer=new MyTextResizer(this);
		textResizer.setTextSize(R.id.placeName,5,0.2f);
		textResizer.setTextSize(R.id.infoBar,6,0.2f);
		textResizer.setTextSize(R.id.imageCredits,7,0.2f);
		//============ Set Size On Text Views ======//
		
		// ====== Get The Place Name ======//
		currentStage = (Integer) getIntent().getExtras().get("currentStage");
		// ====== Get The Place Name ======//

		
		//======== But The Image Without Puzzling ==========// 
		intialize();
		//======== But The Image Without Puzzling ==========//
		
		// ====== Get The Level To Determine Missing ======//
		level = Integer.parseInt(getIntent().getExtras().get("level")
				.toString());
		// ====== Get The Level To Determine The Missing ======//
		
		//====== Set The Masks To Be Used ========//
		maskHId=R.drawable.maskh;
		maskVId=R.drawable.maskv;
		//====== Set The Masks To Be Used ========//
		
		
		//========== Some Events ======//
		LinearLayout startButton=(LinearLayout) findViewById(R.id.startPuzzleButton);
		startButton.setOnClickListener(this);
		
		LinearLayout helpButton=(LinearLayout) findViewById(R.id.helpButton);
		helpButton.setOnClickListener(this);
		//========== Some Events ======//
		
		//======== Initialize Helpers Param =====//
		Controller.showText="Show Image";
		Controller.pauseText="Pause";
		Controller.placePieces=3;
		Controller.resetPieces=2+level;
		//======== Initialize Helpers Param =====//
	}

	// =========================//

	// ========== To Create The Puzzle and Scroll Piece View =========//
	public void startTheGame() {

		controller=new Controller(getApplicationContext(),this,level,currentStage);
		
		
		RelativeLayout container = (RelativeLayout) findViewById(R.id.originalImageContainer);

		HashMap<String,Integer> variables=new HashMap<String, Integer>();
		variables.put("width", container.getWidth()-10);
		variables.put("height", container.getHeight()-10);
		variables.put("puzzle", puzzle);
		variables.put("maskHId",maskHId);
		variables.put("maskVId",maskVId);
		new MyProgressBar(getApplicationContext(),current,controller,variables).execute();
	
	}
	// ========== To Create The Puzzle and Scroll Piece View =========//
	
	//=========== Initialize The Board Layout ===============//
	void intialize()
	{
		RelativeLayout container = (RelativeLayout) findViewById(R.id.puzzleContainer);
		
		// ====== To Call It When Container Is ready ======== //
		container.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						// TODO Auto-generated method stub
						if (!started) {
							started = true;
							RelativeLayout container = (RelativeLayout) findViewById(R.id.originalImageContainer);
							
							Bitmap image=BitmapFactory.decodeResource(getResources(),puzzle);
							MyImageResizer resizer=new MyImageResizer();
							
							ImageView iv=new ImageView(getApplicationContext());
							LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
							iv.setLayoutParams(lp);							
							iv.setImageBitmap(resizer.scaleBitMap(image,container.getWidth()-10,container.getHeight()-10));
							
							container.addView(iv);
							image.recycle();
							image=null;
						}
					}
				});
		// ====== To Call It When Container Is ready ======== //
	}
	//=========== Initialize The Board Layout ===============//

	//============= On Start Or Help Click ============//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.startPuzzleButton)
		{
			startTheGame();
		}
		else
		{
			Intent in=new Intent(this,HelpersMenu.class);
			startActivity(in);
		}
		
	}
	//============= On Start Help Click ============//
	
	//============= Activate Appropriate Helper ======//
	private void activateHelper(int helper)
	{
		if (helper==1)
		{
			//======= Hide The Image and Show The Puzzle =========//
			RelativeLayout originalImage=(RelativeLayout) findViewById(R.id.originalImageContainer);
			RelativeLayout puzzle=(RelativeLayout) findViewById(R.id.puzzleContainer);
			
			//======= Show The Image and Hide The Puzzle =========//
			if(originalImage.getVisibility()==View.GONE)
			{
				originalImage.setVisibility(View.VISIBLE);
				puzzle.setVisibility(View.GONE);
				Controller.showText="Show Puzzle";
			}
			//======= Show The Image and Hide The Puzzle =========//
			//======= Hide The Image and Show The Puzzle =========//
			else if(originalImage.getVisibility()==View.VISIBLE)
			{
				originalImage.setVisibility(View.GONE);
				puzzle.setVisibility(View.VISIBLE);
				Controller.showText="Show Image";
			}
			//======= Hide The Image and Show The Puzzle =========//
		}
		else if(helper==2)
		{
			controller.resetWrongPieces();
		}
		else if(helper==3)
		{
			controller.placePiece(3*level);
		}
		else if(helper==4)
		{
			if(Controller.pauseText.equals("Pause"))
			{
				Controller.pauseGame();
			}
			else
			{
				Controller.resumeGame();				
			}
		}
	}
	//============= Activate Appropriate Helper ======//
	
	//============ When We back To Board ======// 
	@Override
	protected void onNewIntent(Intent intent) 
	{
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		int callingAct=intent.getExtras().getInt("callingAct", -1);
		int helper=intent.getExtras().getInt("helpValue", -1);
		if(callingAct==1 && helper!=-1)
		{
			activateHelper(helper);
		}
	}
	//============ When We back To Board ======//
	
	//============= On Back Click =============//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	AlertDialog alert = new AlertDialog.Builder(this).create();
			alert.setMessage("Are You Sure You Want To Exit The Board !!");
			alert.setButton(DialogInterface.BUTTON_POSITIVE,"Yes",this);
			alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No",this);
			alert.show();
	    }	    
	    return true;
	}
	//============= On Back Click =============//
	
	//============= To Change The Help Button Size ========//
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	  // TODO Auto-generated method stub
	  super.onWindowFocusChanged(hasFocus);
	  
	  //======== Change Help Button Dimensions ======//
	  LinearLayout helpButton=(LinearLayout) findViewById(R.id.helpButton);
	  LayoutParams params = helpButton.getLayoutParams();
	  params.width = helpButton.getHeight();
	  //======== Change Help Button Dimensions ======//
	  
	  //======== Change Start Button Dimensions ======//
	  LinearLayout startButton=(LinearLayout) findViewById(R.id.startPuzzleButton);
	  LayoutParams startParams = startButton.getLayoutParams();
	  startParams.width = startButton.getHeight();
	  //======== Change Start Button Dimensions ======//
	 }
	//============= To Change The Help Button Size ========//
	
	
	
	//============= Finish This Activity =========//
	public void finishMe(boolean away)
	{
		if(controller!=null)
    	{
    		controller.clear();
    	}
		this.clear();
		System.gc();
		//======================//
		if(!away)
		{
			new MyProgressBarMenu(getApplicationContext(),this, level).execute();
		}
		else{
			this.finish();
		}
		//======================//
	}
	//============= Finish This Activity =========//
		
	
	//========= Clear =======//
	public void clear()
	{
		RelativeLayout originalImage=(RelativeLayout) findViewById(R.id.originalImageContainer);
		ImageView im=(ImageView) originalImage.getChildAt(0);
		((BitmapDrawable)im.getDrawable()).getBitmap().recycle();
		im.setImageDrawable(null);
		
		LinearLayout ll=(LinearLayout) findViewById(R.id.accPuzzleBoard);
		ll.removeAllViews();
		
		LinearLayout ll2=(LinearLayout) findViewById(R.id.startButtonParent);
		ll2.removeAllViews();
	}
	//========= Clear =======//
	
	

	//========= Dialog click Listener =====//
	@Override
	public void onClick(DialogInterface arg0, int which) {
		// TODO Auto-generated method stub
		if(which==DialogInterface.BUTTON_POSITIVE)
		{
			finishMe(false);
		}
		
	}
	//========= Dialog click Listener =====//

}
