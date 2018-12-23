package de.jung.luciano.photviewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create Model
        Model model = new Model(primaryStage);

        //call/ create PhotoViewController
        PhotoViewController photoViewController = new PhotoViewController(model);
        photoViewController.show();
    }
}
