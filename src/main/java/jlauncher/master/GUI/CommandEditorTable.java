package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;
import java.util.ArrayList;
import java.util.List;

class CommandEditorTable {

    private List<CommandTableItemInfo> applicationList = new ArrayList<>();
    private ObservableList<CommandTableItemInfo> applications = FXCollections.observableArrayList(applicationList);
    private TableView<CommandTableItemInfo> table;
    private double windowHeight;

    private enum StringColumns{
        Name, Group, Cmd, Dir, ComputerAddress, Delay
    }

    CommandEditorTable(double windowHeight){
        this.windowHeight = windowHeight;
        Platform.runLater(() -> applications.add(new CommandTableItemInfo()));
    }

    TableView<CommandTableItemInfo> getTable(){
        if(table == null) return crateNewTable();
        else return table;
    }

    private TableView<CommandTableItemInfo> crateNewTable(){
        TableView<CommandTableItemInfo> _table = new TableView<>(applications);

        _table.setMaxWidth(Double.MAX_VALUE);
        _table.setMaxHeight(600);
        _table.setPrefWidth(windowHeight / 2);
        _table.setMaxHeight(windowHeight / 2);
        _table.setItems(applications);
        _table.setEditable(true);

        VBox.setVgrow(_table, Priority.ALWAYS);

        _table.getColumns().add(getEnableColumn());
        _table.getColumns().add(getTableColumn(_table, StringColumns.Name));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Group));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Cmd));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Dir));
        _table.getColumns().add(getTableColumn(_table, StringColumns.ComputerAddress));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Delay));

        table = _table;
        return _table;
    }

    private TableColumn<CommandTableItemInfo, String> getTableColumn(TableView<CommandTableItemInfo> _table, StringColumns column){
        switch(column){
            case Name:
                return TableUtils.getStringColumn
                        (_table, "Name", 7.0, true, true, getCallback(column));
            case Cmd:
                return TableUtils.getStringColumn
                        (_table, "Cmd", 3.7, true, true, getCallback(column));
            case Dir:
                return TableUtils.getStringColumn
                        (_table, "Dir", 7.0, true, true, getCallback(column));
            case Group:
                return TableUtils.getStringColumn
                        (_table, "Group", 7.0, true, true, getCallback(column));
            case Delay:
                return TableUtils.getStringColumn
                        (_table, "Delay", 10.0, true, true, getCallback(column));
            case ComputerAddress:
                return TableUtils.getStringColumn
                        (_table, "Address", 7.6, true, true, getCallback(column));
            default:
                return TableUtils.getStringColumn
                        (_table, "Undefined", 7.0, true, true, getCallback(column));
        }
    }

    private NodeTableColumn<CommandTableItemInfo> getEnableColumn(){
        NodeTableColumn<CommandTableItemInfo> column =  new NodeTableColumn<>();
        column.setSortable(true);
        column.setText("En");
        column.setPrefWidth(40);
        column.setCellValueFactory(node -> node.getValue().getButtonProperty());
        column.setResizable(false);
        return column;
    }

    private Callback<CommandTableItemInfo, StringProperty> getCallback(StringColumns column){
        return new Callback<CommandTableItemInfo, StringProperty>() {
            @Override
            public StringProperty apply(CommandTableItemInfo arg) {
                switch(column){
                    case Name: return arg.getAppNameProperty();
                    case Cmd: return arg.getCommandProperty();
                    case Dir: return arg.getDirectoryProperty();
                    case Group: return arg.getGroupProperty();
                    case Delay: return  arg.getDelayProperty();
                    case ComputerAddress: return arg.getComputerAddressProperty();
                    default: return new SimpleStringProperty("Undefined");
                }
            }
        };
    }

    class CommandTableItemInfo extends TableItemInfo{

        private Button enableButton = new Button();
        private ObjectProperty<Node> enableButtonProperty = new SimpleObjectProperty<>(enableButton);

        CommandTableItemInfo(){
            enableButton.setPrefHeight(20);
            enableButton.setPrefWidth(20);
            enableButton.setOnAction(e -> setButtonStyleClass(true));
            setButtonStyleClass(false);
        }

        private void setButtonStyleClass(boolean isAction){
            if(getEnableProperty().get()) {
                if(isAction) setEnable(false);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-on");
            }
            else {
                if(isAction) setEnable(true);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-off");
            }
        }

        ObjectProperty<Node> getButtonProperty(){
            return enableButtonProperty;
        }
    }
}
