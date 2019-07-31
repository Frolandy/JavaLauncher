package jlauncher.master.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jlauncher.master.controller.Controller;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractTable<T> {

    private List<T> itemsList = new ArrayList<>();
    protected ObservableList<T> items = FXCollections.observableArrayList(itemsList);
    protected TableView<T> table;

    void removeItem(){
        T item = table.getSelectionModel().getSelectedItem();
        if(item != null) javafx.application.Platform.runLater(() -> items.remove(item));
    }

    void clear(){items.clear();}

    void addNewItem(){}

    void saveConfig(Controller controller){}

    void readConfig(){}

    public TableView<T> getTable(){
        if(table == null) return createNewTable();
        else return table;
    }

    protected TableView<T> createNewTable(){
        TableView<T> _table = new TableView<>(items);
        _table.setItems(items);
        table = _table;
        setColumns();
        return _table;
    }

    abstract void setColumns();
}
