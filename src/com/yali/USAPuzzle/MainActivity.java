package com.yali.USAPuzzle;


import com.yali.USAPuzzle.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity implements View.OnClickListener {

	Activity parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//=====================//
		LinearLayout newButton=(LinearLayout) findViewById(R.id.newButton);
		newButton.setOnClickListener(this);
		
		LinearLayout loadButton=(LinearLayout) findViewById(R.id.loadButton);
		loadButton.setOnClickListener(this);
	}

	//=====================//
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	//=====================//
	
	//=============== On Click Event =======//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.exitButton)
		{
			this.finish();
		}
		else if (v.getId()==R.id.newButton)
		{
			Intent in=new Intent(this,NewUser.class);
			startActivity(in);
		}
		else if(v.getId()==R.id.loadButton)
		{
			Intent in=new Intent(this,LoadUser.class);
			startActivity(in);
		}
	}
	//=============== On Click Event =======//
	
	//============= On Back Click =============//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    //Handle the back button
	    if(keyCode == KeyEvent.KEYCODE_BACK) {
	    	ExitDialog.parent=this;
	    	Intent intent = new Intent(this, ExitDialog.class);
			startActivity(intent);
	    }	    
	    return true;
	}
	//============= On Back Click =============//

}
