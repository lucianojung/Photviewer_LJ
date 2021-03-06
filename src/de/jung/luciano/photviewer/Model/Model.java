package de.jung.luciano.photviewer.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private Stage primaryStage = null;
    private List<Image> images = null;
    private long diashowDuration;
    private int indexOfCenterImage;

    //++++++++++++++++++++++++++++++
    // constructor
    // ++++++++++++++++++++++++++++++

    public Model(Stage primaryStage){
        this.primaryStage = primaryStage;
        images = new ArrayList<Image>();
        diashowDuration = 2000;
        indexOfCenterImage = -1;

    }

    //++++++++++++++++++++++++++++++
    // getter and setter
    // ++++++++++++++++++++++++++++++

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public List<Image> getImages() {
        return images;
    }

    public long getDiashowDuration() {
        return diashowDuration;
    }

    public void setDiashowDuration(long diashowDuration) {
        this.diashowDuration = diashowDuration;
    }

    public int getIndexOfCenterImage() {
        return indexOfCenterImage;
    }

    public void setIndexOfCenterImage(int indexOfCenterImage) {
        this.indexOfCenterImage = indexOfCenterImage;
    }

    public Image getActualImage(){
        return images.get(indexOfCenterImage);
    }
}
