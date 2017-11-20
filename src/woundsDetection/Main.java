package woundsDetection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    private final double SCREEN_OFFSET=20;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Automatic Wounds Detection");

        //Set up Application Screen size  Using Monitor Screen size boundaries
        setScreenSize(primaryStage);

        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
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
}
