package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText username, password;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		username = (EditText) findViewById(R.id.unField);
		password = (EditText) findViewById(R.id.pwField);

		IOBasic.finalWrite(getApplicationContext());
		IOBasic.initRead(getApplicationContext());

		/*
		 * 
		 * Context context = getApplicationContext(); CharSequence text =
		 * Integer.toString(IOBasic.getPoints("abaldwin")); int duration =
		 * Toast.LENGTH_LONG;
		 * 
		 * Toast toast = Toast.makeText(context, text, duration); toast.show();
		 * 
		 * Intent i = new Intent(this, SignUpActivity.class); startActivity(i);
		 */

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Constants.LOGOUT_SUCCESSFUL) {
			showToast("You have logged out!");
		} else if (resultCode == Constants.REGISTERED) {
			showToast("Thank you for registering!");
		}
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
		resetLoginFields();
	}

	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

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

	private void invalidLogin(String message) {
		if (message == null)
			message = "Login Failed!";

		showToast(message);
		resetLoginFields();
	}

	private void resetLoginFields() {
		username.setText("");
		password.setText("");
		username.requestFocus();
	}

	public void onTapQuit(View view) {
		finish();
	}

	public void onTapRegister(View view) {
		startActivityForResult(new Intent(this, SignUpActivity.class),
				Constants.NEW_USER);
	}
}
