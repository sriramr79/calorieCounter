/**
 * 
 */
package edu.upenn.cis350;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import android.content.Context;
import android.util.Log;

/**
 * @author Zhang
 *
 */
public class IOBasic{

	
	
	/*
	 * This function should be called when closing app: it writes the current state of the datastructure
	 * that will keep track of things
	 */
	static public void finalWrite(Context context)
	{
		String FILENAME = "hello_file";
		String string = "hello world!";
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * This function should be called when launching the app: it reads the file in its entirety and
	 * constructs a datastructure out of it
	 */
	static public void initRead()
	{
		try{
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Helper method that returns the info based on full name
	 */
	static private String[] getInfoName (String name)
	{
		return null;
	}
	
	/*
	 * Helper method that returns the info based on username
	 */
	static private String[] getInfoUSN (String USN)
	{
		return null;
	}

	/*
	 * Returns the password for a person's username
	 */
	static public String password (String USN)
	{
		return null;
	}
	
	/*
	 * get points of a person based off of the username
	 */
	static public int points (String USN)
	{
		return 0;
	}
	
	/*
	 * get the full name of a person based off of the username
	 */
	static public String fullName (String USN)
	{
		return null;
	}
}
