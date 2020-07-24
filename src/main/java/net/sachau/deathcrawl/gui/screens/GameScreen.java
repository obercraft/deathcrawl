package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.HBox;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.dto.Player;

import java.util.Observable;
import java.util.Observer;

public class GameScreen extends HBox implements Observer {

    public static final double WINDOW_WIDTH = 1200;
    public static final double WINDOW_HEIGHT = 900;

    public static final double GAME_WIDTH = WINDOW_WIDTH - 200;
    public static final double GAME_HEIGHT = WINDOW_HEIGHT;
    Player player;
    public GameScreen() {
        super();
        player = new Player();
        Game.events().addObserver(this);
        MainRegion mainRegion = new MainRegion(player);
        SideRegion sideRegion = new SideRegion(player);
        getChildren().addAll(mainRegion, sideRegion);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Game.get(arg)) {
            case GAMEOVER:
                player = new Player();
                return;
        }
    }

}

