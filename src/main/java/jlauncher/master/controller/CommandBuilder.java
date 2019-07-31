package jlauncher.master.controller;

public class CommandBuilder {
    private String command;
    private String dir;
    private String address;
    private String appName;
    private String group;
    private boolean enable;
    private String id;
    private String delay;
    private String[] onStopCommand;

    public void setCommand(String command) {
        this.command = command;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOnStopCommand(String[] onStopCommand) {
        this.onStopCommand = onStopCommand;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getAddress() {
        return address;
    }

    public String getAppName() {
        return appName;
    }

    public String getCommand() {
        return command;
    }

    public String getDelay() {
        return delay;
    }

    public String getDir() {
        return dir;
    }

    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }

    public String[] getOnStopCommand() {
        return onStopCommand;
    }
}
