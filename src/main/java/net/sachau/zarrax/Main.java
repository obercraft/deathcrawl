package net.sachau.zarrax;

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
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.GameEventContainer;
import net.sachau.zarrax.engine.GameEvent;
import net.sachau.zarrax.gui.Fonts;
import net.sachau.zarrax.gui.screens.GameScreen;
import net.sachau.zarrax.gui.text.TextParser;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        int fontSize = 12;
        Fonts.getInstance()
                .load("symbol-12", Main.class.getResourceAsStream("/fontawesome-solid-900.otf"), 12)
                .load("garamond-12", Main.class.getResourceAsStream("/gara.ttf"), 28)
                .load("standardx",Main.class.getResourceAsStream("/Adobe Caslon Pro Regular.ttf"), 16)
                .load("h1",Main.class.getResourceAsStream("/Adobe Caslon Pro Regular.ttf"), 32)
                .load("boldx",Main.class.getResourceAsStream("/caslon-bold.ttf"), 16)
                .load("standard",Main.class.getResourceAsStream("/fonts/LibreBaskerville-Regular.ttf"), fontSize)
                .load("italic",Main.class.getResourceAsStream("/fonts/LibreBaskerville-Italic.ttf"), fontSize)
                .load("bold",Main.class.getResourceAsStream("/fonts/LibreBaskerville-Bold.ttf"), fontSize)

        ;

        //primaryScreenBounds.getHeight()
        double width = primaryScreenBounds.getWidth() - 100;
        double height = primaryScreenBounds.getHeight() - 100;

        Scene content = new Scene(new GameScreen(width, height), width, height);

        GameEngine.getInstance().setInitialized(true);



        content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
        primaryStage.setScene(content);


        primaryStage.show();

        content.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            final KeyCombination keyComb = new KeyCodeCombination(KeyCode.ENTER);
            public void handle(KeyEvent ke) {
                if (keyComb.match(ke)) {
                    Logger.debug("Key Pressed: " + GameEvent.getInstance().getStage() + ", l=" + GameEvent.getInstance().getLastStage());
                    if (GameEventContainer.Type.WAITINGFORPLAYERACTION.equals(GameEvent.getInstance().getType())) {
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


