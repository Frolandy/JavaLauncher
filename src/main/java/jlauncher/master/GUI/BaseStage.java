package jlauncher.master.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

class BaseStage extends Stage {

    BaseStage(double windowWidth, double windowHeight){
        setWidth(windowWidth);
        setHeight(windowHeight);
        setMinWidth(windowWidth);
        setMinHeight(windowHeight);

        getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
    }

    void setRoot(Pane pane){
        pane.setBackground(new Background(new BackgroundFill(Color.web("#4C4C4C"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());

        setScene(scene);

        show();
    }
}
