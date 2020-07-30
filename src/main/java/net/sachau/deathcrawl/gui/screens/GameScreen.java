package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.HBox;
import net.sachau.deathcrawl.GameEngine;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.cards.catalog.Catalog;
import net.sachau.deathcrawl.dto.Player;

import java.util.Observable;
import java.util.Observer;

public class GameScreen extends HBox implements Observer {

    private double width;
    private double height;

    private double gameHeight;
    private double gameWidth;

    Player player;
    public GameScreen(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        this.gameWidth = width - 200;
        this.gameHeight = height;
        Catalog.init();
        player = new Player();
        GameEngine.getInstance().setPlayer(player);
        GameEvent.getInstance().addObserver(this);


        MainRegion mainRegion = new MainRegion(gameWidth, gameHeight, player);
        SideRegion sideRegion = new SideRegion(player);
        getChildren().addAll(mainRegion, sideRegion);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case GAMEOVER:
                player = new Player();
                GameEngine.getInstance().setPlayer(player);
                return;
            default:
        }
    }

    public double getGameHeight() {
        return gameHeight;
    }

    public double getGameWidth() {
        return gameWidth;
    }
}

