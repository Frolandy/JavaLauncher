package jlauncher.slave.app.storage;

import jlauncher.packets.AppLog;
import jlauncher.utils.Listener;

import java.io.File;
import java.util.List;

class App implements ApplicationApi {

    private ProcessBuilder processBuilder;
    private Process process;
    private String appName;

    public App(String name){
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
    public Boolean start(Listener<AppLog> logListener) {
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
    public Boolean stop() {
        if(process != null){
            process.destroy();
            return true;
        }else return false;
    }

    @Override
    public Boolean isAlive() {
        if(process != null) return process.isAlive();
        else return false;
    }

}