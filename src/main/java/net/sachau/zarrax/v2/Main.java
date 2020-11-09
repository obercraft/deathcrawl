package net.sachau.zarrax.v2;

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
import net.sachau.zarrax.v2.GEngine;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        GEngine.getInstance().init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


