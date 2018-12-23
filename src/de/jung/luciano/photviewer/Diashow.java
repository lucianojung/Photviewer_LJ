package de.jung.luciano.photviewer;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Diashow {
    private Scene scene;

    //Nodes...

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Diashow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
