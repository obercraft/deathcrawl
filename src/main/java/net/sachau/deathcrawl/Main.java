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
    Player player = new Player();
    Scene content = new Scene(new GameScreen(), GameScreen.WINDOW_WIDTH, GameScreen.WINDOW_HEIGHT);
    content.getStylesheets().add(this.getClass().getResource("/application.css").toExternalForm());
    primaryStage.setScene(content);

    //for (Card c : player.getParty().getCards()) {
    //    c.triggerPhaseEffects(CardEffect.Phase.PREPARE);
    // }


    primaryStage.show();

  }

  //@Override
  public void startOld(Stage primaryStage) throws Exception {


    Player player = new Player();

    Reflections reflections = new Reflections("net.sachau.deathcrawl.cards");
    Set<Class<?>> basic = reflections.getTypesAnnotatedWith(Basic.class);

    for (Class b : basic) {
      for (int i = 0; i < 2; i++) {
        Card card = (Card) b.newInstance();
        card.setOwner(player);
        player
          .getDraw()
          .add(card);

      }
    }
    player.getDraw()
      .shuffe();


    DrawPile drawPile = new DrawPile(player);
    DiscardPile discardPile = new DiscardPile(player);
    HandHold hand = new HandHold(player, 5);

    Deck hazards = new Deck();

    for (int i = 0; i < 3; i++) {
      Goblin goblin = new Goblin();
      goblin.setVisible(true);
      if (i == 0) {
        goblin.getConditions().add(new Armor());
      } else if (i == 1) {
        goblin.getConditions().add(new Guard());
        goblin.getConditions().add(new Armor());
      }
      hazards.add(goblin);
    }

    player.setHazard(hazards);

    player.getParty().add(new Warrior());
    player.getParty().add(new Wizard());
    player.getParty().add(new Thief());

    // PlayArea playArea = new PlayArea(player, 5);
//
//
//        PartyArea partyArea = new PartyArea(player, 5);
//
//        VBox rows = new VBox();
//        Scene content = new Scene(rows, WINDOW_WIDTH, WINDOW_HEIGHT);
//        primaryStage.setScene(content);
//
//        HBox row1 = new HBox();
//        HBox row2 = new HBox();
//        HBox row3 = new HBox();
//
//        row1.getChildren().addAll(playArea, drawPile);
//        row2.getChildren().addAll(hand, discardPile);
//        row3.getChildren().addAll(partyArea);
//        rows.getChildren().addAll(row1, row2, row3);

    Scene content = new Scene(new CardBoard(player), GameScreen.WINDOW_WIDTH, GameScreen.WINDOW_HEIGHT);
    primaryStage.setScene(content);

    for (Card c : player.getParty().getCards()) {
      c.triggerPhaseEffects(CardEffect.Phase.PREPARE);
    }


    primaryStage.show();


  }


  public static void main(String[] args) {
    launch(args);
  }
}


