package de.jung.luciano.photviewer;

import javafx.event.ActionEvent;
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
    Model model;
    //View
    PhotoView photoView;

    //++++++++++++++++++++++++++++++
    // constructor
    //++++++++++++++++++++++++++++++

    public PhotoViewController(Model model){
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
        //get Files chosen in FileChooser
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(model.getPrimaryStage());

        //try to get the Image for each chosen File
        try{
            for (File file : files) {
                String imagePath = file.toURI().toString(); //get image Path as String
                System.out.println("Loading Image from: " + imagePath);     //Message

                /*
                * Load Image with ImagePath and Background Loading
                * create new ImageView
                * set prefered width and height
                * add ImageView to List View from photoView
                */
                Image image = new Image(imagePath, true);
                model.getImages().add(image);
                photoView.getImageViewListView().getItems().add(newImageViewForListView(image));
            }
        } catch (NullPointerException e){return;}   //it workes anyway but the nullpointer exception is not shown
        setCenterImage(model.getImages().get(0));
    }

    private void handleDiashow(ActionEvent actionEvent) {
        DiashowController diashowController = new DiashowController(model);     //give model to diashowController
        diashowController.show();                                               //show new Scene (Diashow)
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
        int index = model.getIndexOfCenterImage().intValue();
        if (index == -1)return;                 //if no Images load yet
        else if (index <= 0)                     //first image get previous => last image
            index = photoView.getImageViewListView().getItems().size();
        model.getIndexOfCenterImage().set(--index);
        setCenterImage(photoView.getImageViewListView().getItems().get(index).getImage());
    }

    private void handleRightArrow(ActionEvent actionEvent) {
        int index = model.getIndexOfCenterImage().intValue();
        if (index == -1)return;                                                  //if no Images load yet
        else if (index >= photoView.getImageViewListView().getItems().size()-1)   //last image get next => first image
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

    public void show(){
        photoView.show(model.getPrimaryStage());    //show photoView on primaryStage got from model
    }

    private void loadImages() {
        /*
        * method is called when you return from the diashow and there are still images in the Model.list<Image>
        * set a new ImageView for all Images in the ListView
        * sets the actual chosen Image in the Center
        */
        if (model.getImages().size() == 0) return;
        for (Image image: model.getImages()){
            photoView.getImageViewListView().getItems().add(newImageViewForListView(image));
        }
        setCenterImage(model.getImages().get(model.getIndexOfCenterImage().intValue()));
    }

    private ImageView newImageViewForListView(Image image){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);        //set max width and height
        imageView.setPreserveRatio(true);   //preserveRatio(true) resize the imageView distorted automatically
        return imageView;
    }

    private void setCenterImage(Image image){
        for (int i = 0; i < model.getImages().size(); i++){
            if (!model.getImages().get(i).equals(image))continue;
            model.getIndexOfCenterImage().set(i);
            break;
        }
        /*
         * create new ImageView
         * set given Image
         * set fit width and height got from CenterPaneSize
         * Clear Pane and set new ImageView
         */
        ImageView imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(photoView.getCenterPane().widthProperty());
        imageView.fitHeightProperty().bind(photoView.getCenterPane().heightProperty());
        imageView.setPreserveRatio(true);
        photoView.getCenterPane().getChildren().clear();
        photoView.getCenterPane().getChildren().add(imageView);
    }
}
