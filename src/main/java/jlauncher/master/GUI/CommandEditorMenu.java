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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

class CommandEditorMenu {

    private boolean isNewFile = false;

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
        addRowMenuItem.setOnAction(e -> Platform.runLater(() -> {
            //TODO: mainStage.addEmptyModel();
        }));

        return addRowMenuItem;
    }

    private MenuItem getRemoveRowMenuItem(){
        MenuItem removeRowMenuItem = new MenuItem("Remove row");

        removeRowMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        removeRowMenuItem.setOnAction(e -> {
            //TODO: mainStage.removeItem();
        });

        return removeRowMenuItem;
    }

    private MenuItem getNewFileFileMenuItem(){
        MenuItem newFileMenuItem = new MenuItem("New File");

        newFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        newFileMenuItem.setOnAction(e -> {
            isNewFile = true;
            Platform.runLater(() -> {
                //TODO: mainStage.clearAll();
            });
        });

        return newFileMenuItem;
    }

    private MenuItem getOpenFileMenuItem(Stage stage){
        MenuItem openFileMenuItem = new MenuItem("Open File");

        openFileMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        openFileMenuItem.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("./")); //TODO: (pathStorage.getCurrentDirectory())
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*", "*.conf"); //TODO: (pathStorage.getConfigFileBase(), pathStorage.getConfigFileExtension())
            File file = chooser.showOpenDialog(stage);
            if(file != null){
                isNewFile = false;
                //TODO: pathStorage.changeCurrentPathToFile(file);
                Platform.runLater(() -> {
                    //TODO: mainStage.clearAll();
                    //TODO: mainStage.readConfig();
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
                chooser.setInitialDirectory(new File("./")); //TODO: (pathStorage.getCurrentDirectory())
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*", "*.conf"); //TODO: (pathStorage.getConfigFileBase(), pathStorage.getConfigFileExtension())
                File file = chooser.showOpenDialog(stage);
                if(file != null){
                    isNewFile = true;
                    //TODO: pathStorage.storageConfigDirectory(f)
                }
            }
            //TODO: mainStage.saveConfig(pathStorage.getCurrentPathToFile())
        });

        return saveMenuItem;
    }

    private MenuItem getSaveAsFileMenuItem(Stage stage){
        MenuItem saveAsMenuItem = new MenuItem("Save As...");

        saveAsMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));

        saveAsMenuItem.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("./")); //TODO: (pathStorage.getCurrentDirectory())
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("*", "*.conf"); //TODO: (pathStorage.getConfigFileBase(), pathStorage.getConfigFileExtension())
            File file = chooser.showOpenDialog(stage);
            if(file != null){
                isNewFile = true;
                //TODO: pathStorage.storageConfigDirectory(f)
            }
            //TODO: mainStage.saveConfig(pathStorage.getCurrentPathToFile())
        });

        return saveAsMenuItem;
    }
}
