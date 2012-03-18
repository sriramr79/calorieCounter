package edu.upenn.cis350;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        
       
       
    }
    
    public void register (View view) {
    	String fullName = ((EditText)findViewById(R.id.signUpFullName)).getText().toString();
    	String username = ((EditText)findViewById(R.id.signUpUN)).getText().toString();
    	String password = ((EditText)findViewById(R.id.signUpPass)).getText().toString();
    	if (!IOBasic.addUser(username, password, fullName))
    	{
    		Context context = getApplicationContext();
            CharSequence text = "Sorry this username is already taken";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
    		return;
    	}
    	
    	Context context = getApplicationContext();
        CharSequence text = "Congratulation, you are signed up successfully!";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    	
    	
    	setResult(Constants.REGISTERED);
		finish();
	}
    
    public void Back (View view) {
		finish();
	}

}
