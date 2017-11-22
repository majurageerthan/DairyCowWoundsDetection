package woundsDetection;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EventHandlerr {
    private static FileChooser fileChooser;

    //Method take a Canvas and specific area as rectangle coordinates
    //if mouse triggered on particular area it get image path from user
    //and return as string
    public  static String openFile(String chooserTitle, Canvas canvas, Rectangle2D rectangle, final Stage stage){
       final StringBuilder path=new StringBuilder();
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 //Check wheather user clicks on specified rectangl area
                 double x=event.getX();
                 double y=event.getY();
                 if(x>=rectangle.getMinX() && x<=rectangle.getMaxX()&& y>=rectangle.getMinY() && y<=rectangle.getMaxY()){
                     fileChooser=new FileChooser();
                     fileChooser.setTitle(chooserTitle);
                     fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("png","*.png"),new
                             FileChooser.ExtensionFilter("jpg","*.jpg"),new FileChooser.ExtensionFilter("jpeg","*.jpeg"));
                     File file =fileChooser.showOpenDialog(stage);
                     if(file!=null){
                         path.append(file.getAbsolutePath());
                     }

                 }
             }
         });
        return path.toString();
    }

}
