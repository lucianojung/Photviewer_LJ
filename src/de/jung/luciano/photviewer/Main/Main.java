package de.jung.luciano.photviewer.Main;

import de.jung.luciano.photviewer.Model.Model;
import de.jung.luciano.photviewer.PhotoView.PhotoViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    /*
    * Main class for the photviewer
    * @author: Luciano Jung
    *
    * Programed with Model View Control-Pattern
    * create Model and give it to the first controller use it
    * create PhotoViewController and
    * show first view with method controller.show
    */

    //Model
    private Model model;

    //first controller
    private Controller firstController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //create Model
        model = new Model(primaryStage);

        //create PhotoViewController
        firstController = new PhotoViewController(model);
        firstController.show();
    }
}
