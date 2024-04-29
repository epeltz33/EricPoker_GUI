package reports;

public class ReportRow {
    private int gameId;
    private String handDescr;
    private int amountWon;
    private int playerBank;

    public ReportRow(int gameId, String handDescr, int amountWon, int playerBank) {
        this.gameId = gameId;
        this.handDescr = handDescr;
        this.amountWon = amountWon;
        this.playerBank = playerBank;
    }

    // Getters and setters for the properties
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public int getGameId() {
        return gameId;
    }

    public String getHandDescr() {
        return handDescr;
    }
    public void setHandDescr(String handDescr) {
        this.handDescr = handDescr;
    }


    public int getAmountWon() {
        return amountWon;
    }
    public void setAmountWon(int amountWon){
        this.amountWon = amountWon;
    }

    public void setPlayerBank(int playerBank) {
        this.playerBank = playerBank;
    }

    public int getPlayerBank() {
        return playerBank;
    }
}