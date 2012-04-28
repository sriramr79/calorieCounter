package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AdminActivity extends ListActivity {


	ArrayList<String> names;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin);
		
		names = new ArrayList<String>();
		
		HashMap<String, String> allNames = IOBasic.allNames();
		ArrayList<String> values = new ArrayList<String>();
		for (String usn : allNames.keySet()) {
			values.add(allNames.get(usn) + " (" + usn + ")");
			names.add(usn);
		}
		
		String[] items = new String[values.size()];
		items = values.toArray(items);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
		
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		String usn = names.get(position);
		Intent i = new Intent(this, AdminDetailActivity.class);
		i.putExtra(Constants.UNEXTRA, usn);
		startActivity(i);
	}
	
	
	
	
	
	

}
