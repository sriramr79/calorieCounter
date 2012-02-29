package edu.upenn.cis350;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private final String testUN = "cal";
	private final String testPW = "count";
	
	private EditText username, password;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        username = (EditText) findViewById(R.id.loginField);
        password = (EditText) findViewById(R.id.passwordField);
        
    }
    
    public void submitLogin (View view) {
    	String un = username.getText().toString();
    	String pw = password.getText().toString();
    	
    	if (un.equalsIgnoreCase(testUN) && pw.equals(testPW)) {    	
    		Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
    		Intent i = new Intent(this, CalorieCounterActivity.class);
    		startActivity(i);
    	} else
    		Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show();    		
    }  

}
