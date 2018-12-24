package de.jung.luciano.photviewer;

public class DiashowController {
    //Model
    Model model;

    //View
    Diashow diashowView;
    
    public DiashowController(Model model) {
        this.model = model;
        this.diashowView = new Diashow();

        //Listener

    }

    public void show() {
        diashowView.show(model.getPrimaryStage());
    }
}
