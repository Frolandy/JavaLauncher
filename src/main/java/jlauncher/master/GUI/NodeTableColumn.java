package jlauncher.master.GUI;

import javafx.scene.Node;
import javafx.scene.control.TableColumn;

class NodeTableColumn<T> extends TableColumn<T, Node> {
    NodeTableColumn(){
        setCellFactory(e -> new NodeTableCell<>());
    }
}
