package jlauncher.master.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import jlauncher.master.controller.Controller;
import jlauncher.master.controller.Variable;
import jlauncher.utils.Callback;
import jlauncher.utils.TableUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VariableEditorTable extends AbstractTable<VariableEditorTable.VarInfo> {

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
        //TODO: FileStorage.getCurrentPathToFile
    }

    @Override
    public void saveConfig(Controller controller) {
        ArrayList<Variable> variables = new ArrayList<>();
        items.forEach(item -> variables.add(new Variable(item.varName.getValue(), item.value.getValue(), item.id.getValue())));
        controller.saveVariableConfig(variables);
    }

    private enum StringColumns{
        VariableName, Value
    }


    protected TableView<VarInfo> createNewTable(){
        TableView<VarInfo> _table = super.createNewTable();

        _table.setMaxWidth(Double.MAX_VALUE);
        _table.setMaxHeight(Double.MAX_VALUE);

        _table.setEditable(true);

        VBox.setVgrow(_table, Priority.ALWAYS);

        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return _table;
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

        StringProperty getVarNameProperty(){return varName;}
        StringProperty getValueProperty(){return value;}
        StringProperty getIdProperty(){return id;}

    }
}
