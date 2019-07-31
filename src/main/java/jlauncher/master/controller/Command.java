package jlauncher.master.controller;

import jlauncher.master.GUI.TableItemInfo;

public class Command {
    private String command;
    private String dir;
    private String address;
    private String appName;
    private String group;
    private boolean enable;
    private String id;
    private String delay;
    private String[] onStopCommand;

    public Command(TableItemInfo item){
        command = item.getCommandProperty().getValue();
        dir = item.getDirectoryProperty().getValue();
        address = item.getComputerAddressProperty().getValue();
        appName = item.getAppNameProperty().getValue();
        group = item.getGroupProperty().getValue();
        enable = item.getEnableProperty().getValue();
        id = item.getIdProperty().getValue();
        delay = item.getDelayProperty().getValue();
        onStopCommand = item.getObjectProperty().getValue();
    }

    public Command(CommandBuilder commandBuilder){
        command = commandBuilder.getCommand();
        dir = commandBuilder.getDir();
        address = commandBuilder.getAddress();
        appName = commandBuilder.getAppName();
        group = commandBuilder.getGroup();
        enable = commandBuilder.isEnable();
        id = commandBuilder.getId();
        delay = commandBuilder.getDelay();
        onStopCommand = commandBuilder.getOnStopCommand();
    }

    public String getCommand(){
        return command;
    }

    public String getDir(){
        return dir;
    }

    public String getComputerAddress(){
        return address;
    }

    public String getAppName(){
        return appName;
    }

    public String getGroup(){
        return group;
    }

    public boolean getEnable(){
        return enable;
    }

    public String getId(){
        return id;
    }

    public String getDelay(){
        return delay;
    }

    public String[] getOnStopCommands(){
        return onStopCommand;
    }
}
