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
				475, 525, 25);
		FoodItem fries2 = new FoodItem(res.getDrawable(R.drawable.bkfries),
				res.getString(R.string.bkfries),
				325, 375, 25);
		FoodItem apple = new FoodItem(res.getDrawable(R.drawable.redapple),
				res.getString(R.string.redapple),
				50, 90, 10);
		FoodItem banana = new FoodItem(res.getDrawable(R.drawable.banana),
				res.getString(R.string.banana),
				80, 110, 15);
		FoodItem milkglass = new FoodItem(res.getDrawable(R.drawable.milkglass),
				res.getString(R.string.milkglass),
				80, 120, 20);
		FoodItem waterglass = new FoodItem(res.getDrawable(R.drawable.waterglass),
				res.getString(R.string.waterglass),
				0, 0, 0);
		FoodItem cokecan = new FoodItem(res.getDrawable(R.drawable.cokecan),
				res.getString(R.string.cokecan),
				110, 130, 10);
		FoodItem tacobellburrito = new FoodItem(res.getDrawable(R.drawable.tacobellburrito),
				res.getString(R.string.tacobellburrito),
				525, 575, 25);
		/*FoodItem sliceofbread = new FoodItem(res.getDrawable(R.drawable.sliceofbread)),
				res.getString(R.string.sliceofbread),
				40,70,20);//make sure this is correct 80cal
		FoodItem broccoli = new FoodItem(re.getDrawable(R.drawable.broccoli)),
				res.getString(R.string.broccoli),
				50,90,10);//make sure this is right 90 cal
		FoodItem cupofrice = new FoodItem(res.getDrawable(R.drawable.cupofrice)),
				res.getString(R.string.cupofrice),
				);//220
*/		
		unseenItems.add(fries1);
		unseenItems.add(fries2);
		unseenItems.add(apple);
		unseenItems.add(banana);
		unseenItems.add(milkglass);
		unseenItems.add(waterglass);
		unseenItems.add(cokecan);
		unseenItems.add(tacobellburrito);
		/*unseenItems.add(sliceofbread);
		unseenItems.add(broccoli);
		unseenItems.add(cupofrice);*/
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
