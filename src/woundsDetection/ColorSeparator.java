package woundsDetection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;

/*
This class contains static methods to proceesing color of each pixel in an image

 */
public class ColorSeparator {
    private static  int regionsCount;
    private final Map<Integer,List<Point>> regions;
    public ColorSeparator(){
        regions=new HashMap<Integer, List<Point>>();

    }
    static {
        regionsCount=0;

    }
    public static boolean  compareColors(Color color1 ,Color color2,double radius){
        double color1_rValue= color1.getRed();
        double color1_bValue= color1.getBlue();
        double color1_gValue= color1.getGreen();
        double color2_rValue= color2.getRed();
        double color2_bValue= color2.getBlue();
        double color2_gValue= color2.getGreen();

        //Check wheather two points are inside sphere
        if(getDistance(color1_rValue,color1_gValue,color1_bValue,color2_rValue,color2_gValue,color2_bValue) <=radius){
            return true;
        }else{ return false;}

    }

    /*
    method return distance between two points in  3 dimensional

     */
    private static double getDistance( double x1,double y1,double z1,double x2,double y2 ,double z2){
        double radius =Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2)+Math.pow((z1-z2),2));
        //System.out.println(radius);
        return radius;
    }

    protected void regionOfInterestDetector(Image image, Color color, double radius){

        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){

                if(compareColors(color,image.getPixelReader().getColor(i,j),radius)){
                    //points.add(new Point(i,j));
                    if(regions.isEmpty()){
                        regions.put(regionsCount,new  ArrayList<Point>());
                        regions.get(regionsCount).add(new Point(i,j));
                    }else{
                        final int x=i;
                        final int y=j;

                        //System.out.println(x+" , "+y);
                        Iterator<Map.Entry<Integer, List<Point>>> it =regions.entrySet().iterator();
                        while(it.hasNext()){
                            List<Point> list = it.next().getValue();

                            for(int k=0;i<list.size();k++){
                                Point point =list.get(k);
                                if ( pointRegionFinder(point, x, y)) {
                                    list.add(new Point(x, y));
                                } else {
                                    regionsCount++;
                                    regions.put(regionsCount, new ArrayList<Point>());
                                    regions.get(regionsCount).add(new Point(x, y));
                                }
                            }

                        }

                    }
                }
            }
        }
    }
    protected void edgeMarker(GraphicsContext graphicsContext,Color color){
        regions.forEach((regionsCount,list)->{
            list.forEach(point -> {
                graphicsContext.setStroke(color);
                graphicsContext.strokeOval(point.getX(),point.getY(),.5,.5);
            });
        });

    }
    private boolean pointRegionFinder(Point point,int x,int y){

        if((point.getX()==x-1 && point.getY()==y)|| (point.getX()==x-1 && point.getY()==y-1)||(point.getX()==x && point.getY()==y-1)){
            System.out.println("true");
            return true;
        }else{
            System.out.println("false");
            return false;
        }
    }
}
