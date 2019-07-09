package jlauncher.master.GUI;

import javafx.beans.property.*;

abstract class TableItemInfo {
    private StringProperty computerAddress = new SimpleStringProperty("");
    private StringProperty appName = new SimpleStringProperty("");
    private StringProperty group = new SimpleStringProperty("");
    private StringProperty cmd = new SimpleStringProperty("");
    private StringProperty dir = new SimpleStringProperty("");
    private BooleanProperty enable = new SimpleBooleanProperty(false);
    private StringProperty delay = new SimpleStringProperty("");
    private StringProperty id = new SimpleStringProperty("");
    private ObjectProperty<String[]> onStopCommands = new SimpleObjectProperty<>(new String[0]);

    public StringProperty getComputerAddressProperty() {
        return computerAddress;
    }

    public StringProperty getAppNameProperty() {
        return appName;
    }

    public StringProperty getCommandProperty() {
        return cmd;
    }

    public StringProperty getDirectoryProperty() {
        return dir;
    }

    public BooleanProperty getEnableProperty() {
        return enable;
    }

    public StringProperty getGroupProperty() {
        return group;
    }

    public StringProperty getIdProperty() {
        return id;
    }

    public StringProperty getDelayProperty() {
        return delay;
    }

    public ObjectProperty<String[]> getObjectProperty() {
        return onStopCommands;
    }

    public void setComputerAddress(String value) {
        computerAddress.set(value);
    }

    public void setAppName(String value) {
        appName.set(value);
    }

    public void setCommand(String value) {
        cmd.set(value);
    }

    public void setDirectory(String value) {
        dir.set(value);
    }

    public void setEnable(Boolean value) {
        enable.set(value);
    }

    public void setGroup(String value) {
        group.set(value);
    }

    public void setId(String value) {
        id.set(value);
    }

    public void setDelay(String value) {
        delay.set(value);
    }

    public void setStopCommands(String[] value) {
        onStopCommands.set(value);
    }
}
