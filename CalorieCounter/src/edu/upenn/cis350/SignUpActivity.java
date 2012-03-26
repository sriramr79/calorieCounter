package edu.upenn.cis350;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

public class SignUpActivity extends Activity {

	private final static int INV_USN_DIALOG = 1;
	
	private EditText fnField, unField, pwField;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		fnField = ((EditText) findViewById(R.id.signUpFullName));
		unField = ((EditText) findViewById(R.id.signUpUN));
		pwField = ((EditText) findViewById(R.id.signUpPass));
		
		setFieldListeners();
		
		if (fnField == null || unField == null || pwField == null)
			return;
		
	}
	
	private void setFieldListeners() {
		pwField.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
					onTapRegister(v);
					return true;
				} else
					return false;
			}
		});

	}

	private void createDialog(int id, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg);

		if (id == INV_USN_DIALOG) {
			builder.setPositiveButton(R.string.ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.dismiss();
						}
					});
		}
		builder.create().show();
	}
	
	public void onTapRegister(View view) {
		String fullname = fnField.getText().toString();
		String username = unField.getText().toString();
		String password = pwField.getText().toString();

		if (fullname.equals("") || username.equals("") || password.equals("")) {
			createDialog(INV_USN_DIALOG, getResources().getString(R.string.invUsnMsg));
			resetFields();
			return;
		}
		
		if (!IOBasic.addUser(username, password, fullname)) {
			createDialog(INV_USN_DIALOG, getResources().getString(R.string.dupUsnMsg));
			resetFields();
			return;
		}
		

		Toast.makeText(this, "Congratulations, you have successfully signed up!", Toast.LENGTH_SHORT).show();
		
		Intent i = new Intent();
		i.putExtra(Constants.UNEXTRA, username);
		setResult(Constants.REGISTERED, i);
		finish();
	}

	private void resetFields() {
		fnField.setText("");
		unField.setText("");
		pwField.setText("");
		fnField.requestFocus();
	}

	public void onTapBack(View view) {
		finish();
	}

}
