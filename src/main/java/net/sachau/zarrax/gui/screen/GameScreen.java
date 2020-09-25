package net.sachau.zarrax.gui.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEvent;

import java.util.Observable;
import java.util.Observer;

public class GameScreen extends HBox implements Observer {


    private WelcomeScreen welcomeScreen;
    private CreateGameScreen createScreen;
    private MovementScreen movementScreen;

    public GameScreen(double width, double height) {
        super();
        GameEvent.getInstance().addObserver(this);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setWidth(width);
        setHeight(height);

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case WELCOME:
                getChildren().clear();
                if (welcomeScreen == null) {
                    welcomeScreen = new WelcomeScreen(this.getWidth(), this.getHeight());
                }
                getChildren().add(welcomeScreen);
                return;

            case CREATE_GAME:
                getChildren().clear();

                if (createScreen == null) {
                    createScreen = new CreateGameScreen(this.getWidth(), this.getHeight());
                }
                getChildren().add(createScreen);
                return;

            case START_TURN:
                getChildren().clear();

                if (movementScreen == null) {
                    movementScreen = new MovementScreen(this.getWidth(), this.getHeight());
                }
                getChildren().add(movementScreen);
                movementScreen.init();
                return;


            case GAMEOVER:
                GameEngine.getInstance()
                        .gameOver();
                return;
            default:
        }
    }


}


