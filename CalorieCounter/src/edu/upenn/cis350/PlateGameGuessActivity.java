package edu.upenn.cis350;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PlateGameGuessActivity extends Activity {

	private String username;
	private String opponent;
	private ArrayList<FoodItem> tableFoods;
	private FoodGenerator fg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
        this.opponent = getIntent().getStringExtra(Constants.UNEXTRA);
        String stateString = getIntent().getStringExtra(Constants.GSEXTRA);
        
        // Shouldn't happen
        if(username == null || opponent == null || stateString == null) {
        	Toast.makeText(this, "Error passing String Extra!", Toast.LENGTH_LONG).show();
        	this.finish();
        }

        fg = new FoodGenerator(getResources());
        tableFoods = fg.restoreState(stateString);
        
        this.setContentView(R.layout.plateguess);
        
        if(tableFoods.size() != 3) {
        	Toast.makeText(this, "Error: invalid restore game state: " + stateString, Toast.LENGTH_LONG).show();
        	finish();
        }
        if(username == null) {
        	Toast.makeText(this, "Error: username was null", Toast.LENGTH_LONG).show();
        	finish();
        }
        
        updateDisplayedFoods();
        
	}
	
	public void updateDisplayedFoods() {
		ImageView tableFood1 = (ImageView)this.findViewById(R.id.tableFoodButton1);
		ImageView tableFood2 = (ImageView)this.findViewById(R.id.tableFoodButton2);
		ImageView tableFood3 = (ImageView)this.findViewById(R.id.tableFoodButton3);
		tableFood1.setImageDrawable(tableFoods.get(0).getImage());
		tableFood2.setImageDrawable(tableFoods.get(1).getImage());
		tableFood3.setImageDrawable(tableFoods.get(2).getImage());
	}
	
	private int[] getUserGuess() {
		EditText guess1 = (EditText)this.findViewById(R.id.food1Guess);
		EditText guess2 = (EditText)this.findViewById(R.id.food2Guess);
		EditText guess3 = (EditText)this.findViewById(R.id.food3Guess);
		int [] count = new int[3];
		try {
			count[0] += Integer.parseInt(guess1.getText().toString());
			count[1] += Integer.parseInt(guess2.getText().toString());
			count[2] += Integer.parseInt(guess3.getText().toString());
		} catch (NumberFormatException e) {
			return null;
		}
		return count;
	}
	
	private int pointsWon = 0;
	
	public void onSubmitButtonClick(View view) {
		int [] guess = getUserGuess();
		if(guess == null || guess[0] < 0 || guess[1] < 0 || guess[2] < 0) {
			this.createDialog(SUBMIT_FAIL).show();
		}
		else {
			int points = 0;
			points += getPointsForInput(0, guess[0]);
			points += getPointsForInput(1, guess[1]);
			points += getPointsForInput(2, guess[2]);
			pointsWon = points;
			this.createDialog(SUBMIT_OKAY).show();
		}
		
	}
	
	private int getPointsForInput(int foodNum, int guess) {
		if(foodNum < 0 || foodNum >= 3) {
			return -1;
		}
	
		FoodItem.AnswerType eval = tableFoods.get(foodNum).checkGuess(guess);
		EditText guessBox = (foodNum == 0) ? (EditText)this.findViewById(R.id.food1Guess) :
							(foodNum == 1) ? (EditText)this.findViewById(R.id.food2Guess) :
							(EditText)this.findViewById(R.id.food3Guess);
		if(eval == FoodItem.AnswerType.CORRECT) {
			guessBox.setTextColor(Color.GREEN);
			return 2;
		} else if(eval == FoodItem.AnswerType.CLOSEHIGH || eval == FoodItem.AnswerType.CLOSELOW) {
			guessBox.setTextColor(Color.YELLOW);
			return 1;
		} else {
			guessBox.setTextColor(Color.RED);
			return 0;
		}
	}
	
	public void onQuitButtonClick(View view) {
		this.finish();
	}
	
	public void onFood1Click(View view) {
		this.createDialog(FOOD1_INFO).show();
	}
	
	public void onFood2Click(View view) {
		this.createDialog(FOOD2_INFO).show();
	}
	
	public void onFood3Click(View view) {
		this.createDialog(FOOD3_INFO).show();
	}
	
	private void passToGuessActivity() {
		Intent i = new Intent(this, PlateGameActivity.class);
		i.putExtra(Constants.UNEXTRA, username);
		i.putExtra(Constants.OPEXTRA, opponent);
		startActivity(i);
		finish();
	}
	
	public static final int SUBMIT_OKAY = 1;
	public static final int SUBMIT_FAIL= 2;
	public static final int FOOD1_INFO = 3;
	public static final int FOOD2_INFO = 4;
	public static final int FOOD3_INFO = 5;
	
	/**
	 * Creates an instance of the desired Dialog
	 * @param id ID number of the dialog to create
	 * @return a Dialog instance ready to be displayed
	 */
	public Dialog createDialog(int id) {
    	if(id == SUBMIT_OKAY) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		String message = getResources().getString(R.string.tableGuessOkay1) + Integer.toString(pointsWon) 
    					+ getResources().getString(R.string.tableGuessOkay2) + opponent;
    		builder.setMessage(message);
    		builder.setPositiveButton(R.string.ok,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.dismiss();
    				passToGuessActivity();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == SUBMIT_FAIL) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(getResources().getString(R.string.tableGuessFail));
    		builder.setPositiveButton(R.string.retryButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == FOOD1_INFO || id == FOOD2_INFO || id == FOOD3_INFO) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		int num = (id == FOOD1_INFO) ? 0 : (id == FOOD2_INFO) ? 1 : 2;
    		String message = this.getString(R.string.tableFoodInfo) + tableFoods.get(num).getName();
    		builder.setMessage(message);
    		builder.setPositiveButton(R.string.ok,
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
