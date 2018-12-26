package de.jung.luciano.photviewer;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    /*
    * Main class for the photviewer
    * @author: Luciano Jung
    *
    * Programed with MVC-Pattern
    * create Model first and give it to the first controller use it
    * show first view with method controller.show witch shows the view from the controller which got the Stage from the model
    */

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
