package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Activity for the game where you put a bunch of foods on a plate.
 * 
 * Someone can think of a better name for this...
 * 
 * @author Paul M. Gurniak
 * @date 3/28/12
 * @version 0.1
 *
 */
public class PlateGameActivity extends Activity {

	private String username;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
		
        //should never occur
        if (username == null) {
        	finish();
        }
        
        this.setContentView(R.layout.plate);
	}
	
	public void onFoodButtonClick(View view) {
		ImageView clickedImage = (ImageView)view;
		int clickedId = clickedImage.getId();
		
		if(clickedId == R.id.scrollFoodButton1) {
			Toast.makeText(this, "Test press button 1", Toast.LENGTH_LONG);
		}
		
	}
	
	public void onQuitButtonClick(View view) {
		this.finish();
	}
	
	
	
	
}
