package main;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Diashow {
    //----------components----------
    Stage stage;
    ArrayList<SortedImage> images;
    //----------constructor----------
    public Diashow (Stage stage, ArrayList<SortedImage> images){
        this.stage = stage;
        this.images = images;
    }

    public void startDiashow(){
        stage.setFullScreen(true);
        VBox vBoxPictures = (VBox) ((BorderPane)stage.getScene().getRoot()).getLeft();
        ((VBox)((BorderPane)stage.getScene().getRoot()).getLeft()).getChildren().clear();
    }

    public void stopDiashow(){

    }
}
