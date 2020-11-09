package net.sachau.zarrax.gui.map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import net.sachau.zarrax.engine.*;
import net.sachau.zarrax.gui.image.Tile;
import net.sachau.zarrax.gui.screen.Autowired;
import net.sachau.zarrax.map.Land;
import net.sachau.zarrax.v2.GEngine;

import java.util.*;

@GuiComponent
public class WorldMap extends HBox implements Observer {


    private final GEngine engine;

    public static DataFormat mapFormat =new DataFormat("map");
    private AnchorPane map = new AnchorPane();

    private PartyTile partyTile;

    private StringProperty landInfo = new SimpleStringProperty();
    private StringProperty moveInfo = new SimpleStringProperty();
    private Text landInfoText = new Text();
    private Text moveInfoText = new Text();

    @Autowired
    public WorldMap(GEngine engine) {
        super();
        this.engine = engine;
        GameEvent.getInstance()
                .addObserver(this);

        landInfo.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                landInfoText.setText(newValue);
            }
        });

        moveInfo.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                moveInfoText.setText(newValue);
            }
        });

    }

    public void init() {
        int columns = engine.getWorld().getColumns();
        int rows = engine.getWorld().getRows();
        Player player = engine.getPlayer();
        Land[][] worldMap = engine.getWorld().getMap();
        map.getChildren().clear();
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Node n = new MapTile(worldMap[x][y], x, y);
                n.relocate(x * Tile.WIDTH, y * Tile.HEIGHT);
                map.getChildren().add(n);

            }
        }

        partyTile = new PartyTile();
        partyTile.relocate(player.getX() * Tile.WIDTH, player.getY() * Tile.HEIGHT);
        map.getChildren().add(partyTile);

        landInfoText.setFill(Color.WHITE);

        moveInfoText.setText("" + player.getMoves());

        getChildren().addAll(map, landInfoText, moveInfoText);

    }

//    private void fill(List<MapTile> tiles, MapCoord.Type type, int count) {
//        int i = 0;
//        while (i < count) {
//            int randomNum = ThreadLocalRandom.current()
//                    .nextInt(0, tiles.size());
//            if (tiles.get(randomNum)
//                    .getMapCoord()
//                    .getType()
//                    .equals(MapCoord.Type.VALLEY)) {
//                tiles.get(randomNum)
//                        .getMapCoord()
//                        .setType(type);
//                tiles.get(randomNum)
//                        .setFill(type.getColor());
//                i++;
//            }
//        }
//    }

    @Override
    public void update(Observable o, Object arg) {
        switch (GameEvent.getType(arg)) {
            case PARTYMOVE:
                Player player = engine.getPlayer();
                partyTile.relocate(player.getX() * Tile.WIDTH, player.getY() * Tile.HEIGHT);
                return;
            case LANDINFO:
                Land land = (Land) GameEvent.getInstance().getData();

                landInfo.set(land.toString());
        }
    }
}
