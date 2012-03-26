package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * This is the launch activity for the CalorieCounter app. It handles user
 * login, and also contains an option for a user to sign-up for an account. This
 * class also loads the data from the database to a local file on the device.
 * 
 * @author Sriram Radhakrishnan
 * 
 */
public class LoginActivity extends Activity {

	private EditText username, password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// login fields
		username = (EditText) findViewById(R.id.unField);
		password = (EditText) findViewById(R.id.pwField);

		setFieldListeners();

		// load database
		IOBasic.finalWrite(getApplicationContext());
		IOBasic.initRead(getApplicationContext());

	}
	
	private void setFieldListeners () {
		password.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					submitLogin(v);
					return true;
				} else
					return false;
			}
		});
		
	}

	/**
	 * This method performs any special actions required when returning to this
	 * activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Constants.LOGOUT_SUCCESSFUL) {
			showToast("You have logged out!");
		}

		else if (resultCode == Constants.REGISTERED) {
			login(data.getStringExtra(Constants.UNEXTRA));
			showToast("Thank you for registering!  You are now logged in.");
		}
	}

	/**
	 * Resets the login fields upon return to this activity
	 */
	@Override
	protected void onResume() {
		super.onResume();
		resetLoginFields();
	}

	/**
	 * Shows a short-duration toast with the input message
	 * 
	 * @param message
	 */
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private boolean checkLogin(String un) {
		String pw = password.getText().toString();
		String actPW = IOBasic.password(un);

		if (actPW == null) {
			invalidLogin("User does not exist!");
			return false;
		} else if (pw.equals(actPW)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Submits the login, checking for a valid user and password.
	 * 
	 * @param view
	 */
	public void submitLogin(View view) {
		String un = username.getText().toString();

		if (checkLogin(un)) {
			showToast("Login Successful!");
			login(un);
		} else {
			resetLoginFields();
		}
	}
	
	private void login(String un) {
		Intent i = new Intent(this, HomeActivity.class);
		i.putExtra(Constants.UNEXTRA, un);
		startActivityForResult(i, Constants.LOGIN_SUCCESSFUL);
	}

	/**
	 * Shows an error message and then resets the login fields for another login
	 * attempt
	 * 
	 * @param message
	 */
	private void invalidLogin(String message) {
		if (message == null)
			message = "Login Failed!";

		showToast(message);
		resetLoginFields();
	}

	/**
	 * Clears the login fields
	 */
	private void resetLoginFields() {
		username.setText("");
		password.setText("");
		username.requestFocus();
	}

	public void onTapQuit(View view) {
		finish();
	}

	public void onTapRegister(View view) {
		startActivityForResult(new Intent(this, SignUpActivity.class), Constants.NEW_USER);
	}
}
