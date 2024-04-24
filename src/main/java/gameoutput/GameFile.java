package gameoutput;

import player.Player;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GameFile {
    public static void writeCSVData(String filename, Player player, int winAmount) {
        try (PrintWriter output = new PrintWriter(new FileWriter("files/" + filename))) {
            output.println("Player ID, Player Name, Hand, Hand Descr, Win Amount, Bank");
            output.println(player.getId() + "," + player.getName() + "," + player.getHand() + "," +
                    player.getHand().getHandDescr() + "," + winAmount + "," + player.getBank());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}

