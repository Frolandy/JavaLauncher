package jlauncher.slave;

import javafx.application.Application;
import javafx.stage.Stage;
import jlauncher.slave.GUI.Tray;
import jlauncher.slave.controller.SlaveController;


public class JavaLauncherSlave extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SlaveController slaveController = new SlaveController();
        Tray trayIcon = new Tray(slaveController);
        trayIcon.show();
    }
}
