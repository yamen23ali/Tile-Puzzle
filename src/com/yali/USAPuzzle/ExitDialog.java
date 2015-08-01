package com.yali.USAPuzzle;

import com.yali.USAPuzzle.R;

import myUtils.MyTextResizer;
import myUtils.MyWindowResizer;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExitDialog extends Activity implements OnClickListener {
	public static Activity parent;
	boolean rated=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exit_dialog);
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(10,0.55f);
		float size2=textResizer.calcSize(2,0.08f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/CevicheOne.ttf");
		Typeface face2 = Typeface.createFromAsset(getAssets(),"Fonts/HennyPenny.ttf");
		//=============== Text Size And Font ==========//
		
		//============= To Know If User Has Rate The Game =========//
		SharedPreferences prefs = getSharedPreferences("com.yali.USAPuzzle", getApplicationContext().MODE_PRIVATE);
		rated=prefs.getBoolean("rated",false); 
		//============= To Know If User Has Rate The Game =========//
		
		
		//============= Style ==============//
		TextView tv=(TextView) findViewById(R.id.exitTextView);
		tv.setTypeface(face);
    	tv.setTextColor(Color.BLACK);
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
    	
		Button b = (Button) findViewById(R.id.exitD);
		b.setOnClickListener(this);
		b.setTypeface(face2,Typeface.BOLD);
		b.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
		b.setTextColor(Color.BLACK);

		Button b2 = (Button) findViewById(R.id.stayD);
		b2.setOnClickListener(this);
		b2.setTypeface(face2,Typeface.BOLD);
		b2.setTextSize(TypedValue.COMPLEX_UNIT_PX,size2);
		b2.setTextColor(Color.BLACK);
		if(rated)
		{
			tv.setText("Get More Free Games At \n Our Market !!");
			b2.setText("Free Games");
		}
		else
		{
			b2.setText("Rate");
		}
		//============= Style ==============//

		//========= Resize The Activity On The Screen ====//
		MyWindowResizer.resizeWindow(this, 0.6f, 0.6f);
		//========= Resize The Activity On The Screen ====//
	}

	
	//================ Exit Or Rate =========//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int currentId = v.getId();
		if (currentId == R.id.exitD) {
			this.finish();
			parent.finish();
		} 
		else
		{
			if (!rated)
			{
				// ============= To Know If User Has Rate The Game =========//
				SharedPreferences prefs = this.getSharedPreferences(
						"com.yali.USAPuzzle",
						getApplicationContext().MODE_PRIVATE);
				prefs.edit().putBoolean("rated", true).commit();
				// ============= To Know If User Has Rate The Game =========//

				Intent goToMarket = new Intent();
				goToMarket.setAction(Intent.ACTION_VIEW);
				goToMarket.addCategory(Intent.CATEGORY_BROWSABLE);
				try {
					goToMarket
							.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.yali.USAPuzzle"));
					startActivity(goToMarket);
				} catch (Exception e) {
					try {
						goToMarket.setData(Uri
								.parse("market://search?q=pub:\"Happy Tech\""));
						startActivity(goToMarket);
					} catch (Exception e1) {
						try {
							goToMarket.setData(Uri
									.parse("market://search?q=pub:Happy Tech"));
							startActivity(goToMarket);
						} catch (Exception e2) {

						}
					}
				}
			}
			else
			{
				Intent goToMarket = new Intent();
				goToMarket.setAction(Intent.ACTION_VIEW);
				goToMarket.addCategory(Intent.CATEGORY_BROWSABLE);
				goToMarket
				.setData(Uri.parse("http://play.google.com/store/apps/developer?id=HappyTech"));
				startActivity(goToMarket);
			}
		} 
	}
	//================ Exit Or Rate =========//

}
