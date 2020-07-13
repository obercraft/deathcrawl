package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double WINDOW_WIDTH = 800;
    private static final double WINDOW_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox tileMap = new VBox();
        Scene content = new Scene(tileMap, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(content);
        CardHolder cardHolder = new CardHolder();

        cardHolder.addCard("card 1 1");
        cardHolder.addCard("card 1 2");
        cardHolder.addCard("card 1 3");
        cardHolder.addCard("card 1 4");

        CardHolder cardHolderTarget = new CardHolder();
        cardHolderTarget.addCard("card 2 1");
        cardHolderTarget.addCard("card 2 2");
        cardHolderTarget.addCard("card 2 3");
        cardHolderTarget.addCard("card 2 4");
        cardHolderTarget.addCard("card 2 5");
        cardHolderTarget.addCard("card 2 6");
        cardHolderTarget.addCard("card 2 7");
        cardHolderTarget.addCard("card 2 8");
        cardHolderTarget.addCard("card 2 9");
        tileMap.getChildren().add(cardHolder);
        tileMap.getChildren().add(cardHolderTarget);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


