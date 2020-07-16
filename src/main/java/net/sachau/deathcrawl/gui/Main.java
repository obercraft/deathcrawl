package net.sachau.deathcrawl.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.sachau.deathcrawl.cards.*;
import net.sachau.deathcrawl.cards.classes.Thief;
import net.sachau.deathcrawl.cards.classes.Warrior;
import net.sachau.deathcrawl.cards.classes.Wizard;
import net.sachau.deathcrawl.cards.items.Knife;
import net.sachau.deathcrawl.cards.monsters.Goblin;
import net.sachau.deathcrawl.cards.spells.MagicMissile;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.dto.Player;
import org.reflections.Reflections;

import java.util.Set;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 1024;
    private static final double WINDOW_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) throws Exception {



        Player player = new Player();

        Reflections reflections = new Reflections("net.sachau.deathcrawl.cards");
        Set<Class<?>> basic = reflections.getTypesAnnotatedWith(Basic.class);

        for (Class b : basic) {
            for (int i = 0; i < 2; i++) {
                Card card = (Card) b.newInstance();
                card.setOwner(player);
                player
                        .getDraw()
                        .add(card);

            }
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
            if (i == 0) {
                goblin.getConditions().add(new Armor());
            } else if (i == 1) {
                goblin.getConditions().add(new Guard());
                goblin.getConditions().add(new Armor());
            }
            hazards.add(goblin);
        }

        player.setHazard(hazards);

        player.getParty().add(new Warrior());
        player.getParty().add(new Wizard());
        player.getParty().add(new Thief());


        PartyArea partyArea = new PartyArea(player, 5);

        VBox rows = new VBox();
        Scene content = new Scene(rows, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);

        HBox row1 = new HBox();
        HBox row2 = new HBox();
        HBox row3 = new HBox();

        row1.getChildren().addAll(playArea, drawPile);
        row2.getChildren().addAll(hand, discardPile);
        row3.getChildren().addAll(partyArea);
        rows.getChildren().addAll(row1, row2, row3);

        for (Card c : player.getParty().getCards()) {
            c.triggerPhaseEffects(CardEffect.Phase.PREPARE);
        }


        primaryStage.show();





    }


    public static void main(String[] args) {
        launch(args);
    }
}


