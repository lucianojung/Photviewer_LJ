package de.jung.luciano.photviewer;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

public class PhotoViewController {

    //Model
    Model model;
    //View
    PhotoView photoView;

    public PhotoViewController(Model model){
        this.model = model;
        this.photoView = new PhotoView();

        //Listener
        photoView.getMenuItemOpenFiles().setOnAction(event -> handleOpenFiles(event));
        photoView.getMenuItemExit().setOnAction(event -> System.exit(0));
        photoView.getMenuItemDiashow().setOnAction(event -> handleDiashow(event));
        photoView.getButtonLeftArrow().setOnAction(event -> handleLeftArrow(event));
        photoView.getButtonRightArrow().setOnAction(event -> handleRightArrow(event));
        photoView.getImageViewListView().setOnMouseClicked(event -> handleListView(event));
    }

    public void show(){
        photoView.show(model.getPrimaryStage());
    }

    private void handleOpenFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(model.getPrimaryStage());

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
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(150);
                photoView.getImageViewListView().getItems().add(imageView);
            }
        } catch (NullPointerException e){return;}   //it workes anyway but the nullpointer exception is not shown
    }

    private void handleDiashow(ActionEvent actionEvent) {
        DiashowController diashowController = new DiashowController(model);
        diashowController.show();
    }

    private void handleLeftArrow(ActionEvent actionEvent) {
        int index = getIndexOfImageViewCenter()-1;
        if (index < 0)
            index = photoView.getImageViewListView().getItems().size()-1;
        setCenterImage(photoView.getImageViewListView().getItems().get(index).getImage());
    }

    private void handleRightArrow(ActionEvent actionEvent) {
        int index = getIndexOfImageViewCenter()+1;
        if (index >= photoView.getImageViewListView().getItems().size())
            index = 0;
        setCenterImage(photoView.getImageViewListView().getItems().get(index).getImage());
    }

    private void handleListView(MouseEvent event) {
        setCenterImage(photoView.getImageViewListView().getSelectionModel().getSelectedItem().getImage());
    }

    private int getIndexOfImageViewCenter(){
        if (photoView.getCenterPane().getChildren().get(0) == null) return -1;
        int index = 0;

        Image image = ((ImageView) photoView.getCenterPane().getChildren().get(0)).getImage();
        for (ImageView imageView : photoView.getImageViewListView().getItems()){
            if (imageView.getImage().equals(image)){
                return index;
            }
            index++;
        }
        System.err.println("Cant find Image in List");
        return -1;
    }

    private void setCenterImage(Image image){
        /*
         * create new ImageView
         * set given Image
         * set fit width and height got from CenterPaneSize
         * Clear Pane and set new ImageView
         */
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(photoView.getCenterPane().getWidth());
        imageView.setFitHeight(photoView.getCenterPane().getHeight());
        photoView.getCenterPane().getChildren().clear();
        photoView.getCenterPane().getChildren().add(imageView);
    }
}
