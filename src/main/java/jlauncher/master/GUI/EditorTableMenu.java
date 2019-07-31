package jlauncher.master.GUI;


import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jlauncher.master.controller.Controller;
import jlauncher.master.path.storage.PathStorage;
import java.io.File;

class EditorTableMenu {

    private boolean isNewFile = false;
    private AbstractTable abstractTable;
    private PathStorage pathStorage = PathStorage.getInstance();
    private Controller controller;

    EditorTableMenu(AbstractTable table, Controller controller){
        abstractTable = table;
        this.controller = controller;
    }

    MenuBar getMenuBar(Stage stage){
        MenuBar menuBar = new MenuBar();

        menuBar.maxWidth(Double.MAX_VALUE);
        VBox.setVgrow(menuBar, Priority.ALWAYS);
        menuBar.setUseSystemMenuBar(true);

        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");

        fileMenu.getItems().add(getOpenFileMenuItem(stage));
        fileMenu.getItems().add(getNewFileFileMenuItem());
        fileMenu.getItems().add(getSaveMenuItem(stage));
        fileMenu.getItems().add(getSaveAsFileMenuItem(stage));

        editMenu.getItems().add(getAddRowMenuItem());
        editMenu.getItems().add(getRemoveRowMenuItem());

        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);

        return menuBar;
    }

    private MenuItem getAddRowMenuItem(){
        MenuItem addRowMenuItem = new MenuItem("Add row");

        addRowMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.INSERT));
        addRowMenuItem.setOnAction(e -> Platform.runLater(() -> abstractTable.addNewItem()));

        return addRowMenuItem;
    }

    private MenuItem getRemoveRowMenuItem(){
        MenuItem removeRowMenuItem = new MenuItem("Remove row");

        removeRowMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        removeRowMenuItem.setOnAction(e -> abstractTable.removeItem());

        return removeRowMenuItem;
    }

    private MenuItem getNewFileFileMenuItem(){
        MenuItem newFileMenuItem = new MenuItem("New File");

        newFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        newFileMenuItem.setOnAction(e -> {
            isNewFile = true;
            Platform.runLater(() -> abstractTable.clear());
        });

        return newFileMenuItem;
    }

    private MenuItem getOpenFileMenuItem(Stage stage){
        MenuItem openFileMenuItem = new MenuItem("Open File");

        openFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        openFileMenuItem.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File(pathStorage.getCurrentDir()));
            FileChooser.ExtensionFilter filter =
                    new FileChooser.ExtensionFilter(pathStorage.getConfigFileBase(),pathStorage.getConfigFileExtension());
            File file = chooser.showOpenDialog(stage);
            if(file != null){
                isNewFile = false;
                pathStorage.changeCurrentPathToFile(file);
                Platform.runLater(() -> {
                    abstractTable.clear();
                    abstractTable.readConfig();
                });
            }
        });

        return openFileMenuItem;
    }

    private MenuItem getSaveMenuItem(Stage stage){
        MenuItem saveMenuItem = new MenuItem("Save");

        saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveMenuItem.setOnAction(e -> {
            if(isNewFile){
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File(pathStorage.getCurrentDir()));
                FileChooser.ExtensionFilter filter =
                        new FileChooser.ExtensionFilter(pathStorage.getConfigFileBase(),pathStorage.getConfigFileExtension());
                File file = chooser.showOpenDialog(stage);
                if(file != null){
                    isNewFile = false;
                    pathStorage.storeConfigDir(file);
                    abstractTable.saveConfig(controller);
                }
            }else abstractTable.saveConfig(controller);
        });

        return saveMenuItem;
    }

    private MenuItem getSaveAsFileMenuItem(Stage stage){
        MenuItem saveAsMenuItem = new MenuItem("Save As...");

        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        saveAsMenuItem.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setInitialDirectory(new File(pathStorage.getCurrentDir()));
            File file = chooser.showDialog(stage);
            if(file != null){
                isNewFile = false;
                pathStorage.storeConfigDir(file);
                abstractTable.saveConfig(controller);
            }
        });

        return saveAsMenuItem;
    }
}
