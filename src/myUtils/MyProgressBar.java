package myUtils;

import java.util.HashMap;

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

public class MyProgressBar extends AsyncTask<Object, Void, String> {
    
	ProgressDialog mDialog;
	HashMap<String,Integer> variables;
	Controller controller;
	Activity parent;
	Context context;
	
	//==========================================//
    public MyProgressBar(Context context,Activity parent,Controller controller,HashMap<String,Integer> variables) {
        this.variables=variables;
        this.parent=parent;
        this.context=context;
        this.controller=controller;
    }
  //==========================================//
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(parent);
        mDialog.setMessage("Puzzling The Image .. Please Wait ");
        mDialog.show();
    }
  //==========================================//
    @Override
    protected String doInBackground(Object... params) {

    	controller.createPuzzle(
						variables.get("width"),
						variables.get("height"),
						variables.get("puzzle"),
						variables.get("maskHId"),
						variables.get("maskVId"), 
						R.id.puzzleContainer
						);
				
		controller.createScrollView(variables.get("maskVId"), R.id.firstRowScroll);
    	
    	return "";
    }
  //==========================================//
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mDialog.dismiss();
        
        parent.runOnUiThread(new Runnable() {
			
        	//======== To Enable Updating The UI From AsyncTask =========//
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				//===== Add Generated Puzzle and Pieces To UI =====//
				controller.addToParent();
				//===== Add Generated Puzzle and Pieces To UI =====//
				
				//======= Hide The Image and Show The Puzzle =========//
				LinearLayout ll=(LinearLayout) parent.findViewById(R.id.startButtonParent);
				ll.setVisibility(View.GONE);
				
				RelativeLayout originalImage=(RelativeLayout) parent.findViewById(R.id.originalImageContainer);
				originalImage.setVisibility(View.GONE);
				
				LinearLayout hsv=(LinearLayout) parent.findViewById(R.id.accPuzzleBoard);
				hsv.setVisibility(View.VISIBLE);
				
				RelativeLayout puzzle=(RelativeLayout) parent.findViewById(R.id.puzzleContainer);
				puzzle.setVisibility(View.VISIBLE);
				
				//======= Hide The Image and Show The Puzzle =========//
				
			}
		});
      //======== To Enable Updating The UI From AsyncTask =========//
    
    }
}

