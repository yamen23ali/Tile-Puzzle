package com.yali.USAPuzzle;

import myUtils.MyTextResizer;
import myUtils.MyWindowResizer;

import com.yali.USAPuzzle.R;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

public class MyADSDialogBox extends Activity {
	
	//================= Some Variables ========//
	private static Activity caller;
	protected AdView adView;
	String adsMsg="";
	//================= Some Variables ========//
	
	//=========== Resize And Show ADS =========//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ads_dialog_box);
		
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(this);
		float size=textResizer.calcSize(12,0.4f);
		Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/MarckScript.ttf");
		TextView tv=(TextView) findViewById(R.id.adsTextView);
		tv.setTypeface(face);
		tv.setTextSize(size);
		//=============== Text Size And Font ==========//
		
		adView = (AdView)findViewById(R.id.adDiag);
 	    adView.setAdListener(new AdListener() {
			
			@Override
			public void onReceiveAd(Ad arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onPresentScreen(Ad arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onLeaveApplication(Ad arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onDismissScreen(Ad arg0) {
				// TODO Auto-generated method stub
			}
		});
 	   adView.loadAd(new AdRequest());
	}
	//=========== Resize And Show ADS =========//

}
