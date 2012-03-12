package edu.upenn.cis350;

import java.util.ArrayList;
import android.content.res.Resources;

/**
 * Class to represent a game level, which consists of a sequence of food items
 * @author Paul M. Gurniak
 * @version 1.1
 *
 */
public class GameLevel {

	private ArrayList<FoodItem> foodList;
	private int [] calorieGuesses;
	private int currentFood;
	
	/**
	 * Preferred constructor, creates Game Level object with zero progress
	 * @param foodList List of foods that comprise this level, in order of appearance
	 */
	public GameLevel(ArrayList<FoodItem> foodList) {
		this.foodList = foodList;
		this.calorieGuesses = new int[foodList.size()];
		this.currentFood = -1;
	}
	
	/**
	 * Constructs Game level consisting of every food in the system, in random order
	 * As of v1.1 no longer deprecated.
	 */
	public GameLevel(Resources res) {
		this.foodList = new ArrayList<FoodItem>();
		FoodGenerator gen = new FoodGenerator(res);
		while(gen.hasNextFood()) {
			foodList.add(gen.nextFood());
		}
		this.calorieGuesses = new int[foodList.size()];
		this.currentFood = 0;
	}
	
	/**
	 * Get method for the current food item.
	 * @return Reference to the current food item, or null if there are no foods in the list
	 */
	public FoodItem getCurrentFood() {
		return (currentFood >= 0 && currentFood < foodList.size()) ? foodList.get(currentFood) : null;
	}

	/**
	 * Test method to query if there are additional food items after the current one in the level.
	 * @return True if there are additional items, false if there are not.
	 */
	public boolean hasNextFood() {
		return currentFood + 1 < foodList.size();
	}
	
	/**
	 * Increments the game to the next food item
	 */
	public void moveToNextFood() {
		currentFood = this.hasNextFood() ? currentFood + 1 : currentFood;
	}

	
	/**
	 * Resets the level state to the original.
	 * @param clearGuesses If true, all guesses are cleared.  If false, they are preserved.
	 */
	public void resetLevel(boolean clearGuesses) {
		currentFood = 0;
		if(clearGuesses) {
			calorieGuesses = new int[foodList.size()];
		}
	}
	
	/**
	 * Stores the entered calorie guess for the current food item for later use.
	 * @param calorieGuess The calorie guess for the current food item
	 */
	public void enterCurrentGuess(int calorieGuess) {
		calorieGuesses[currentFood] = calorieGuess;
	}
	
	public String toString() {
		return this.foodList.toString();
	}
	
	
	
}
