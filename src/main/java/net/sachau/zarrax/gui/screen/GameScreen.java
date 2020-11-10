package net.sachau.zarrax.gui.screen;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import net.sachau.zarrax.engine.GuiComponent;
import net.sachau.zarrax.engine.MainScreen;
import net.sachau.zarrax.gui.CardBoard;
import net.sachau.zarrax.v2.GEngine;
import net.sachau.zarrax.v2.GEvents;
import net.sachau.zarrax.v2.GGraphics;

import java.util.Observable;
import java.util.Observer;

@MainScreen
@GuiComponent
public class GameScreen extends HBox implements Observer {

    final private WelcomeScreen welcomeScreen;

    final private CreateGameScreen createScreen;

    final private MovementScreen movementScreen;

    final private CardBoard cardBoard;

    final private GEngine engine;

    final private GEvents events;

    @Autowired
    public GameScreen(WelcomeScreen welcomeScreen, CreateGameScreen createScreen, MovementScreen movementScreen, GEngine engine, GEvents events, GGraphics graphics, CardBoard cardBoard) {
        super();
        this.welcomeScreen = welcomeScreen;
        this.createScreen = createScreen;
        this.movementScreen = movementScreen;
        this.engine = engine;
        this.events = events;
        this.cardBoard = cardBoard;
        events.addObserver(this);
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        setWidth(graphics.getWidth());
        setHeight(graphics.getHeight());

    }

    @Override
    public void update(Observable o, Object arg) {
        switch (events.getType(arg)) {
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

            case GUI_STARTENCOUNTER:
                getChildren().clear();
                getChildren().add(cardBoard);
                cardBoard.init();
                return;

            case GAMEOVER:
                engine.gameOver();
                return;
            default:
        }
    }


}


