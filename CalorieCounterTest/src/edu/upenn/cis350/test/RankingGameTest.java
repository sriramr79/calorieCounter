package edu.upenn.cis350.test;

import java.util.ArrayList;

import edu.upenn.cis350.FoodItem;
import edu.upenn.cis350.RankingGameActivity;
import edu.upenn.cis350.RankingGameView;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.MotionEvent;

/**
 * Test class for the Ranking game activity and ranking game view classes
 * @author Paul M. Gurniak
 * @version 1.0
 */
public class RankingGameTest extends
		ActivityInstrumentationTestCase2<RankingGameActivity> {
	
	// Provide local variables for the Activity and the associated View
	private RankingGameActivity mActivity;
	private RankingGameView mView;
	
	// If you want to send motion events to your activity/view, you need to use this to do it
	private Instrumentation mInst;
	
	// You need to add a default (no-arg) constructor that does this:
	public RankingGameTest() {
		super(RankingGameActivity.class);
		setActivityInitialTouchMode(false);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		 mInst = this.getInstrumentation();
		 this.injectInstrumentation(mInst);
		 mActivity = this.getActivity();
		 mView = (RankingGameView)mActivity.findViewById(edu.upenn.cis350.R.id.rankingView);
	}
	
	/**
	 * Given in the tutorial, just check to see that the view is being found correctly
	 */
	public void testPreconditions() {
		assertNotNull(mView);
		assertNotNull(mInst);
	}
	
	/**
	 * Tests to see whether the food items are being sorted by average calorie content correctly
	 */
	public void testProperFoodSorting() {
		ArrayList<FoodItem> sorted = mView.correctOrder;
		for(int i = 0; i < sorted.size()-1; i++) {
			FoodItem current = sorted.get(i);
			FoodItem next = sorted.get(i+1);
			assertTrue(current.compareTo(next) <= 0);
		}
	}
	
	/**
	 * Tests to see whether the setup procedures for the RankingGameView class are run properly
	 */
	public void testProperViewSetup() {
		// All setup methods are called through the constructor, so just sanity-check them results
		
		assertFalse(mView.food0Occupied);
		assertFalse(mView.food1Occupied);
		assertFalse(mView.food2Occupied);
		assertFalse(mView.checkEnteredOrder());
		
		assertNull(mView.touchedSquare);
		
	}
	
	/**
	 * Tests the touch functionality
	 */
	public void testOnTouchEvent() {
		
		// This is an example of how to generate a motion event.  Just fill unused fields with nothing.
		
		// The submit button should do nothing right now
		mView.fakeTouchEvent(MotionEvent.ACTION_DOWN, mView.submitSquare.getCenterX(), mView.submitSquare.getCenterY());
		assertNull(mView.touchedSquare);
		
		// Motion should do nothing if no square is touched
		mView.fakeTouchEvent(MotionEvent.ACTION_MOVE, mView.submitSquare.getCenterX() + 50, mView.submitSquare.getCenterY() + 50);
		assertNull(mView.touchedSquare);
		
		// Up action should do nothing if no square was touched
		mView.fakeTouchEvent(MotionEvent.ACTION_UP, mView.submitSquare.getCenterX() + 50, mView.submitSquare.getCenterY() + 50);
		assertNull(mView.touchedSquare);

		// Motion should do nothing if nothing is touched (assume there is nothing at the origin)
		mView.fakeTouchEvent(MotionEvent.ACTION_DOWN, 0, 0);
		assertNull(mView.touchedSquare);
	
		
		// Unfortunately, we can only test no-op test functionality from here.  Anything that causes onTouchEvent to
		// return true will cause a privilege/thread violation exception, resulting in a failed test.
		
	}
	
	
}
