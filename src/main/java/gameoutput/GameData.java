package gameoutput;


import java.sql.*;
import player.Player;

public class GameData {
    private Connection connection;
    Statement statement;
    private ResultSet results;

    public GameData() {
        try {
            // Connect to the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the tioli database
            connection = DriverManager.getConnection("jdbc:mysql://localhost/tioli", "root", "theboys36");

            // Create a statement object
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); // This is where the bug was found, needed to add ResultSet.TYPE_SCROLL_INSENSITIVE & ResultSet.CONCUR_UPDATABLE
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBank(Player player) {
        try {
            String sql = "UPDATE player SET bank = " + player.getBank() + " WHERE player_id = '" + player.getId() + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertResults(Player player, int amountWon) {
        try {
            String sql = STR."INSERT INTO game_results (game_id, player_id, hand_descr, amount_won, player_bank) VALUES(DEFAULT, '\{player.getId()}', '\{player.getHand().getHandDescr()}', \{amountWon}, \{player.getBank()})";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (results != null) {
                results.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}