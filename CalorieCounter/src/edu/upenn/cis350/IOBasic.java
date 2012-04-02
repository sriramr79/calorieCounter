
package edu.upenn.cis350;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import edu.upenn.cis350.util.HttpRequest;
import edu.upenn.cis350.util.HttpRequest.HttpMethod;

/**
 * @author Zhang
 * @contributors Paul M. Gurniak
 *
 */
public class IOBasic{
	//HashMap that maps a USN to an array of Strings that contains all the other info for the person
	//arrayFormat = [password, full name, points]
	static HashMap<String,String[]> dataStruct;
	public static final String readKEY = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/backend.php";
	public static String write = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/updateDB.php?command=";
	
	// Data structure to store the persistence data for the PlateGame
	static HashMap<String,HashMap<String,String>> games = new HashMap<String, HashMap<String,String>>();
	
	/**
	 * Returns a list of the other users the specified user is currently playing a game with
	 * @param user The username of the user
	 * @return a List of the usernames of users this user is playing with
	 */
	static public List<String> getOpponents(String user) {
		if(!games.containsKey(user)) {
			return null;
		}
		return new ArrayList<String>(games.get(user).keySet());
	}
	
	/**
	 * Adds a game state string for the given user and opponent
	 * @param user username of the user
	 * @param opponent username of the user's opponent
	 * @param state persistence string generated by FoodGenerator, or "" if you are waiting for this user to move.
	 */
	static public void addOpponent(String user, String opponent,String state)
	{
		if (!games.containsKey(user))
		{
			HashMap<String,String> userGames = new HashMap<String,String>();
			userGames.put(opponent, state);
			games.put(user, userGames);
		}
		else
		{
			games.get(user).put(opponent, state);
		}
	}
	
	/**
	 * Gets the game state for the given user and opponent
	 * @param user username of the user
	 * @param opponent username of the user's opponent
	 * @return state persistence string generated by FoodGenerator, or "" if you are waiting for this user to move
	 */
	static public String getGameState(String user, String opponent)
	{
		if (!games.containsKey(user))
		{
			return null;
		}
		if (games.get(user)==null)
			return null;
		
		return games.get(user).get(user);
	}
	
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
			fn = fn.replace(" ", "%20");
			String points = dataStruct.get(user)[2];
			String response=m_request.postData(write+"insert%20into%20users%20values('"+user+"','"+password+"','"+fn+"',"+points+")", null);
			Log.e("ERROR", response);

		}

	}
	
	static public void reset()
	{
		finalWrite();
		//dataStruct = null;
		//initRead();
	
	}
	
	/*
	 * This function should be called when launching the app: it reads the file in its entirety and
	 * constructs a datastructure out of it
	 */
	static public void initRead()
	{
		dataStruct = new HashMap<String,String[]>();
		/*
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
		*/
	
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