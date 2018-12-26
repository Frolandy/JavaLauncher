package jlauncher.slave.controller;

import jlauncher.slave.GUI.GuiEvents;
import jlauncher.slave.app.controller.AppController;
import jlauncher.slave.app.server.SlaveServer;
import jlauncher.slave.app.storage.AppStorage;

public class SlaveController implements GuiEvents {

    private Integer defaultPort = 25552;
    private AppController appController = new AppController(new AppStorage());
    private SlaveServer server = new SlaveServer(defaultPort);
    private SlavePacketsParser parser = new SlavePacketsParser(appController, server);

    @Override
    public void restart() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void exit() {
        appController.stopAll();
    }
}
