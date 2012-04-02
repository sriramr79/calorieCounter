package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

/**
 * Class to represent a food item.
 * @author Paul M. Gurniak
 * @version 1.1
 */
public class FoodItem implements Comparable<FoodItem> {
	
	/**
	 * Enumeration type to represent the accuracy of a response 
	 * @author Paul
	 */
	public enum AnswerType{
		CORRECT, CLOSELOW, CLOSEHIGH, WRONGLOW, WRONGHIGH, INVALID
	}
	
	private Drawable image;
	private String name;
	private String shortName;
	private int calorieLow;
	private int calorieHigh;
	private int calorieError;
	
	/**
	 * Constructor that takes an image, name, and three calorie figures.
	 * These figures work as follows:
	 * 		a. If the guess is between calorieLow and calorieHigh (inclusive), the guess is correct
	 * 		b. If the guess is below calorieLow but within calorieError, the guess is close but low
	 * 		c. If the guess is above calorieHigh but within calorieError, the guess is close but high
	 * 		d. If the guess is none of a-c, it is incorrect.
	 * 
	 * @param image The image that will be displayed for this food item
	 * @param name A String that contains the display text for this item
	 * @param calorieLow A low estimate for the number of calories in this food item
	 * @param calorieHigh A high estimate for the number of calories in this food item
	 * @param calorieError A tolerance range within which a guess will be considered "close"
	 */
	public FoodItem(Drawable image, String name, int calorieLow, int calorieHigh, int calorieError) {
		this.image = image;
		this.name = name;
		this.calorieLow = calorieLow;
		this.calorieHigh = calorieHigh;
		this.calorieError = calorieError;
		this.shortName = null;
	}
	
	public Drawable getImage() {
		return this.image;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	
	/**
	 * Sets the short name for this FoodItem, which will serve as a persistence identifier
	 * @param name The string to use to identify this FoodItem
	 */
	public void setShortName(String name) {
		this.shortName = name;
	}
	
	/**
	 * Returns the integer calorie count of this food item.
	 * As of v1.1, no longer deprecated: use this for games that need an exact number.
	 */
	public int getCalorieCount() {
		return (this.calorieLow + this.calorieHigh)/2;
	}
	
	/**
	 * Evaluates the given guess relative to this food item's actual calorie content
	 * @param calorieGuess The guess provided by the user
	 * @return An <code>AnwerType</code> that describes the quality of the guess
	 */
	public AnswerType checkGuess(int calorieGuess) {
		if(calorieGuess < 0) {
			return AnswerType.INVALID;
		}
		if(calorieGuess >= calorieLow && calorieGuess <= calorieHigh) {
			return AnswerType.CORRECT;
		}
		if(calorieGuess >= calorieLow-calorieError && calorieGuess < calorieHigh) {
			return AnswerType.CLOSELOW;
		}
		if(calorieGuess <= calorieHigh+calorieError && calorieGuess > calorieLow) {
			return AnswerType.CLOSEHIGH;
		}
		return calorieGuess > calorieHigh ? AnswerType.WRONGHIGH : AnswerType.WRONGLOW;
		
	}
	
	public String toString() {
		return this.name;
	}
	
	public boolean equals(Object o) {
		if(o instanceof FoodItem) {
			return ((FoodItem) o).getName() == this.getName();
		}
		return false;
	}
	
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public int compareTo(FoodItem other) {
		return this.getCalorieCount() - other.getCalorieCount();
	}
	
}
