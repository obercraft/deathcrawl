package net.sachau.zarrax;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.zarrax.engine.ApplicationContext;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.gui.screen.GameScreen;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        //primaryScreenBounds.getHeight()
        double width = primaryScreenBounds.getWidth() - 100;
        double height = primaryScreenBounds.getHeight() - 100;
        //GameScreen gameScreen = new GameScreen();
        HBox loadScreen = new HBox();
        loadScreen.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene content = new Scene(loadScreen, width, height);

        primaryStage.setScene(content);
        primaryStage.show();

//        ProgressBar progressBar = new ProgressBar();
        Text pleaseWait = new Text("loading Zarrax");
        pleaseWait.setFill(Color.WHITE);
        loadScreen.getChildren().addAll(pleaseWait);

        // Unbind progress property
//        progressBar.progressProperty().unbind();

//        // Bind progress property
//        InitTask initTask = new InitTask(width, height);
//        progressBar.progressProperty().bind(initTask.progressProperty());
//
//        initTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, //
//                t -> {
//                    GameEvent.getInstance()
//                            .send(GameEventContainer.Type.WELCOME);
//                });
//
//
//        new Thread(initTask).start();

        ApplicationContext.init(width, height);
        loadScreen.getChildren().add((GameScreen) ApplicationContext.getInstance().getContextData().get(GameScreen.class));
        GameEvent.getInstance().send(GameEventContainer.Type.WELCOME);


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


