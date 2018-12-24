package de.jung.luciano.photviewer;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Model {
    Stage primaryStage = null;
    private ArrayList<Image> images = null;

    public Model(Stage primaryStage){
        this.primaryStage = primaryStage;
        images = new ArrayList<Image>();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
