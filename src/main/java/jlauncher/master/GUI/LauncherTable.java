package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.config.ConfigReader;
import jlauncher.master.controller.Command;
import jlauncher.master.path.storage.PathStorage;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;

import java.util.ArrayList;

class LauncherTable extends AbstractTable<LauncherTable.LauncherTableItemInfo> {

    private double windowHeight;
    private PathStorage pathStorage = PathStorage.getInstance();
    private ConfigReader configReader = ConfigReader.getInstance();

    public enum LaunchStatus{
        Started, Stopped, Empty
    }

    public enum OnlineStatus{
        Online, Offline
    }

    private enum StringColumns{
        Name, Computer, Group, LaunchStatus
    }

    public void configureMenu(){
        TableUtils.configureMenu(table, new Callback<LauncherTableItemInfo, ObservableList<MenuItem>>(){
            @Override
            public ObservableList<MenuItem> apply(LauncherTableItemInfo arg) {
                return getContextMenu(arg).getItems();
            }
        });
    }

    LauncherTable(double windowHeight){
        this.windowHeight = windowHeight;
    }

    @Override
    void setColumns() {
        table.getColumns().add(getRequestTableColumn());
        table.getColumns().add(getTableColumn(table, StringColumns.Name));
        table.getColumns().add(getTableColumn(table, StringColumns.Computer));
        table.getColumns().add(getTableColumn(table, StringColumns.Group));
        table.getColumns().add(getTableColumn(table, StringColumns.LaunchStatus));
        table.getColumns().add(getOnlineTableColumn(table));
    }

    @Override
    void readConfig(){
        ArrayList<Command> commands = configReader.readCommands(pathStorage.getCurrentPathToFile());
        Platform.runLater(() -> {
            commands.forEach(command -> {
                if(command.getEnable()) {
                    LauncherTableItemInfo itemInfo = new LauncherTableItemInfo();
                    itemInfo.setAppName(command.getAppName());
                    itemInfo.setCommand(command.getCommand());
                    itemInfo.setComputerAddress(command.getComputerAddress());
                    itemInfo.setGroup(command.getGroup());
                    itemInfo.setEnable(command.getEnable());
                    itemInfo.setDelay(command.getDelay());
                    itemInfo.setDirectory(command.getDir());
                    itemInfo.setId(command.getId());
                    itemInfo.setStopCommands(command.getOnStopCommands());
                    items.add(itemInfo);
                }
            });
        });
    }

    protected void createNewTable(){
        super.createNewTable();

        table.setMaxWidth(Double.MAX_VALUE);
        table.setPrefHeight(Double.MAX_VALUE);
        table.maxHeight(600);
        table.setPrefWidth(windowHeight / 2);
        table.setMinHeight(windowHeight / 2);
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setRowFactory(c -> getTableRow());

        VBox.setVgrow(table, Priority.ALWAYS);

        configureMenu();
    }

    private TableColumn<LauncherTableItemInfo, String> getTableColumn(TableView<LauncherTableItemInfo> _table, StringColumns column){
        switch(column){
            case Name:
                return TableUtils.getStringColumn
                        (_table, "Name", 5.0, true, true, getCallback(column));
            case Computer:
                return TableUtils.getStringColumn
                        (_table, "Computer", 5.7, true, true, getCallback(column));
            case Group:
                return TableUtils.getStringColumn
                        (_table, "Group", 5.0, true, true, getCallback(column));
            case LaunchStatus:
                return TableUtils.getStringColumn
                        (_table, "Launch status", 5.0, true, true, getCallback(column));
            default:
                return TableUtils.getStringColumn
                        (_table, "Undefined", 5.0, true, true, getCallback(column));
        }
    }

    private Callback<LauncherTableItemInfo, StringProperty> getCallback(StringColumns column){
        return new Callback<LauncherTableItemInfo, StringProperty>() {
            @Override
            public StringProperty apply(LauncherTableItemInfo arg) {
                switch(column){
                    case Name: return arg.getAppNameProperty();
                    case Computer: return arg.getComputerAddressProperty();
                    case Group: return arg.getGroupProperty();
                    case LaunchStatus: return  arg.getLaunchStatusStringProperty();
                    default: return new SimpleStringProperty("Undefined");
                }
            }
        };
    }

    private NodeTableColumn<LauncherTableItemInfo> getRequestTableColumn(){
        NodeTableColumn<LauncherTableItemInfo> column = new NodeTableColumn<>();
        column.setEditable(false);
        column.setMaxWidth(50);
        column.setPrefWidth(50);
        column.setMinWidth(50);
        column.setSortable(true);
        column.setCellValueFactory(node -> node.getValue().requestButtonProperty);
        column.setResizable(false);
        return column;
    }

    private NodeTableColumn<LauncherTableItemInfo> getOnlineTableColumn(TableView<LauncherTableItemInfo> _table){
        NodeTableColumn<LauncherTableItemInfo> column = new NodeTableColumn<>();
        column.setEditable(false);
        column.setText("Slave status");
        column.setSortable(true);
        column.setCellValueFactory(node -> node.getValue().onlineButtonProperty);
        column.setResizable(false);

        _table.widthProperty().addListener((obs, oldVal, newVal) -> column.setPrefWidth(newVal.doubleValue() / 5));

        return column;
    }

    private TableRow<LauncherTableItemInfo> getTableRow(){
        return new TableRow<LauncherTableItemInfo>() {
            private StringBinding simpleBinding = Bindings.createStringBinding(() -> "");
            @Override protected void updateItem(LauncherTableItemInfo item, boolean empty)  {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    this.styleProperty().unbindBidirectional(simpleBinding);
                    StringBinding styleBinding = Bindings.createStringBinding(() -> {
                        if(item.onlineStatus.get() == OnlineStatus.Offline) return "-fx-background-color: red;";
                        else{
                            if(item.reply.get() == LaunchStatus.Started) return "-fx-background-color: green;";
                            else return "-fx-background-color: #3A3A3A;";
                        }
                    }, item.reply, item.onlineStatus);
                    this.styleProperty().bind(styleBinding);
                } else {
                this.styleProperty().unbind();
                this.styleProperty().bind(simpleBinding);
                }
            }
        };
    }

    private ContextMenu getContextMenu(LauncherTableItemInfo item){
        ContextMenu contextMenu = new ContextMenu();

        MenuItem start = new MenuItem("Start");

        start.setOnAction(e -> {

        });

        MenuItem stop = new MenuItem("Stop");

        stop.setOnAction(e -> {

        });

        MenuItem restart = new MenuItem("Restart");

        restart.setOnAction(e -> {

        });

        MenuItem startGroup = new MenuItem("Start group");

        startGroup.setOnAction(e -> {

        });

        MenuItem restartGroup = new MenuItem("Restart group");

        restartGroup.setOnAction(e -> {

        });

        MenuItem stopGroup = new MenuItem("Stop group");

        stopGroup.setOnAction(e -> {

        });

        MenuItem restartComputer = new MenuItem("Restart computer");

        restartComputer.setOnAction(e -> {

        });

        MenuItem stopComputer = new MenuItem("Stop computer");

        stopComputer.setOnAction(e -> {

        });

        contextMenu.getItems().add(start);
        contextMenu.getItems().add(stop);
        contextMenu.getItems().add(restart);
        contextMenu.getItems().add(startGroup);
        contextMenu.getItems().add(restartGroup);
        contextMenu.getItems().add(stopGroup);
        contextMenu.getItems().add(restartComputer);
        contextMenu.getItems().add(stopComputer);

        return contextMenu;
    }

    class LauncherTableItemInfo extends TableItemInfo{

        private Button requestButton = new Button();
        private Button onlineButton = new Button();
        private ObjectProperty<Node> requestButtonProperty = new SimpleObjectProperty<>(requestButton);
        private ObjectProperty<Node> onlineButtonProperty = new SimpleObjectProperty<>(onlineButton);
        private ObjectProperty<LaunchStatus> launcherStatus = new SimpleObjectProperty<>(LaunchStatus.Empty);
        private StringProperty launchStatusStringProperty = new SimpleStringProperty("");
        private ObjectProperty<OnlineStatus> onlineStatus = new SimpleObjectProperty<>(OnlineStatus.Offline);
        private ObjectProperty<LaunchStatus> reply = new SimpleObjectProperty<>(LaunchStatus.Empty);

        LauncherTableItemInfo(){
            onlineButton.setPrefHeight(15);
            onlineButton.setPrefWidth(50);
            parseOnlineStatus(onlineStatus.get());
            onlineStatus.addListener((obs, oldVal, newVal) -> parseOnlineStatus(newVal));

            requestButton.setPrefWidth(20);
            requestButton.setPrefHeight(20);
            parseLaunchStatus(launcherStatus.get());
            launcherStatus.addListener((obs, oldVal, newVal) -> parseLaunchStatus(newVal));

            parseReplyStatus(reply.get());
            reply.addListener((obs, olVal, newVal) -> parseReplyStatus(newVal));
        }

        private void parseReplyStatus(LaunchStatus status){
            requestButton.getStyleClass().clear();
            launchStatusStringProperty.set("");
            switch (status){
                case Started:
                    requestButton.getStyleClass().add("started-icon");
                    launchStatusStringProperty.set("Started");
                case Stopped:
                    launchStatusStringProperty.set("Stopped");
                    requestButton.getStyleClass().add("stopped-icon");
                default:
                    if(onlineStatus.get() == OnlineStatus.Offline) requestButton.getStyleClass().clear();
                    else {
                        launchStatusStringProperty.set("Stopped");
                        requestButton.getStyleClass().add("stopped-icon");
                    }
            }
        }

        private void parseLaunchStatus(LaunchStatus status){
            requestButton.getStyleClass().clear();
            launchStatusStringProperty.set("");
            switch (status){
                case Started:
                    requestButton.getStyleClass().add("started-icon");
                    launchStatusStringProperty.set("Starting");
                case Stopped:
                    launchStatusStringProperty.set("Stopping");
                    requestButton.getStyleClass().add("stopped-icon");
                default:
                    if(onlineStatus.get() == OnlineStatus.Offline) requestButton.getStyleClass().clear();
                    else {
                        launchStatusStringProperty.set("Stopping");
                        requestButton.getStyleClass().add("stopped-icon");
                    }
            }
        }

        private void parseOnlineStatus(OnlineStatus status){
            onlineButton.getStyleClass().clear();
            switch (status){
                case Online:
                    onlineButton.getStyleClass().add("online-icon");
                case Offline:
                    onlineButton.getStyleClass().add("offline-icon");
                    default:
            }
        }

        StringProperty getLaunchStatusStringProperty(){
            return launchStatusStringProperty;
        }
    }
}
