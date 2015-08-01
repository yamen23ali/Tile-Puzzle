package myUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.sax.StartElementListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yali.USAPuzzle.PuzzleBoard;
import com.yali.USAPuzzle.Rating;
import com.yali.USAPuzzle.R;

import puzzleCreator.*;

public class Controller {

	// =================================//
	private static int clickedScrollPiece;
	private static HashMap<Integer, ScrollPiece> scrollPieces;
	private static ArrayList<Integer> emptyPieces;
	private static Context context;
	private static Activity parent;
	private static Puzzle puzzle;
	private static LinearLayout firstRow;
	private static RelativeLayout container;
	private static Timer timer = new Timer();
	private static MyTimerTask timerTask;
	private static int missing;
	private static int level;
	private static int currentStage;
	private static String userName="";
	//=======================//
	public static HashMap<Integer, Integer> originalSelectedMapping;
	public static ArrayList<Integer> selectedPieces;
	public static int moves;
	public static int stage;
	public static int penalty;
	public static int wrongPieces;
	public static String showText;
	public static int placePieces;
	public static int resetPieces;
	public static String pauseText;
	//============ Images To Use When Puzzling ( Declared Here To Save Memory As much As We Could ) ====//
	public static Bitmap image;
	public static Bitmap imageEmpty;
	public static Bitmap maskH;
	public static Bitmap maskV;
	public static Bitmap resultH;
	public static Bitmap resultV;
	public static int wallPaperImageId;
	//============ Images To Use When Puzzling ( Declared Here To Save Memory As much As We Could ) ====//

	// ================ Initialize The Class Values =================//
	public Controller(Context context, Activity parent, int level,int currentStage) {
		scrollPieces = new HashMap<Integer, ScrollPiece>();
		emptyPieces = new ArrayList<Integer>();
		selectedPieces = new ArrayList<Integer>();
		originalSelectedMapping = new HashMap<Integer, Integer>();
		clickedScrollPiece = -1;
		moves = 0;
		this.context = context;
		this.parent = parent;
		Rating.parent=(PuzzleBoard) parent;
		this.currentStage=currentStage;
		this.level = level;
		penalty=0;
		wrongPieces=0;
	}

	// ================ Initialize The Class Values =================//

	// =========== Create The Puzzle ==============//
	public void createPuzzle(int width, int height, int imageId, int maskHId,
			int maskVId, int containerId) {
		
		wallPaperImageId=imageId;
		//======== Find Puzzle in Parent ========//
		container = (RelativeLayout) parent.findViewById(containerId);
		//======== Find Puzzle in Parent ========//
		MyImageResizer resizer = new MyImageResizer();
		float maskRatio;
		Bitmap temp;
		//=============================//
		
		//========== Fill The Puzzling Images =======//
		//========= Original Image ========//
		temp = BitmapFactory.decodeResource(context.getResources(),imageId);
		image=resizer.scaleBitMap(temp, width, height);
		imageEmpty=Bitmap.createBitmap(image.getWidth(), image.getHeight(),Config.ARGB_8888);
		//========= Original Image ========//
		
		//========= Empty Image ========//
		Paint myPaint = new Paint();
		myPaint.setColor(Color.argb(150,146,140,138));			
		//myPaint.setColor(Color.TRANSPARENT);
		myPaint.setStrokeWidth(1);
		Canvas canvas = new Canvas(imageEmpty);
		canvas.drawRect(0,0,imageEmpty.getWidth(),imageEmpty.getHeight(), myPaint);
		canvas=null;
		//========= Empty Image ========//
		
		//======= Horizontal Mask =====//
		temp=BitmapFactory.decodeResource(context.getResources(),maskHId);
		maskRatio=getResizingRatio(width,height,temp.getWidth(),temp.getHeight());
		maskH = resizer.scaleBitMap(temp,(int)((float)temp.getWidth()*maskRatio),(int)((float)temp.getHeight()*maskRatio));
		resultH = Bitmap.createBitmap(maskH.getWidth(),maskH.getHeight(),Config.ARGB_8888);
		//======= Horizontal Mask =====//
		
		//======= Vertical Mask =====//
		temp=BitmapFactory.decodeResource(context.getResources(),maskVId);
		maskV = resizer.scaleBitMap(temp,(int)((float)temp.getWidth()*maskRatio),(int)((float)temp.getHeight()*maskRatio));	
		resultV=Bitmap.createBitmap(maskV.getWidth(), maskV.getHeight(),Config.ARGB_8888);
		//======= Vertical Mask =====//
		
		temp.recycle(); temp=null;

		//========== Fill The Puzzling Images =======//
		
		//=========== Start Puzzling ========//
		puzzle = new Puzzle(context);

		calcMissing(puzzle.pieces.size());
		//=========== Start Puzzling ========//
		
		//======= Some Cleaning Up ====//
		image.recycle(); image=null;
		imageEmpty.recycle();imageEmpty=null;
		maskH.recycle(); maskH=null;
		maskV.recycle(); maskV=null;
		resultH.recycle(); resultH=null;
		resultV.recycle(); resultV=null;
		//======= Some Cleaning Up ====//
		
		System.gc();
	}

	// =========== Create The Puzzle ==============//

	// =========== Create The Scroll ==============//
	public void createScrollView(int maskId, int firstRowId) {
		Random rand = new Random();
		ArrayList picesNumbers = new ArrayList();

		// =========== Put All Pieces Numbers In List ======//
		for (int i = 0; i < puzzle.pieces.size(); i++) {
			picesNumbers.add(puzzle.pieces.get(i).pieceNumber);
		}
		// =========== Put All Pieces Numbers In List ======//

		// =========== Choose Random Numbers From The List And Put Them In
		// (emptyPieces) List ======//
		for (int i = 1; i <= missing; i++) {
			int index = rand.nextInt(picesNumbers.size());
			Integer value = (Integer) picesNumbers.get(index);
			emptyPieces.add(value);
			originalSelectedMapping.put(value, -1);
			picesNumbers.remove(index);
		}
		// =========== Choose Random Numbers From The List And Put Them In (emptyPieces) List ======//

		// ========== Change The State and Image Of each piece from
		// (emptyPeices) List ====//
		for (int i = 0; i < emptyPieces.size(); i++) {
			for (int j = 0; j < puzzle.pieces.size(); j++) {
				if ((int) emptyPieces.get(i) == puzzle.pieces.get(j).pieceNumber) {
					puzzle.pieces.get(j).switchState();
				}
			}
		}
		// ========== Change The State and Image Of each piece from (emptyPeices) List ====//
		
		//======= Find Scroll Pieces ======
		firstRow = (LinearLayout) parent.findViewById(firstRowId);
		//======= Find Scroll Pieces ======
	}

	// =========== Create The Scroll ==============//

	//======== To Add The Puzzle And Scroll To Parent View ======//
	public void addToParent()
	{
		//====== Add Puzzle To Parent =====//
		container.removeAllViews();
		container.addView(puzzle);
		//====== Add Puzzle To Parent =====//
		
		// ========== Add Scroll Pieces To Parent ======//
		constructScrollView();
		startTimer();
		//========== Add Scroll Pieces To Parent ======//
	
	}
	//======== To Add The Puzzle And Scroll To Parent View ======//
	
	// =========== Construct The Scroll View ============//
	public static void constructScrollView() 
	{
		//============= To Make Sure There Is No Cheating 
		//============  Resume The Game In Case Of Any Changes In The Scroll 
		if(timerTask!=null)
		{
			resumeGame();
		}
		 
		//========= To Add Some Randomization To Scroll Pieces ===//
		Random rand = new Random();
		ArrayList indexes = new ArrayList();
		for (int i = 0; i < puzzle.pieces.size(); i++) {
			indexes.add(i);
		}
		//========= To Add Some Randomization To Scroll Pieces ===//
	
		//======= Clear All The Children ========//
		firstRow.removeAllViews();
		//======= Clear All The Children ========//
		
		while(true) 
		{
			//========= Get The Next Piece Index (i) =====//
			int index = rand.nextInt(indexes.size());
			int i=(Integer)indexes.get(index);
			indexes.remove(index);
			//========= Get The Next Piece Index (i) =====//
			//======== CHeck If This Piece Belong To Scroll =====//
			if (emptyPieces.contains(puzzle.pieces.get(i).pieceNumber)
					&& !selectedPieces
							.contains(puzzle.pieces.get(i).pieceNumber)) {
				ScrollPiece sp = new ScrollPiece(context,
						puzzle.pieces.get(i).pictures.get("Main"),
						puzzle.pieces.get(i).pieceNumber,
						puzzle.pieces.get(i).type);
				scrollPieces.put(puzzle.pieces.get(i).pieceNumber, sp);
				
				firstRow.addView(scrollPieces.get(puzzle.pieces.get(i).pieceNumber));
			}
			//======== CHeck If This Piece Belong To Scroll =====//
			
			if(indexes.size()==0) break;
		}

	}

	// =========== Construct The Scroll View ============//

	// ============== Change Image For ( piece ) ========//
	public static void switchImage(Piece caller) {
		//======== Go Back For Switching Mode  =====//
		wrongPieces=0;
		//======== Go Back For Switching Mode  =====//
		
		if (clickedScrollPiece != -1) 
		{
			ScrollPiece sp = scrollPieces.get(clickedScrollPiece);
			if (sp.type == caller.type) 
			{
				Bitmap picture = ((BitmapDrawable) sp.content.getDrawable())
						.getBitmap();
				caller.setImageBitmap(picture);

				selectedPieces.add(clickedScrollPiece);

				if (originalSelectedMapping.get(caller.pieceNumber) != -1) 
				{
					selectedPieces.remove(originalSelectedMapping
							.get(caller.pieceNumber));
				}
				originalSelectedMapping.put(caller.pieceNumber,
						clickedScrollPiece);

				//constructScrollView();

				clickedScrollPiece = -1;

				// ======= Change Moves Number ======//
				moves++;
				// ======= Change Moves Number ======//
			}

		}
		else 
		{
			if (originalSelectedMapping.get(caller.pieceNumber) != -1) 
			{
				caller.setImageBitmap(caller.pictures.get("Empty"));
				selectedPieces.remove(originalSelectedMapping.get(caller.pieceNumber));
				originalSelectedMapping.put(caller.pieceNumber, -1);
				//constructScrollView();
			}
		}

		// ======= When User Put All Pieces From Scroll In The Puzzle Check For
		// The Result ======//
		if (selectedPieces.size() == scrollPieces.size()) 
		{
			    checkResult();
		}
		// ======= When User Put All Pieces From Scroll In The Puzzle Check For
		// The Result ======//
	}

	// ============== Change Image For ( piece ) ========//

	// ========= Change Image For ( scrollPiece ) ==========//
	public static void switchImage(ScrollPiece caller) {
		if (clickedScrollPiece != -1) {
			scrollPieces.get(clickedScrollPiece).deSelect();
		}
		clickedScrollPiece = caller.pieceNumber;
		caller.setBackgroundColor(Color.parseColor("#77ffa500"));
	}
	// ========= Change Image For ( scrollPiece ) ==========//
	
	// ========= Change Image For ( scrollPiece ) In D&&D State ==========//
	public static void switchImage(int callerPieceNumber) {
		clickedScrollPiece = callerPieceNumber;
	}
	// ========= Change Image For ( scrollPiece ) In D&&D State ==========//

	// ========== DeterMine How Many Missing Pieces Depend on Level =========//
	private void calcMissing(int puzzleSize) {
		int lowBound = 0, highBound = 0;
		if (level == 1) {
			lowBound = (puzzleSize * 20) / 100;
			highBound = (puzzleSize * 30) / 100;
			//lowBound = 1;
			//highBound = 2;
		} else if (level == 2) {
			lowBound = (puzzleSize * 50) / 100;
			highBound = (puzzleSize * 60) / 100;
		} else if (level == 3) {
			lowBound = (puzzleSize * 75) / 100;
			highBound = (puzzleSize * 80) / 100;
		} else if (level == 4) {
			lowBound = (puzzleSize * 75) / 100;
			highBound = (puzzleSize * 80) / 100;
		}
		// ====== Missing Is between Low and High ====//
		Random rand = new Random();
		missing = rand.nextInt((highBound - lowBound)) + lowBound;
		// ====== Missing Is between Low and High ====//
	}

	// ========== DeterMine How Many Missing Pieces Depend on Level =========//

	// ============= Start The Timer =============//
	private void startTimer() {
		timer = new Timer();
		timerTask = new MyTimerTask(parent,level);
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
	}

	// ============= Start The Timer =============//
	
	//============= Pause Game =============//
	public static void pauseGame() {
		timerTask.paused=true;
		pauseText="Resume";
	}
	//============= Pause Game =============//
	
	//============= Resume Game =============//
	public static void resumeGame() {
		timerTask.paused=false;
		pauseText="Pause";
	}
	//============= Resume Game =============//

	// =========== Check The Result ============//
	private static void checkResult() {
		int wrongPiecesNum=0;
		Object[] keys = originalSelectedMapping.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			int original = (Integer) keys[i];
			int selected = originalSelectedMapping.get(original);
			if (original != selected)
				wrongPiecesNum++;
		}
		//==============//
		if(wrongPiecesNum>0)
		{
			//======== To Stop Switching Mode ======//
			wrongPieces=wrongPiecesNum;
			//======== To Stop Switching Mode ======//
		}
		else
		{
			timer.cancel();
			Intent in = new Intent(parent, Rating.class);
			in.putExtra("number", rateScore());
			in.putExtra("level", level);
			parent.startActivity(in);
		}
	}

	// =========== Check The Result ============//

	// =========== Rate The Game ===========//
	private static int rateScore() {
		int movesScore = (missing * 100) / moves;

		int seconds = (int) timerTask.getElapsedTime();
		int timeScore = (300 * level * 100) / seconds;
		if (timeScore > 100)
			timeScore = 100;

		return ((movesScore + timeScore) / 2)+penalty;
	}

	// =========== Rate The Game ===========//

	// ========== Clear All Things From Memory =======//
	public void clear() {
		if (puzzle != null) 
		{
			puzzle.clear();
			container.removeAllViews();
			timer.cancel();
			timerTask.cancel();
		}
		// ===========================//
		if (scrollPieces != null) 
		{
			for (ScrollPiece value : scrollPieces.values()) 
			{
				value.clear();
			}
			scrollPieces.clear();
			firstRow.removeAllViews();		
		}

	}
	// ========== Clear All Things From Memory =======//
	
	//============ Set User Name ==========//
	public static void setUserName(String user)
	{
		userName=user;
	}
	//============ Set User Name ==========//
	//============ Get User Name ==========//
	public static String getUserName()
	{
		return userName;
	}
	//============ Get User Name ==========//
	
	//========= Resize The Mask ======//
	float getResizingRatio(int imageWidth,int imageHeight,int maskWidth,int maskHeight)
	{
		//======== Get The Allowed Number Of Pieces For This Level =====//
		int lowBound,heighBound; //===block based
		if(level==1 || level==2)
		{
			lowBound=4; heighBound=5;
		}
		else if(level==3)
		{
			lowBound=6; heighBound=7;
		}
		else
		{
			lowBound=8; heighBound=9;
		}
		//======== Get The Allowed Number Of Pieces For This Level =====//
		
		//======== Calc Ratio ==========//
		float blockSize=maskWidth+((0.33f)*maskHeight);
		float numOfBlocks=((float)imageWidth/blockSize);
		float ratio=1;
	
		if(numOfBlocks>heighBound || numOfBlocks<lowBound) 
			ratio=(float)numOfBlocks/(float)heighBound;
		//======== Calc Ratio ==========//	
		return ratio;
	}
	//========= Resize The Mask ======//
	
	//=======  Help Player With Wrong Pieces =======//
	public static void resetWrongPieces()
	{
		penalty-=2;
		Object[] keys = originalSelectedMapping.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			int original = (Integer) keys[i];
			int selected = originalSelectedMapping.get(original);
			if (original != selected)
			{
				for(int j=0;j<puzzle.pieces.size();j++)
				{
					Piece piece=puzzle.pieces.get(j);
					if(puzzle.pieces.get(j).pieceNumber==original)
					{
						//===== Switch The Image To Empty ====//
						piece.setImageBitmap(piece.pictures.get("Empty"));
						selectedPieces.remove(originalSelectedMapping.get(piece.pieceNumber));
						originalSelectedMapping.put(piece.pieceNumber, -1);
						//===== Switch The Image To Empty ====//
						break;
					}
				}
				
			}
		}
		constructScrollView();
	}
	//======= Help Player With Wrong Pieces =======//
	
	//======= Help Player With And Place Some Pieces =======//
	public static void placePiece(int number)
	{
		penalty-=3;
		Object[] keys = originalSelectedMapping.keySet().toArray();
		int counter=0;
		for (int i = 0; i < keys.length; i++) 
		{
			int original = (Integer) keys[i];
			int selected = originalSelectedMapping.get(original);
			if (selected!=original)
			{
				//======= Remove The Piece From Other Places====//
				for (int k = 0; k < keys.length; k++) 
				{
					if(originalSelectedMapping.get((Integer) keys[k])==original)
					{
						originalSelectedMapping.put((Integer) keys[k],-1);
						selectedPieces.remove(new Integer(original));
						for(int j=0;j<puzzle.pieces.size();j++)
						{
							Piece piece=puzzle.pieces.get(j);
							if(puzzle.pieces.get(j).pieceNumber==(Integer)keys[k])
							{
								piece.setImageBitmap(piece.pictures.get("Empty"));
								break;
							}
						}
						break;
					}
				}
			    //======= Remove The Piece From Other Places====//
				
				//======= Add The Correct Piece ====//
				for(int j=0;j<puzzle.pieces.size();j++)
				{
					Piece piece=puzzle.pieces.get(j);
					if(puzzle.pieces.get(j).pieceNumber==original)
					{
						//===== Switch The Image To Main ====//
						piece.setImageBitmap(piece.pictures.get("Main"));
						selectedPieces.add(original);
						if (selected != -1) 
						{
							selectedPieces.remove(new Integer(selected));
						}
						originalSelectedMapping.put(piece.pieceNumber,piece.pieceNumber);

						// ======= Change Moves Number ======//
						moves++;
						// ======= Change Moves Number ======//
						
						break;
						//===== Switch The Image To Main ====//
					}
				}
				//======= Add The Correct Piece ====//
				counter++;
			}
			if(counter==number) break;
		}
		constructScrollView();
		//========== Check For Result If All Pieces Are Placed======//
		if (selectedPieces.size() == scrollPieces.size()) {
			checkResult();
		}
		//========== Check For Result If All Pieces Are Placed======//
	}
	//======= Help Player With And Place A Piece =======//
	
}
