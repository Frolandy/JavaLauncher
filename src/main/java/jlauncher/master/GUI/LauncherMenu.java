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

    private boolean isNewFile = false;
    private MainStage stage;

    LauncherMenu(MainStage stage){
        this.stage = stage;
    }

    MenuBar getMenuBar(){

        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(menuBar, Priority.ALWAYS);
        menuBar.setUseSystemMenuBar(true);

        Menu fileMenu = new Menu("File");
        Menu toolsMenu = new Menu("Tools");

        fileMenu.getItems().add(getOpenFileMenuItem(stage.getPrimaryStage()));

        toolsMenu.getItems().add(getCommandEditorMenuItem());
        toolsMenu.getItems().add(getVariableEditorMenuItem());

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(toolsMenu);

        return menuBar;
    }

    private MenuItem getOpenFileMenuItem(Stage stage){
        MenuItem openFileItem = new MenuItem("Open File");

        openFileItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        openFileItem.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("./"));
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("launcher","*.conf");
            chooser.getExtensionFilters().add(filter);
            File file = chooser.showOpenDialog(stage);
        });

        return openFileItem;
    }

    private MenuItem getCommandEditorMenuItem(){
        MenuItem editorVarItem = new MenuItem("Command Editor");

        editorVarItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
        editorVarItem.setOnAction(e -> new CommandEditorStage());

        return editorVarItem;
    }

    private MenuItem getVariableEditorMenuItem(){
        MenuItem editorVarItem = new MenuItem("Variable Editor");

        editorVarItem.setAccelerator(new KeyCodeCombination(KeyCode.F2));

        editorVarItem.setOnAction(e -> new VariableEditorStage());

        return editorVarItem;
    }
}
