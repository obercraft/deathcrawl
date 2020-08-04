package net.sachau.deathcrawl.gui.map;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import net.sachau.deathcrawl.engine.GameEventContainer;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.engine.Player;

import java.util.HashMap;
import java.util.Map;

public class MapTile extends Rectangle {


    private MapCoord mapCoord;

    Player player;

    public MapTile(Player player, MapCoord mapCoord) {
        super();
        this.player = player;
        // creates the polygon using the corner coordinates

        this.mapCoord = mapCoord;

        double x = mapCoord.getX();
        double y = mapCoord.getY();

        this.setX(x);
        this.setY(y);
        this.setWidth(RectMap.TILE_WIDTH);
        this.setHeight(RectMap.TILE_HEIGHT);

        // set up the visuals and a click listener for the tile
        setFill(mapCoord.getType().getColor());
        setStrokeWidth(1);
        setStroke(Color.BLACK);

        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(RectMap.mapFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();

        });

        setOnDragDetected(event -> {
            if (player.getMapCoord()
                    .equals(mapCoord)) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(RectMap.mapFormat, getMapCoord());
                db.setContent(map);
            }
            event.consume();

        });


        setOnDragDropped(event -> {

            MapCoord sourceCoord = (MapCoord) event.getDragboard()
                    .getContent(RectMap.mapFormat);


            if (player.getMoves() > 0 && Math.abs(sourceCoord.getColumn() - mapCoord.getColumn()) <= 1 && Math.abs(sourceCoord.getRow() - mapCoord.getRow()) <= 1) {
                player.setMapCoord(mapCoord);
                int moves = player.getMoves() -1;
                player.setMoves(moves);
                GameEvent.getInstance()
                        .send(GameEventContainer.Type.PARTYMOVE);
            }

            event.consume();
        });
    }

    public MapCoord getMapCoord() {
        return mapCoord;
    }

    public void setMapCoord(MapCoord mapCoord) {
        this.mapCoord = mapCoord;
    }

    @Override
    public String toString() {
        return mapCoord.getColumn() + "," + mapCoord.getRow() + "," + mapCoord.getX() + "," + mapCoord.getY();

    }
}
