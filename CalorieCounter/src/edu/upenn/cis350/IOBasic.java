/**
 * 
 */
package edu.upenn.cis350;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;



import android.content.Context;

/**
 * @author Zhang
 *
 */
public class IOBasic{
	//HashMap that maps a USN to an array of Strings that contains all the other info for the person
	//arrayFormat = [password, full name, points]
	static HashMap<String,String[]> dataStruct;
	static String directory="";
	//
	
	
	/*
	 * This function should be called when closing app: it writes the current state of the datastructure
	 * that will keep track of things
	 */
	static public void finalWrite(Context context)
	{
		String FILENAME = "userInfo.txt";
		String string = "zhangka,zhangka,Alex Zhang,100\npgurns,pgurns,Paul Gurniak,0\nmkreider,mkreider,Molly Kreider,500\nsriramr,sriramr,Sriram Radhakrishnan,100\nabaldwin,abaldwin,Ashley Baldwin,100";
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			directory = context.getFilesDir().toString();
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
	static public void initRead(Context context)
	{
		dataStruct = new HashMap<String,String[]>();
		BufferedReader buff;
		String FILENAME = "userInfo.txt";
		String currentLine = null;
		StringTokenizer tk;
		String[] additionalData=null;
		String usn=null;
		int length;
		try{
			buff = new BufferedReader(new FileReader(directory+"/"+FILENAME));
			while((currentLine=buff.readLine())!=null)
			{
				tk = new StringTokenizer(currentLine,",");
				additionalData = new String[tk.countTokens()-1];
				length = additionalData.length;
				usn = tk.nextToken();
				for (int i =0;i<length;i++)
				{
					additionalData[i]=tk.nextToken();
				}
				dataStruct.put(usn, additionalData);
				
			}
			buff.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Returns the password for a person's username
	 */
	static public String password (String USN)
	{
		String[] data=dataStruct.get(USN);
		return data[0];
	}
	
	/*
	 * get points of a person based off of the username
	 */
	static public int points (String USN)
	{
		String[] data=dataStruct.get(USN);
		return Integer.parseInt(data[2]);
	}
	
	/*
	 * get the full name of a person based off of the username
	 */
	static public String fullName (String USN)
	{
		String[] data=dataStruct.get(USN);
		return data[1];
	}
}