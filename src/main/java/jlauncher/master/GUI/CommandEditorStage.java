package jlauncher.master.GUI;

import javafx.scene.layout.VBox;
import jlauncher.master.controller.Controller;

class CommandEditorStage {

    private double windowHeight = 600;
    private double windowWidth = 795;
    private CommandEditorTable table = new CommandEditorTable(windowHeight);
    private EditorTableMenu menu;
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);

    CommandEditorStage(Controller controller){
        menu = new EditorTableMenu(table, controller);
        stage.setTitle("Command Editor");
        stage.setRoot(new VBox(menu.getMenuBar(stage), table.getTable()));
    }


}
