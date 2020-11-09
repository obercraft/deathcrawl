package net.sachau.zarrax.v2;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GGraphics {

    double width;
    double height;
    private boolean initialized;

    private Stage primaryStage;
    public GGraphics() {

    }

    public void init (Stage primaryStage) {
        this.primaryStage = primaryStage;
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();


        //primaryScreenBounds.getHeight()
        this.width = primaryScreenBounds.getWidth() - 100;
        this.height = primaryScreenBounds.getHeight() - 100;
        //GameScreen gameScreen = new GameScreen();
        HBox loadScreen = new HBox();
        loadScreen.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene content = new Scene(loadScreen, width, height);

        primaryStage.setScene(content);
        setInitialized(true);
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void show() {
        primaryStage.show();
    }
}
