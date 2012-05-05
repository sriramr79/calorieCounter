package edu.upenn.cis350.test;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import edu.upenn.cis350.AdminDetailActivity;
import edu.upenn.cis350.IOBasic;
import edu.upenn.cis350.R;

public class AdminDetailActivityTest extends ActivityInstrumentationTestCase2<AdminDetailActivity> {

	// Provide local variables for the Activity and the associated View
	private AdminDetailActivity mActivity;

	// If you want to send motion events to your activity/view, you need to use
	// this to do it
	private Instrumentation mInst;

	private EditText username, password;
	private Button submit, signup, quit;

	public AdminDetailActivityTest() {
		super(AdminDetailActivity.class);
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
		i.setClassName("edu.upenn.cis350", "AdminActivity");
		i.putExtra(edu.upenn.cis350.Constants.UNEXTRA, "sriram");
		setActivityIntent(i);

	}

	/**
	 * Given in the tutorial, just check to see that the view is being found
	 * correctly
	 */
	public void testPreconditions() {
		assertNotNull(mActivity);
		assertNotNull(mInst);
	}

	public void testDataLoading() {
		assertEquals("Sriram Radhakrishnan", ((TextView) mActivity.findViewById(R.id.adminName)).getText().toString());
		assertEquals("sriram", ((TextView) mActivity.findViewById(R.id.adminUSNField)).getText().toString());
		assertEquals("15", ((TextView) mActivity.findViewById(R.id.adminScoreField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminLoginsField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminCountAttemptsField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminRankingAttemptsField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminORPAttemptsField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminPlateAttemptsField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminCountField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminRankingField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminORPField)).getText().toString());
		assertEquals("0", ((TextView) mActivity.findViewById(R.id.adminPlateField)).getText().toString());
	}
}
