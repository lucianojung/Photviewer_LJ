package de.jung.luciano.photviewer;

import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;

import javax.swing.text.html.ImageView;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.security.Key;

import static javafx.application.Platform.runLater;

public class DiashowController {
    //Model
    Model model;

    //View
    Diashow diashowView;

    //Image Task
    ImageTask imageTask;
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

        //Listener
        diashowView.getMenuItemPauseDiashow().setOnAction(event -> handlePauseDiashow(event));
        diashowView.getMenuItemStopDiashow().setOnAction(event -> handleStopDiashow(event));
        diashowView.getScene().setOnKeyPressed(event -> {           //Handle Key Pressed ESCAPE while Diashow is shown
            if (event.getCode().toString().equals("ESCAPE"))
                handleStopDiashow(new ActionEvent());               //-> back to photoviewer
        });
    }

    //++++++++++++++++++++++++++++++
    // Event Handler
    // ++++++++++++++++++++++++++++++

    //stops the Diashow (names are contradictory)
    private void handlePauseDiashow(ActionEvent event) {
        if (imageThread.getState().equals(Thread.State.TERMINATED))
            handleStartDiashow(event);
        else
            handleInterruptDiashow(event);
    }

    //stops diahow via handlePause and return to photoviewer scene
    private void handleStopDiashow(ActionEvent event) {
        handleInterruptDiashow(event);
        PhotoViewController photoViewController = new PhotoViewController(model);       //give model to diashowController
        photoViewController.show();                                                     //show new Scene (Diashow)
    }

    public void handleStartDiashow(ActionEvent event){
        //creates and starts a new Thread
        imageThread = new Thread(imageTask);
        imageThread.start();
        diashowView.getMenuItemPauseDiashow().setText("Pause");
    }

    private void handleInterruptDiashow(ActionEvent event){
        imageThread.interrupt();
        try {
            imageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        diashowView.getMenuItemPauseDiashow().setText("Start");
    }

    //++++++++++++++++++++++++++++++
    // other methods
    // ++++++++++++++++++++++++++++++

    public void show() {
        if(model.getImages().size() == 0) return;   //return if there are no Images
        /*
        * else Start Diashow Thread
        * set Image (get number of Image in the List from model.indexOfCenterImage
        * call show method of view with primary Stage
        * set FullScreen
        */
        handleStartDiashow(new ActionEvent());
        diashowView.getImageView().setImage(model.getImages().get(model.getIndexOfCenterImage().getValue().intValue()));
        diashowView.show(model.getPrimaryStage());
        model.getPrimaryStage().setFullScreen(true);
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++
    //inner class
    //+++++++++++++++++++++++++++++++++++++++++++++++

    private class ImageTask implements Runnable{

        @Override
        public void run() {
            try{
                while (true){ //Never Stop automaticly!
                    Thread.sleep(model.getDiashowDuration());   //sleep duration from model
                    //set Center image IntProperty to next Image (+1) and update Image
                    model.getIndexOfCenterImage().set(model.getIndexOfCenterImage().intValue()+1);
                    updateImage(model.getIndexOfCenterImage().intValue());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //update Image method with runLater(Runnable)
        private void updateImage(int index)
        {
            runLater(new Runnable() {
                @Override public void run() {
                    diashowView.getImageView().setImage(model.getImages().get(index));
                }
            });
        }
    }
}
