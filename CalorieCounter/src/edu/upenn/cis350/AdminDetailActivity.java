package edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AdminDetailActivity extends Activity {


	ArrayList<String> names;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admindetail);
		
		populateFields(getIntent().getStringExtra(Constants.UNEXTRA));
		
	}
	
	private void populateFields(String username) {
		((TextView)findViewById(R.id.adminName)).setText(IOBasic.fullName(username));
		((TextView)findViewById(R.id.adminUSNField)).setText(username);
		((TextView)findViewById(R.id.adminScoreField)).setText(Integer.toString(IOBasic.getPoints(username)));
		((TextView)findViewById(R.id.adminLoginsField)).setText(Integer.toString(IOBasic.getGameAttempts(username, IOBasic.HomeScreen)));
		((TextView)findViewById(R.id.adminCountAttemptsField)).setText(Integer.toString(IOBasic.getGameAttempts(username, IOBasic.CalorieCounter)));
		((TextView)findViewById(R.id.adminRankingAttemptsField)).setText(Integer.toString(IOBasic.getGameAttempts(username, IOBasic.RankingGame)));
		((TextView)findViewById(R.id.adminORPAttemptsField)).setText(Integer.toString(IOBasic.getGameAttempts(username, IOBasic.OneRightPrice)));
		((TextView)findViewById(R.id.adminPlateAttemptsField)).setText(Integer.toString(IOBasic.getGameAttempts(username, IOBasic.PlateGameGuess)));
		((TextView)findViewById(R.id.adminCountField)).setText(Integer.toString(IOBasic.getGameWins(username, IOBasic.CalorieCounter)));
		((TextView)findViewById(R.id.adminRankingField)).setText(Integer.toString(IOBasic.getGameWins(username, IOBasic.RankingGame)));
		((TextView)findViewById(R.id.adminORPField)).setText(Integer.toString(IOBasic.getGameWins(username, IOBasic.OneRightPrice)));
		((TextView)findViewById(R.id.adminPlateField)).setText(Integer.toString(IOBasic.getGameWins(username, IOBasic.PlateGameGuess)));
	}

}
