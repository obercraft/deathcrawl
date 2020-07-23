package net.sachau.deathcrawl.gui.screens;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.GameEvent;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.dto.Player;

import java.util.Observable;
import java.util.Observer;

public class SideRegion extends VBox implements Observer {

    private final Button newGame;
    private final Button partyDone;
    private final Button partyClear;
    private final Button randomParty;
    private final Button encounter;
    private final Button endCardPhase;
    VBox buttons = new VBox();


    Player player;
    public SideRegion(Player player) {
        super();
        this.player = player;
        GameEvent.events().addObserver(this);

        setMaxWidth(200);
        buttons.setMinHeight(400);


        newGame = new Button();
        newGame.setText("NEW GAME");

        partyDone = new Button("PARTY DONE");
        partyDone.setDisable(true);

        randomParty = new Button("RANDOM PARTY");

        endCardPhase = new Button("DONE");



        buttons.getChildren().add(newGame);

        getChildren().addAll(buttons, Logger.getConsole());


        partyClear = new Button();
        partyClear.setText("CLEAR PARTY");

        encounter = new Button("ENCOUNTER");


        newGame.setOnMouseClicked(event -> {
            GameEvent.events().send(GameEvent.Type.NEWGAME);
        });

        randomParty.setOnMouseClicked(event -> {
            GameEvent.events().send(GameEvent.Type.RANDOMPARTY);
        });

        encounter.setOnMouseClicked(event -> {
            GameEvent.events().send(GameEvent.Type.STARTENCOUNTER);
        });

        endCardPhase.setOnMouseClicked(event -> {
            GameEvent.events().send(GameEvent.Type.ENDCARDPHASE);
        });

/*
        newGame.setOnMouseClicked(event -> {


            mainRegion.getChildren()
                    .removeAll(welcomeScreen);
            gameButtons.getChildren()
                    .remove(newGame);

            Button partyClear = new Button();
            partyClear.setText("CLEAR PARTY");

            partySelection = new PartySelection(player, partyDone, partyClear, 5);
            mainRegion.getChildren()
                    .add(partySelection);

            gameButtons.getChildren()
                    .addAll(partyClear, partyDone);


        });

        partyDone.setOnMouseClicked(event -> {
            gameButtons.getChildren()
                    .remove(0, gameButtons.getChildren()
                            .size());
            mainRegion.getChildren()
                    .remove(partySelection);


            Deck party = player.getParty();
            for (Card partyMember : party.getCards()) {

                if (partyMember instanceof CharacterCard) {
                    CharacterCard characterCard = (CharacterCard) partyMember;
                    for (Card c : characterCard.getStartingCards()
                            .getCards()) {
                        c.setOwner(player);
                        c.setVisible(false);
                        player.getDraw()
                                .add(c);
                    }
                }

            }

            Deck hazards = new Deck();

            for (int i = 0; i < 3; i++) {
                Goblin goblin = new Goblin();
                goblin.setVisible(true);
                if (i == 0) {
                    goblin.getConditions()
                            .add(new Armor());
                } else if (i == 1) {
                    goblin.getConditions()
                            .add(new Guard());
                    goblin.getConditions()
                            .add(new Armor());
                }
                hazards.add(goblin);
            }

            player.setHazard(hazards);

            for (int i = 0; i < player.getHandSize(); i++) {
                player.getDraw()
                        .draw(player.getHand());
            }

            mainRegion.getChildren()
                    .add(new CardBoard(player));
        });

*/
    }




    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.get(arg)) {
            case NEWGAME:
                buttons.getChildren().remove(newGame);
                buttons.getChildren().addAll(partyClear, partyDone, randomParty);
                return;
            case STARTTURN:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                buttons.getChildren().add(encounter);
                return;

            case STARTENCOUNTER:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                return;
            case STARTCARDPHASE:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                buttons.getChildren().add(endCardPhase);
                return;
            case GAMEOVER:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                buttons.getChildren().add(newGame);
                return;


        }
    }
}

