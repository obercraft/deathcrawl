package net.sachau.deathcrawl.gui.screens;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.engine.GameEventContainer;

import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.engine.Player;

import java.util.Observable;
import java.util.Observer;

public class SideRegion extends VBox implements Observer {

    private final Button newGame;
    private final Button partyDone;
    private final Button partyClear;
    private final Button randomParty;
    private final Button encounter;
    private final Button actionDone;
    private final Button cardInfoButton;
    VBox buttons = new VBox();


    Player player;
    public SideRegion(Player player) {
        super();
        this.player = player;
        GameEvent.getInstance().addObserver(this);

        setMaxWidth(200);
        buttons.setMinHeight(400);


        HBox toolButtons = new HBox();
        cardInfoButton = new Button("CARDS");
        toolButtons.getChildren().add(cardInfoButton);
        newGame = new Button();
        newGame.setText("NEW GAME");

        partyDone = new Button("PARTY DONE");
        partyDone.setDisable(true);

        randomParty = new Button("RANDOM PARTY");

        actionDone = new Button("NEXT");



        buttons.getChildren().addAll(toolButtons, newGame);

        getChildren().addAll(buttons);


        partyClear = new Button();
        partyClear.setText("CLEAR PARTY");

        encounter = new Button("ENCOUNTER");


        newGame.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.NEWGAME);
        });

        randomParty.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.RANDOMPARTY);
        });

        encounter.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.STARTENCOUNTER);
        });

        actionDone.setOnMouseClicked(event -> {
            GameEvent.getInstance().send(GameEventContainer.Type.PLAYERACTIONDONE);
        });

        cardInfoButton.setOnMouseClicked(event -> {
            Logger.getConsole().getCardInfoWindow().show();
        });

    }




    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
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
            case NEXTACTION:
            case STARTCARDPHASE:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                buttons.getChildren().add(actionDone);
                return;
            case GAMEOVER:
                buttons.getChildren().remove(0, buttons.getChildren().size());
                buttons.getChildren().add(newGame);
                return;


        }
    }

}

