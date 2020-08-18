package net.sachau.zarrax.gui.screens;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.card.catalog.Catalog;
import net.sachau.zarrax.engine.Player;

import java.util.Observable;
import java.util.Observer;

public class GameScreen extends HBox implements Observer {

    private final MainRegion mainRegion;
    private final SideRegion sideRegion;
    private double width;
    private double height;

    private double gameHeight;
    private double gameWidth;

    Player player;
    public GameScreen(double width, double height) {
        super();
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        this.width = width;
        this.height = height;
        this.gameWidth = width - 200;
        this.gameHeight = height;
        player = new Player();
        GameEngine.getInstance().setPlayer(player);
        GameEvent.getInstance().addObserver(this);
        mainRegion = new MainRegion(gameWidth, gameHeight, player);
        sideRegion = new SideRegion(player);
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

    public MainRegion getMainRegion() {
        return mainRegion;
    }

    public SideRegion getSideRegion() {
        return sideRegion;
    }
}

