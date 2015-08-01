package myUtils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MyImageResizer {
	
	//=============Constructor===============//
	public MyImageResizer(){}
	//=============Constructor===============//
	
	
	//==============Resize The Image==============//
	public Bitmap resizeIt(Bitmap original,int wantedWidth,int wantedHeight)
	{
		int width=original.getWidth(),height=original.getHeight();
		
		while(true)
		{
			if(width>wantedWidth)
			{
				height=calcNewDimension(width, height, wantedWidth,1);
				width=wantedWidth;
			}
			else if(height>wantedHeight)
			{
				width=calcNewDimension(width, height, wantedHeight,2);
				height=wantedHeight;
			}
			else
			{
				break;
			}
		}
		return scaleBitMap(original,width, height);
	}
	//==============Resize The Image==============//
	
	//==============Scale Bit Map Algorithm ==============//
	public Bitmap scaleBitMap(Bitmap original,int newWidth,int newHeight)
	{
		int width = original.getWidth();
	    int height = original.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    
	    // create a matrix for the manipulation
	    Matrix matrix = new Matrix();
	    
	    // resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);
	    
	    // recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix,true);
	    
	    return resizedBitmap;
	}
	//==============Scale Bit Map Algorithm ==============//
	
	//===============Calculate New Dimensions Saving The Ratio ===================//
	public int calcNewDimension(int width,int height,int wantedDim,int type)
	{
		float ratio=(float)Math.max(width,height)/(float)Math.min(width,height);
		int newDim; 
		
		if(type==1) //====== wanted dim is width , new dim is height
		{
			if(width<=height) newDim=(int)(wantedDim*ratio);
			else newDim=(int)(wantedDim/ratio);
		}
		else //====== wanted dim is height , new dim is width
		{
			if(height<=width) newDim=(int)(wantedDim*ratio);
			else newDim=(int)(wantedDim/ratio);
		}
		 
		return newDim;
	}
	//===============Calculate New Dimensions Saving The Ratio ===================//

}
