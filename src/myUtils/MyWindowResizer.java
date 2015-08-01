package myUtils;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
public class MyWindowResizer {


	//================================================//
	public static void resizeWindow(Activity parent,float widthWeight,float heightWeight)
	{
		Window window=parent.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		int screenHeight = parent.getResources().getDisplayMetrics().heightPixels;
		int screenWidth = parent.getResources().getDisplayMetrics().widthPixels;
		int wpx = (int) (screenWidth * widthWeight);
		int hpx = (int) (screenHeight * heightWeight);
		
	    WindowManager.LayoutParams params = window.getAttributes();  
	    params.x =0;  
	    params.height = hpx;  
	    params.width = wpx;  
	    params.y =0;  
	    window.setAttributes(params);  
	}
	//================================================//
	public static void resizeWindowWidth(Activity parent,float widthWeight)
		{
			Window window=parent.getWindow();
			window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);			
			
			int screenWidth = parent.getResources().getDisplayMetrics().widthPixels;
			int wpx = (int) (screenWidth * widthWeight);
			
			
		    WindowManager.LayoutParams params = window.getAttributes();  
		    params.x =0;  
		    params.width = wpx;  
		    params.y =0;  
		    window.setAttributes(params);  
		}
	//=========================================================//
	public static void resizeWindowSetHeight(Activity parent,int height)
	{
		Window window=parent.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
	    WindowManager.LayoutParams params = window.getAttributes();  
	    params.x =0;  
	    params.height = height;  
	    params.y =0;  
	    window.setAttributes(params);  
	}
}


