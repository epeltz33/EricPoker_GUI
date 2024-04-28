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
    // ...
}