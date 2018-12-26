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

    public Model(Stage primaryStage){
        this.primaryStage = primaryStage;
        images = new ArrayList<Image>();
        diashowDuration = 2500;
        indexOfCenterImage = new SimpleIntegerProperty(-1);

    }

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
        if(images.size() <= 0) return new SimpleIntegerProperty(-1);
        else if(indexOfCenterImage.intValue() >= images.size()) {
            indexOfCenterImage.set(0);
            return indexOfCenterImage;
        }
        return indexOfCenterImage;
    }
}
