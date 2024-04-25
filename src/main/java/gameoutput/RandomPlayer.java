package gameoutput;

import java.sql.ResultSet;
import java.sql.SQLException;

import player.Player;

public class RandomPlayer {
	
	protected static ResultSet playerData;
	private static GameData dbObj;

	public static Player getPlayer() {
		Player player = null;
		
		dbObj = new GameData();
		
		getPlayers();  //Get all the players from the database
		
		try {
			//Determine the size of the list (number of players)
			playerData.last();
			int size = playerData.getRow() - 1;
	
			//Get random player number and move to that place in the ResultSet
			int randomPlayer = (int)(Math.random() * size) + 1;
			playerData.absolute(randomPlayer);
	
			//Create a new player object with that players data
			player = new Player(playerData.getString(1), playerData.getString(2), playerData.getInt(3)); 
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		dbObj.close();  //Shut down the connection
		
		return player;  //Return the random player object
		
	}

	private static void getPlayers() {
		try {
			playerData = dbObj.statement.executeQuery("SELECT * FROM player");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}	

}
