package jlauncher.master.GUI;

import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class LauncherTable {

    class LuancherTableMenuInfo extends TableItemInfo{

        private Button requestButton = new Button();
        private Button onlineButton = new Button();
        private ObjectProperty<Node> requestButtonProperty = new SimpleObjectProperty<>(requestButton);
        private ObjectProperty<Node> onlineButtonProperty = new SimpleObjectProperty<>(onlineButton);

        LuancherTableMenuInfo(){
        }
    }
}
