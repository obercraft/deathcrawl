package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.VBox;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.CardBoard;
import net.sachau.deathcrawl.gui.PartySelection;
import net.sachau.deathcrawl.gui.map.HexMap;

import java.util.Observable;
import java.util.Observer;

public class MainRegion extends VBox implements Observer {

    public static final double WINDOW_WIDTH = 1200;
    public static final double WINDOW_HEIGHT = 900;

    public static final double GAME_WIDTH = WINDOW_WIDTH - 200;
    public static final double GAME_HEIGHT = WINDOW_HEIGHT;
    private final PartySelection partySelection;

    WelcomeScreen welcomeScreen;
    Player player;
    CardBoard cardBoard;
    HexMap hexMap;
    public MainRegion(Player player) {
        super();
        this.player =  player;
        Game.events().addObserver(this);
        setMinHeight(GAME_HEIGHT);
        setMinWidth(GAME_WIDTH);

        partySelection = new PartySelection(player, 5, "card");

        welcomeScreen = new WelcomeScreen(GAME_WIDTH, GAME_HEIGHT);
        getChildren()
                .addAll(welcomeScreen);


    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Game.get(arg)) {
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
            case STARTENCOUNTERVIEW:
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

