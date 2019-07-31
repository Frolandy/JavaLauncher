package jlauncher.master.GUI;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jlauncher.master.controller.Controller;

public class MainStage {

    private double windowHeight = 500;
    private double windowWidth = 695;
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);
    private LauncherMenu menu;
    private LauncherTable table = new LauncherTable(windowHeight);

    public MainStage(Controller controller){
        menu = new LauncherMenu(this, controller);
        stage.setTitle("JLauncher");
        stage.setOnCloseRequest(e -> System.exit(0));
        VBox vbox = new VBox(menu.getMenuBar(), table.getTable());
        stage.setRoot(vbox);
    }

    Stage getPrimaryStage() { return this.stage; }

    public void updateAllStages(){
        table.clear();
        table.readConfig();
    }
}
