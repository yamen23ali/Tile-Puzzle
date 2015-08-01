package myUtils;

import java.util.ResourceBundle.Control;

import puzzleCreator.Piece;
import puzzleCreator.ScrollPiece;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;

public class MyPieceTouchListener implements OnTouchListener {

	Piece caller;
	static int test=0;
	// ============ Constructor ============//
	public MyPieceTouchListener(Piece caller) {
		this.caller = caller;
	}

	// ============ Constructor ============//

	// =========== When Scroll Piece Is Dragged ========//
	@SuppressLint("NewApi")
	public boolean onTouch(View v, MotionEvent event) {
		if (caller.state == 2) 
		{
			Integer selectedPiece = Controller.originalSelectedMapping
					.get(caller.pieceNumber);
			if (event.getAction() == MotionEvent.ACTION_DOWN) 
			{
				test++;
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
				v.startDrag(data, shadowBuilder, v, 0);
				Controller.switchImage(selectedPiece);
				// ========= Remove The Current Piece From Placed Pieces =====//
				caller.setImageBitmap(caller.pictures.get("Empty"));
				Controller.selectedPieces.remove(selectedPiece);
				Controller.originalSelectedMapping.put(caller.pieceNumber, -1);
				// ========= Remove The Current Piece From Placed Pieces =====//
				return true;
			} 
			return false;
		} else {
			return false;
		}
	}
	// =========== When Scroll Piece Is Dragged ========//

}
