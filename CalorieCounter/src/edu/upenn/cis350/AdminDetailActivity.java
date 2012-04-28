package edu.upenn.cis350;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AdminDetailActivity extends Activity {


	ArrayList<String> names;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admindetail);
		
		populateFields(getIntent().getStringExtra(Constants.UNEXTRA));
		
	}
	
	private void populateFields(String username) {
		((TextView)findViewById(R.id.adminUSNField)).setText(username);
		((TextView)findViewById(R.id.adminScoreField)).setText(Integer.toString(IOBasic.getPoints(username)));
	}

}
