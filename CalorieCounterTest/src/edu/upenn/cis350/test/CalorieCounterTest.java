package edu.upenn.cis350.test;

import java.util.ArrayList;
import java.util.HashSet;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import edu.upenn.cis350.CalorieCounterActivity;
import edu.upenn.cis350.FoodItem;
import edu.upenn.cis350.FoodGenerator;
import edu.upenn.cis350.GameLevel;
import edu.upenn.cis350.R;

/**
 * This class contains unit tests for the following classes:
 * 
 *  1. CalorieCounterActivity
 *  2. FoodGenerator
 *  3. GameLevel
 *  
 * The reason for combining these into one file is because
 * FoodGenerator requires access to resources, which would
 * not be reachable through standard JUnit testing, but it
 * also does not extend Activity, so we can't use an
 * ActivityInstrumentationTestCase2 with it.  Same applies
 * for the GameLevel class.
 * 
 * @author Paul M. Gurniak
 * @version 1.0
 *
 */
public class CalorieCounterTest extends
		ActivityInstrumentationTestCase2<CalorieCounterActivity> {

	// Provide local variables for the Activity and the associated View
	private CalorieCounterActivity mActivity;
	
	// If you want to send motion events to your activity/view, you need to use this to do it
	private Instrumentation mInst;
	
	public CalorieCounterTest() {
		super(CalorieCounterActivity.class);
		setActivityInitialTouchMode(false);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mInst = this.getInstrumentation();
		this.injectInstrumentation(mInst);
		mActivity = this.getActivity();
	}
	
	/**
	 * Given in the tutorial, just check to see that the view is being found correctly
	 */
	public void testPreconditions() {
		assertNotNull(mActivity);
		assertNotNull(mInst);
	}
	
	/**
	 * Test that the internal data structure used for the level is created properly
	 */
	public void testLevelCreation() {
		GameLevel level = mActivity.level;
		assertNotNull(level);
		// The level should have at least one food in it right now
		assertTrue(level.hasNextFood());
	}
	
	/**
	 * Test the behavior of FoodGenerator
	 */
	public void testFoodGenerator() {
		FoodGenerator uut = new FoodGenerator(mActivity.getResources());
		
		// There should be food items present
		assertTrue(uut.hasNextFood());
		FoodItem current = uut.nextFood();
		assertNotNull(current);
		
		// It should be returning unique, non-null foods to us the entire time
		HashSet<FoodItem> seen = new HashSet<FoodItem>();
		seen.add(current);
		while(uut.hasNextFood()) {
			current = uut.nextFood();
			assertNotNull(current);
			assertFalse(seen.contains(current));
			seen.add(current);
		}
		
		// It should now be out of foods
		assertFalse(uut.hasNextFood());
		current = uut.nextFood();
		assertNull(current);
		
		// Resetting should return the same food item (same random order)
		uut.reset();
		assertTrue(uut.hasNextFood());
		while(uut.hasNextFood()) {
			current = uut.nextFood();
			assertNotNull(current);
			assertTrue(seen.contains(current));
			seen.remove(current);
		}
		
		// This should have covered all of the same objects as before
		assertTrue(seen.isEmpty());
		
	}
	
	/**
	 * Tests functionality for the GameLevel class
	 */
	public void testGameLevel() {
		GameLevel uut = new GameLevel(mActivity.getResources());
		
		// The recently created GameLevel should have food items in it
		assertTrue(uut.hasNextFood());
		FoodItem current = uut.getCurrentFood();
		assertNotNull(current);
		
		// Getting the current food should not advance the game
		assertEquals(current, uut.getCurrentFood());
		assertTrue(uut.hasNextFood());
		
		uut.moveToNextFood();
		// Moving to the next food should give a food item that is different than the last
		assertFalse(current.equals(uut.getCurrentFood()));
		
		// It should be returning unique, non-null foods to us the entire time
		HashSet<FoodItem> seen = new HashSet<FoodItem>();
		seen.add(current);
		seen.add(uut.getCurrentFood());
		while(uut.hasNextFood()) {
			uut.moveToNextFood();
			assertNotNull(uut.getCurrentFood());
			assertFalse(seen.contains(uut.getCurrentFood()));
			seen.add(uut.getCurrentFood());
		}
		
		// We should be able to reset to the same point we started
		uut.resetLevel(false);
		assertEquals(current, uut.getCurrentFood());

		assertTrue(seen.contains(current));
		seen.remove(current);
		while(uut.hasNextFood()) {
			uut.moveToNextFood();
			assertTrue(seen.contains(uut.getCurrentFood()));
			seen.remove(uut.getCurrentFood());
		}

		// This should have covered all of the same objects as before
		assertTrue(seen.isEmpty());
		
		
	}
	
	
	
	
	
}
