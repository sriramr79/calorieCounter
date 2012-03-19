package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the launch activity for the CalorieCounter app.
 * It handles user login, and also contains an option for
 * a user to sign-up for an account.
 * This class also loads the data from the database to a local
 * file on the device.
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

		//login fields
		username = (EditText) findViewById(R.id.unField);
		password = (EditText) findViewById(R.id.pwField);

		//load database
		IOBasic.finalWrite(getApplicationContext());
		IOBasic.initRead(getApplicationContext());

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
			showToast("Thank you for registering!");
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
	 * @param message
	 */
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Submits the login, checking for a valid user and
	 * password.
	 * 
	 * @param view
	 */
	public void submitLogin(View view) {
		String un = username.getText().toString();
		String pw = password.getText().toString();

		String actPW = IOBasic.password(un);
		
		if (actPW == null)
			invalidLogin("User does not exist!");

		else if (pw.equals(actPW)) {
			showToast("Login Successful!");
			Intent i = new Intent(this, HomeActivity.class);
			i.putExtra(Constants.UNEXTRA, un);
			startActivityForResult(i, Constants.LOGIN_SUCCESSFUL);
		} else {
			resetLoginFields();
		}
	}

	/**
	 * Shows an error message and then resets the login
	 * fields for another login attempt
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
		startActivity(new Intent(this, SignUpActivity.class));
	}
}
