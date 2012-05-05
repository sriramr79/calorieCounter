package edu.upenn.cis350.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
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
		mInst = this.getInstrumentation();
		this.injectInstrumentation(mInst);
		mActivity = this.getActivity();
		
		username = (EditText)mActivity.findViewById(edu.upenn.cis350.R.id.unField);
		password = (EditText)mActivity.findViewById(edu.upenn.cis350.R.id.pwField);
		
		submit = (Button)mActivity.findViewById(edu.upenn.cis350.R.id.submit);
		signup = (Button)mActivity.findViewById(edu.upenn.cis350.R.id.signUpButton);
		quit = (Button)mActivity.findViewById(edu.upenn.cis350.R.id.quitButton);
		
		
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
	
	public void testAValidLogin() {
		username.clearComposingText();
		password.clearComposingText();
		
		TouchUtils.tapView(this, username);
		sendKeys("S R I R A M R");
		
		TouchUtils.tapView(this, password);
		sendKeys("S R I R A M R");
		
		assertEquals("sriramr", username.getText().toString());
		assertEquals("sriramr", password.getText().toString());
	}
	
	public void testInvalidLogin() {
		username.clearComposingText();
		password.clearComposingText();
		
		TouchUtils.tapView(this, username);
		sendKeys("W R O N G");
		
		TouchUtils.tapView(this, password);
		sendKeys("W R O N G");
		
		assertEquals("wrong", username.getText().toString());
		assertEquals("wrong", password.getText().toString());
	}
	
}
