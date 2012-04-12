package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	
	private int defaultColor;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
        //should never occur
        if (username == null) {
        	finish();
        }
        
        this.setContentView(R.layout.platehome);
        
        defaultColor = ((Button)findViewById(R.id.tableCreateGameButton)).getTextColors().getDefaultColor();
        
        updateAllButtons();
        
        if(!IOBasic.getShownHelp(username, IOBasic.PlateGameHome)) { 
        	createDialog(WELCOME_MSG1).show();
        	IOBasic.setShownHelp(username, IOBasic.PlateGameHome);
        }
        
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateAllButtons();
	}
	
	/**
	 * Updates the text on all buttons to reflect the state of each game.
	 * This is one of three strings:
	 * 
	 * - Waiting for opponent
	 * - Game with opponent: Your turn
	 * - No game created (create below)
	 * 
	 * If the state is "your turn", the button is made clickable.  Otherwise,
	 * the button is not clickable.
	 * 
	 * If there are already three games in progress, the create button is disabled.
	 */
	private void updateAllButtons() {
		Button game1 = (Button)this.findViewById(R.id.tableGameButton1);
		Button game2 = (Button)this.findViewById(R.id.tableGameButton2);
		Button game3 = (Button)this.findViewById(R.id.tableGameButton3);
		Button create = (Button)this.findViewById(R.id.tableCreateGameButton);
		
		opponents = IOBasic.getOpponents(username);
        if(opponents == null) {
        	opponents = new ArrayList<String>(); 
        }
		
        game1.setClickable(false);
        game2.setClickable(false);
        game3.setClickable(false);
        create.setClickable(true);
        
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
			create.setClickable(false);
		}
	}
	
	private void updateButtonText(Button b, String opponent) {
		String state = IOBasic.getGameState(username, opponent);
		String display_name = IOBasic.fullName(opponent);
		
		// Null string should not occur: mark in red to designate bug
		if(state == null) {
			b.setText(this.getString(R.string.tableGameWith) + display_name + ": " + this.getString(R.string.tableError));
			b.setTextColor(Color.RED);
			b.setTypeface(Typeface.DEFAULT_BOLD);
			b.setClickable(true);
		}
		// Empty string means we are waiting for the opponent
		else if("".equals(state)) {
			b.setText(this.getString(R.string.tableGameWith) + display_name + this.getString(R.string.tableGameWaiting));
			b.setTextColor(defaultColor);
			b.setTypeface(Typeface.DEFAULT);
			b.setClickable(false);
		}
		// Otherwise, opponent is waiting for us
		else {
			b.setText(this.getString(R.string.tableGameWith) + display_name + this.getString(R.string.tableGameYourTurn));
			b.setTextColor(Color.GREEN);
			b.setTypeface(Typeface.DEFAULT_BOLD);
			b.setClickable(true);
		}
	}
	
	public void onButton1Click(View view) {
		startGameGuess(opponents.get(0));
	}
	
	public void onButton2Click(View view) {
		startGameGuess(opponents.get(1));
	}
	
	public void onButton3Click(View view) {
		startGameGuess(opponents.get(2));
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
	
	public static final int WELCOME_MSG1 = 0;
	public static final int WELCOME_MSG2 = 1;
	
	
	/**
	 * Creates an instance of the desired Dialog
	 * @param id ID number of the dialog to create
	 * @return a Dialog instance ready to be displayed
	 */
	public Dialog createDialog(int id) {
    	if(id == WELCOME_MSG1) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(getString(R.string.tableHomeInstructions1));
    		builder.setPositiveButton(R.string.nextButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.dismiss();
    				createDialog(WELCOME_MSG2).show();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == WELCOME_MSG2) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(getString(R.string.tableHomeInstructions2));
    		builder.setPositiveButton(R.string.startButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		return builder.create();
    	}
    	return null;
	}

}
