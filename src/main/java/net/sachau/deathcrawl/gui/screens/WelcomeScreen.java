package net.sachau.deathcrawl.gui.screens;

import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class WelcomeScreen extends StackPane {

    public WelcomeScreen(double width, double height) {
        setMinHeight(height);
        setMinWidth(width);

        getChildren().add(new Text("Welcome to deathcrawl"));
    }
}
