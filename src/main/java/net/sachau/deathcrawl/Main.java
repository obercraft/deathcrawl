package net.sachau.deathcrawl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sachau.deathcrawl.cards.*;
import net.sachau.deathcrawl.cards.classes.Thief;
import net.sachau.deathcrawl.cards.classes.Warrior;
import net.sachau.deathcrawl.cards.classes.Wizard;
import net.sachau.deathcrawl.cards.monsters.Goblin;
import net.sachau.deathcrawl.conditions.Armor;
import net.sachau.deathcrawl.conditions.Guard;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.*;
import net.sachau.deathcrawl.gui.screens.GameScreen;
import org.reflections.Reflections;

import java.util.Set;

public class Main extends Application {


  @Override
  public void start(Stage primaryStage) throws Exception {
    Scene content = new Scene(new GameScreen(), GameScreen.WINDOW_WIDTH, GameScreen.WINDOW_HEIGHT);
    content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
    primaryStage.setScene(content);




    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}


