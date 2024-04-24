package gameoutput;


import player.Player;

import java.io.*;

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

    public static void writeBinaryData(String fileName, Player player, int winAmount) {
        String filePath = "files/" + fileName;

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(filePath, true))) {
            outputStream.writeUTF(player.getId());
            outputStream.writeUTF(player.getName());
            outputStream.writeUTF(player.getHand().getHandDescr());
            outputStream.writeInt(winAmount);
            outputStream.writeInt(player.getBank());
        } catch (IOException e) {
            System.out.println("Error writing data");
        }
    }
}

