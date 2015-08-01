package com.yali.USAPuzzle;

import myUtils.Controller;
import myUtils.MyTextResizer;

import com.yali.USAPuzzle.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class HelpersMenu extends Activity implements OnClickListener{

	
	//================ Build The Main Menu Structure =======//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.helpers_menu);
		
		//======= To Be Sensitive And Aware For Outside Touching ====// 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
		//======= To Be Sensitive And Aware For Outside Touching ====//
		
		//============= Change Font On Buttons ========//
		MyTextResizer textResizer=new MyTextResizer(this);
    	float size=textResizer.calcSize(6,0.25f);
    	Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/GochiHand.ttf");     
        
    	//========== Get The Buttons =====//
    	
    	Button showOriginal=(Button) findViewById(R.id.showOriginal);
    	showOriginal.setTypeface(face,Typeface.BOLD);
    	showOriginal.setTextColor(Color.parseColor("#331A11"));
    	showOriginal.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	showOriginal.setText(Controller.showText);
    	showOriginal.setOnClickListener(this);
    	
    	Button resetWrongPieces=(Button) findViewById(R.id.resetWrongPieces);
    	resetWrongPieces.setTypeface(face,Typeface.BOLD);
    	resetWrongPieces.setTextColor(Color.parseColor("#331A11"));
    	resetWrongPieces.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	resetWrongPieces.setOnClickListener(this);
    	
    	Button placePieces=(Button) findViewById(R.id.placePieces);
    	placePieces.setTypeface(face,Typeface.BOLD);
    	placePieces.setTextColor(Color.parseColor("#331A11"));
    	placePieces.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	placePieces.setOnClickListener(this);
    	
    	Button pause=(Button) findViewById(R.id.pause);
    	pause.setTypeface(face,Typeface.BOLD);
    	pause.setTextColor(Color.parseColor("#331A11"));
    	pause.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	pause.setText(Controller.pauseText);
    	pause.setOnClickListener(this);
    	
    	TextView tv=(TextView) findViewById(R.id.menuTitle);
    	tv.setTypeface(face,Typeface.BOLD);
    	tv.setTextColor(Color.rgb(218,165,32));
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size+10);
    	//========== Get The Buttons =====//
    	
    	//=========== Disable Used Helpers =======//
    	if(Controller.placePieces==0) 
    	{
    		placePieces.setEnabled(false);
    		placePieces.setBackgroundResource(R.drawable.helpers_menu_buttons_disabled);
    	}
    	if(Controller.resetPieces==0)
    	{
    		resetWrongPieces.setEnabled(false);
    		resetWrongPieces.setBackgroundResource(R.drawable.helpers_menu_buttons_disabled);
    	}
    	//=========== Disable Used Helpers =======//
	}
	//================ Build The Main Menu Structure =======//
	
	
	//============ What To Do On Click ========//
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		int help=1;
		if(v.getId()==R.id.resetWrongPieces)
		{
			help=2;
			Controller.resetPieces--;
		}
		else if (v.getId()==R.id.placePieces)
		{
			help=3;
			Controller.placePieces--;
		}
		else if (v.getId()==R.id.pause)
		{
			help=4;
		}
		//========= Back To Board ======//
		Intent in=new Intent(this,PuzzleBoard.class);
		in.putExtra("callingAct",1);
		in.putExtra("helpValue",help);
		startActivity(in);
		//========= Back To Board ======//
	}
	//============ What To Do On Click ========//
	
	//============ To Place Menu At The Bottom =======//
	@Override
	public void onAttachedToWindow() 
	{
	    super.onAttachedToWindow();

	    //============ Get The Screen Dimensions ===========//
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    //============ Get The Screen Dimensions ===========//
	    
	    //============= Set The Main Layout OF The Menu =========//
	    View view = getWindow().getDecorView();
	    WindowManager.LayoutParams lp = (WindowManager.LayoutParams) view.getLayoutParams();
	    lp.gravity = Gravity.CENTER;
	    lp.width = (int)(dm.widthPixels*0.7f);
	    lp.height = (int) (dm.heightPixels*0.4);
	    getWindowManager().updateViewLayout(view, lp);
	    
	   //============= Set The Main Layout OF The Menu =========//
	}
	//============ To Place Menu At The Bottom =======//
	
	//================ Get The Touches ========//
	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{
		// If we've received a touch notification that the user has touched
	    // outside the app, finish the activity.
	    if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
	      finish();
	      return true;
	    }

	    // Delegate everything else to Activity.
	    return super.onTouchEvent(event);
	 }
	//================ Get The Touches ========//
}
