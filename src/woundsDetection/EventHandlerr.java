package woundsDetection;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class EventHandlerr {
    private static FileChooser fileChooser;

    //Method take a Canvas and specific area as rectangle coordinates
    //if mouse triggered on particular area it get image path from user
    //and return as string
    public  static String openFile(String chooserTitle, Canvas canvas, Rectangle2D rectangle, final Stage stage,boolean isThermal){
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
                         try {
                             path.append(file.toURI().toURL().toExternalForm());
                             final Image  image =new Image(path.toString());
                             /*
                             Start a new Thread to do imaging
                             if image is thermal only
                              */
                             if(isThermal){

                                 Thread imagingThread =new Thread(new Runnable() {
                                     @Override
                                     public void run() {
                                         ColorSeparator colorSeparator =new ColorSeparator();
                                         colorSeparator.regionOfInterestDetector(image, Color.WHITE,.1);
                                         colorSeparator.edgeMarker(canvas.getGraphicsContext2D(),Color.RED);
                                     }
                                 });
                                 imagingThread.start();
                                  //imagingThread.join();
                             }

                             stage.setWidth((canvas.getWidth()>image.getWidth())?(stage.getWidth()+image.getWidth()-canvas.getWidth()):(stage.getWidth()+image.getWidth()-canvas.getWidth()));
                             stage.setHeight((canvas.getHeight() >image.getHeight())? (stage.getHeight()-canvas.getHeight()+image.getHeight()): (image.getHeight() ));
                             canvas.setHeight(image.getHeight());
                             canvas.setWidth(image.getWidth());

                             canvas.getGraphicsContext2D().clearRect(0,0,canvas.getWidth(),canvas.getHeight());
                             canvas.getGraphicsContext2D().drawImage(image,0,0);
                         } catch (MalformedURLException e) {
                             e.printStackTrace();
                             System.out.println(e);
                         }
                     }

                 }
             }
         });

        return path.toString();
    }

}
