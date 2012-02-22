package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

/**
 * Class to represent a food item.
 * @author Paul M. Gurniak
 * @version 1.0
 */
public class FoodItem {
	
	/**
	 * Enumeration type to represent the accuracy of a response 
	 * @author Paul
	 */
	public enum AnswerType{
		CORRECT, CLOSELOW, CLOSEHIGH, WRONGLOW, WRONGHIGH, INVALID
	}
	
	private Drawable image;
	private String name;
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
	}
	
	public Drawable getImage() {
		return this.image;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the integer calorie count of this food item
	 * @deprecated
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
	
}
