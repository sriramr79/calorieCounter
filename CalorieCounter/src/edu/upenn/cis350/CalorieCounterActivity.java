package edu.upenn.cis350;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class CalorieCounterActivity extends Activity {
	
	public GameLevel level;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcount);
        level = new GameLevel(getResources());
        updateDisplayedFood(level.getCurrentFood());
    }
    
    public void onSubmitEvent(View view) {
    	EditText userInput = (EditText)findViewById(R.id.calorieInput);
    	// Surround in try-catch to avoid crash on unparseable input
    	int calorieGuess = -1;
    	try {
    		calorieGuess = Integer.parseInt(userInput.getText().toString());
    	} catch (NumberFormatException e) {
    		// Do nothing
    	}
    	level.enterCurrentGuess(calorieGuess);
    	
    	// Evaluate user's answer and show an appropriate response dialog
    	FoodItem.AnswerType evaluation = level.getCurrentFood().checkGuess(calorieGuess);
    	switch(evaluation) {
    	case CORRECT:
    		removeDialog(CORRECT_DIALOG);
            showDialog(CORRECT_DIALOG);
            break;
    	case CLOSELOW:
    		removeDialog(CLOSELOW_DIALOG);
    		showDialog(CLOSELOW_DIALOG);
    		break;
    	case CLOSEHIGH:
    		removeDialog(CLOSEHIGH_DIALOG);
    		showDialog(CLOSEHIGH_DIALOG);
    		break;
    	case WRONGLOW:
    		removeDialog(WRONGLOW_DIALOG);
    		showDialog(WRONGLOW_DIALOG);
    		break;
    	case WRONGHIGH:
    		removeDialog(WRONGHIGH_DIALOG);
    		showDialog(WRONGHIGH_DIALOG);
    		break;
    	case INVALID:
    		removeDialog(INVALID_DIALOG);
    		showDialog(INVALID_DIALOG);
    		break;
    	default:
    		throw new RuntimeException("Non-existant FoodItem.AnswerType returned?  You should not be returning any int from FoodItem.checkGuess()!  Use the Enum.");
    	}
    	
   
    }
    
    /**
     * Updates the display to the user to match the given food item.
     * Call this whenever the current food item changes.
     * @param currentFood The item to be displayed
     */
    public void updateDisplayedFood(FoodItem currentFood) {
    	// Set food text, set food image, clear user input
    	TextView foodName = (TextView)findViewById(R.id.foodName);
    	foodName.setText(getResources().getString(R.string.food) + currentFood);
    	ImageView foodImage = (ImageView)findViewById(R.id.foodImage);
    	foodImage.setImageDrawable(currentFood.getImage());
    	EditText calorieGuess = (EditText)findViewById(R.id.calorieInput);
    	calorieGuess.setText("");
    }
    
    
    private static final int CORRECT_DIALOG = 0;
    private static final int CLOSELOW_DIALOG = 1;
    private static final int WRONGLOW_DIALOG = 2;
    private static final int CLOSEHIGH_DIALOG = 3;
    private static final int WRONGHIGH_DIALOG = 4;
    private static final int INVALID_DIALOG = 5;
    
    protected Dialog onCreateDialog(int id) {
    	
    	Resources res = getResources();
    	
    	if(id == CORRECT_DIALOG) {
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		// Example format: "That's right, an order of McDonald's fries is 550 calories."
    		String message = res.getString(R.string.correctMessage) + level.getCurrentFood()
    				+ res.getString(R.string.correctIs) + level.getCurrentFood().getCalorieCount() + res.getString(R.string.calories);
    		builder.setMessage(message);
    		builder.setPositiveButton(R.string.nextButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    	        	if(level.hasNextFood()) {
    	        		level.moveToNextFood();
    	        	}
    	        	else {
    	        		level.resetLevel(true);
    	        	}
    	    		updateDisplayedFood(level.getCurrentFood());
    			}
    		});
    		
    		return builder.create();
    	}
    	
    	else if(id == CLOSELOW_DIALOG || id == CLOSEHIGH_DIALOG) {
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		// Example format: "That's close, but an order of McDonald's fries is a little more than that."
    		String message = res.getString(R.string.closeMessage) + level.getCurrentFood()
    				+ res.getString(id == CLOSELOW_DIALOG ? R.string.closeLow : R.string.closeHigh);
    		builder.setMessage(message);
    		builder.setPositiveButton(R.string.retryButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    	    		updateDisplayedFood(level.getCurrentFood());
    			}
    		});
    		
    		return builder.create();
    	}
    	
    	else if(id == WRONGLOW_DIALOG || id == WRONGHIGH_DIALOG) {
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		// Example format: "Sorry, but an order of McDonald's fries is more calories than that."
    		String message = res.getString(R.string.wrongMessage) + level.getCurrentFood()
    				+ res.getString(id == WRONGLOW_DIALOG ? R.string.wrongLow : R.string.wrongHigh);
    		builder.setMessage(message);
    		builder.setPositiveButton(R.string.retryButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    	    		updateDisplayedFood(level.getCurrentFood());
    			}
    		});
    		
    		return builder.create();
    	}
    	
    	else {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage("This dialog not yet implemented");
    		builder.setPositiveButton(R.string.nextButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.cancel();
    				updateDisplayedFood(level.getCurrentFood());
    			}
    		});
    		return builder.create();
    	}
    
    }
    
    public void onQuitEvent(View view) {
//    	setResult(Constants.LOGOUT_SUCCESSFUL);
    	finish();
    }
}