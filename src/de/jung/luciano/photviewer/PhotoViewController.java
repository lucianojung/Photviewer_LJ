package de.jung.luciano.photviewer;

import javafx.event.ActionEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class PhotoViewController {

    //Model
    private Model model;
    //View
    private PhotoView photoView;

    //++++++++++++++++++++++++++++++
    // constructor
    //++++++++++++++++++++++++++++++

    protected PhotoViewController(Model model){
        //set model and view
        this.model = model;
        this.photoView = new PhotoView();
        loadImages();

        //Listener
        photoView.getMenuItemOpenFiles().setOnAction(event -> handleOpenFiles(event));
        photoView.getMenuItemExit().setOnAction(event -> System.exit(0));
        photoView.getMenuItemDiashow().setOnAction(event -> handleDiashow(event));
        photoView.getMenuItemDiashowDuration().setOnAction(event -> handleDiashowDuration(event));
        photoView.getButtonLeftArrow().setOnAction(event -> handleLeftArrow(event));
        photoView.getHyperlinkDiashow().setOnAction(event -> photoView.getMenuItemDiashow().fire());
        photoView.getButtonRightArrow().setOnAction(event -> handleRightArrow(event));
        photoView.getImageViewListView().setOnMouseClicked(event -> handleListView(event));
        photoView.getScene().setOnKeyPressed(event -> {           //Handle Key Pressed LEFT and RIGHT Arrow
            if (event.getCode().toString().equals("LEFT"))
                photoView.getButtonLeftArrow().fire();
            if (event.getCode().toString().equals("RIGHT"))
                photoView.getButtonRightArrow().fire();
        });
    }

    //++++++++++++++++++++++++++++++++
    //Event Handler
    //++++++++++++++++++++++++++++++++

    private void handleOpenFiles(ActionEvent event) {
        /*
        * open new File Chooser to choose the Images you want to show
        * Works with: JPG, PNG, GIF, BMP
        * try to get the Image for each chosen ImageFile and add it to the Image List
        * set Center Image Index to 0
        * Load all Images
        */
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(model.getPrimaryStage());

        try{
            for (File file : files) {
                String imagePath = file.toURI().toString();                                                             //get image Path as String
                System.out.println("Loading Image from Path: " + imagePath);                                            //Message
                Image image = new Image(imagePath, true);
                model.getImages().add(image);
            }
        } catch (NullPointerException e){return;}                                                                       //return if no image is load

        model.getIndexOfCenterImage().set(0);                                                                           //Center Image is First Image
        loadImages();
    }

    private void handleDiashow(ActionEvent actionEvent) {
        DiashowController diashowController = new DiashowController(model);                                             //create new Diashow Controller and give him the model
        diashowController.show();                                                                                       //show new Scene (Diashow)
    }

    private void handleDiashowDuration(ActionEvent event) {
        TextInputDialog inputDialog = new TextInputDialog(Long.toString(model.getDiashowDuration()));
        inputDialog.setTitle("Diashow Duration");
        inputDialog.setHeaderText("");
        inputDialog.setContentText("Set the Duration of the Diashow (in millis):");

        Optional<String> result = inputDialog.showAndWait();
        if(!result.isPresent())return;
        try{
            model.setDiashowDuration(Long.parseLong(result.get()));
        } catch (NumberFormatException e){
            System.out.println(result.get() + " is Not a number");
        }
    }

    private void handleLeftArrow(ActionEvent actionEvent) {
        if (model.getImages().size() == 0) return;                                                                      //if no Images load yet
        int index = model.getIndexOfCenterImage().intValue();
        if (index <= 0)                                                                                                 //first image get previous => last image
            index = photoView.getImageViewListView().getItems().size();
        model.getIndexOfCenterImage().set(--index);
        setCenterImage(photoView.getImageViewListView().getItems().get(index).getImage());
    }

    private void handleRightArrow(ActionEvent actionEvent) {
        if (model.getImages().size() == 0) return;                                                                      //if no Images load yet
        int index = model.getIndexOfCenterImage().intValue();
        if (index >= photoView.getImageViewListView().getItems().size()-1)                                              //last image get next => first image
            index = -1;
        model.getIndexOfCenterImage().set(++index);
        setCenterImage(photoView.getImageViewListView().getItems().get(index).getImage());
    }

    private void handleListView(MouseEvent event) {
        setCenterImage(photoView.getImageViewListView().getSelectionModel().getSelectedItem().getImage());
    }

    //+++++++++++++++++++++++++++++
    //other Methods
    //+++++++++++++++++++++++++++++

    protected void show(){
        photoView.show(model.getPrimaryStage());                                                                        //show photoView on primaryStage got from model
    }

    private void loadImages() {
        /*
        * method is called each time the ListView have to be refreshed
        * Load all Images (in ImageViews) from Model in the ListView
        * fit Width and Height are for max Width and Height, because preserveRatio(true) bind them so the are not distorted
        * sets the actual chosen Image in the Center
        */
        if (model.getImages().size() == 0) return;
        for (Image image: model.getImages()){
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);                                                                                    //set max width and height
            imageView.setPreserveRatio(true);
            photoView.getImageViewListView().getItems().add(imageView);
        }
        setCenterImage(model.getImages().get(model.getIndexOfCenterImage().intValue()));
    }

    private void setCenterImage(Image image){

        /*
         * for-Schleife: locate index of center Image
         * possible source of error:
         * if you load an image twice with the same URL the for-loop will only gets the index of the first one
         *
         * create new ImageView and set given Image
         * set fit width and height got from CenterPaneSize and preserveRatio(true)
         * Clear Pane and set new ImageView
         */
        for (int i = 0; i < model.getImages().size(); i++){
            if (!model.getImages().get(i).equals(image))continue;
            model.getIndexOfCenterImage().set(i);
            break;
        }
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(photoView.getCenterPane().widthProperty());
        imageView.fitHeightProperty().bind(photoView.getCenterPane().heightProperty());
        imageView.setPreserveRatio(true);
        photoView.getCenterPane().getChildren().clear();
        photoView.getCenterPane().getChildren().add(imageView);
    }
}
