package net.sachau.deathcrawl.gui.map;

import javafx.scene.Node;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import net.sachau.deathcrawl.engine.GameEventContainer;
import net.sachau.deathcrawl.engine.GameEvent;
import net.sachau.deathcrawl.engine.Player;
import net.sachau.deathcrawl.gui.images.TileSet;

import java.util.HashMap;
import java.util.Map;

public class MapTile extends AnchorPane {


    private MapCoord mapCoord;

    Player player;

    public MapTile(Player player, MapCoord mapCoord) {
        super();
        this.player = player;
        // creates the polygon using the corner coordinates

        this.mapCoord = mapCoord;

        this.setWidth(RectMap.TILE_WIDTH);
        this.setHeight(RectMap.TILE_HEIGHT);


        if (mapCoord.getType().getTile() != null) {
            this.getChildren().add(TileSet.getInstance().getTile(mapCoord.getType().getTile()));
        } else {
            // set up the visuals and a click listener for the tile
            Rectangle rectangle = new Rectangle(RectMap.TILE_WIDTH,RectMap.TILE_HEIGHT);
            rectangle.setFill(mapCoord.getType()
                    .getColor());
            rectangle.setStrokeWidth(1);
            rectangle.setStroke(Color.BLACK);
            this.getChildren().add(rectangle);
        }

        this.relocate(mapCoord.getX(),mapCoord.getY());


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
