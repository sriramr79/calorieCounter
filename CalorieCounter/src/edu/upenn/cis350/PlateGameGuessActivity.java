package edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class PlateGameGuessActivity extends Activity {

	private String username;
	private ArrayList<FoodItem> tableFoods;
	private FoodGenerator fg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
        fg = new FoodGenerator(getResources());
        String stateString = getIntent().getStringExtra(Constants.GSEXTRA);
        tableFoods = fg.restoreState(stateString);
        
	}
	
	
}
