package de.jung.luciano.photviewer;

import javafx.geometry.Dimension2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Diashow {
    private Scene scene;
    private final Dimension2D DIMENSION = new Dimension2D(1000, 600);
    private BorderPane borderPane;  //root
    //MenuBar (TOP)
    private MenuBar menuBar;
    private Menu menuAction;
    private MenuItem menuItemPauseDiashow;
    private MenuItem menuItemStopDiashow;
    //for CenterImage (CENTER)
    private ImageView imageView = new ImageView();

    //++++++++++++++++++++++++++++++
    // constructor
    // ++++++++++++++++++++++++++++++

    protected Diashow(){
        //BorderPane Layout
        borderPane = new BorderPane();

        //--create MenuBar--
        menuItemPauseDiashow = new MenuItem("Pause");
        menuItemStopDiashow = new MenuItem("Stop");
        menuAction = new Menu("Aktion");
        menuAction.getItems().addAll(menuItemPauseDiashow, menuItemStopDiashow);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuAction);
        borderPane.setTop(menuBar);

        //--Center for chosen Picture--
        imageView.setPreserveRatio(true);
        borderPane.setCenter(imageView);

        //set Scene
        scene = new Scene(borderPane,  DIMENSION.getWidth(), DIMENSION.getHeight());

    }

    //++++++++++++++++++++++++++++++
    // methods
    // ++++++++++++++++++++++++++++++

    protected void show(Stage primaryStage) {
        primaryStage.setTitle("Diashow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //++++++++++++++++++++++++++++++
    // getter
    // ++++++++++++++++++++++++++++++

    public MenuItem getMenuItemPauseDiashow() {
        return menuItemPauseDiashow;
    }

    public MenuItem getMenuItemStopDiashow() {
        return menuItemStopDiashow;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Scene getScene() {
        return scene;
    }
}
