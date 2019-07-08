package jlauncher.master.GUI;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

class LauncherMenu {

    Boolean isNewFile = false;

    MenuBar getMenuBar(Stage stage){
        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(menuBar, Priority.ALWAYS);
        menuBar.setUseSystemMenuBar(true);

        Menu fileMenu = new Menu("File");
        Menu toolsMenu = new Menu("Tools");

        MenuItem openFileItem = new MenuItem("Open File");

        openFileItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        openFileItem.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("./"));
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("launcher","*.conf");
            chooser.getExtensionFilters().add(filter);
            File file = chooser.showOpenDialog(stage);
        });

        fileMenu.getItems().add(openFileItem);

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(toolsMenu);

        return menuBar;
    }
}
