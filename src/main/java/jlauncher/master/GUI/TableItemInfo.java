package jlauncher.master.GUI;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public interface TableItemInfo {
    StringProperty getComputerAddressProperty();
    StringProperty getAppNameProperty();
    StringProperty getCommandProperty();
    StringProperty getDirectoryProperty();
    BooleanProperty getEnableProperty();
    StringProperty getGroupProperty();
    StringProperty getIdProperty();
    StringProperty getDelayProperty();
    ObjectProperty<String[]> getObjectProperty();

    void setComputerAddressProperty(String value);
    void setAppNameProperty(String value);
    void setCommandProperty(String value);
    void setDirectoryProperty(String value);
    void setEnableProperty(Boolean value);
    void setGroupProperty(String value);
    void setIdProperty(String value);
    void setDelayProperty(String value);
    void setObjectProperty(String[] value);
}
