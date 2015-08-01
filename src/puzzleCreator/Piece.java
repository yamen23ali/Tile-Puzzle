package puzzleCreator;

import java.util.HashMap;

import myUtils.Controller;
import myUtils.MyPieceDragListener;
import myUtils.MyPieceTouchListener;
import myUtils.MyScrollTouchListener;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;;

public class Piece extends ImageView implements OnClickListener{
	
   //============= Define Main Variables =========//
	public HashMap<String,Bitmap> pictures = new HashMap<String, Bitmap>();
	public int type; //=== 1 for Horizontal , 2 for Vertical ==//
	public int width,height;
	float ratio;
	public int pieceNumber;
	public int state; //====== 1 Main , 2 Empty 
   //============= Define Main Variables =========//
	public Piece(Context context,int pieceNumber,int type) {
		super(context);
		// TODO Auto-generated constructor stub
		if(type==1)
		{
			width=Controller.maskH.getWidth();
			height=Controller.maskH.getHeight();
		}
		else{
			width=Controller.maskV.getWidth();
			height=Controller.maskV.getHeight();
		}
		//======= Initialize The Piece Mask and Size =======//
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width,height);
		this.setLayoutParams(layoutParams);

		//======= Initialize The Piece Mask and Size =======//
		
		//======= Set Piece Number && ID =======//
		this.pieceNumber=pieceNumber;
		this.setId(pieceNumber);
		//======= Set Piece Number && ID =======//
		
		//======= Set Type & State =======//
		this.type=type;
		this.state=1;
		//======= Set Type & State =======//
		
		//===== To Stop Image Cropping ======//
		this.setScaleType(ScaleType.MATRIX); 
		//===== To Stop Image Cropping ======//
		
		//======== Set The click Listener =======//
		
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
			MyPieceTouchListener mtl=new MyPieceTouchListener(this);
			this.setOnTouchListener(mtl);
			MyPieceDragListener mdl=new MyPieceDragListener(this);
		}
		else
		{
			this.setOnClickListener(this);
		}
		//======== Set The click Listener =======//
		
	}
	
	//========= Cut The Suitable puzzle piece from the provided Image =======//
	public void cutPiece(int x, int y) 
	{
		if(type==1)
		{
			Canvas canvas = new Canvas(Controller.resultH);
	
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	
			//======= Set Main Image ======//
			canvas.drawBitmap(Controller.image, x, y, null);
			canvas.drawBitmap(Controller.maskH, 0, 0, paint);
			
			pictures.put("Main",Bitmap.createBitmap(Controller.resultH));
			Controller.resultH.eraseColor(android.graphics.Color.TRANSPARENT);
			//======= Set Main Image ======//
			
			//======= Set Empty Image ======//
			canvas.drawBitmap(Controller.imageEmpty, x, y, null);
			canvas.drawBitmap(Controller.maskH, 0, 0, paint);
			
			paint.setXfermode(null);
			canvas=null;
			
			pictures.put("Empty",Bitmap.createBitmap(Controller.resultH));
			Controller.resultH.eraseColor(android.graphics.Color.TRANSPARENT);
			//======= Set Empty Image ======//
			
			
			this.setImageBitmap(pictures.get("Main"));
		}
		else
		{
			Canvas canvas = new Canvas(Controller.resultV);
			
			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
	
			//======= Set Main Image ======//
			canvas.drawBitmap(Controller.image, x, y, null);
			canvas.drawBitmap(Controller.maskV, 0, 0, paint);
			
			pictures.put("Main",Bitmap.createBitmap(Controller.resultV));
			Controller.resultV.eraseColor(android.graphics.Color.TRANSPARENT);
			//======= Set Main Image ======//
			
			//======= Set Empty Image ======//
			canvas.drawBitmap(Controller.imageEmpty, x, y, null);
			canvas.drawBitmap(Controller.maskV, 0, 0, paint);
			
			paint.setXfermode(null);
			canvas=null;
			
			pictures.put("Empty",Bitmap.createBitmap(Controller.resultV));
			Controller.resultV.eraseColor(android.graphics.Color.TRANSPARENT);
			//======= Set Empty Image ======//
			
			this.setImageBitmap(pictures.get("Main"));
			
		}
		
	}
	//========= Cut The Suitable puzzle piece from the provided Image =======//

	
	//================== On Click Event To Switch Images ===========//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(state==1)
		{
			//this.setImageBitmap(pictures.get("notClicked"));
			//state=2;
		}
		else if(state==2)
		{
			Controller.switchImage(this);
			Controller.constructScrollView();
		}
	}
	//================== On Click Event To Switch Images ===========//
	
	
	//================ Change State ( Empty Some Pieces ) ==================//
	public void switchState()
	{
		state=2;
		this.setImageBitmap(pictures.get("Empty"));
		
	}
	//================ Change State ( Empty Some Pieces ) ==================//
	
	//================ Clear This Piece ==========//
	public void clear()
	{
		
		((BitmapDrawable)getDrawable()).getBitmap().recycle();
		setImageDrawable(null);
		
		pictures.get("Empty").recycle();
		pictures.put("Empty", null);

		pictures.get("Main").recycle();
		pictures.put("Main", null);
	}
	//================ Clear This Piece ==========//
	
}
