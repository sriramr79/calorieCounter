package edu.upenn.cis350;

import android.graphics.drawable.Drawable;

/**
 * Class to represent a food item.
 * @author Paul M. Gurniak
 * @version 1.0
 */
public class FoodItem {
	
	private Drawable image;
	private String name;
	private int calorieCount;
	
	/**
	 * Constructor
	 * @param image The image that will be displayed for this food item
	 * @param name A String that contains the display text for this item
	 * @param calorieCount The number of calories in this food item
	 */
	public FoodItem(Drawable image, String name, int calorieCount) {
		this.image = image;
		this.name = name;
		this.calorieCount = calorieCount;
	}
	
	public Drawable getImage() {
		return this.image;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCalorieCount() {
		return this.calorieCount;
	}
	
	public String toString() {
		return this.name;
	}
	
}
