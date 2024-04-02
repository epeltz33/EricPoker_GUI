package com.poker.ericpoker;
import game.Poker;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayPoker extends Application {
    @Override
    public void start(Stage primaryStage) {
        Poker poker = new Poker();
    }

    public static void main(String[] args) {
        launch(args);
    }
}