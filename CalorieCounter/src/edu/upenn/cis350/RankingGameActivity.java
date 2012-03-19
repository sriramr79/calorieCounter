package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity class to represent the calorie ranking (1-2-3) game.
 * @author Paul M. Gurniak
 * @version 1.0
 */

public class RankingGameActivity extends Activity {
	
	private String username;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
        //should never occur
        if (username == null) {
        	finish();
        }
        
        setContentView(R.layout.ranking);
    }
    
    /**
     * Get method for the username of the current user
     * @return the String of the username for the current user
     */
    public String getUsername() {
    	return this.username;
    }
   
}
