package edu.upenn.cis350;

import java.util.LinkedList;
import java.util.Random;
import android.content.res.Resources;

/**
 * Class to abstract the FoodItem generation procedure to an iterator-style interface.
 * Successive calls to the nextFood method will return all of the food items in the resources
 * directory in random order.
 * @author Paul M. Gurniak
 * @version 1.0
 *
 */
public class FoodGenerator {
	
	private LinkedList<FoodItem> unseenItems;
	private LinkedList<FoodItem> seenItems;
	private Random rand;
	
	public FoodGenerator(Resources res) {
		rand = new Random();
		unseenItems = new LinkedList<FoodItem>();
		seenItems = new LinkedList<FoodItem>();
		FoodItem fries1 = new FoodItem(res.getDrawable(R.drawable.mcfries),
				res.getString(R.string.mcfries),
				500, 600, 50);
		FoodItem fries2 = new FoodItem(res.getDrawable(R.drawable.bkfries),
				res.getString(R.string.bkfries),
				300, 400, 50);
		FoodItem apple = new FoodItem(res.getDrawable(R.drawable.redapple),
				res.getString(R.string.redapple),
				80, 120, 40);
		unseenItems.add(fries1);
		unseenItems.add(fries2);
		unseenItems.add(apple);
	}
	
	/**
	 * Checks to see if there are additional food items that are yet to be provided by nextFood
	 * @return True if there are still food items remaining, false if not
	 */
	public boolean hasNextFood() {
		return !unseenItems.isEmpty();
	}
	
	/**
	 * Returns the next food item (determined randomly from those remaining), or null if there aren't any left
	 * @return The next FoodItem
	 */
	public FoodItem nextFood() {
		if(unseenItems.isEmpty()) {
			return null;
		}
		int selection = rand.nextInt(unseenItems.size());
		FoodItem nextFood = unseenItems.remove(selection);
		seenItems.add(nextFood);
		return nextFood;
	}
	
	/**
	 * Resets the generator to consider all food items as unseen.
	 */
	public void reset() {
		unseenItems.addAll(seenItems);
		seenItems.clear();
	}

}
