package com.yali.USAPuzzle;

import java.util.Random;

import com.yali.USAPuzzle.R;


import myUtils.Controller;
import myUtils.MyProgressBarMenu;
import myUtils.MyTextResizer;
import myUtils.MyWindowResizer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class ChooseLevel extends Activity implements OnCheckedChangeListener{

	private String[] level_Name={"Easy","Medium","Hard","Evil"};
	private SQLiteDatabase db;
	//=================================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.choose_level);
		
		//=============== Fill The Radio Group =================//
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(5,0.4f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
		//=============== Text Size And Font ==========//
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		RadioGroup rg=(RadioGroup) findViewById(R.id.chooseLevelRadioGroup);
		rg.setOnCheckedChangeListener(this);
		
		for(int i=0;i<4;i++)
		{
			RadioButton rb=new RadioButton(rg.getContext());
			rb.setText(level_Name[i]);
			rb.setId(i);
			RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(getApplicationContext(), null);
			lp.setMargins(0,10,0,0);
			rb.setLayoutParams(lp);
			rb.setTypeface(face);
			rb.setTextColor(Color.BLACK); 
			rb.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);		
			rg.addView(rb);
		}
		//=============== Fill The Radio Group =================//
		
		//========== Resize Radio Group ===========//
		
		//========== Resize Radio Group ===========//
		
		//========= Resize The Activity On The Screen ====//
		MyWindowResizer.resizeWindow(this,0.4f,0.8f);
		//========= Resize The Activity On The Screen ====//
	}
	//=================================//
	
	//============ Choose The Level =======//
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		RadioButton rb=(RadioButton) group.findViewById(checkedId);
		String s=rb.getText().toString();
		//==================================//
		int level=1;
		if(s.equals(level_Name[1]))
		{
			level=2;
		}
		else if(s.equals(level_Name[2]))
		{
			level=3;
		}
		else if(s.equals(level_Name[3]))
		{
			level=4;
		}
		//==================================//
		
		//========= Update Level For User ======// 
		String userName=Controller.getUserName();
		try {
			db = openOrCreateDatabase("PuzzleDataBase", MODE_PRIVATE, null);
			String sqlComm="UPDATE UserStage SET Level="+level+" WHERE User ='"+userName+"'";
			db.execSQL(sqlComm);
			db.close();
		} 
		catch (Exception e) {
		}
		//========= Update Level For User ======//
				
		//===== Start The Game ======//
		Controller.stage=1;
		new MyProgressBarMenu(getApplicationContext(),this, level).execute();
		//===== Start The Game ======//
	}
	//============ Choose The Level =======//
	

}
