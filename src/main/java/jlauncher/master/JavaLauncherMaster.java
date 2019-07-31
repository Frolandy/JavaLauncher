package jlauncher.master;

import javafx.application.Application;
import javafx.stage.Stage;
import jlauncher.master.GUI.MainStage;
import jlauncher.master.controller.Controller;


public class JavaLauncherMaster extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Controller controller = new Controller();
    }
}