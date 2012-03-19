package edu.upenn.cis350.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import edu.upenn.cis350.IOBasic;
import edu.upenn.cis350.HomeActivity;


public class HomeActivityTest extends
		ActivityInstrumentationTestCase2<HomeActivity> {

	// Provide local variables for the Activity and the associated View
	private HomeActivity mActivity;
	
	// If you want to send motion events to your activity/view, you need to use this to do it
	private Instrumentation mInst;
	
	public HomeActivityTest() {
		super(HomeActivity.class);
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
	

	public void testNameLoaded() {
		
	}
	
	public void testScoreLoaded() {
		
	}
	
	public void testGame1() {
		
	}
	
	public void testGame2() {
		
	}
	
	public void testGame3() {
		
	}
	
	public void testLogout() {
		
	}
	
	
}
