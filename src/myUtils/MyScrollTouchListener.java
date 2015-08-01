package myUtils;

import puzzleCreator.Piece;
import puzzleCreator.ScrollPiece;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;

public class MyScrollTouchListener implements  OnTouchListener {

	ScrollPiece caller;
	//============ Constructor ============//
	public MyScrollTouchListener(ScrollPiece caller)
	{
		this.caller=caller;
	}
	//============ Constructor ============//
	
	//=========== When Scroll Piece Is Dragged ========//
	@SuppressLint("NewApi")
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) 
		{
			ClipData data = ClipData.newPlainText("","");
		    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
		    v.startDrag(data, shadowBuilder, v, 0);
		    //v.setVisibility(View.INVISIBLE);
		    Controller.switchImage(caller.pieceNumber);
		    return true;
		} 
		else {
			return false;
		}
	}	
	//=========== When Scroll Piece Is Dragged ========//

}
