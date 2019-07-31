package jlauncher.utils;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.ContextMenuEvent;

public class TableUtils {

    public static <T> TableColumn<T, String> getStringColumn(TableView<T> table,
                                                    String name,
                                                    double offset,
                                                    boolean editable,
                                                    boolean resizable,
                                                    Callback<T, StringProperty> callback){
        TableColumn<T, String> column =  new TableColumn<>();
        column.setEditable(editable);
        column.setSortable(true);
        column.setText(name);
        column.setPrefWidth(table.getWidth() / offset);
        table.widthProperty().addListener((obs, oldVal, newVal) -> column.setPrefWidth(newVal.doubleValue() / offset));
        column.setCellValueFactory(node -> callback.apply(node.getValue()));
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setResizable(resizable);
        return column;
    }

    public static <Row> void configureMenu(TableView<Row> table, Callback<Row, ObservableList<MenuItem>> callback){
        final RecursionGuard<Unit> configureRowFactoryDecorator = new RecursionGuard<>(new Callback<Unit, Unit>() {
            @Override
            public Unit apply(Unit arg) {
                javafx.util.Callback<TableView<Row>, TableRow<Row>> _oldFactory = table.getRowFactory();
                if(_oldFactory == null) _oldFactory = param -> new TableRow<Row>();

                final javafx.util.Callback<TableView<Row>, TableRow<Row>> oldFactory = _oldFactory;

                table.setRowFactory(x -> {
                    TableRow<Row> row = oldFactory.call(x);
                    row.addEventHandler(ContextMenuEvent.ANY, event -> {
                        if(row.getItem() != null){
                            ContextMenu contextMenu = new ContextMenu();
                            contextMenu.getItems().setAll(callback.apply(row.getItem()));
                            contextMenu.show(row.getScene().getWindow(), event.getScreenX(), event.getScreenY());
                            event.consume();
                        }
                    });
                    return row;
                });
                return null;
            }
        });

        table.rowFactoryProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                configureRowFactoryDecorator.apply(new Unit());
            }
        });
        configureRowFactoryDecorator.apply(new Unit());

    }

    static class RecursionGuard<A>{

        Callback<A, Unit> callback;
        boolean entered = false;

        RecursionGuard(Callback<A, Unit> callback){
            this.callback = callback;
        }

        public boolean isEntered() {
            return entered;
        }

        void apply(A x){
            if(!entered){
                entered = true;
                try {
                    callback.apply(x);
                }finally {
                    entered = false;
                }
            }
        }
    }

    private static class Unit{
    }
}
