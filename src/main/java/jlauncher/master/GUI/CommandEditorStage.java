package jlauncher.master.GUI;

import javafx.scene.layout.VBox;

class CommandEditorStage {

    private double windowHeight = 600;
    private double windowWidth = 795;
    private CommandEditorTable table = new CommandEditorTable(windowHeight);
    private EditorTableMenu menu;
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);

    CommandEditorStage(){
        stage.setTitle("Command Editor");
        menu = new EditorTableMenu(table);
        stage.setRoot(new VBox(menu.getMenuBar(stage), table.getTable()));
    }


}
