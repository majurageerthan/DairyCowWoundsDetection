package woundsDetection;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private final double SCREEN_OFFSET=20;
    private final double IMAGE_SPACING=30;
    private final double EXTRA_SPACE_VERTICAL=260;
    private final double EXTRA_SPACE_HORIZONTAL=260;
    private final double TOOLBOX_HEIGHT=100;
    private final List<Point> points= new ArrayList<>();
    private double thermalImageHeight=0;
    private double thermalImageWidth=0;
    protected Canvas thermalImgCanvas,visualImgCanvas;

    public Main(){
        thermalImgCanvas =new Canvas(400,400);
        visualImgCanvas  =new Canvas(400,400);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root =  (BorderPane) FXMLLoader.load(getClass().getResource("gui.fxml"));

        primaryStage.setTitle("Automatic Wounds Detection");
        addElements(root,primaryStage);
        handleEvents(primaryStage);


        Scene scene =new Scene(root);

        primaryStage.setX(20);
        primaryStage.setY(20);
        primaryStage.setScene(scene);
        primaryStage.show();
        //System.out.println(imageBox.toString());
    }


    public static void main(String[] args) {
        launch(args);
    }

    /*
    Set Screen Size of App little lesser than full Screen of
    Primary Screen
    Arguments: Stage
     */
    private void setScreenSize(Stage stage ,double prefferedWidth,double preferredHeight){
        stage.setWidth(prefferedWidth);
        stage.setHeight(preferredHeight);
        Rectangle2D screenBoundaries = Screen.getPrimary().getVisualBounds();
        stage.setMaxWidth(screenBoundaries.getMaxX()-2*SCREEN_OFFSET);
        stage.setMaxHeight(screenBoundaries.getMaxY()-SCREEN_OFFSET);
    }

    private void addElements(BorderPane root,Stage stage){
        double stagePreferredWidth=0;
        double stagePreferredHeight=0;
        //Menu Bar for Application
        HBox menuBox =new HBox();
        MenuBar menuBar =new MenuBar();

        //File Menu
        Menu fileMenu =new Menu("File");
        MenuItem fileMenuItem_1 =new MenuItem("Open Visual");
        MenuItem fileMenuItem_2=new MenuItem("Open Thermal");
        MenuItem fileMenuItem_3=new MenuItem("Save As");
        MenuItem fileMenuItem_4=new MenuItem("Save");
        MenuItem fileMenuItem_5=new MenuItem("Exit");

        fileMenu.getItems().addAll(fileMenuItem_1,fileMenuItem_2,fileMenuItem_3,fileMenuItem_4,fileMenuItem_5);

        //Edit Menu
        Menu editMenu =new Menu("Edit");
        MenuItem editMenuItem_1 =new MenuItem("Zoom In");
        MenuItem editMenuItem_2=new MenuItem("Zoom Out");
        MenuItem editMenuItem_3=new MenuItem("Copy Thermal Image ");
        MenuItem editMenuItem_4 =new MenuItem("Copy Visula Image");

        editMenu.getItems().addAll(editMenuItem_1,editMenuItem_2,editMenuItem_3,editMenuItem_4);

        //About Menu
        Menu aboutMenu =new Menu("About");
        MenuItem aboutMenuItem_1 =new MenuItem("About Us");
        MenuItem aboutMenuItem_2=new MenuItem("Licences");
        MenuItem aboutMenuItem_3=new MenuItem("Help");
        aboutMenu.getItems().addAll(aboutMenuItem_1,aboutMenuItem_2,aboutMenuItem_3);

        menuBar.getMenus().addAll(fileMenu,editMenu,aboutMenu);
        //menuBox.getChildren().add(menuBar);
        root.setTop(menuBar);



       // Image visualImage =new Image("file:C:\\Users\\shank\\Desktop\\index.jpg");
        //Image thermalImage =new Image("file:C:\\Users\\shank\\Desktop\\index.jpg");

        //thermalImageHeight=thermalImage.getHeight();
        //thermalImageWidth=thermalImage.getWidth();

        //PixelReader px =thermalImage.getPixelReader();
        //regionOfInterestDetector(px);


        //stagePreferredHeight=(visualImage.getHeight()+thermalImageHeight)/2;
        //stagePreferredWidth=visualImage.getWidth()+thermalImageWidth;

        //setScreenSize(stage,stagePreferredWidth+EXTRA_SPACE_HORIZONTAL,stagePreferredHeight+EXTRA_SPACE_VERTICAL);


        //ImageView visualImageView =new ImageView();
        //visualImageView.setImage(visualImage);


        //Canvas canvas =new Canvas(thermalImage.getWidth(),thermalImage.getHeight());
        //canvas.getGraphicsContext2D().drawImage(thermalImage,0,0);
        //edgeMarker(canvas.getGraphicsContext2D());


        Image file_open_icon =new Image("file:Resources\\open.png");
        GraphicsContext graphicsContext_visual =visualImgCanvas.getGraphicsContext2D();
        GraphicsContext graphicsContext_thermal =thermalImgCanvas.getGraphicsContext2D();

        graphicsContext_thermal.drawImage(file_open_icon,140,140);
        graphicsContext_visual.drawImage(file_open_icon,140,140);
        graphicsContext_thermal.fillText("Open Thermal Image\n",180,145);
        graphicsContext_visual.fillText("Open Visual Image \n",180,145);



        setScreenSize(stage,1200,800);
        //Thermal image and visual image container
        HBox imageBox =new HBox(IMAGE_SPACING);
        imageBox.prefHeight(1000);
        imageBox.getChildren().addAll(visualImgCanvas,thermalImgCanvas);

        ScrollPane scrollPane =new ScrollPane();
        scrollPane.setPadding(new Insets(30,10,20,30));
        //scrollPane.setFitToHeight(true);
        scrollPane.setContent(imageBox);
        root.setCenter(scrollPane);

        //Tool Box
        HBox toolBox =new HBox();
        toolBox.setMinHeight(TOOLBOX_HEIGHT);
        root.setBottom(toolBox);

        //Status Box

        VBox statusBox =new VBox();
        statusBox.setMinWidth(160);

        root.setRight(statusBox);



    }

    private void regionOfInterestDetector(PixelReader px){
        for(int i=0;i<thermalImageWidth;i++){
            for(int j=0;j<thermalImageHeight;j++){
                if(ColorSeparator.compareColors(Color.WHITE,px.getColor(i,j),.3)){
                    points.add(new Point(i,j));
                }
            }
        }
    }
    private void edgeMarker(GraphicsContext graphicsContext){
        points.forEach(point -> {
            graphicsContext.setStroke(Color.RED);
            graphicsContext.strokeOval(point.getX(),point.getY(),.5,.5);
        });
    }

    private void handleEvents(Stage stage){
        //Hanling File open
        EventHandlerr.openFile("Open Visual Image",visualImgCanvas,new Rectangle2D(150,150,300,300),stage,false);
        EventHandlerr.openFile("Open Thermal Image",thermalImgCanvas,new Rectangle2D(150,150,300,300),stage,true);

    }
}
