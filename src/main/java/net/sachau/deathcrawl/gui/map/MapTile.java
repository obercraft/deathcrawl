package net.sachau.deathcrawl.gui.map;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import net.sachau.deathcrawl.Event;
import net.sachau.deathcrawl.Game;
import net.sachau.deathcrawl.dto.Player;

import java.util.HashMap;
import java.util.Map;

public class MapTile extends Polygon {


    private MapCoord mapCoord;

    Player player;

    public MapTile(Player player, MapCoord mapCoord) {
        super();
        this.player = player;
        // creates the polygon using the corner coordinates

        this.mapCoord = mapCoord;

        double x = mapCoord.getX();
        double y = mapCoord.getY();

        getPoints().addAll(
                x, y,
                x, y + HexMap.r,
                x + HexMap.n, y + HexMap.r * 1.5,
                x + HexMap.TILE_WIDTH, y + HexMap.r,
                x + HexMap.TILE_WIDTH, y,
                x + HexMap.n, y - HexMap.r * 0.5
        );

        // set up the visuals and a click listener for the tile
        setFill(MapCoord.Type.VALLEY.getColor());
        setStrokeWidth(1);
        setStroke(Color.BLACK);

        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(HexMap.mapFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();

        });

        setOnDragDetected(event -> {
            if (player.getMapCoord()
                    .equals(mapCoord)) {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(HexMap.mapFormat, getMapCoord());
                db.setContent(map);
            }
            event.consume();

        });


        setOnDragDropped(event -> {

            MapCoord sourceCoord = (MapCoord) event.getDragboard()
                    .getContent(HexMap.mapFormat);


            if (player.getMoves() > 0 && Math.abs(sourceCoord.getColumn() - mapCoord.getColumn()) <= 1 && Math.abs(sourceCoord.getRow() - mapCoord.getRow()) <= 1) {
                player.setMapCoord(mapCoord);
                int moves = player.getMoves() -1;
                player.setMoves(moves);
                Game.events()
                        .send(Event.PARTYMOVE);
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
