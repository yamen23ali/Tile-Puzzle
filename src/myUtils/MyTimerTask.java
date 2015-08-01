package myUtils;

import java.util.TimerTask;

import com.yali.USAPuzzle.MyADSDialogBox;
import com.yali.USAPuzzle.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

public class MyTimerTask extends TimerTask {

	//======================//
	Activity parent;
	long seconds;
	int currentTextNum=-1;
	public  boolean paused=false;
	int level;
	//======================//
	
	//======================//
	public MyTimerTask(Activity parent,int level)
	{
		this.parent=parent;
		seconds=0;
		this.level=level;
		
	}
	//======================//
	
	
	//=========== What To Do On Time ===========//
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(!paused)
		{
			seconds++;
			//========== Change The Text View =======//
			if(seconds%10==0)
			{
				currentTextNum=(currentTextNum+1)%3;
			}
			//========== Change The Text View =======//
			
			//============== Call Dialog ADS ==========//
			if(seconds%(30*level)==0)
			{
				Intent adsDiag=new Intent(parent,MyADSDialogBox.class);
				parent.startActivity(adsDiag);
			}
			//============== Call Dialog ADS ==========//
			
			parent.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String info="";
					if(currentTextNum==0 || currentTextNum==-1)
					{
						
						info= "" + 
								   String.format("%02d",(seconds/3600)) +":"+
								   String.format("%02d",(seconds/60)) + ":" +
								   String.format("%02d",(seconds%60));
					}
					else if (currentTextNum==1)
					{
						info= Controller.moves + " Moves";
					}
					else
					{
						info=Controller.penalty + " Penalty"  ;
					}
					
					//======== IF Wrong Pieces Have To Be Shown ======//
					if(Controller.wrongPieces>0)
					{
						info=Controller.wrongPieces + " Wrong" ;
					}
					//======== IF Wrong Pieces Have To Be Shown ======//
					
					
					//========= But Text With Type Face =======//
					TextView tv=(TextView) parent.findViewById(R.id.infoBar);
					Typeface face=Typeface.createFromAsset(parent.getAssets(), "Fonts/HennyPenny.ttf");
					tv.setTextColor(Color.BLACK);
					if(Controller.wrongPieces>0)
						tv.setTextColor(Color.RED);
					tv.setTypeface(face,Typeface.BOLD); 
					tv.setText(info);
					//========= But Text With Type Face =======//
				}
			});
		}
	}
	//=========== What To Do On Time ===========//
	
	//=========== Get The Current Time In Seconds ========//
	public long getElapsedTime()
	{
		return seconds;
	}
	//=========== Get The Current Time In Seconds ========//

}
