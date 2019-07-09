package jlauncher.utils;

import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

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
}
