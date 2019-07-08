package jlauncher.master.GUI;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainStage {

    private double windowHeight = 500;
    private double windowWidth = 695;
    private BaseStage stage = new BaseStage(windowWidth, windowHeight);
    private LauncherMenu menu = new LauncherMenu();

    public MainStage(){
        stage.setTitle("JLauncher");
        stage.setOnCloseRequest(e -> System.exit(0));
        VBox vbox = new VBox(menu.getMenuBar(this));
        stage.setRoot(vbox);
    }

    Stage getPrimaryStage() { return this.stage; }


}
