package net.sachau.deathcrawl.gui.screens;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.cards.Card;
import net.sachau.deathcrawl.cards.Deck;
import net.sachau.deathcrawl.cards.monsters.Goblin;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.CardBoard;
import net.sachau.deathcrawl.gui.PartySelection;

import java.util.HashSet;

public class GameScreen extends HBox {

    public static final double WINDOW_WIDTH = 1200;
    public static final double WINDOW_HEIGHT = 900;

    public static final double GAME_WIDTH = WINDOW_WIDTH -200;
    public static final double GAME_HEIGHT = WINDOW_HEIGHT - 50;

    private PartySelection partySelection;


    public GameScreen() {
        super();

        VBox mainRegion = new VBox();
        mainRegion.setMinHeight(GAME_HEIGHT);
        mainRegion.setMinWidth(GAME_WIDTH);


        VBox logRegion = new VBox();
        mainRegion.setMinWidth(GAME_WIDTH);
        mainRegion.setMinHeight(50);

        logRegion.getChildren().add(Logger.getConsole());

        WelcomeScreen welcomeScreen = new WelcomeScreen(GAME_WIDTH, GAME_HEIGHT);
        mainRegion.getChildren().add(welcomeScreen);

        VBox left = new VBox();
        left.getChildren().addAll(mainRegion, logRegion);

        VBox right = new VBox();

        Button newGame = new Button();
        newGame.setText("NEW GAME");
        right.getChildren().add(newGame);
        getChildren().addAll(left, right);

        Button partyDone = new Button("PARTY DONE");
        partyDone.setDisable(true);
        Player player = new Player();
        newGame.setOnMouseClicked(event -> {


            mainRegion.getChildren().removeAll(welcomeScreen);
            right.getChildren().remove(newGame);

            Button partyClear = new Button();
            partyClear.setText("CLEAR PARTY");

            partySelection = new PartySelection(player, partyDone, partyClear, 5);
            mainRegion.getChildren().add(partySelection);

            right.getChildren().addAll(partyClear, partyDone);




        });

        partyDone.setOnMouseClicked(event -> {
                right.getChildren().remove(0, right.getChildren().size());
            mainRegion.getChildren().remove(partySelection);


            Deck party = player.getParty();
            for (Card partyMember : party.getCards()) {

                player.getDraw().addAll(partyMember.getStartingCards(), player);

            }

            Deck hazards = new Deck();

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


            mainRegion.getChildren().add(new CardBoard(player));
        });



    }
}

