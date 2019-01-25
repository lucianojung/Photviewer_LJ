package de.jung.luciano.photviewer.Diashow;

import de.jung.luciano.photviewer.Diashow.Diashow;
import de.jung.luciano.photviewer.Main.Controller;
import de.jung.luciano.photviewer.Model.Model;
import de.jung.luciano.photviewer.PhotoView.PhotoViewController;
import javafx.event.ActionEvent;
import static javafx.application.Platform.runLater;

public class DiashowController implements Controller{
    //Model
    private Model model;
    //View
    private Diashow diashowView;

    //Image Task and Thread
    private ImageTask imageTask;
    private Thread imageThread  = null;

    //++++++++++++++++++++++++++++++
    // constructor
    // ++++++++++++++++++++++++++++++

    public DiashowController(Model model) {
        this.model = model;
        this.diashowView = new Diashow();
        imageTask = new ImageTask();

        //Binding
        diashowView.getImageView().fitWidthProperty().bind( model.getPrimaryStage().widthProperty());
        diashowView.getImageView().fitHeightProperty().bind(model.getPrimaryStage().heightProperty());

        generateEventHandler();
    }

    //++++++++++++++++++++++++++++++
    // Event Handler
    // ++++++++++++++++++++++++++++++

    @Override
    public void generateEventHandler(){
        //generates all EventHandlers for the Diashow
        diashowView.getMenuItemPauseDiashow().setOnAction(event -> handlePauseDiashow(event));
        diashowView.getMenuItemStopDiashow().setOnAction(event -> handleStopDiashow(event));
        diashowView.getScene().setOnKeyPressed(event -> {           //Handle Key Pressed ESCAPE while Diashow is shown
            if (event.getCode().toString().equals("ESCAPE"))
                handleStopDiashow(new ActionEvent());               //-> back to photoviewer
            if (event.getCode().toString().equals("RIGHT"))
                imageTask.nextImage();
        });
    }

    //stops the Diashow (names are contradictory)
    private void handlePauseDiashow(ActionEvent event) {
        if (imageThread.getState().equals(Thread.State.TERMINATED))
            handleStartDiashow(event);
        else
            interruptDiashow();
    }

    //stops diahow via handlePause and return to photoviewer scene
    private void handleStopDiashow(ActionEvent event) {
        interruptDiashow();
        Controller photoViewController = new PhotoViewController(model);       //give model to diashowController
        photoViewController.show();                                                     //show new Scene (Diashow)
    }

    private void handleStartDiashow(ActionEvent event){
        //creates and starts a new Thread
        imageThread = new Thread(imageTask);
        imageThread.start();
        diashowView.getMenuItemPauseDiashow().setText("Pause");
    }

    //++++++++++++++++++++++++++++++
    // other methods
    //++++++++++++++++++++++++++++++

    @Override
    public void show() {
        /*
         * return if there are no Images -> you can show an Alert here if You want to informate the User
         *
         * else Start Diashow Thread -> via handleStartDiashow()
         * set Image (get number of Image in the List from model.indexOfCenterImage
         * call show method of view with primary Stage
         * set FullScreen
         */
        if(model.getImages().size() <= 0) return;

        handleStartDiashow(new ActionEvent());
        diashowView.getImageView().setImage(model.getActualImage());
        diashowView.show(model.getPrimaryStage());
        model.getPrimaryStage().setFullScreen(true);
    }

    private void interruptDiashow(){
        imageThread.interrupt();
        try {
            imageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        diashowView.getMenuItemPauseDiashow().setText("Start");
    }

    //++++++++++++++++++++++++++++++
    // Inner Class
    //++++++++++++++++++++++++++++++

    private class ImageTask implements Runnable{

        @Override
        public void run() {
            /*
             * try-catch because of Thread.sleep
             * while (true), because the Diashow Can run forever
             * sleep for X millis, got from model.diashowDuration
             * set next ImageView:
             *   -> set index ++
             *   -> look if Last Image
             *   -> call updateImage
             */
            try{
                while (true){
                    Thread.sleep(model.getDiashowDuration());
                    nextImage();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void nextImage(){
            model.setIndexOfCenterImage((model.getIndexOfCenterImage()+1) % model.getImages().size());
            updateImage();
        }
    }

    //update Image method with runLater(Runnable)
    private void updateImage()
    {
        runLater(new Runnable() {
            @Override public void run() {
                diashowView.getImageView().setImage(model.getActualImage());
            }
        });
    }
}

