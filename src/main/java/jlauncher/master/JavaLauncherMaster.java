package jlauncher.master;

import javafx.application.Application;
import javafx.stage.Stage;
import jlauncher.master.GUI.MainStage;


public class JavaLauncherMaster extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainStage mainStage = new MainStage();
    }
}