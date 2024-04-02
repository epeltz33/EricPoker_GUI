package gameobjects;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameColors extends VBox {
    private RadioButton rbRed;
    private RadioButton rbBlue;
    private RadioButton rbGreen;
    private RadioButton rbPurple;
    private Text textToColor;

    public GameColors(Text textToColor) {
        this.textToColor = textToColor;

        rbRed = new RadioButton("Red");
        rbBlue = new RadioButton("Blue");
        rbGreen = new RadioButton("Green");
        rbPurple = new RadioButton("Purple");

        toggleButtons();
        createListeners();

        this.getChildren().addAll(rbRed, rbBlue, rbGreen, rbPurple);
    }

    private void toggleButtons() {
        ToggleGroup toggleGroup = new ToggleGroup();
        rbRed.setToggleGroup(toggleGroup);
        rbBlue.setToggleGroup(toggleGroup);
        rbGreen.setToggleGroup(toggleGroup);
        rbPurple.setToggleGroup(toggleGroup);

        //  default hand description text color??

        rbGreen.setSelected(true);
    }

    private void createListeners() {
        rbRed.setOnAction(e -> textToColor.setFill(Color.RED));
        rbBlue.setOnAction(e -> textToColor.setFill(Color.BLUE));
        rbGreen.setOnAction(e -> textToColor.setFill(Color.GREEN));
        rbPurple.setOnAction(e -> textToColor.setFill(Color.PURPLE));
    }
}