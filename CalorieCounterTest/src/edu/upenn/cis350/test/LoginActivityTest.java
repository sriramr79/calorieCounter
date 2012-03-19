package edu.upenn.cis350.test;

import java.util.HashSet;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import edu.upenn.cis350.CalorieCounterActivity;
import edu.upenn.cis350.FoodItem;
import edu.upenn.cis350.FoodGenerator;
import edu.upenn.cis350.GameLevel;
import edu.upenn.cis350.IOBasic;
import edu.upenn.cis350.LoginActivity;


public class LoginActivityTest extends
		ActivityInstrumentationTestCase2<LoginActivity> {

	// Provide local variables for the Activity and the associated View
	private LoginActivity mActivity;
	
	// If you want to send motion events to your activity/view, you need to use this to do it
	private Instrumentation mInst;
	
	public LoginActivityTest() {
		super(LoginActivity.class);
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
	

	public void testDatabaseLoading() {
		assertEquals("Sriram Radhakrishnan", IOBasic.fullName("sriramr"));
	}
	
	public void testValidLogin() {
		
	}
	
	public void testInvalidLogin() {
		
	}
	
	public void testQuit() {
		
	}
	
	public void testSignUp() {
		
	}
	
	
}
