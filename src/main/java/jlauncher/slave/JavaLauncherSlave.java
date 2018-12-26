package jlauncher.slave;

import javafx.application.Application;
import javafx.stage.Stage;
import jlauncher.slave.controller.SlaveController;


public class JavaLauncherSlave extends Application {

    private SlaveController slaveController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        slaveController = new SlaveController();
    }
}
