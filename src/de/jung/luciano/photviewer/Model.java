package de.jung.luciano.photviewer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Model {
    private Stage primaryStage = null;
    private ArrayList<Image> images = null;
    private long diashowDuration;
    private IntegerProperty indexOfCenterImage;

    //++++++++++++++++++++++++++++++
    // constructor
    // ++++++++++++++++++++++++++++++

    public Model(Stage primaryStage){
        this.primaryStage = primaryStage;
        images = new ArrayList<Image>();
        diashowDuration = 2000;
        indexOfCenterImage = new SimpleIntegerProperty(-1);

    }

    //++++++++++++++++++++++++++++++
    // getter and setter
    // ++++++++++++++++++++++++++++++

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public long getDiashowDuration() {
        return diashowDuration;
    }

    public void setDiashowDuration(long diashowDuration) {
        this.diashowDuration = diashowDuration;
    }

    public IntegerProperty getIndexOfCenterImage() {
        return indexOfCenterImage;
    }
}
