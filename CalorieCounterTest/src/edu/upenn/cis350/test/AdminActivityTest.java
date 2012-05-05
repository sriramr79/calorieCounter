package edu.upenn.cis350.test;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import edu.upenn.cis350.AdminActivity;
import edu.upenn.cis350.IOBasic;


public class AdminActivityTest extends
		ActivityInstrumentationTestCase2<AdminActivity> {

	// Provide local variables for the Activity and the associated View
	private AdminActivity mActivity;
	
	// If you want to send motion events to your activity/view, you need to use this to do it
	private Instrumentation mInst;
	
	private EditText username, password;
	private Button submit, signup, quit;
	
	
	public AdminActivityTest() {
		super(AdminActivity.class);
		setActivityInitialTouchMode(false);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		IOBasic.initRead();

		mInst = this.getInstrumentation();
		this.injectInstrumentation(mInst);
		mActivity = this.getActivity();
		

		Intent i = new Intent();
		i.setClassName("edu.upenn.cis350", "LoginActivity");
		i.putExtra(edu.upenn.cis350.Constants.UNEXTRA, "a");
		setActivityIntent(i);
		
		
	}
	
	/**
	 * Given in the tutorial, just check to see that the view is being found correctly
	 */
	public void testPreconditions() {
		assertNotNull(mActivity);
		assertNotNull(mInst);
	}
	

	public void testDatabaseLoading() {
		ListView l = mActivity.getListView();
		
		assertEquals(6, l.getAdapter().getCount());
	}
	
}
