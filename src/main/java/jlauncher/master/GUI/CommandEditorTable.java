package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.utils.Callback;
import java.util.ArrayList;
import java.util.List;

class CommandEditorTable {

    private List<CommandTableItemInfo> applicationList = new ArrayList<>();
    private ObservableList<CommandTableItemInfo> applications = FXCollections.observableArrayList(applicationList);
    private TableView<CommandTableItemInfo> table;
    private Double windowHeight;

    private enum StringColumns{
        Name, Group, Cmd, Dir, ComputerAddress, Delay
    }

    CommandEditorTable(Double windowHeight){
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
        _table.getColumns().add(getStringColumn(_table, "Name", 7.0, getCallback(StringColumns.Name)));
        _table.getColumns().add(getStringColumn(_table, "Group", 7.0, getCallback(StringColumns.Group)));
        _table.getColumns().add(getStringColumn(_table, "Cmd", 3.7, getCallback(StringColumns.Cmd)));
        _table.getColumns().add(getStringColumn(_table, "Dir", 7.0, getCallback(StringColumns.Dir)));
        _table.getColumns().add(getStringColumn(_table, "Address", 7.6, getCallback(StringColumns.ComputerAddress)));
        _table.getColumns().add(getStringColumn(_table, "Delay", 10.0, getCallback(StringColumns.Delay)));
        table = _table;
        return _table;
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

    private TableColumn<CommandTableItemInfo, String> getStringColumn(TableView<CommandTableItemInfo> table,
                                                                      String name,
                                                                      Double offset,
                                                                      Callback<CommandTableItemInfo, StringProperty> callback){
        TableColumn<CommandTableItemInfo, String> column =  new TableColumn<>();
        column.setEditable(true);
        column.setSortable(true);
        column.setText(name);
        column.setPrefWidth(table.getWidth() / offset);
        table.widthProperty().addListener((obs, oldVal, newVal) -> column.setPrefWidth(newVal.doubleValue() / offset));
        column.setCellValueFactory(node -> callback.apply(node.getValue()));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setResizable(true);
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

    class CommandTableItemInfo implements TableItemInfo{

        private StringProperty computerAddress = new SimpleStringProperty("");
        private StringProperty appName = new SimpleStringProperty("");
        private StringProperty group = new SimpleStringProperty("");
        private StringProperty cmd = new SimpleStringProperty("");
        private StringProperty dir = new SimpleStringProperty("");
        private BooleanProperty enable = new SimpleBooleanProperty(false);
        private StringProperty delay = new SimpleStringProperty("");
        private StringProperty id = new SimpleStringProperty("");
        private ObjectProperty<String[]> onStopCommands = new SimpleObjectProperty<>(new String[0]);
        private Button enableButton = new Button();
        private ObjectProperty<Node> enableButtonProperty = new SimpleObjectProperty<>(enableButton);

        CommandTableItemInfo(){
            enableButton.setPrefHeight(20);
            enableButton.setPrefWidth(20);
            enableButton.setOnAction(e -> setButtonStyleClass(true));
            setButtonStyleClass(false);
        }

        private void setButtonStyleClass(Boolean isAction){
            if(enable.get()) {
                if(isAction) enable.setValue(false);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-on");
            }
            else {
                if(isAction) enable.setValue(true);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-off");
            }
        }

        ObjectProperty<Node> getButtonProperty(){
            return enableButtonProperty;
        }

        @Override
        public StringProperty getComputerAddressProperty() {
            return computerAddress;
        }

        @Override
        public StringProperty getAppNameProperty() {
            return appName;
        }

        @Override
        public StringProperty getCommandProperty() {
            return cmd;
        }

        @Override
        public StringProperty getDirectoryProperty() {
            return dir;
        }

        @Override
        public BooleanProperty getEnableProperty() {
            return enable;
        }

        @Override
        public StringProperty getGroupProperty() {
            return group;
        }

        @Override
        public StringProperty getIdProperty() {
            return id;
        }

        @Override
        public StringProperty getDelayProperty() {
            return delay;
        }

        @Override
        public ObjectProperty<String[]> getObjectProperty() {
            return onStopCommands;
        }

        @Override
        public void setComputerAddressProperty(String value) {
            computerAddress.set(value);
        }

        @Override
        public void setAppNameProperty(String value) {
            appName.set(value);
        }

        @Override
        public void setCommandProperty(String value) {
            cmd.set(value);
        }

        @Override
        public void setDirectoryProperty(String value) {
            dir.set(value);
        }

        @Override
        public void setEnableProperty(Boolean value) {
            enable.set(value);
        }

        @Override
        public void setGroupProperty(String value) {
            group.set(value);
        }

        @Override
        public void setIdProperty(String value) {
            id.set(value);
        }

        @Override
        public void setDelayProperty(String value) {
            delay.set(value);
        }

        @Override
        public void setObjectProperty(String[] value) {
            onStopCommands.set(value);
        }
    }
}
