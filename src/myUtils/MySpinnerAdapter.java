package myUtils;

import com.yali.USAPuzzle.R;
import com.yali.USAPuzzle.R.color;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MySpinnerAdapter extends ArrayAdapter<String> {

	Activity parentAct;
	String[] itemsText;
	//==================== Constructor ========//
	public MySpinnerAdapter(Context context, int textViewResourceId,String[] objects, Activity parent) {
		super(context, textViewResourceId, objects);
		this.parentAct = parent;
		this.itemsText=objects;
	}
	//==================== Constructor ========//
	
	//================== Go To Custom View =============//
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomDropDownView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	//================== Go To Custom View =============//
	
	//================== My Custom Drop Down Spinner View ==========//
	public View getCustomDropDownView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = parentAct.getLayoutInflater();
		View row = inflater.inflate(R.layout.spinner_item, parent, false);
		//=============== Text Size And Font ==========//
		MyTextResizer textResizer=new MyTextResizer(parentAct);
		float size=textResizer.calcSize(6,0.4f);
		Typeface face = Typeface.createFromAsset(parentAct.getAssets(),"Fonts/MarckScript.ttf");

		CheckedTextView ctv = (CheckedTextView) row.findViewById(R.id.spinnerItemText);
		ctv.setText(itemsText[position]);
		ctv.setTypeface(face);
		ctv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
		ctv.setTextColor(Color.BLACK);
		//=============== Text Size And Font ==========//
		
		return row;
	}
	//================== My Custom Drop Down Spinner View ==========//
	
	//================== My Custom Spinner View ==========//
	public View getCustomView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = parentAct.getLayoutInflater();
		View row = inflater.inflate(R.layout.spinner_item, parent, false);
		
		Typeface face = Typeface.createFromAsset(parentAct.getAssets(),"Fonts/MarckScript.ttf");
		MyTextResizer textResizer=new MyTextResizer(parentAct);
		float size=textResizer.calcSize(8,0.4f);
		
		CheckedTextView ctv = (CheckedTextView) row.findViewById(R.id.spinnerItemText);
		ctv.setText(itemsText[position]);
		ctv.setTypeface(face,Typeface.BOLD);
		ctv.setTextSize(TypedValue.COMPLEX_UNIT_PX,size);
		ctv.setTextColor(Color.BLACK);
		//=============== Text Size And Font ==========//
		return row;
	}
	//================== My Custom Spinner View ==========//
	
}
