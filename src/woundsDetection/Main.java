package woundsDetection;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    private final double SCREEN_OFFSET=20;


    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root =  (BorderPane) FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Automatic Wounds Detection");
        addElements(root);

        //Set up Application Screen size  Using Monitor Screen size boundaries
        setScreenSize(primaryStage);

        Scene scene =new Scene(root);


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
    private void setScreenSize(Stage stage){
        Rectangle2D screenBoundaries = Screen.getPrimary().getVisualBounds();

        stage.setWidth(screenBoundaries.getMaxX()-2*SCREEN_OFFSET);
        stage.setHeight(screenBoundaries.getMaxY()-SCREEN_OFFSET);
    }

    private void addElements(BorderPane root){

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

        menuBar.getMenus().addAll(fileMenu,editMenu,aboutMenu);
        menuBox.getChildren().add(menuBar);
        root.setTop(menuBox);



    }
}
