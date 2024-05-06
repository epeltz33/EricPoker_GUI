package reports;

import gameoutput.GameData;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import player.Player;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameReport {
	Stage reportStage = new Stage();
	VBox reportPane;
	private Player player;
	private GameData gameData;
	private TableView<ReportRow> tableView;

	public GameReport(Player player) {
		this.player = player;
		gameData = new GameData();
		createReportPane();
		showScene();
	}

	private void createReportPane() {
		reportPane = new VBox();
		reportPane.setFillWidth(true);
		reportPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		Label titleLabel = new Label(String.format("Game Report for %s", player.getName()));
		tableView = new TableView<>();

		TableColumn<ReportRow, Integer> gameIdColumn = new TableColumn<>("Game ID");
		gameIdColumn.setCellValueFactory(new PropertyValueFactory<>("gameId"));
		gameIdColumn.setPrefWidth(80);

		TableColumn<ReportRow, String> handDescrColumn = new TableColumn<>("Hand Description");
		handDescrColumn.setCellValueFactory(new PropertyValueFactory<>("handDescr"));
		handDescrColumn.setPrefWidth(200);

		TableColumn<ReportRow, Integer> amountWonColumn = new TableColumn<>("Amount Won");
		amountWonColumn.setCellValueFactory(new PropertyValueFactory<>("amountWon"));
		amountWonColumn.setPrefWidth(120);

		TableColumn<ReportRow, Integer> playerBankColumn = new TableColumn<>("Player Bank");
		playerBankColumn.setCellValueFactory(new PropertyValueFactory<>("playerBank"));
		playerBankColumn.setPrefWidth(120);

		tableView.getColumns().addAll(gameIdColumn, handDescrColumn, amountWonColumn, playerBankColumn);

		VBox tableBox = new VBox(tableView);
		tableBox.setFillWidth(true);
		tableBox.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		VBox.setVgrow(tableView, Priority.ALWAYS);

		Button saveButton = new Button("Save");
		saveButton.setOnAction(e -> saveReport());

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> exitReport());

		reportPane.getChildren().addAll(titleLabel, new ScrollPane(tableBox), saveButton, exitButton);
	}
	private void saveReport() {
		try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("report.dat"))) {
			for (ReportRow row : tableView.getItems()) {
				outputStream.writeInt(row.getGameId());
				outputStream.writeUTF(player.getName());
				outputStream.writeUTF(row.getHandDescr());
				outputStream.writeInt(row.getAmountWon());
				outputStream.writeInt(row.getPlayerBank());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// This method will be called by the Exit/Close button's listener
	private void exitReport() {
		gameData.closeConnection();
		reportStage.close();
	}


	// This method will make report rendering and display the scene
	private void showScene() {
		Screen screen = Screen.getPrimary();
		double screenWidth = screen.getVisualBounds().getWidth();
		double screenHeight = screen.getVisualBounds().getHeight();

		double windowWidth = screenWidth * 0.8;
		double windowHeight = screenHeight * 0.6;

		Scene scene = new Scene(reportPane, windowWidth, windowHeight);
		reportStage.setTitle(String.format("Game Report for %s", player.getName()));
		reportStage.setScene(scene);
		reportStage.show();

		ResultSet reportData = null;
		try {
			reportData = gameData.fetchPlayerReport(player);
			tableView.getItems().clear();

			while (reportData != null && reportData.next()) {
				int gameId = reportData.getInt("game_id");
				String handDescr = reportData.getString("hand_descr");
				int amountWon = reportData.getInt("amount_won");
				int playerBank = reportData.getInt("player_bank");

				// Create a new ReportRow object and add it to the TableView items list to display the data
				ReportRow row = new ReportRow(gameId, handDescr, amountWon, playerBank);
				tableView.getItems().add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close ResultSet here if no longer needed
			if (reportData != null) {
				try {
					reportData.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
