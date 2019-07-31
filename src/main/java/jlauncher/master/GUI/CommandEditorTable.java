package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.config.ConfigReader;
import jlauncher.master.controller.Command;
import jlauncher.master.controller.Controller;
import jlauncher.master.path.storage.PathStorage;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;

import java.util.ArrayList;

class CommandEditorTable extends AbstractTable<CommandEditorTable.CommandTableItemInfo> {

    private double windowHeight;
    private PathStorage pathStorage = PathStorage.getInstance();
    private ConfigReader configReader = ConfigReader.getInstance();

    @Override
    public void addNewItem() {
        items.add(new CommandTableItemInfo());
    }

    @Override
    public void readConfig() {
        ArrayList<Command> commands = configReader.readCommands(pathStorage.getCurrentPathToFile());
        Platform.runLater(() -> {
            commands.forEach(command -> {
                CommandTableItemInfo itemInfo = new CommandTableItemInfo();
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
            });
        });
    }

    @Override
    public void saveConfig(Controller controller) {
        ArrayList<Command> commands = new ArrayList<>();
        items.forEach(item -> commands.add(new Command(item)));
        controller.saveCommandConfig(commands);
        controller.updateAllStages();
    }

    private enum StringColumns{
        Name, Group, Cmd, Dir, ComputerAddress, Delay
    }

    CommandEditorTable(double windowHeight){
        this.windowHeight = windowHeight;
    }

    protected void createNewTable(){
        super.createNewTable();

        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(600);
        table.setPrefWidth(windowHeight / 2);
        table.setMaxHeight(windowHeight / 2);
        table.setEditable(true);

        VBox.setVgrow(table, Priority.ALWAYS);
    }

    @Override
    void setColumns() {
        table.getColumns().add(getEnableColumn());
        table.getColumns().add(getTableColumn(table, StringColumns.Name));
        table.getColumns().add(getTableColumn(table, StringColumns.Group));
        table.getColumns().add(getTableColumn(table, StringColumns.Cmd));
        table.getColumns().add(getTableColumn(table, StringColumns.Dir));
        table.getColumns().add(getTableColumn(table, StringColumns.ComputerAddress));
        table.getColumns().add(getTableColumn(table, StringColumns.Delay));
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
                if(isAction) setEnable(true);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-on");
            }
            else {
                if(isAction) setEnable(false);
                enableButton.getStyleClass().clear();
                enableButton.getStyleClass().add("rb-off");
            }
        }

        @Override
        public void setEnable(boolean value){
            super.setEnable(value);
            setButtonStyleClass(false);
        }

        ObjectProperty<Node> getButtonProperty(){
            return enableButtonProperty;
        }
    }
}
