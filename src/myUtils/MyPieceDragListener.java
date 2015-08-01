package myUtils;

import puzzleCreator.Piece;
import android.annotation.SuppressLint;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;

public class MyPieceDragListener implements OnDragListener {

	Piece caller;
	//============ Constructor ============//
	@SuppressLint("NewApi")
	public MyPieceDragListener(Piece caller)
	{
		this.caller=caller;
		caller.setOnDragListener(this);
	}
	//============ Constructor ============//
	
	// ============== On Image Dragged Over the piece ============//
	@SuppressLint("NewApi")
	@Override
	public boolean onDrag(View v, DragEvent event) {
		int action = event.getAction();
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED:
			break;
		case DragEvent.ACTION_DRAG_ENTERED:
			break;
		case DragEvent.ACTION_DRAG_EXITED:
			break;
		case DragEvent.ACTION_DROP:
			if (caller.state == 2) {
				Controller.switchImage(caller);
				Controller.constructScrollView();
			}
			break;
		case DragEvent.ACTION_DRAG_ENDED:
		default:
			break;
		}
		return true;

	}
	// ============== On Image Dragged Over the piece ============//

}
