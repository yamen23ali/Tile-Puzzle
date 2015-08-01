package myUtils;

import java.util.HashMap;

import com.yali.USAPuzzle.ChoosePuzzle;
import com.yali.USAPuzzle.R;
import com.yali.USAPuzzle.PuzzleBoard;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class MyProgressBarMenu extends AsyncTask<Object, Void, String> {
    
	ProgressDialog mDialog;
	Activity parent;
	Context context;
	int level;
	boolean started;
	
	//==========================================//
    public MyProgressBarMenu(Context context,Activity parent,int level) {
        this.parent=parent;
        this.context=context;
        this.level=level;
        started=false;
    }
  //==========================================//
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(parent);
        mDialog.setMessage("Loading Puzzles .. Please Wait ");        
        mDialog.show();
    }
  //==========================================//
    @Override
    protected String doInBackground(Object... params) {
    	if(!started)
	    {
    		started=true;
	    	//===== Start The Choose Puzzle ======//
		    Intent in=new Intent(parent,ChoosePuzzle.class);
		    in.putExtra("level",level);
		    parent.startActivity(in);	
		    //===== Start The Choose Puzzle ======//
    	}

    	return "";
    }
  //==========================================//
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        parent.finish();
        mDialog.dismiss();
    }
}

