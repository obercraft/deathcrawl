package net.sachau.zarrax.gui.screen;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.sachau.zarrax.engine.ApplicationContext;

public abstract  class ScreenWithSidebar extends HBox {

    private VBox mainArea = new VBox();
    private VBox sideArea = new VBox();

    public ScreenWithSidebar() {
        super();
        double width = ApplicationContext.getInstance().getWidth();
        double height = ApplicationContext.getInstance().getHeight();

        setWidth(width);
        setHeight(height);

        double sideWidth = 200;
        double mainWidth = width - sideWidth;

        mainArea.setMinHeight(height);
        mainArea.setMaxHeight(height);
        mainArea.setMaxWidth(mainWidth);
        mainArea.setMinWidth(mainWidth);
        sideArea.setMinHeight(height);
        sideArea.setMaxHeight(height);
        sideArea.setMinWidth(sideWidth);
        sideArea.setMaxWidth(sideWidth);

        getChildren().addAll(mainArea, sideArea);

    }

    public VBox getMainArea() {
        return mainArea;
    }

    public VBox getSideArea() {
        return sideArea;
    }

}
