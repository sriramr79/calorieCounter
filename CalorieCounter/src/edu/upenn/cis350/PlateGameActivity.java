package edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
	
	private ArrayList<FoodItem> selectableFoods;
	private ArrayList<FoodItem> tableFoods;
	private int visibleFood;
	private FoodGenerator fg;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.username = getIntent().getStringExtra(Constants.UNEXTRA); 
		
        //should never occur
        if (username == null) {
        	finish();
        }

        this.setContentView(R.layout.plate);
        
        fg = new FoodGenerator(this.getResources());
        selectableFoods = new ArrayList<FoodItem>();
        tableFoods = new ArrayList<FoodItem>();
        for(int i = 0; i < 6; i++) {
        	if(fg.hasNextFood()) {
        		this.selectableFoods.add(fg.nextFood());
        		
        	} else {
        		fg.reset();
        		this.selectableFoods.add(fg.nextFood());
        	}
        }

        visibleFood = this.selectableFoods.size()/2;
        updateDisplayedFoods();
		updateCalorieText();
	}
	
	public void onFoodButtonClick(View view) {
		addFoodToTable();
		updateDisplayedFoods();
		updateCalorieText();
	}
	
	public void onRightButtonClick(View view) {
		visibleFood = (visibleFood == selectableFoods.size() - 1) ? visibleFood : visibleFood + 1;
		updateDisplayedFoods();
		updateCalorieText();
	}
	
	public void onLeftButtonClick(View view) {
		visibleFood = (visibleFood == 0) ? visibleFood : visibleFood - 1;
		updateDisplayedFoods();
		updateCalorieText();
	}
	
	public void onTableButton1Click(View view) {
		removeFoodFromTable(0);
		updateDisplayedFoods();
		updateCalorieText();
	}
	
	public void onTableButton2Click(View view) {
		removeFoodFromTable(1);
		updateDisplayedFoods();
		updateCalorieText();
	}

	public void onTableButton3Click(View view) {
		removeFoodFromTable(2);
		updateDisplayedFoods();
		updateCalorieText();
	}
	
	
	
	/**
	 * Updates the ImageViews in the associated layout to display the
	 * current state of the game.
	 */
	private void updateDisplayedFoods() {
		ImageView selected = (ImageView)this.findViewById(R.id.scrollFoodButton);
		selected.setImageDrawable(selectableFoods.get(visibleFood).getImage());
		ImageView tableFood1 = (ImageView)this.findViewById(R.id.tableFoodButton1);
		ImageView tableFood2 = (ImageView)this.findViewById(R.id.tableFoodButton2);
		ImageView tableFood3 = (ImageView)this.findViewById(R.id.tableFoodButton3);
		// Clear the currently stored image
		tableFood1.setImageDrawable(null);
		tableFood2.setImageDrawable(null);
		tableFood3.setImageDrawable(null);
		
		if(tableFoods.size() >= 1) {
			tableFood1.setImageDrawable(tableFoods.get(0).getImage());
		}
		if(tableFoods.size() >= 2) {
			tableFood2.setImageDrawable(tableFoods.get(1).getImage());
		}
		if(tableFoods.size() == 3) {
			tableFood3.setImageDrawable(tableFoods.get(2).getImage());
		}
	}
	
	/**
	 * Updates the TextViews in the associated layout to display the
	 * current state of the game.
	 */
	private void updateCalorieText() {
		TextView food1 = (TextView)this.findViewById(R.id.food1Desc);
		TextView food2 = (TextView)this.findViewById(R.id.food2Desc);
		TextView food3 = (TextView)this.findViewById(R.id.food3Desc);
		
		if(tableFoods.size() >= 1) {
			food1.setText(this.getResources().getString(R.string.tableFood1Desc) + Integer.toString(tableFoods.get(0).getCalorieCount()));
		} else {
			food1.setText(this.getResources().getString(R.string.tableFood1Desc));
		}
		if(tableFoods.size() >= 2) {
			food2.setText(this.getResources().getString(R.string.tableFood2Desc) + Integer.toString(tableFoods.get(1).getCalorieCount()));
		} else {
			food2.setText(this.getResources().getString(R.string.tableFood2Desc));
		}
		if(tableFoods.size() == 3) {
			food3.setText(this.getResources().getString(R.string.tableFood3Desc) + Integer.toString(tableFoods.get(2).getCalorieCount()));
		} else {
			food3.setText(this.getResources().getString(R.string.tableFood3Desc));
		}
		
		int totalCalories = 0;
		for(int i = 0; i < tableFoods.size(); i++) {
			totalCalories += tableFoods.get(i).getCalorieCount();
		}
		TextView total = (TextView)this.findViewById(R.id.foodTotalDesc);
		total.setText(this.getResources().getString(R.string.tableTotalCalories) + Integer.toString(totalCalories));
		
	}
	
	private void addFoodToTable() {
		FoodItem food = selectableFoods.get(visibleFood);
		if(food == null || selectableFoods.size() <= 3 || tableFoods.size() >= 3) {
			return;
		}
		selectableFoods.remove(food);
		tableFoods.add(food);
		visibleFood = visibleFood == 0 ? 0 : visibleFood - 1;
	}
	
	private void removeFoodFromTable(int pos) {
		if(pos >= 3 || pos < 0 || pos > tableFoods.size()-1) {
			return;
		}
		FoodItem food = tableFoods.get(pos);
		selectableFoods.add(food);
		tableFoods.remove(pos);
	}
	
	public void onQuitButtonClick(View view) {
		this.finish();
	}
	
	
	public void onSubmitButtonClick(View view) {
		if(tableFoods.size() == 3) {
			String state = fg.getStateString(tableFoods);
			this.createDialog(SUBMIT_OKAY).show();
		} else {
			this.createDialog(SUBMIT_FAIL).show();
		}
	}

	public static final int SUBMIT_OKAY = 1;
	public static final int SUBMIT_FAIL= 2;
	
	/**
	 * Creates an instance of the desired Dialog
	 * @param id ID number of the dialog to create
	 * @return a Dialog instance ready to be displayed
	 */
	public Dialog createDialog(int id) {
    	if(id == SUBMIT_OKAY) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(getResources().getString(R.string.tableSubmitOkay));
    		builder.setPositiveButton(R.string.quitButton,
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int id) {
    				dialog.dismiss();
    				finish();
    			}
    		});
    		return builder.create();
    	}
    	else if(id == SUBMIT_FAIL) {
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(getResources().getString(R.string.tableSubmitFail));
    		builder.setPositiveButton(R.string.retryButton,
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
