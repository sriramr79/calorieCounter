package edu.upenn.cis350;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity class to represent the calorie ranking (1-2-3) game.
 * @author Paul M. Gurniak
 * @version 1.0
 */

public class RankingGameActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);
    }
   
}
