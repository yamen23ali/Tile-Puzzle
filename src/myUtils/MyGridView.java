package myUtils;

import java.util.HashMap;

import com.yali.USAPuzzle.R;
import com.yali.USAPuzzle.PuzzleBoard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class MyGridView extends LinearLayout implements OnClickListener{

	//=================//
	Context context;
	int columnsNumber;
	Activity parent;
	HashMap<Integer,Integer> imageViewPictureMapping;
	int level;
	int cellWidth;
	//=================//
	
	//======== Just Constructor =========//
	public MyGridView(Context context,Activity parent,int columnsNumber,int level,int cellWidth) {
		super(context);
		//=====================//
		this.context=context;
		this.columnsNumber=columnsNumber;
		this.parent=parent;
		imageViewPictureMapping=new HashMap<Integer, Integer>();
		this.level=level;
		this.cellWidth=cellWidth;
		//=====================//
		LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		this.setLayoutParams(lp);
		this.setOrientation(VERTICAL);
		//=====================//
	}
	//======== Just Constructor =========//
	
	public void generateGridView(Bitmap[] images,StatesInfo[] states,int cellLayoutId,int stage)
	{
		int rows=(int)Math.ceil((double)images.length/(double)columnsNumber);
		Typeface face=Typeface.createFromAsset(parent.getAssets(), "Fonts/CevicheOne.ttf");
		
		for(int i=1;i<=rows;i++)
		{
			int start=(i-1)*columnsNumber;
			
			//======== Initialize Row ========//
			LinearLayout rowLayout=new LinearLayout(context);
			LayoutParams lp=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			rowLayout.setLayoutParams(lp);
			rowLayout.setOrientation(HORIZONTAL);
			//======== Initialize Row ========//
			
			for(int j=start;j<start+columnsNumber;j++)
			{
				if(j==images.length) break;
				//======== Inflate Layout =======//
				LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View v = inflator.inflate(cellLayoutId,null);
				MyTextResizer textResizer=new MyTextResizer(v);
				//======== Inflate Layout =======//
				
				//======= Set Names ==========//
				TextView tv=(TextView) v.findViewById(R.id.chooseTextView);
				tv.setTextColor(Color.BLACK);
				tv.setTypeface(face,Typeface.NORMAL); 				
				tv.setText(states[j].name);
				//======= Set Names ==========//
				
				//======= Set Rating ========//
				RatingBar rb=(RatingBar)v.findViewById(R.id.cellRatingBar);
				rb.setRating(states[j].rating);
			     
			    TextView rtv=(TextView) v.findViewById(R.id.ratingTextView);
				rtv.setTextColor(Color.BLACK);
				rtv.setTypeface(face,Typeface.NORMAL); 
				if(states[j].rating>0)
				{
					rtv.setText(new Integer((int)(states[j].rating*100)/5).toString()+" %");
				}
				else
				{
					rtv.setText("");
				}
				rtv.setContentDescription(states[j].source);
			    //======= Set Rating ========//
				
				//========= Resize Text ======//
				textResizer.setTextSize(R.id.chooseTextView,6,cellWidth,1.0f);
				textResizer.setTextSize(R.id.ratingTextView,6,cellWidth,1.0f);
				//========= Resize Text ======//
				
			    //======= Add To Row ========//
				v.setId(states[j].imageId);
				v.setTag(j+1);//=== Image Stage
				v.setContentDescription(states[j].place);//==== place
				if(j<stage)
				{
					//======= Set Image ==========//
					ImageView im=(ImageView) v.findViewById(R.id.chooseImageView);
					im.setImageBitmap(images[j]);
					//======= Set Image ==========//
					v.setOnClickListener(this);
				}
				else
				{
					//======= Set Image ==========//
					ImageView im=(ImageView) v.findViewById(R.id.chooseImageView);
					im.setImageBitmap(images[stage]);
					//======= Set Image ==========//
				}
				rowLayout.addView(v);
				//======= Add To Row ========//
			} 
			rowLayout.setGravity(Gravity.CENTER);
			this.addView(rowLayout);
		}
		
	}

	//=============== What To Do When Cell Is Clicked ========//
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId(); 
		TextView tv=(TextView) v.findViewById(R.id.ratingTextView);
		
		Intent in=new Intent(parent,PuzzleBoard.class);
		in.putExtra("puzzleId",id);
		in.putExtra("level",level);
		in.putExtra("place",v.getContentDescription().toString());
		in.putExtra("currentStage",Integer.parseInt(v.getTag().toString()));
		in.putExtra("imageCredits",tv.getContentDescription().toString());
		parent.startActivity(in);
		parent.finish();
	}
	//=============== What To Do When Cell Is Clicked ========//
	
	//================= To Open New Stage For User ==========//
	public void openStage(Bitmap image,int stage,int imageId)
	{
		View v=this.findViewById(imageId);
		ImageView iv=(ImageView) v.findViewById(R.id.chooseImageView);
		iv.setImageBitmap(image);
		v.setOnClickListener(this);
	}
	//================= To Open New Stage For User ==========//
	
	//================= To Change Stage Rating ==========//
	public void changeStageRating(float rating,int imageId)
	{
		View v=this.findViewById(imageId);
		
		RatingBar rb=(RatingBar) v.findViewById(R.id.cellRatingBar);
		rb.setRating(rating);
		
		TextView rtv=(TextView) v.findViewById(R.id.ratingTextView);
		if(rating>0)
		{
			rtv.setText(new Integer((int)(rating*100)/5).toString()+" %");
		}
		else
		{
			rtv.setText("");
		}
	}
	//================= To Change Stage Rating ==========//
	

	

}
