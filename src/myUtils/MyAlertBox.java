package myUtils;

import java.text.DecimalFormat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Debug;
import android.util.Log;
import android.view.View.OnClickListener;

public class MyAlertBox implements DialogInterface.OnClickListener {
	
	//=========== Constructor ======//
	public MyAlertBox(Context context,String message) {
		// TODO Auto-generated constructor stub
		shwoAlert(context,message);
	}
	//=========== Constructor ======//
	
	//========== Show The Message ======//
	public  void shwoAlert(Context context,String message)
	{
		AlertDialog alert = new AlertDialog.Builder(context).create();
		alert.setMessage(message);
		alert.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",this);
		alert.setButton(DialogInterface.BUTTON_NEGATIVE, "No", this);
		alert.show();
	}
	//========== Show The Message ======//

	//========== Things To Do When Dialog Buttons Are Clicked ======//
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	//========== Things To Do When Dialog Buttons Are Clicked ======//

}
