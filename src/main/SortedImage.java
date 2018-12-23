package main;

import javafx.scene.image.Image;
/*
* Subclass of Image to sort the Images with a predecessor and a successor
* */
public class SortedImage extends Image {

    //----------components----------
    private SortedImage predecessor;
    private SortedImage successor;
    private int listIndex;

    //----------constructors----------
    public SortedImage(String url) {
        super(url);
        init();
    }

    public SortedImage(String url, boolean backgroundLoading) {
        super(url, backgroundLoading);
        init();
    }

    public SortedImage(String url, double requestedWidth, double requestedHeight, boolean preserveRatio, boolean smooth, boolean backgroundLoading) {
        super(url, requestedWidth, requestedHeight, preserveRatio, smooth, backgroundLoading);
        init();
    }

    //----------initialize Methods---------
    private void init(){
        this.successor = null;
        this.predecessor = null;
        this.listIndex = -1;
    }
    //----------getter and setter----------
    public SortedImage getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(SortedImage predecessor) {
        this.predecessor = predecessor;
    }

    public SortedImage getSuccessor() {
        return successor;
    }

    public void setSuccessor(SortedImage successor) {
        this.successor = successor;
    }

    public int getListIndex() {
        return listIndex;
    }

    public void setListIndex(int listIndex) {
        this.listIndex = listIndex;
    }
}
