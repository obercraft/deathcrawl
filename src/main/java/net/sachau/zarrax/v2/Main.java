package net.sachau.zarrax.v2;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        GEngine.getInstance().init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}


