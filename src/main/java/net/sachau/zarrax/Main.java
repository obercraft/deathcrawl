package net.sachau.zarrax;

import javafx.application.Application;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.gui.screen.GameScreen;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        //primaryScreenBounds.getHeight()
        double width = primaryScreenBounds.getWidth() - 100;
        double height = primaryScreenBounds.getHeight() - 100;
        GameScreen gameScreen = new GameScreen(width, height);
        Scene content = new Scene(gameScreen, width, height);

        primaryStage.setScene(content);
        primaryStage.show();

        ProgressBar progressBar = new ProgressBar();
        Text pleaseWait = new Text("loading Zarrax");
        gameScreen.getChildren().addAll(pleaseWait, progressBar);

        // Unbind progress property
        progressBar.progressProperty().unbind();

        // Bind progress property
        InitTask initTask = new InitTask();
        progressBar.progressProperty().bind(initTask.progressProperty());

        initTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
                t -> {
                    GameEvent.getInstance()
                            .send(GameEventContainer.Type.WELCOME);
                });


        new Thread(initTask).start();


        // content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());

        content.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    Logger.debug("Key Pressed: " + GameEvent.getInstance().getStage() + ", l=" + GameEvent.getInstance().getLastStage());
                    if (GameEventContainer.Type.WAITING_FOR_PLAYER_ACTION.equals(GameEvent.getInstance().getType())) {
                        Logger.debug("Key Pressed: " + keyComb);
                        GameEvent.getInstance().send(GameEventContainer.Type.PLAYERACTIONDONE);
                    }
                    ke.consume(); // <-- stops passing the event to next node
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}


