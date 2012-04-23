package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdminActivity extends Activity {


	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		
		ListView list = (ListView) findViewById(R.id.adminList);
		HashMap<String, String> names = IOBasic.allNames();
		ArrayList<String> values = new ArrayList<String>();
		for (String usn : names.keySet()) {
			values.add(names.get(usn) + " (" + usn + ")");
		}
		
		String[] items = new String[values.size()];
		items = values.toArray(items);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
		
		list.setAdapter(adapter);

	}
	
	

}
