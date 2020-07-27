package net.sachau.deathcrawl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sachau.deathcrawl.cards.CardParser;
import net.sachau.deathcrawl.gui.screens.GameScreen;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene content = new Scene(new GameScreen(), GameScreen.WINDOW_WIDTH, GameScreen.WINDOW_HEIGHT);





        content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
        primaryStage.setScene(content);


        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}


