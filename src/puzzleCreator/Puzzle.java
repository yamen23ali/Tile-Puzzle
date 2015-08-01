package puzzleCreator;

import java.util.ArrayList;

import myUtils.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Puzzle extends RelativeLayout {

	//======== Some Variables =======//
	public ArrayList<Piece> pieces = new ArrayList<Piece>();
	int widthNum=0;
	Context context;
	//======== Some Variables =======//
	
	//======= Our Constructor =======//
	public Puzzle(Context context) {
		super(context);
		this.context=context;
		
		//===== Some Positioning =====//
		LayoutParams lp=new android.widget.RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		this.setLayoutParams(lp);
		this.setGravity(Gravity.CENTER);
		//===== Some Positioning =====//
		
		puzzleIt();
	}
	//======= Our Constructor =======//
	
	//================= Cut Picese For Puzzle ==============//
	public void cutIt() 
	{
		
		int maskOrintation = 1;
		int firstMaskOrintation = 1;
		int x = 0, y = 0, oldY = 0;
		int pieceNumber = 1;

		int originalWidth = Controller.image.getWidth();
		int originalHeight = Controller.image.getHeight();

		int width = Controller.maskH.getWidth();
		int height = Controller.maskH.getHeight();


		while (true) 
		{
			// ======= Don't Enter The If for the first Row of pieces ====//
			if (pieceNumber != 1) 
			{ 
				if (firstMaskOrintation == 1) {
					y = oldY - height;
					y += (height / 3);
					x = -width / 5;
					firstMaskOrintation = 2;
					if ((originalHeight - (y * -1)) < (width * 0.33))
						break;
				} 
				else {
					y = oldY - width;
					y += (height / 3);
					x = 0;
					firstMaskOrintation = 1;
					if ((originalHeight - (y * -1)) < (height * 0.33))
						break;
				}
				oldY = y;
				maskOrintation = firstMaskOrintation;
			}
			// ======= Don't Enter The If for the first Row of pieces ====//
			int counter=0;
			while (true) 
			{
				// ====================//
				Piece piece;			
				if (maskOrintation == 1)
					piece = new Piece(context,pieceNumber,1);
				else
					piece = new Piece(context,pieceNumber,2);
				// ====================//
				piece.cutPiece(x, y);
				//========================//
				pieces.add(piece);
				pieceNumber++;
				counter++;
				// ====================//
				if (maskOrintation == 1) {
					x = x - (width - (width / 5));
					y = y + (width / 5);
					maskOrintation = 2;
				} 
				else {
					x = x - (height - (height / 3));
					y = y - (width / 5);
					maskOrintation = 1;
				}
				//============ To know When To break the form puzzling the current row ===//
				if(oldY==0){
					int tempX=x;
					if (maskOrintation == 1) tempX-=(height / 3);
					if ((originalWidth - ( tempX * -1)) < (height * 0.33))
						break;
				}
				else if( counter== widthNum)	 break;
				//============ To know When To break the form puzzling the current row ===//
			}
			if ( oldY==0)
				widthNum=pieceNumber-1; //===== To Know How many pieces there is in a row
		}
	}
	//================= Cut Picese For Puzzle ==============//
	
	
	//========== Put The Pieces Together =============//
	public void puzzleIt()
	{
		cutIt();
		float width=pieces.get(0).width;
		
		int rows=pieces.size()/widthNum;
		int shift1=-(int)(width*0.21);
		int shift2=(int)-(width*0.18);
		int shift3=-1*shift2;
		int start=1;
		for(int i=0;i<rows;i++)
		{
			for(int j=start;j<=widthNum;j++)
			{
				Piece current=pieces.get((j+(widthNum*i)-1));
				//=========== First Row =======//
				if(i==0)
				{
					RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					if(j!=start)
					{
						relativeParams.addRule(RelativeLayout.RIGHT_OF,current.pieceNumber-1) ;
						 if(current.type==1)
						 {
							 relativeParams.setMargins(shift1,0,0,0);
						 }
						 else
						 {
							 relativeParams.setMargins(shift1,shift2,0,0);
						 }
					 }
					this.addView(current, relativeParams);
				}
				//=========== First Row =======//
				
				//=========== rest Of Rows =======//
				else
				{
					RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					relativeParams.addRule(RelativeLayout.BELOW,current.pieceNumber-widthNum);
					 if(j!=start)
					 {
						 relativeParams.addRule(RelativeLayout.RIGHT_OF,current.pieceNumber-1);
						 relativeParams.setMargins(shift1,shift1,0,0);
					 }
					 else
					 {
						 if(current.type==2)
						 {
							 relativeParams.setMargins(shift3,shift1,0,0);
						 }
						 else
						 {
							 relativeParams.setMargins(0,shift1,0,0);
						 }
					 }
					 this.addView(current, relativeParams);
				}
				//=========== rest Of Rows =======//
			}
		}	 	
	}
	//========== Put The Pieces Together =============//

	//============ Clear =======//
	public void clear()
	{
		for (int i = 0; i < pieces.size(); i++) 
		{
			pieces.get(i).clear();
		}
		pieces.clear();
		
		this.removeAllViews();
	}
	//============ Clear =======//

}
