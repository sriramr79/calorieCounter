package edu.upenn.cis350;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

	private final static int NEW_USER_DIALOG = 1;
	private final static int INVALID_LOGIN_DIALOG = 2;

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
		//IOBasic.finalWrite(getApplicationContext());
		IOBasic.initRead();

	}

	private void setFieldListeners() {
		password.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					onTapLogin(v);
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
		
		if (un == null || un.trim().equals("")) {
			createDialog(INVALID_LOGIN_DIALOG, "Username or password is incorrect.  Please try again.");
			return false;
		}
		
		String pw = password.getText().toString();
		String actPW = IOBasic.password(un);

		if (actPW == null) {
			createDialog(NEW_USER_DIALOG, "The user " + un + " does not exist.  Please sign up for an account.");
			return false;
		} else if (pw.equals(actPW)) {
			return true;
		} else {
			createDialog(INVALID_LOGIN_DIALOG, "Username or password is incorrect.  Please try again.");
			return false;
		}
	}

	/**
	 * Submits the login, checking for a valid user and password.
	 * 
	 * @param view
	 */
	public void onTapLogin(View view) {
		String un = username.getText().toString();

		if (checkLogin(un)) {
			showToast("Login Successful!");
			login(un);
		}
	}

	private void login(String un) {
		Intent i = new Intent(this, AdminActivity.class);
		i.putExtra(Constants.UNEXTRA, un);
		startActivityForResult(i, Constants.LOGIN_SUCCESSFUL);
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

	private void createDialog(int id, String msg) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg);

		if (id == INVALID_LOGIN_DIALOG) {
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					resetLoginFields();
				}
			});
		} else if (id == NEW_USER_DIALOG) {
			builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					onTapRegister(getCurrentFocus());
				}
			});
			
			builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					resetLoginFields();
				}
			});
		}
		builder.create().show();
	}

}
