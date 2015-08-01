package com.yali.USAPuzzle;


import com.yali.USAPuzzle.R;

import myUtils.Controller;
import myUtils.MyAlertBox;
import myUtils.MyBitmapDecoder;
import myUtils.MyGridView;
import myUtils.MyImageResizer;
import myUtils.MyProgressBarMenu;
import myUtils.StatesInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ChoosePuzzle extends Activity {

	//======================//
	Bitmap[] images;
	Bitmap hiddenBitMap;
	StatesInfo[] states;
	int stage=0;
	int level;
	int statesNumber=50;
	int wantedWidth,wantedHeight;
	MyGridView gridview;
	float ratings[];	
	private SQLiteDatabase db;
	
	ScrollView container;
	//======================//
	
	//===============================================//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.choose_puzzle);
		//==================================================//
		
		//============== Call Dialog ADS ==========//
		Intent adsDiag=new Intent(this,MyADSDialogBox.class);
		startActivity(adsDiag);
		//============== Call Dialog ADS ==========//
		level= this.getIntent().getExtras().getInt("level");
		stage=Controller.stage; 
		//stage=50;
		//======= Fill Data In Arrays =====//
		fillRatings();
		fillData();
		//======= Fill Data In Arrays =====//
		
		//======= Build It =====//
		buildPuzzleMenu();
		//======= Build It =====//
 	}
	//===============================================//
	
	//============== Build Puzzle Menu ===============//
	void buildPuzzleMenu()
	{
		//=========== Get Screen Dimension =========//
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		//=========== Get Screen Dimension =========//
		
		//======== How Much The Image Take From Screen ======//
		int widthRatio=2,heightRatio=4;
		widthRatio=3;heightRatio=2;
		//======== How Much The Image Take From Screen ======//
		
		//======== Fill The images Array ========//
		wantedWidth=(width/widthRatio)-(width/10);
		wantedHeight=(height/heightRatio);
		fillImages();	
		//======== Fill The images Array ========//
		
		//============ Put Images In GridView ========//
		gridview=new MyGridView(getApplicationContext(),this,widthRatio,level,wantedWidth);
		gridview.generateGridView(images,states,R.layout.cell_layout,stage);
		
		container=(ScrollView) findViewById(R.id.gridContainer);
		container.addView(gridview);
		//============ Put Images In GridView ========//
	}
	//============== Build Puzzle Menu ===============//
	
	//============== Fill Puzzle Image Array ===============//
	void fillImages()
	{
		images =new Bitmap[statesNumber];
		MyBitmapDecoder mbd=new MyBitmapDecoder();
		MyImageResizer rI=new MyImageResizer();
		hiddenBitMap=mbd.decodeSampledBitmapFromResource(getResources(),R.drawable.hidden_puzzle,wantedWidth,wantedHeight);
		//============================//
		for(int i=0;i<statesNumber;i++)
		{
			//========= open just the user stages ======//
			if(i>=stage)
			{
				images[i]=rI.resizeIt(hiddenBitMap,wantedWidth,wantedHeight);
				break;
			}
			else
			{
				images[i]=rI.resizeIt(
						mbd.decodeSampledBitmapFromResource(getResources(),states[i].imageId,wantedWidth,wantedHeight),
						wantedWidth,
						wantedHeight
						);
			}
			
			//========= open just the user stages ======//
		}
	}
	//============== Fill Puzzle Image Array ===============//
	
	
	//======== Fill The Id's , Names , Places =========//
	private void fillData()
	{
		states=new StatesInfo[50];
		//=====A
		states[0]=new StatesInfo(R.drawable.alabama_usa_space_and_rocket_center,"Alabama","USA Space And Rocket Center",ratings[1],"Daderot");
		
		states[1]=new StatesInfo(R.drawable.alaska_iditarod_trail_race,"Alaska","Iditarod Trail Race",ratings[2],"Frank Kovalchek");
		
		states[2]=new StatesInfo(R.drawable.arizona_horseshoe_bend,"Arizona","Horseshoe Bend",ratings[3],"Paul Hermans");
		
		states[3]=new StatesInfo(R.drawable.arkansas_white_river,"Arkansas","White River",ratings[4],"Linda Tanner");
		
		//=====C
		states[4]=new StatesInfo(R.drawable.california_hollywood,"California","Hollywood",ratings[5],"");
		
		states[5]=new StatesInfo(R.drawable.colorado_rocky_mtn_church,"Colorado","Rocky MTN Church",ratings[6],"");
		
		states[6]=new StatesInfo(R.drawable.connecticut_mark_twain_house,"Connecticut","Mark Twain House",ratings[7],"Kenneth C. Zirkel");
		
		//=====D
		states[7]=new StatesInfo(R.drawable.delaware_wilmington_furness_station,"Delaware","Wilmington Furness Station",ratings[8],"");
		
		//=====F
		states[8]=new StatesInfo(R.drawable.florida_miami_beach,"Florida","Miami Beach",ratings[9],"Chensiyuan");
		
		//=====G
		states[9]=new StatesInfo(R.drawable.georgia_cnn_center,"Georgia","CNN Center",ratings[10],"Connor.carey");
		
		//=====H
		states[10]=new StatesInfo(R.drawable.hawaii_hanauma_bay_beach,"Hawaii","Hanauma Bay Beach",ratings[11],"Cristo Vlahos");
		
		//=====i
		states[11]=new StatesInfo(R.drawable.idaho_shoshone_falls,"Idaho","Shoshone Falls",ratings[12],"Karthikc123");
		
		states[12]=new StatesInfo(R.drawable.illinois_the_bean,"Illinois","The Bean",ratings[13],"Serge Melki");
		
		states[13]=new StatesInfo(R.drawable.indiana_indianapolis_motor_speedway,"Indiana","Indianapolis Motor Speedway",ratings[14],"Curtis Palmer");
		
		states[14]=new StatesInfo(R.drawable.iowa_capitol,"Iowa","Iowa Capitol",ratings[15],"w:User:Cburnett");
		
		//=====k
		states[15]=new StatesInfo(R.drawable.kansas_rock_city,"Kansas","Rock City",ratings[16],"Nationalparks");
		
		states[16]=new StatesInfo(R.drawable.kentucky_churchill_downs,"Kentucky","Churchill Downs",ratings[17],"Jarrett Campbell");
		
		//=====L
		states[17]=new StatesInfo(R.drawable.louisiana_mardi_gras_tour,"Louisiana","Mardi Gras Tour",ratings[18],"Catherine Roche-Wallace");
		
		//=====M
		states[18]=new StatesInfo(R.drawable.maine_portland_head_lighthouse,"Maine","Portland Head Lighthouse",ratings[19],"Rapidfire");
		
		states[19]=new StatesInfo(R.drawable.maryland_national_aquarium,"Mayrland","National Aquarium",ratings[20],"Fritz Geller-Grimm");
		
		states[20]=new StatesInfo(R.drawable.massachusetts_harvard_university,"Massachusetts","Harvard University",ratings[21],"");
		
		states[21]=new StatesInfo(R.drawable.michigan_detroit_institute_of_arts,"Michigan","Detroit Institute Of Arts",ratings[22],"Michael Barera");
		
		states[22]=new StatesInfo(R.drawable.minnesota_blue_line,"Minnesota","Blue Line",ratings[23],"Sinn");
		
		states[23]=new StatesInfo(R.drawable.mississippi_vicksburg_national_military_park,"Mississippi","Vicksburg National Military Park",ratings[24],"");
		
		states[24]=new StatesInfo(R.drawable.missouri_titanic_museum,"Missouri","Titanic Museum",ratings[25],"");
		
		states[25]=new StatesInfo(R.drawable.montana_yellow_stone,"Montana","Yellow Stone",ratings[26],"");
		
		//=====N
		states[26]=new StatesInfo(R.drawable.nebraska_sandhill_cranes,"Nebraska","Sandhill Cranes",ratings[27],"");

		states[27]=new StatesInfo(R.drawable.nevada_las_vegas,"Nevada","Las Vegas",ratings[28],"Juan David Ruiz");
		
		states[28]=new StatesInfo(R.drawable.newhampshire_fishercats,"New Hampshire","Fisher Cats",ratings[29],"MoiraMoira");
		
		states[29]=new StatesInfo(R.drawable.newjersey_great_falls,"New Jersey","Great Falls",ratings[30],"Merle9999");
		
		states[30]=new StatesInfo(R.drawable.newmexico_st_francis_cathedral,"New Mexico","Santa Fe",ratings[31],"Asaavedra32");
		
		states[31]=new StatesInfo(R.drawable.newyork_times_square,"New York","Ttimes Square",ratings[32],"Jean-Christophe BENOIST");
		
		states[32]=new StatesInfo(R.drawable.northcarolina_biltmore_estate,"North Carolina","Biltmore Estate",ratings[33],"DDima");
		
		states[33]=new StatesInfo(R.drawable.northdakota_lutheran_church,"North Dakota","Lutheran Church",ratings[34],"Andrew Filer");
		
		//=====O
		states[34]=new StatesInfo(R.drawable.ohio_lake_erie,"Ohio","Lake Erie",ratings[35],"Kevin Payravi");
		
		states[35]=new StatesInfo(R.drawable.oklahoma_automobile_alley,"Oklahoma","Automobile Alley",ratings[36],"katsrcool");
		
		states[36]=new StatesInfo(R.drawable.oregon_shakespeare_festival,"Oregon","Shakespeare Festival",ratings[37],"T. Charles Erickson");
		
		//=====P
		states[37]=new StatesInfo(R.drawable.pennsylvania_independence_hall,"Pennsylvania","Independence Hall",ratings[38],"Rdsmith4");
		
		//=====R
		states[38]=new StatesInfo(R.drawable.rhode_island_warwick_trail_truss_bridge,"Rhode Island","Warwick Trail Truss Bridge",ratings[39],"Pi.1415926535");
		
		//=====S
		states[39]=new StatesInfo(R.drawable.southcarolina_brookgreen_gardens,"South Carolina","Brookgreen Gardens",ratings[40],"Doug Coldwell");
		
		states[40]=new StatesInfo(R.drawable.southdakota_reptile_gardens,"South Dakota","Reptile Gardens",ratings[41],"Tbennert");
		
		//=====T
		states[41]=new StatesInfo(R.drawable.tennessee_david_crockett_state_park,"Tennessee","David Crockett State Park",ratings[42],"Wdwic Pictures");
		
		states[42]=new StatesInfo(R.drawable.texas_paisano_pete_statue,"Texas","Paisano Pete Statue",ratings[43],"");
		
		//=====U
		states[43]=new StatesInfo(R.drawable.utah_temple_square,"Utah","Temple Square",ratings[44],"Leon7");
		
		//=====v
		states[44]=new StatesInfo(R.drawable.vermont_richardson_building,"Vermont","Richardson Building",ratings[45],"Beyond My Ken");
		
		states[45]=new StatesInfo(R.drawable.virginia_pony_swim,"Virginia","Pony Swim",ratings[46],"");
		
		//=====w
		states[46]=new StatesInfo(R.drawable.washington_kobe_bell,"Washington","Kobe Bell",ratings[47],"Joe Mabel");
		
		states[47]=new StatesInfo(R.drawable.westvirginia_harpers_ferry,"West Virginia","Harpers Ferry",ratings[48],"");
		
		states[48]=new StatesInfo(R.drawable.wisconsin_little_white_schoolhouse,"Wisconsin","Little White School House",ratings[49],"Royalbroil");

		states[49]=new StatesInfo(R.drawable.wyoming_beehive_geyser,"Wyoming","Beehive Geyser",ratings[50],"");
	}
	//======== Fill The Id's , Names , Places =========//
		

	//============= On Back Click =============//
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		    //Handle the back button
		    if(keyCode == KeyEvent.KEYCODE_BACK) {
		    	this.finish();
		    }
		    return true;
	}
	//============= On Back Click =============//
	
	//=========== What To Do When This Activity Is Destroyed =======//
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clear();
	}
	//=========== What To Do When This Activity Is Destroyed =======//
	
	//=========== Clear Data ==========//
	public void clear()
	{
    	gridview.removeAllViews();
    	container.removeAllViews();
		for(int i=0;i<images.length;i++)
    	{
			if(i>stage) break;
    		images[i].recycle();
    		images[i]=null;
    	}
    	hiddenBitMap.recycle();
    	hiddenBitMap=null;
    	System.gc();
	}
	//=========== Clear Data ==========//

	//================= To Fill The Ratings Matrix From DB ==========//
	public void fillRatings()
	{
		String sqlCommand="";
		String name=Controller.getUserName();
		//====== Initialize ======//
		ratings=new float[51];
		for(int i=0;i<51;i++)
		{
			ratings[i]=0.0f;
		}
		//====== Initialize ======//
		try{
			//====== Open And Create Table ======//
			db = openOrCreateDatabase("PuzzleDataBase",MODE_PRIVATE, null);
			sqlCommand="CREATE TABLE IF NOT EXISTS UserRating (Stage INT(3),Rating VARCHAR,User VARCHAR );";
			db.execSQL(sqlCommand);
			//====== Open And Create Table ======//
	
			//======= Get Values From DB =======//
			sqlCommand="SELECT * FROM UserRating WHERE User='"+name+"';";
			Cursor iterator = db.rawQuery(sqlCommand, null);
			int stageIndex = iterator.getColumnIndex("Stage");
			int ratingIndex = iterator.getColumnIndex("Rating");
			while (iterator.moveToNext()) 
			{
				ratings[iterator.getInt(stageIndex)]=Float.parseFloat(iterator.getString(ratingIndex));
			}
			//======= Get Values From DB =======//
			iterator.close();
			db.close();
		}
		catch(Exception e)
		{
			MyAlertBox mab=new MyAlertBox(this,"5: " + e.getMessage());
		}
	}
	//================= To Fill The Ratings Matrix From DB ==========//
}
