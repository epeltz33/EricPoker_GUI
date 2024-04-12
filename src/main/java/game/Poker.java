package game;

import deck.Deck;
import gameobjects.GameColors;
import gameobjects.PayoutTable;
import gameobjects.ScoreBoard;
import gameobjects.Wager;
import hand.Hand;
import helpers.PokerSolver;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import player.Dealer;
import player.Player;

public class Poker {
    private Player player;
    private Dealer dealer;
    private PlayerArea playerArea;
    private BorderPane gameScreen;
    private HBox header;
    private VBox centerSection;
    private VBox rightSection;
    private VBox leftSection;
    private PayoutTable payoutTable;
    private Wager wager;
    private ScoreBoard scoreBoard;
    private Stage primaryStage;
    private Button btnDeal;
    private Button btnExit;
    private GameColors gameColors;

    public Poker() {
        player = new Player("9765467", "FastFreddy", 2650);
        dealer = new Dealer(new Deck(52));
        playerArea = new PlayerArea(player, 5, "poker");
        payoutTable = new PayoutTable("drawpoker");
        wager = new Wager(player, 100);
        scoreBoard = new ScoreBoard(player);
        gameColors = new GameColors(playerArea.getHandResults());
        btnDeal = new Button("Deal");
        btnExit = new Button("Exit");

        createHeader();
        createCenterSection();
        createRightSection();
        createLeftSection();

        gameScreen = new BorderPane();
        gameScreen.setTop(header);
        gameScreen.setCenter(centerSection);
        gameScreen.setRight(rightSection);

        showGame();
        createButtonListeners();
    }

    private void createHeader() {
        Text welcomeText = new Text("Welcome " + player.getName());
        header = new HBox(welcomeText);
        header.setAlignment(Pos.CENTER);
    }

    private void createCenterSection() {
        HBox buttonBox = new HBox(btnDeal);
        buttonBox.setAlignment(Pos.CENTER);
        centerSection = new VBox(playerArea, buttonBox);
        centerSection.setAlignment(Pos.CENTER);
    }

    private void createRightSection() {
        rightSection = new VBox(payoutTable, wager, scoreBoard, btnExit);
    }

    private void createLeftSection() {
        leftSection = new VBox(gameColors);
        leftSection.setAlignment(Pos.CENTER);
        leftSection.setSpacing(10);
        leftSection.setPadding(new Insets(10));
    }

    private void showGame() {
        Scene scene = new Scene(gameScreen, 800, 600);
        primaryStage = new Stage();
        primaryStage.setTitle("Eric's Poker");
        primaryStage.setScene(scene);
        primaryStage.show();
        gameScreen.setLeft(leftSection);
    }

    private void createButtonListeners() {
        btnDeal.setOnAction(event -> startDeal());
        btnExit.setOnAction(event -> exitGame());
    }

    private void startDeal() {
        clearCards();
        dealPlayer();
        evaluateHand();
        playerArea.showCards();
        playerArea.showHandDescr();
        endHand();
    }

    private void dealPlayer() {
        for (int i = 0; i < 5; i++) {
            dealer.dealCard(player);
        }
    }

    private void evaluateHand() {
        Hand playerHand = player.getHand();
        PokerSolver.evaluateHand(playerHand, playerHand.getHandString()); // Evaluate the hand using the PokerSolver
    }

    private void clearCards() {
        player.getHand().discardAll(dealer.getDeck());
    }

    private void exitGame() {
        primaryStage.close();
    }

    private void endHand() {
        // Get the current wager amount
        int currentWager = wager.getWagerAmount();

        // Calculate the payout
        int payout = payoutTable.getPayout(player.getHand(), currentWager);

        // Update the player's bank amount
        player.updateBank(payout);

        // Update the score board with win/loss amount
        scoreBoard.setWinAmount(payout);

        // Update the score board with the new bank amount
        scoreBoard.updateBank();
        System.out.println("Wager: " + currentWager + " Payout: " + payout + " Bank: " + player.getBank());


    }
}