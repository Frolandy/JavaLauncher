package jlauncher.master.GUI;

import javafx.scene.Node;
import javafx.scene.control.TableCell;

class NodeTableCell<T> extends TableCell<T, Node> {
    NodeTableCell(){
        itemProperty().addListener((obs, oldVal, newVal) -> setGraphic(newVal));
    }
}
