package net.sachau.deathcrawl.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.Goblin;
import net.sachau.deathcrawl.cards.Knife;
import net.sachau.deathcrawl.cards.Warrior;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.card.CardTile;
import net.sachau.deathcrawl.gui.card.CardTileCache;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Warrior warrior = new Warrior();

        CardTile mainCardTile = CardTileCache.getTile(warrior);

        Player player = new Player(warrior);

        for (int i = 0; i < 5; i++) {
            Knife knife = new Knife();
            knife.setOwner(player);
            player
                    .getDraw()
                    .add(knife);

        }
        player.getDraw()
                .shuffe();


        DrawPile drawPile = new DrawPile(player);
        DiscardPile discardPile = new DiscardPile(player);
        HandHold hand = new HandHold(player, 5);

        Deck hazards = new Deck();
        PlayArea playArea = new PlayArea(player, hazards, 5);
        for (int i = 0; i < 3; i++) {
            Goblin goblin = new Goblin();
            goblin.setVisible(true);
            hazards.add(goblin);
        }

        player.setHazard(hazards);

        VBox tileMap = new VBox();
        Scene content = new Scene(tileMap, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);

        tileMap.getChildren()
                .add(playArea);
        tileMap.getChildren()
                .add(hand);

        HBox piles = new HBox();

        piles.getChildren()
                .add(drawPile);
        piles.getChildren()
                .add(discardPile);

        piles.getChildren().add(mainCardTile);

        tileMap.getChildren()
                .add(piles);




        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


