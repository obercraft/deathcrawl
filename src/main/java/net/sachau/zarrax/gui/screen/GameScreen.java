package net.sachau.zarrax.gui.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.v2.GEngine;

import java.util.Observable;
import java.util.Observer;

@MainScreen
@GuiComponent
public class GameScreen extends HBox implements Observer {

    final private WelcomeScreen welcomeScreen;

    final private CreateGameScreen createScreen;

    final private MovementScreen movementScreen;

    final private GEngine engine;

    @Autowired
    public GameScreen(WelcomeScreen welcomeScreen, CreateGameScreen createScreen, MovementScreen movementScreen, GEngine engine) {
        super();
        this.welcomeScreen = welcomeScreen;
        this.createScreen = createScreen;
        this.movementScreen = movementScreen;
        this.engine = engine;
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
                engine.gameOver();
                return;
            default:
        }
    }


}


