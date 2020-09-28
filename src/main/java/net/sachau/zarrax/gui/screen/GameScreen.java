package net.sachau.zarrax.gui.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameComponent;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEvent;

import javax.annotation.Resource;
import java.util.Observable;
import java.util.Observer;

@GameComponent
public class GameScreen extends HBox implements Observer {


    @Resource
    private WelcomeScreen welcomeScreen;

    @Resource
    private CreateGameScreen createScreen;

    @Resource
    private MovementScreen movementScreen;

    @Resource
    private GameEngine gameEngine;

    public GameScreen() {
        super();
        GameEvent.getInstance().addObserver(this);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setWidth(ApplicationContext.getInstance().getWidth());
        setHeight(ApplicationContext.getInstance().getHeight());

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case WELCOME:
                getChildren().clear();
                getChildren().add(welcomeScreen);
                return;

            case CREATE_GAME:
                getChildren().clear();
                getChildren().add(createScreen);
                return;

            case START_TURN:
                getChildren().clear();
                getChildren().add(movementScreen);
                movementScreen.init();
                return;

            case GAMEOVER:
                gameEngine.gameOver();
                return;
            default:
        }
    }


}


