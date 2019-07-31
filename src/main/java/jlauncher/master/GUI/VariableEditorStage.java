package jlauncher.master.GUI;

import javafx.scene.layout.VBox;
import jlauncher.master.controller.Controller;

class VariableEditorStage {
    private double windowHeight = 500;
    private double windowWidth = 700;
    private VariableEditorTable table = new VariableEditorTable();
    private EditorTableMenu menu;
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);

    VariableEditorStage(Controller controller){
        menu = new EditorTableMenu(table, controller);
        stage.setTitle("Variable Editor");
        stage.setRoot(new VBox(menu.getMenuBar(stage), table.getTable()));
    }
}
