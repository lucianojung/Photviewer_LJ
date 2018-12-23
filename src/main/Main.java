package main;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    //----------create Components----------
    //MenuBar
    private MenuBar menuBar;
    private Menu menuAction;
    private MenuItem menuItemOpenFiles;
    private MenuItem menuItemExit;
    private Menu menuDiashow;
    private MenuItem menuItemDiashow;
    //simple components
    private int imageIndexCenter;
    private boolean diashowRun;
    //displayed components
    private BorderPane borderPane;
    private Stage primaryStage;
    ScrollPane scrollPane;
    private VBox vBoxPictures;
    private Pane pane;
    private Button buttonLeftArrow;
    private Button buttonRightArrow;
    //others
    private ArrayList<SortedImage> imageArrayList;
    private SortedImage shownImage = null;
    //final components
    private final Dimension2D dimension = new Dimension2D(1000, 600);
    private final FileChooser fileChooser = new FileChooser();
    //private final String regularExpressionFilePath = "^(?:[\\w]\\:|\\\\)(\\\\[a-z_\\-\\s0-9\\.]+)+\\.(jpg|png|pdf|doc|docx|xls|xlsx)$";


    //----------start----------
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(0, 10, 10, 0));

        //initialize
        initializeComponents();
        initializeListener();

        //set and open Stage
        primaryStage.setTitle("Luciano's Foto Anzeige");
        primaryStage.setScene(new Scene(borderPane, dimension.getWidth(), dimension.getHeight()));
        primaryStage.show();

        //get parameter hopefully a picture directory, works anyway, no exception if no directory
        Parameters params = getParameters();
        List<String> parameterList = params.getRaw();
        showPictures(parameterList);
    }

    //----------initialize Methods----------
    private void initializeComponents() {
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
        imageArrayList = new ArrayList<>();
        scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPadding(new Insets(10));
        vBoxPictures = new VBox();
        scrollPane.setContent(vBoxPictures);
        borderPane.setLeft(scrollPane);
        //--Bottom for Navigation--
        HBox hBoxButtons = new HBox();
        buttonLeftArrow = new Button("<-");
        buttonRightArrow = new Button("->");
        hBoxButtons.setAlignment(Pos.CENTER);
        hBoxButtons.getChildren().addAll(buttonLeftArrow, buttonRightArrow);
        borderPane.setBottom(hBoxButtons);
        //--Center for chosen Picture--
        pane = new Pane();
        borderPane.setCenter(pane);

    }

    //----------Listener----------
    private void initializeListener() {
        //Listener for Menu Item Open Files
        menuItemOpenFiles.setOnAction(event -> {
            //try to open File Chooser and pic Picture(s)
            try{
                List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
                showPictures(files);
            } catch (NullPointerException e){return;}   //it workes anyway but the nullpointer exception is not shown
        });

        //Listener for EXIT
        menuItemExit.setOnAction(event -> System.exit(0));

        //Listener for Diashow
        /*menuItemDiashow.setOnAction(event -> {
            Diashow diashow = new Diashow(primaryStage, imageArrayList);
            diashow.startDiashow();
        });*/
        ///////////
        menuItemDiashow.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                FadeTransition ft = new FadeTransition();
                for(int i = 0 ; i < imageArrayList.size() ; i ++ ){
                    setCenterImage(imageArrayList.get(i));
                    ft.setNode(pane);
                    ft.setDuration(new Duration(2000));
                    ft.setFromValue(1.0);
                    ft.setToValue(0.0);
                    ft.setCycleCount(0);
                    ft.setAutoReverse(true);
                    ft.play();
                }
            }
        });
        ///////////

        buttonLeftArrow.setOnAction(event -> {
            if (shownImage == null)return;                          //if no picture shown
            else if (shownImage.getPredecessor() == null)return;    //if there is no predecessor
            setCenterImage(shownImage.getPredecessor());
        });

        buttonRightArrow.setOnAction(event -> {
            if (shownImage == null)return;                      //if no picture shown
            else if (shownImage.getSuccessor() == null)return;  //if there is no successor
            setCenterImage(shownImage.getSuccessor());
        });

        //Listener for Width Property
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal != newVal)
                setCenterImage(shownImage);     //resize Shown Image
        });

        //Listener for Height Property
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal != newVal)
                setCenterImage(shownImage);     //resize shown Image
        });
    }

    //----------other Methods----------
    //show Pictures from given List in vBox
    private void showPictures(List files){
        for (int i = 0; i < files.size(); i++) {
            //get image Path as String; String is from Parameters; File is from FileChooser
            String imagePath;
            if (files.get(i).getClass() == File.class){
                imagePath = ((File)files.get(i)).toURI().toString();		        //List from FileChooser
            } else {
                imagePath = new File((String)files.get(i)).toURI().toString();	    //List from Parameters
            }
            System.out.println("Start loading Image from: " + imagePath);

            //create new Image set ListIndex, Prodecessor and Successor and add it to ArrayList
            SortedImage image = new SortedImage(imagePath,true);
            image.setListIndex(imageArrayList.size());
            if (imageArrayList.size() > 0){
                image.setPredecessor(imageArrayList.get(image.getListIndex()-1));
                imageArrayList.get(image.getListIndex()-1).setSuccessor(image);
            }
            imageArrayList.add(image);

            //create ImageView with new Image from ArrayList
            ImageView imageView = getImageView(imageArrayList.get(imageArrayList.size()-1),115, 86);
            vBoxPictures.getChildren().add(imageView);  //add ImageView to vBox

            //add Listener for ImageView
            imageView.setOnMouseClicked(event ->
                    setCenterImage(((ImageView)event.getSource()).getImage()));
        }
    }

    private void setCenterImage(Image image) {
        ImageView imageView2 = getImageView(image, (int) pane.getWidth(), (int) pane.getHeight());
        pane.getChildren().clear();
        pane.getChildren().add(imageView2);
        shownImage = (SortedImage) imageView2.getImage();         //to set the actual shown Image
    }

    private ImageView getImageView(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    //----------getter and setter----------

    //----------main----------
    public static void main(String[] args) {
        launch(args);
    }
}
