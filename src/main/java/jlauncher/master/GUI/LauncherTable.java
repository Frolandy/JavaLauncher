package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;
import java.util.ArrayList;
import java.util.List;

class LauncherTable {

    private List<LauncherTableItemInfo> applicationList = new ArrayList<>();
    private ObservableList<LauncherTableItemInfo> applications = FXCollections.observableArrayList(applicationList);
    private TableView<LauncherTableItemInfo> table;
    private double windowHeight;

    public enum LaunchStatus{
        Started, Stopped, Empty
    }

    public enum OnlineStatus{
        Online, Offline, Empty
    }

    private enum StringColumns{
        Name, Computer, Group, LaunchStatus
    }

    LauncherTable(double windowHeight){
        this.windowHeight = windowHeight;
        Platform.runLater(() -> applications.add(new LauncherTableItemInfo()));
    }

    TableView<LauncherTableItemInfo> getTable(){
        if(table != null) return table;
        else return createTable();
    }

    private TableView<LauncherTableItemInfo> createTable(){
        TableView<LauncherTableItemInfo> _table = new TableView<>(applications);

        _table.setMaxWidth(Double.MAX_VALUE);
        _table.setPrefHeight(Double.MAX_VALUE);
        _table.maxHeight(600);
        _table.setPrefWidth(windowHeight / 2);
        _table.setMinHeight(windowHeight / 2);
        _table.setItems(applications);
        _table.setEditable(false);
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        _table.setRowFactory(c -> getTableRow());

        VBox.setVgrow(_table, Priority.ALWAYS);

        _table.getColumns().add(getRequestTableColumn());
        _table.getColumns().add(getTableColumn(_table, StringColumns.Name));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Computer));
        _table.getColumns().add(getTableColumn(_table, StringColumns.Group));
        _table.getColumns().add(getTableColumn(_table, StringColumns.LaunchStatus));
        _table.getColumns().add(getOnlineTableColumn(_table));

        table = _table;
        return _table;
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

    class LauncherTableItemInfo extends TableItemInfo{

        private Button requestButton = new Button();
        private Button onlineButton = new Button();
        private ObjectProperty<Node> requestButtonProperty = new SimpleObjectProperty<>(requestButton);
        private ObjectProperty<Node> onlineButtonProperty = new SimpleObjectProperty<>(onlineButton);
        private ObjectProperty<LaunchStatus> launcherStatus = new SimpleObjectProperty<>(LaunchStatus.Stopped);
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
