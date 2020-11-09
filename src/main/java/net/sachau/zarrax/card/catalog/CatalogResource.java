package net.sachau.zarrax.card.catalog;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.text.TextFlow;
import net.sachau.zarrax.Logger;
import net.sachau.zarrax.card.Card;
import net.sachau.zarrax.card.CardParser;
import net.sachau.zarrax.util.CardUtils;

import java.util.*;

public interface CatalogResource {

    void init(Catalog catalog, boolean withTexts) throws Exception;
}
