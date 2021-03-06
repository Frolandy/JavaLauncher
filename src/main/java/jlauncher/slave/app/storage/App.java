package jlauncher.slave.app.storage;

import jlauncher.packets.AppLog;
import jlauncher.utils.Listener;

import java.io.File;
import java.util.List;

class App implements ApplicationApi {

    private ProcessBuilder processBuilder;
    private Process process;
    private String appName;

    App(String name){
        appName = name;
        processBuilder = new ProcessBuilder();
    }

    @Override
    public ApplicationApi setCmd(String cmd) {
        String[] cmdArray = cmd.split(" ");
        processBuilder.command(cmdArray);
        return this;
    }

    @Override
    public ApplicationApi setDir(File file) {
        processBuilder.directory(file);
        return this;
    }

    @Override
    public File getDir() {
        return processBuilder.directory();
    }

    @Override
    public List<String> getCmd() {
        return processBuilder.command();
    }

    @Override
    public boolean start(Listener<AppLog> logListener) {
        try{
            process = processBuilder.start();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public String getName() {
        return appName;
    }

    @Override
    public boolean stop() {
        if(process != null){
            process.destroy();
            return true;
        }else return false;
    }

    @Override
    public boolean isAlive() {
        if(process != null) return process.isAlive();
        else return false;
    }

}