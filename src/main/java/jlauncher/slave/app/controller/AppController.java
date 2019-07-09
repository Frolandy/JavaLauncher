package jlauncher.slave.app.controller;

import jlauncher.packets.AppLog;
import jlauncher.packets.AppStatus;
import jlauncher.slave.app.storage.AppStorage;
import jlauncher.slave.app.storage.ApplicationApi;
import jlauncher.utils.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AppController {

    private AppStorage appStorage;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private List<Listener<AppStatus>> statusListener = Collections.synchronizedList(new ArrayList<>());

    public AppController(AppStorage storage){
        appStorage = storage;
        Runnable task = this::checkApps;
        executor.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
    }

    public void addListener(Listener<AppStatus> listener){
        statusListener.add(listener);
    }

    public boolean start(String appName, String dir, String cmd, Listener<AppLog> listener){
        return appStorage.add(appName).setDir(new File(dir)).setCmd(cmd).start(listener);
    }

    public boolean stop(String name){
        return appStorage.remove(name);
    }

    public AppStorage getStorage(){
        return appStorage;
    }

    public void stopAll(){
        appStorage.get().forEach(ApplicationApi::stop);
        executor.shutdown();
    }

    private void checkApps(){
        appStorage.get().forEach(app -> statusListener.forEach(listener -> listener.sendData(new AppStatus(app.getName(), app.isAlive()))));
    }
}
