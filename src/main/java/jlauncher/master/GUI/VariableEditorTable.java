package jlauncher.master.GUI;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import jlauncher.config.ConfigReader;
import jlauncher.master.controller.Command;
import jlauncher.master.controller.Controller;
import jlauncher.master.controller.Variable;
import jlauncher.master.path.storage.PathStorage;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VariableEditorTable extends AbstractTable<VariableEditorTable.VarInfo> {

    private ConfigReader configReader = ConfigReader.getInstance();
    private PathStorage pathStorage = PathStorage.getInstance();

    public void removeVariable(String variableName, String pathId){
        List<VarInfo> toRemove = new ArrayList<>();
        items.forEach(variable -> {
            if (variable.id.getValue().equals(pathId) && variable.varName.getValue().equals(variableName)){
                toRemove.add(variable);
            }
        });
        toRemove.forEach(variable -> items.remove(variable));
    }

    @Override
    public void addNewItem() {
        items.add(new VarInfo());
    }

    @Override
    public void readConfig(){
        items.clear();
        ArrayList<Variable> variables = configReader.readVariables(pathStorage.getCurrentPathToFile());
        Platform.runLater(() -> {
            variables.forEach(variable -> {
                items.add(new VarInfo(variable.getVariableName(), variable.getValue(), variable.getId()));
            });
        });
    }

    @Override
    public void saveConfig(Controller controller) {
        ArrayList<Variable> variables = new ArrayList<>();
        items.forEach(item -> variables.add(new Variable(item.varName.getValue(), item.value.getValue(), item.id.getValue())));
        controller.saveVariableConfig(variables);
        controller.updateAllStages();
    }

    private enum StringColumns{
        VariableName, Value
    }


    protected void createNewTable(){
        super.createNewTable();

        table.setMaxWidth(Double.MAX_VALUE);
        table.setMaxHeight(Double.MAX_VALUE);

        table.setEditable(true);

        VBox.setVgrow(table, Priority.ALWAYS);

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @Override
    void setColumns() {
        TableColumn<VarInfo, String> varColumn = getTableColumn(table, StringColumns.VariableName);
        TableColumn<VarInfo, String> valueColumn = getTableColumn(table, StringColumns.Value);

        varColumn.setStyle("-fx-text-fill: #3db176;-fx-font-size: 10pt;");
        valueColumn.setStyle("-fx-text-fill: #3db176;-fx-font-size: 10pt;");


        table.getColumns().add(varColumn);
        table.getColumns().add(valueColumn);
    }

    private TableColumn<VarInfo, String> getTableColumn(TableView<VarInfo> _table, StringColumns column){
        switch(column){
            case VariableName:
                return TableUtils.getStringColumn
                        (_table, "Variable Name", 1, true, true, getCallback(column));
            case Value:
                return TableUtils.getStringColumn
                        (_table, "Value", 1, true, true, getCallback(column));
            default:
                return TableUtils.getStringColumn
                        (_table, "Undefined", 1, true, true, getCallback(column));
        }
    }

    private Callback<VarInfo, StringProperty> getCallback(StringColumns column){
        return new Callback<VarInfo, StringProperty>() {
            @Override
            public StringProperty apply(VarInfo arg) {
                switch(column){
                    case VariableName: return arg.getVarNameProperty();
                    case Value: return arg.getValueProperty();
                    default: return new SimpleStringProperty("Undefined");
                }
            }
        };
    }

    class VarInfo {
        private StringProperty varName = new SimpleStringProperty("");
        private StringProperty value = new SimpleStringProperty("");
        private StringProperty id = new SimpleStringProperty(UUID.randomUUID().toString());

        VarInfo(){

        }

        VarInfo(String varName, String value, String id){
            this.varName.setValue(varName);
            this.value.setValue(value);
            this.id.setValue(id);
        }

        StringProperty getVarNameProperty(){return varName;}
        StringProperty getValueProperty(){return value;}
        StringProperty getIdProperty(){return id;}

    }
}
