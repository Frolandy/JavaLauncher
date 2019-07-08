package jlauncher.master.GUI;

import javafx.scene.layout.VBox;

class CommandEditorStage {

    private double windowHeight = 600;
    private double windowWidth = 795;
    private CommandEditorTable table = new CommandEditorTable();
    private CommandEditorMenu menu = new CommandEditorMenu();
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);

    CommandEditorStage(){
        stage.setTitle("Command Editor");
        stage.setRoot(new VBox(menu.getMenuBar(stage)));
    }
}
