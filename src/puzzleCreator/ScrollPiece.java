package puzzleCreator;

import java.util.HashMap;
import myUtils.*;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;;

public class ScrollPiece extends LinearLayout implements OnClickListener{
	
   //============= Define Main Variables =========//
	public int type; //=== 1 for Horizontal , 2 for Vertical ==//
	public int pieceNumber;
	public ImageView content;
   //============= Define Main Variables =========//
	
	public ScrollPiece(Context context,Bitmap picture,int pieceNumber,int type) {
		super(context);
		// TODO Auto-generated constructor stub
		
		//======= Set Piece Number =======//
		this.pieceNumber=pieceNumber;
		//======= Set Piece Number =======//
		
		//======= Set Type =======//
		this.type=type;
		//======= Set Type =======//
		
		//======== Set The click Listener =======//
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			MyScrollTouchListener mtl=new MyScrollTouchListener(this);
			this.setOnTouchListener(mtl);
		}
		else
		{
			this.setOnClickListener(this);
		}
		//======== Set The click Listener =======//

		//======== Set The Content ( image )  =======//
		content=new ImageView(context);
		content.setImageBitmap(Bitmap.createBitmap(picture));
		
		LayoutParams lp= new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.setMargins(0,10,0,0);
		this.setLayoutParams(lp);
		this.setGravity(Gravity.CENTER);
		
		this.addView(content);
		//======== Set The Content ( image )  =======//
	}
	
	//================== On Click Event To Switch Images ===========//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Controller.switchImage(this);
	}
	//================== On Click Event To Switch Images ===========//
	
	//================== Deselect This Piece ===========//
	public void deSelect() {
		this.setBackgroundColor(Color.TRANSPARENT);
	}
	//================== Deselect This Piece ===========//
	
	//============= Clear ==============//
	public void clear()
	{
		((BitmapDrawable)content.getDrawable()).getBitmap().recycle();
		content.setImageDrawable(null);
		this.removeAllViews();
	}
	//============= Clear ==============//
}
