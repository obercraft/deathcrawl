package net.sachau.zarrax.gui.map;

import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import net.sachau.zarrax.engine.GameEngine;
import net.sachau.zarrax.engine.Player;
import net.sachau.zarrax.gui.image.Tile;
import net.sachau.zarrax.gui.image.TileSet;
import net.sachau.zarrax.map.Land;

import java.util.HashMap;
import java.util.Map;

public class PartyTile extends AnchorPane {

    public PartyTile() {
        super();

        this.setWidth(Tile.WIDTH);
        this.setHeight(Tile.HEIGHT);

            this.getChildren()
                    .add(TileSet.getInstance()
                            .getTile(Tile.PARTY));

        setOnDragDetected(event -> {
                Dragboard db = this.startDragAndDrop(TransferMode.ANY);
                Map<DataFormat, Object> map = new HashMap<>();
                map.put(WorldMap.mapFormat, "party");
                db.setContent(map);
            event.consume();

        });


        /*
        setOnDragOver(event -> {
            if (event.getDragboard()
                    .hasContent(mapFormat)) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();

        });







        });
        
         */
    }

}
