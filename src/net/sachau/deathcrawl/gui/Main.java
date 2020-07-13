package net.sachau.deathcrawl.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sachau.deathcrawl.Deathcrawl;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Deathcrawl game = Deathcrawl.getInstance();
        VBox tileMap = new VBox();
        Scene content = new Scene(tileMap, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);
        CardHolder cardHolder0 = new CardHolder(0);
        cardHolder0.addCard("card 1 1");
        cardHolder0.addCard("card 1 2");
        cardHolder0.addCard("card 1 3");
        cardHolder0.addCard("card 1 4");

        CardHolder cardHolder1 = new CardHolder(1);
        cardHolder1.addCard("card 2 1");
        cardHolder1.addCard("card 2 2");
        cardHolder1.addCard("card 2 3");
        cardHolder1.addCard("card 2 4");
        cardHolder1.addCard("card 2 5");
        cardHolder1.addCard("card 2 6");
        cardHolder1.addCard("card 2 7");
        cardHolder1.addCard("card 2 8");
        cardHolder1.addCard("card 2 9");

        game.getCardHolders().add(cardHolder0);
        game.getCardHolders().add(cardHolder1);

        tileMap.getChildren().add(cardHolder0);
        tileMap.getChildren().add(cardHolder1);



        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


