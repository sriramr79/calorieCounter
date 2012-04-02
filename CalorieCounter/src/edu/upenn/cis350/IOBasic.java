/**
 * 
 */
package edu.upenn.cis350;

import java.util.HashMap;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.upenn.cis350.util.HttpRequest;
import edu.upenn.cis350.util.HttpRequest.HttpMethod;

/**
 * @author Zhang
 *
 */
public class IOBasic{
	//HashMap that maps a USN to an array of Strings that contains all the other info for the person
	//arrayFormat = [password, full name, points]
	static HashMap<String,String[]> dataStruct;
	public static final String readKEY = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/backend.php";
	public static String write = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/updateDB.php?command=";
	//
	
	
	/*
	 * This function should be called when closing app: it writes the current state of the datastructure
	 * that will keep track of things
	 */
	static public void finalWrite()
	{
		
		
		HttpRequest m_request = new HttpRequest();
		
		Set<String> usn = dataStruct.keySet();
		
		for (String user: usn)
		{
			String password = dataStruct.get(user)[0];
			String fn = dataStruct.get(user)[1];
			String points = dataStruct.get(user)[2];
			m_request.execHttpRequest(write+"insert%20into%20users%20values%20('"+user+"','"+password+"','"+fn+"',"+points+")", HttpMethod.Post, null);
		}
		/*
		String FILENAME = "userInfo.txt";
		String string = "g,g,Test Name,9001\nzhangka,zhangka,Alex Zhang,100\npgurns,pgurns,Paul Gurniak,0\nmkreider,mkreider,Molly Kreider,500\nsriramr,sriramr,Sriram Radhakrishnan,100\nabaldwin,abaldwin,Ashley Baldwin,100";
		try{
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			directory = context.getFilesDir().toString();
			fos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}*/
	}
	
	/*
	 * This function should be called when launching the app: it reads the file in its entirety and
	 * constructs a datastructure out of it
	 */
	static public void initRead()
	{
		dataStruct = new HashMap<String,String[]>();
		String[] data;
		String result = null;
		HttpRequest m_request = new HttpRequest();
		result=m_request.execHttpRequest(readKEY, HttpMethod.Get, null);
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);
				data = new String[3];
				data[0] = json_data.getString("pass");
				data[1] = json_data.getString("fn");
				data[2] = json_data.getString("points");
				dataStruct.put(json_data.getString("usn"), data);
				//Get an output to the screen
			}
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		/*BufferedReader buff;
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
		}*/
	}

	/*
	 * Returns the password for a person's username
	 */
	static public String password (String USN)
	{
		if (!dataStruct.containsKey(USN)) return null;
		String[] data=dataStruct.get(USN);
		return data[0];
	}
	
	/*
	 * get points of a person based off of the username
	 */
	static public int getPoints (String USN)
	{
		if (!dataStruct.containsKey(USN)) return -1;
		String[] data=dataStruct.get(USN);
		return Integer.parseInt(data[2]);
	}
	
	/*
	 * set the Points of user USN to points
	 * 
	 * returns true if it succeeded, false otherwise (no user with specific usn USN)
	 */
	static public boolean setPoints(String USN, int points)
	{
		if (!dataStruct.containsKey(USN)) return false;
		
		dataStruct.get(USN)[2]=Integer.toString(points);
		return true;
	}
	
	/*
	 * get the full name of a person based off of the username
	 */
	static public String fullName (String USN)
	{
		if (!dataStruct.containsKey(USN)) return null;
		String[] data=dataStruct.get(USN);
		return data[1];
	}
	
	/*
	 * returns False if the usn is already taken, otherwise return true
	 */
	static public boolean addUser(String usn, String pass, String fullName)
	{
		if (dataStruct.containsKey(usn)==true) return false;
		String[] data = {pass,fullName,"0"};
		dataStruct.put(usn, data);
		return true;
	}
	
}