package net.sachau.zarrax.v2;

import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GGraphics {

    private double width;
    private double height;
    private boolean initialized;
    private HBox loadScreen;

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
        loadScreen = new HBox();
        loadScreen.setMinHeight(height);
        loadScreen.setMaxHeight(height);
        loadScreen.setMinWidth(width);
        loadScreen.setMinWidth(width);

        loadScreen.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Text pleaseWait = new Text("loading Zarrax");
        pleaseWait.setFill(Color.WHITE);
        loadScreen.getChildren().addAll(pleaseWait);
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

    public HBox getLoadScreen() {
        return loadScreen;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
