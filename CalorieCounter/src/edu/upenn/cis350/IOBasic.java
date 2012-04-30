
package edu.upenn.cis350;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
	//arrayFormat = [password, full name, points, others]
	static HashMap<String,String[]> dataStruct;
	public static final String readKEY = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/backend.php";
	public static String write = "https://fling.seas.upenn.edu/~zhangka/cgi-bin/updateDB.php?command=";
	
	// Data structure to store the persistence data for the PlateGame
	static HashMap<String,HashMap<String,String>> games = new HashMap<String, HashMap<String,String>>();
	
	// Data structure to prevent help dialog from appearing more than once per session
	static HashMap<String,ArrayList<Boolean>> shownHelp = new HashMap<String, ArrayList<Boolean>>();
	
	// Data structures to store local copies of the user's current 
	static HashMap<String,ArrayList<Integer>> gameAttempts = new HashMap<String, ArrayList<Integer>>();
	static HashMap<String,ArrayList<Integer>> gameWins = new HashMap<String, ArrayList<Integer>>();
	
	public static final int HomeScreen = 0;
	public static final int OneRightPrice = 1;
	public static final int PlateGameHome = 2;
	public static final int PlateGameGuess = 3;
	public static final int PlateGameCreate = 4;
	public static final int RankingGame = 5;
	public static final int CalorieCounter = 6;
	
	private static final int miniGameCount = 7;
	
	/**
	 * Get the number of games won by this user in this particular mini-game
	 * 
	 * @param username username of the desired user
	 * @param gameId Integer identifying the desired game (use constants in IOBasic)
	 * @return the number of times this user has won this particular game
	 */
	static public int getGameWins(String username, int gameId) {
		if(username == null || !dataStruct.containsKey(username) || gameId >= miniGameCount || gameId < 0) {
			return -1;
		}
		if(!gameWins.containsKey(username)) {
			return 0;
		}
		return gameWins.get(username).get(gameId);
	}
	
	/**
	 * Sets the number of games won for this user in this particular mini-game.
	 * 
	 * @param username username of the desired user
	 * @param gameId Integer identifying the desired game (use constants in IOBasic)
	 * @param wins The number of wins for this game
	 */
	static public void setGameWins(String username, int gameId, int wins) {
		if(username == null || !dataStruct.containsKey(username) || gameId >= miniGameCount || gameId < 0 || wins < 0) {
			return;
		}
		if(!gameWins.containsKey(username)) {
			gameWins.put(username, new ArrayList<Integer>(miniGameCount));
			for(int i = 0; i < miniGameCount; i++) {
				gameWins.get(username).add(0);
			}
		}
		gameWins.get(username).set(gameId, wins);
	}
	
	/**
	 * Get the number of games attempted by this user in this particular mini-game
	 * 
	 * @param username username of the desired user
	 * @param gameId Integer identifying the desired game (use constants in IOBasic)
	 * @return the number of times this user has tried to play this particular game
	 */
	static public int getGameAttempts(String username, int gameId) {
		if(username == null || !dataStruct.containsKey(username) || gameId >= miniGameCount || gameId < 0) {
			return -1;
		}
		if(!gameAttempts.containsKey(username)) {
			return 0;
		}
		return gameAttempts.get(username).get(gameId);
	}
	
	/**
	 * Sets the number of games attempted for this user in this particular mini-game.
	 * 
	 * @param username username of the desired user
	 * @param gameId Integer identifying the desired game (use constants in IOBasic)
	 * @param attempts The number of attempts for this game
	 */
	static public void setGameAttempts(String username, int gameId, int attempts) {
		if(username == null || !dataStruct.containsKey(username) || gameId >= miniGameCount || gameId < 0 || attempts < 0) {
			return;
		}
		if(!gameAttempts.containsKey(username)) {
			gameAttempts.put(username, new ArrayList<Integer>(miniGameCount));
			for(int i = 0; i < miniGameCount; i++) {
				gameAttempts.get(username).add(0);
			}
		}
		gameAttempts.get(username).set(gameId, attempts);
	}
	
	/**
	 * Checks to see if the user has already been shown the help screen
	 * for the specified activity
	 * @param username The username for the desired user
	 * @param activity The integer corresponding to the desired activity (public variables in IOBasic.java)
	 * @return
	 */
	public static boolean getShownHelp(String username, int activity)
	{
		if(username == null || !dataStruct.containsKey(username) || activity < 0 || activity > miniGameCount) {
			return false;
		}
		return shownHelp.containsKey(username) && shownHelp.get(username).get(activity);
	}
	
	/**
	 * Sets that the user has seen the help dialog for the specified activity
	 * @param username The username for the desired user
	 * @param activity The integer corresponding to the desired activity (public variables in IOBasic.java)
	 */
	public static void setShownHelp(String username, int activity)
	{
		if(username != null && dataStruct.containsKey(username) && activity >= 0 && activity < miniGameCount) {
			if(!shownHelp.containsKey(username)) {
				shownHelp.put(username, new ArrayList<Boolean>(miniGameCount));
				for(int i = 0; i < miniGameCount; i++) {
					shownHelp.get(username).add(false);
				}
			}
			shownHelp.get(username).set(activity, true);
		}
	}
	
	/**
	 * Resets the help dialog status for the specified user
	 * @param username The username for the desired user
	 */
	public static void resetShownHelp(String username)
	{
		if(username != null && dataStruct.containsKey(username)) {
			shownHelp.put(username, new ArrayList<Boolean>(miniGameCount));
		}
	}
	
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
	static public void addOpponent(String user, String opponent, String state)
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
		
		return games.get(user).get(opponent);
	}
	
	/**
	 * Formulates a persistence String for each user's data
	 * 
	 * @param username username of the user
	 * @return the persistence string that can be used to restore this user's data
	 */
	static private String generatePersistenceString(String username) {
		StringBuffer s = new StringBuffer();
		
		// Add PlateGame data
		if(games.containsKey(username)) {
			HashMap<String,String> userGames = games.get(username);
			for(String opponent : userGames.keySet()) {
				s.append(opponent);
				s.append(',');
				s.append("".equals(userGames.get(opponent)) ? "wait" : userGames.get(opponent));
			}
		}
		else {
			s.append("null");
		}
		s.append(".");
		
		// Add wins data
		if(gameWins.containsKey(username)) {
			ArrayList<Integer> userWins = gameWins.get(username);
			for(int i = 0; i < userWins.size(); i++) {
				s.append(Integer.toString(userWins.get(i)));
				s.append(',');
			}
		}
		else {
			s.append("null");
		}
		s.append(".");
		
		// Add attempts data
		if(gameAttempts.containsKey(username)) {
			ArrayList<Integer> userAttempts = gameAttempts.get(username);
			for(int i = 0; i < userAttempts.size(); i++) {
				s.append(Integer.toString(userAttempts.get(i)));
				s.append(',');
			}
		}
		else {
			s.append("null");
		}
		
		return s.toString();
	}
	
	/**
	 * Re-populates our data structures using the given persistence string for each user
	 * 
	 * @param username username of the user for which we are restoring data
	 * @param state persistence string that can be used to restore data
	 */
	static private void restorePersistenceString(String username, String state) {
		if(username == null || !dataStruct.containsKey(username) || state == null) {
			return;
		}
		
		StringTokenizer tokens = new StringTokenizer(state, ".");
		
		// First big token is plate game data
		if(!tokens.hasMoreTokens()) {
			return;
		}
		String plateToken = tokens.nextToken();
		if(!"null".equals(plateToken)) {
			StringTokenizer plateGames = new StringTokenizer(plateToken, ",");
			while(plateGames.hasMoreTokens()) {
				String opponent = plateGames.nextToken();
				String gameState = plateGames.hasMoreTokens() ? plateGames.nextToken() : null;
				if(gameState == null) {
					return;
				}
				addOpponent(username, opponent, "wait".equals(gameState) ? "" : gameState);
			}
		}
		
		// Second big token is wins data
		if(!tokens.hasMoreTokens()) {
			return;
		}
		String winsToken = tokens.nextToken();
		if(!"null".equals(winsToken)) {
			StringTokenizer gameWins = new StringTokenizer(winsToken, ",");
			for(int i = 0; i < miniGameCount && gameWins.hasMoreTokens(); i++) {
				try {
					setGameWins(username, i, Integer.parseInt(gameWins.nextToken()));
				} catch(NumberFormatException e) {
					break;
				}
			}
		}
		
		// Third big token is attempts data
		if(!tokens.hasMoreTokens()) {
			return;
		}
		String attemptsToken = tokens.nextToken();
		if(!"null".equals(attemptsToken)) {
			StringTokenizer gameAttempts = new StringTokenizer(attemptsToken, ",");
			for(int i = 0; i < miniGameCount && gameAttempts.hasMoreTokens(); i++) {
				try {
					setGameAttempts(username, i, Integer.parseInt(gameAttempts.nextToken()));
				} catch(NumberFormatException e) {
					break;
				}
			}
		}
		
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
			//String response=m_request.postData(write+"insert%20into%20users%20values('"+user+"','"+password+"','"+fn+"',"+points+",'null')", null);
			//Log.d("finalWrite", response);
			m_request.postData(write+"update%20users%20set%20points="+points+"%20where%20usn='"+user+"'",null); // update points
			
			// Update persistence String for this user
			dataStruct.get(user)[3] = generatePersistenceString(user);
			
			String temp = dataStruct.get(user)[3];
			if (temp == null)
			{
				m_request.postData(write+"update%20users%20set%20others='null'%20where%20usn='"+user+"'",null);
			}
			else
			{
				//temp.replace(" ", "%20");
				m_request.postData(write+"update%20users%20set%20others='"+temp+"'%20where%20usn='"+user+"'",null);
			}
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
		
		String[] data;
		String result = null;
		HttpRequest m_request = new HttpRequest();
		result=m_request.execHttpRequest(readKEY, HttpMethod.Get, null);
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);
				data = new String[4];
				data[0] = json_data.getString("pass");
				data[1] = json_data.getString("fn");
				data[2] = json_data.getString("points");
				String temp = json_data.getString("others");
				if (temp.toLowerCase().equals("null"))
					data[3] = null;
				else
					data[3] = temp;
				dataStruct.put(json_data.getString("usn"), data);
				
				// Populate other data structures with persistence string
				restorePersistenceString(json_data.getString("usn"), data[3]);
				
				//Get an output to the screen
			}
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		
	
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
	 * return false if user doesn't exist
	 * the String set must HAVE NO SPACE
	 */
	static private boolean setOtherInfo(String USN, String info)
	{
		if (!dataStruct.containsKey(USN)) return false;
		dataStruct.get(USN)[3] = info;
		return true;
	}
	
	
	/*
	 * return null if user doesn't exist
	 */
	static private String otherInfo(String USN)
	{
		if (!dataStruct.containsKey(USN)) return null;
		String[] data=dataStruct.get(USN);
		return data[3];
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
		String[] data = {pass,fullName,"0",null};
		dataStruct.put(usn, data);
		
		HttpRequest m_request = new HttpRequest();
		
		String password = dataStruct.get(usn)[0];
		String fn = dataStruct.get(usn)[1];
		fn = fn.replace(" ", "%20");
		String points = dataStruct.get(usn)[2];
		String response=m_request.postData(write+"insert%20into%20users%20values('"+usn+"','"+password+"','"+fn+"',"+points+",'null')", null);
		Log.d("adding user", response);

		return true;
	}
	
	static public HashMap<String, String> allNames () {
		HashMap<String, String> names = new HashMap<String,String>();
		
		Set<String> usernames = dataStruct.keySet();
		for (String usn : usernames) {
			names.put(usn, dataStruct.get(usn)[1]);
		}
			
		return names;
	}

	public static boolean isTeacher(String un) {
		if (un.equals("a"))
			return true;
		else
			return false;
	}
	
}