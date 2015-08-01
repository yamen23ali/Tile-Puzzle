package myUtils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.yali.USAPuzzle.R;

public class MyTextResizer {
	
	Activity parentAct;
	View parentView;
	//========== Constructors ========//
	public MyTextResizer(){}
	
	public MyTextResizer(Activity parent)
	{
		this.parentAct=parent;
	}
	
	public MyTextResizer(View parent)
	{
		this.parentView=parent;
	}
	//========== Constructors ========//
	
	//========== Resize Text When Text View Is Parent Is Activity========//
	public void setTextSize(int textViewId,int maxLettersNum,float textViewRatio)
	{
		TextView tv=(TextView) parentAct.findViewById(textViewId);
		DisplayMetrics dm = new DisplayMetrics();
		parentAct.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float size=(float) ((textViewRatio*dm.widthPixels))/maxLettersNum;
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	}
	//========== Resize Text When Text View Is Parent Is Activity========//
	
	//========== Resize Text When Text View Is Parent Is View ========//
	public void setTextSize(int textViewId,int maxLettersNum,int totalWidth,float textViewRatio)
	{
		TextView tv=(TextView) parentView.findViewById(textViewId);
		float size=(float) ((textViewRatio*totalWidth))/maxLettersNum;
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
	}
	//========== Resize Text When Text View Is Parent Is View ========//
	
	//========== Calculate The Text Size ========//
	public float calcSize(int maxLettersNum,float textViewRatio)
	{
		DisplayMetrics dm = new DisplayMetrics();
		parentAct.getWindowManager().getDefaultDisplay().getMetrics(dm);
		float size=(float) ((textViewRatio*dm.widthPixels))/maxLettersNum;
		return size;
	}
	//========== Calculate The Text Size ========//
	
	//========== Calculate The Text Size ========//
	public float calcSizePT(int maxLettersNum,float textViewRatio)
	{
			DisplayMetrics dm = new DisplayMetrics();
			parentAct.getWindowManager().getDefaultDisplay().getMetrics(dm);
			float size=(float) ((textViewRatio*dm.widthPixels))/maxLettersNum;
			
			float points = (size * 72.0f) /dm.densityDpi;
			return points;
	}
	//========== Calculate The Text Size ========//

}
