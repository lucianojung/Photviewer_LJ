package de.jung.luciano.photviewer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.geometry.Dimension2D;


public class PhotoView {
    //----------create Components----------
    private Scene scene;
    private final Dimension2D dimension = new Dimension2D(1000, 600);
    private BorderPane borderPane;  //root
    //MenuBar (TOP)
    private MenuBar menuBar;        //menuBar
    private Menu menuAction;
    private MenuItem menuItemOpenFiles;
    private MenuItem menuItemExit;
    private Menu menuDiashow;
    private MenuItem menuItemDiashow;
    //for Thumbnaillist (LEFT)
    private ListView<ImageView> imageViewListView;
    //for CenterImage (CENTER)
    private Pane centerPane;        //for shownImage
    //for Navigation (BOTTOM)
    private Button buttonLeftArrow; //previous Image
    private Button buttonRightArrow;//next Image


    public PhotoView() {
        //BorderPane Layout
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(0, 10, 10, 0));

        //--create MenuBar--
        menuItemOpenFiles = new MenuItem("Open Files");
        menuItemExit = new MenuItem("Exit");
        menuItemDiashow = new MenuItem("Start Diashow");
        menuAction = new Menu("Aktion");
        menuDiashow = new Menu("Diashow");
        menuAction.getItems().addAll(menuItemOpenFiles, menuItemExit);
        menuDiashow.getItems().addAll(menuItemDiashow);
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuAction, menuDiashow);
        borderPane.setTop(menuBar);

        //--Left Side for Pictures--
        imageViewListView = new ListView<ImageView>();
        imageViewListView.setPadding(new Insets(10));
        borderPane.setLeft(imageViewListView);

        //--Bottom for Navigation--
        HBox hBoxButtons = new HBox();
        buttonLeftArrow = new Button("<-");
        buttonRightArrow = new Button("->");
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.getChildren().addAll(buttonLeftArrow, buttonRightArrow);
        borderPane.setBottom(hBoxButtons);

        //--Center for chosen Picture--
        centerPane = new Pane();
        borderPane.setCenter(centerPane);

        //set Scene
        scene = new Scene(borderPane,  dimension.getWidth(), dimension.getHeight());
    }

    public void show(Stage stage) {
        stage.setTitle("PhotoViewer Luciano");
        stage.setScene(scene);
        stage.show();
    }

    public MenuItem getMenuItemOpenFiles() {
        return menuItemOpenFiles;
    }

    public MenuItem getMenuItemExit() {
        return menuItemExit;
    }

    public MenuItem getMenuItemDiashow() {
        return menuItemDiashow;
    }

    public Button getButtonLeftArrow() {
        return buttonLeftArrow;
    }

    public Button getButtonRightArrow() {
        return buttonRightArrow;
    }

    public ListView<ImageView> getImageViewListView() {
        return imageViewListView;
    }

    public Pane getCenterPane() {
        return centerPane;
    }
}