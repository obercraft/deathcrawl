package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.CardBoard;
import net.sachau.deathcrawl.gui.PartySelection;
import net.sachau.deathcrawl.gui.map.HexMap;

import java.util.Observable;
import java.util.Observer;

public class MainRegion extends VBox implements Observer {

    private final PartySelection partySelection;

    WelcomeScreen welcomeScreen;
    Player player;
    CardBoard cardBoard;
    HexMap hexMap;
    public MainRegion(double gameWidth, double gameHeight, Player player) {
        super();
        this.player =  player;
        GameEvent.getInstance().addObserver(this);
        setMinHeight(gameHeight);
        setMinWidth(gameWidth);

        partySelection = new PartySelection(player, 5, "card");

        welcomeScreen = new WelcomeScreen(gameWidth, gameHeight);
        getChildren()
                .addAll(welcomeScreen);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case NEWGAME:
                getChildren().remove(welcomeScreen);
                getChildren().add(partySelection);
                return;
            case PARTYDONE:
                getChildren().remove(partySelection);
                return;
            case STARTTURN:
                getChildren().remove(0, getChildren().size());
                if (hexMap == null) {
                    hexMap = new HexMap(player);
                }
                getChildren().add(hexMap);
                return;
            case GUI_STARTENCOUNTER:
                cardBoard = new CardBoard(player);
                getChildren().remove(0, getChildren().size());
                getChildren().add(cardBoard);
                return;
            case GAMEOVER:
                getChildren().remove(0, getChildren().size());
                return;
            default:
                return;
        }
    }

}

