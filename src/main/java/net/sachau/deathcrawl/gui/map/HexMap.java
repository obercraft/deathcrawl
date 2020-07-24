package net.sachau.deathcrawl.gui.map;

import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.Logger;
import net.sachau.deathcrawl.dto.Player;
import net.sachau.deathcrawl.gui.images.Tile;
import net.sachau.deathcrawl.gui.images.TileSet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class HexMap extends AnchorPane implements Observer {

    public static final DataFormat mapFormat = new DataFormat("map");

    public final static double r = 40; // the inner radius from hexagon center to outer corner
    public final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    public final static double TILE_HEIGHT = 2 * r;
    public final static double TILE_WIDTH = 2 * n;
    private final ImageView partyCounter;

    private AnchorPane map = new AnchorPane();

    List<MapTile> tilesSet = new ArrayList<>();

    Player player;

    public HexMap(Player player) {
        super();
        Game.events()
                .addObserver(this);
        this.player = player;

        int rowCount = 10; // how many rows of tiles should be created
        int tilesPerRow = 10; // the amount of tiles that are contained in each row
        int xStartOffset = 40; // offsets the entire field to the right
        int yStartOffset = 40; // offsets the entire fiels downwards

        for (int x = 0; x < tilesPerRow; x++) {
            for (int y = 0; y < rowCount; y++) {
                double xCoord = x * TILE_WIDTH + (y % 2) * n + xStartOffset;
                double yCoord = y * TILE_HEIGHT * 0.75 + yStartOffset;

                Logger.info(xCoord + ", " + yCoord);
                MapTile tile = new MapTile(player, new MapCoord(x, y, xCoord, yCoord, MapCoord.Type.VALLEY));
                getChildren().add(tile);
                tilesSet.add(tile);
            }
        }


        fill(tilesSet, MapCoord.Type.MOUNTAINS, 5);
        fill(tilesSet, MapCoord.Type.CAVES, 5);
        fill(tilesSet, MapCoord.Type.WOODS, 9);

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
            map.put(HexMap.mapFormat, player.getMapCoord());
            db.setContent(map);
            event.consume();

        });


    }

    private MapTile startCoord(List<MapTile> tiles) {
        int randomNum = ThreadLocalRandom.current()
                .nextInt(0, tiles.size());
        return tilesSet.get(randomNum);
    }

    private void fill(List<MapTile> tiles, MapCoord.Type type, int count) {
        int i = 0;
        while (i < count) {
            int randomNum = ThreadLocalRandom.current()
                    .nextInt(0, tiles.size());
            if (tiles.get(randomNum)
                    .getMapCoord()
                    .getType()
                    .equals(MapCoord.Type.VALLEY)) {
                tiles.get(randomNum)
                        .getMapCoord()
                        .setType(type);
                tiles.get(randomNum)
                        .setFill(type.getColor());
                i++;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        switch (Game.get(arg)) {
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
