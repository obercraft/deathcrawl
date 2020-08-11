package net.sachau.deathcrawl.gui.map;

import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RectMap extends AnchorPane implements Observer {

    public static final DataFormat mapFormat = new DataFormat("rectMap");

    public final static double TILE_HEIGHT = 32;
    public final static double TILE_WIDTH = 32;
    private final ImageView partyCounter;

    private AnchorPane map = new AnchorPane();

    List<MapTile> tilesSet = new ArrayList<>();

    Player player;

    public RectMap(Player player) {
        super();
        GameEvent.getInstance()
                .addObserver(this);
        this.player = player;

        int rowCount = 20; // how many rows of tiles should be created
        int tilesPerRow = 20; // the amount of tiles that are contained in each row


        Noise n = new Noise(null, 1, tilesPerRow, rowCount);
        n.initialise();
        // float[][] noiseMap = noisy.perlinNoiseMap(tilesPerRow, rowCount);

        float[][] noiseMap = n.getGrid();

        for (int x = 0; x < tilesPerRow; x++) {
            for (int y = 0; y < rowCount; y++) {

                MapCoord.Type type = MapCoord.Type.VALLEY;
                float  value = noiseMap[x][y];

                if (value < -0.3f) {
                    System.out.println(value);
                    type = MapCoord.Type.WATER;
                }

                if (value > 0.5f) {
                    type = MapCoord.Type.WOODS;

                }
                if (value > 0.6f) {
                    type = MapCoord.Type.HILL;
                }

                if (value > 0.7f) {
                    type = MapCoord.Type.MOUNTAINS;
                }

                double xCoord = x * TILE_WIDTH;
                double yCoord = y * TILE_HEIGHT;

                MapTile tile = new MapTile(player, new MapCoord(x, y, xCoord, yCoord, type));
                getChildren().add(tile);
                tilesSet.add(tile);
            }
        }



        MapTile partyTile = startCoord(tilesSet);

        player.setMapCoord(partyTile.getMapCoord());

        partyCounter = TileSet.getInstance()
                .getTile(Tile.PARTY);
        partyCounter.setX(partyTile.getMapCoord()
                .getX());
        partyCounter.setY(partyTile.getMapCoord()
                .getY());

        getChildren().add(partyCounter);

        partyCounter.setOnDragDetected(event -> {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            Map<DataFormat, Object> map = new HashMap<>();
            map.put(RectMap.mapFormat, player.getMapCoord());
            db.setContent(map);
            event.consume();

        });


    }

    private MapTile startCoord(List<MapTile> tiles) {
        int randomNum = ThreadLocalRandom.current()
                .nextInt(0, tiles.size());
        return tilesSet.get(randomNum);
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
                getChildren().remove(partyCounter);
                partyCounter.setX(player.getMapCoord()
                        .getX());
                partyCounter.setY(player.getMapCoord()
                        .getY());
                getChildren().add(partyCounter);
                return;
        }
    }
}
