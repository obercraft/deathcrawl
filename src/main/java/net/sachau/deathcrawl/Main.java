package net.sachau.deathcrawl;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.sachau.deathcrawl.events.Event;
import net.sachau.deathcrawl.events.GameEvent;
import net.sachau.deathcrawl.gui.screens.GameScreen;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //primaryScreenBounds.getHeight()
        double width = primaryScreenBounds.getWidth() - 100;
        double height = primaryScreenBounds.getHeight() - 100;

        Scene content = new Scene(new GameScreen(width, height), width, height);





        content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
        primaryStage.setScene(content);


        primaryStage.show();

        content.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    Logger.debug("Key Pressed: " + GameEvent.getInstance().getStage() + ", l=" + GameEvent.getInstance().getLastStage());
                    if (Event.Type.WAITINGFORPLAYERACTION.equals(GameEvent.getInstance().getType())) {
                        Logger.debug("Key Pressed: " + keyComb);
                        GameEvent.getInstance().send(Event.Type.PLAYERACTIONDONE);
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


