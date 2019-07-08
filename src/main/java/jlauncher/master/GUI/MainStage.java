package jlauncher.master.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainStage {

    private int windowHeight = 500;
    private int windowWidth = 695;
    private LauncherMenu menu = new LauncherMenu();

    public MainStage(Stage stage){
        stage.setTitle("Launcher");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icon.png")));
        stage.setMinWidth(windowWidth);
        stage.setMinHeight(windowHeight);
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);

        VBox vbox = new VBox(menu.getMenuBar(stage));
        vbox.setBackground(new Background(new BackgroundFill(Color.web("#4C4C4C"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(vbox);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());

        stage.setScene(scene);

        stage.show();
    }
}
