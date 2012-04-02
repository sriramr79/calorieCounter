package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlateHomeActivity extends Activity {

	private String username;
	
	private List<String> opponents;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
        //should never occur
        if (username == null) {
        	finish();
        }
        
        this.setContentView(R.layout.platehome);
        
        updateButtonText();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateButtonText();
	}
	
	private void updateButtonText() {
		Button game1 = (Button)this.findViewById(R.id.tableGameButton1);
		Button game2 = (Button)this.findViewById(R.id.tableGameButton2);
		Button game3 = (Button)this.findViewById(R.id.tableGameButton3);
		
		opponents = IOBasic.getOpponents(username);
        if(opponents == null) {
        	opponents = new ArrayList<String>(); 
        }
		
        game1.setClickable(false);
        game2.setClickable(false);
        game3.setClickable(false);
        
		if(opponents.size() >= 1) {
			updateButtonText(game1, opponents.get(0));
			game1.setClickable(true);
		}
		if(opponents.size() >= 2) {
			updateButtonText(game2, opponents.get(1));
			game2.setClickable(true);
		}
		if(opponents.size() >= 3) {
			updateButtonText(game3, opponents.get(2));
			game3.setClickable(true);
		}
	}
	
	private void updateButtonText(Button b, String opponent) {
		String state = IOBasic.getGameState(username, opponent);
		if(state == null) {
			b.setText(this.getString(R.string.tableError));
		} else if("".equals(state)) {
			b.setText(this.getString(R.string.tableGameWith) + opponent + this.getString(R.string.tableGameWaiting));
		} else {
			b.setText(this.getString(R.string.tableGameWith) + opponent + this.getString(R.string.tableGameYourTurn));
			b.setTextColor(Color.GREEN);
			b.setTypeface(Typeface.DEFAULT_BOLD);
		}
	}
	
	public void onButton1Click(View view) {
		startGameGuess(opponents.get(0));
	}
	
	public void onNewGameClick(View view) {
		EditText input = (EditText)findViewById(R.id.tableNewGameUser);
		String name = input.getText().toString();
		if(IOBasic.password(name) == null) {
			Toast.makeText(this, "Error: that user doesn't exist!", Toast.LENGTH_LONG).show();
		} else if(IOBasic.getGameState(username, name) != null) {
			Toast.makeText(this, "Error: you already have a game with " + name, Toast.LENGTH_LONG);
		}
		else {
			startGameNew(name);
		}
	}
	
	public void onQuitButtonClick(View view) {
		this.finish();
	}
	
	private void startGameNew(String opponent) {
		Intent i = new Intent(this, PlateGameActivity.class);
		i.putExtra(Constants.UNEXTRA, username);
		i.putExtra(Constants.OPEXTRA, opponent);
		startActivity(i);
	}
	
	private void startGameGuess(String opponent) {
		Intent i = new Intent(this, PlateGameGuessActivity.class);
		i.putExtra(Constants.UNEXTRA, username);
		i.putExtra(Constants.GSEXTRA, IOBasic.getGameState(username, opponent));
		i.putExtra(Constants.OPEXTRA, opponent);
		startActivity(i);
	}
}
