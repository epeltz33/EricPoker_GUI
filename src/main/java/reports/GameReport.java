package reports;

import gameoutput.GameData;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.Player;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameReport {
	// Window dimensions - adjust as needed
	int windowWidth = 1550;
	int windowHeight = 550;

	// This is the Stage (window) object for our report
	Stage reportStage = new Stage();

	// Container to hold all the other objects
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

		Label titleLabel = new Label("Game Report for %s".formatted(player.getName()));
		tableView = new TableView<>();

		TableColumn<ReportRow, Integer> gameIdColumn = new TableColumn<>("Game ID");
		gameIdColumn.setCellValueFactory(new PropertyValueFactory<>("gameId"));

		TableColumn<ReportRow, String> handDescrColumn = new TableColumn<>("Hand Description");
		handDescrColumn.setCellValueFactory(new PropertyValueFactory<>("handDescr"));

		TableColumn<ReportRow, Integer> amountWonColumn = new TableColumn<>("Amount Won");
		amountWonColumn.setCellValueFactory(new PropertyValueFactory<>("amountWon"));

		TableColumn<ReportRow, Integer> playerBankColumn = new TableColumn<>("Player Bank");
		playerBankColumn.setCellValueFactory(new PropertyValueFactory<>("playerBank"));

		tableView.getColumns().addAll(gameIdColumn, handDescrColumn, amountWonColumn, playerBankColumn);

		Button saveButton = new Button("Save");
		saveButton.setOnAction(e -> saveReport());

		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> exitReport());

		reportPane.getChildren().addAll(titleLabel, new ScrollPane(tableView), saveButton, exitButton);
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

	// This method will make report show
	// This method will make report show
	private void showScene() {
		Scene scene = new Scene(reportPane, windowWidth, windowHeight);
		reportStage.setTitle("Game Report for %s".formatted(player.getName()));
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

				// Create a custom object to represent the row data
				ReportRow row = new ReportRow(gameId, handDescr, amountWon, playerBank);
				tableView.getItems().add(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Close ResultSet here if no longer needed beyond this point
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