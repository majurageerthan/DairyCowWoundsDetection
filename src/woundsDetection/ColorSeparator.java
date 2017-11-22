package woundsDetection;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.*;


/*
This class contains static methods to proceesing color of each pixel in an image

 */
public class ColorSeparator {
    private static  int regionsCount;
    private ArrayList<Point> points;
    public ColorSeparator(){
        points =new ArrayList<Point>();

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
                if(compareColors(image.getPixelReader().getColor(i,j),Color.WHITE,radius)){
                    if(points.isEmpty()){
                        regionsCount++;
                        points.add(new Point(i,j,regionsCount));
                    }else{
                        Point point;
                        int regionNumber;
                        ListIterator<Point> iterator =points.listIterator();
                        boolean isInExistingRegion=false;
                        while(iterator.hasNext()){
                             point =iterator.next();
                             regionNumber=pointRegionFinder(point,i,j);
                            if(regionNumber!=0){
                                isInExistingRegion=true;
                                points.add(new Point(i,j,regionNumber));
                                break;
                            }
                        }
                        if(!isInExistingRegion){
                            System.out.println(regionsCount);
                            regionsCount++;
                            points.add(new Point(i,j,regionsCount));
                        }
                    }
                }
            }
        }

    }
    protected void edgeMarker(GraphicsContext graphicsContext,Color color){
        ListIterator<Point> listIterator =points.listIterator();
        Point point;
        while(listIterator.hasNext()){
            point=listIterator.next();
            graphicsContext.setStroke(color);
            graphicsContext.strokeOval(point.getX(),point.getY(),.5,.5);
        }



    }

    private int  pointRegionFinder(Point point,int x,int y){

        if((point.getX()==x-1 && point.getY()==y)|| (point.getX()==x-1 && point.getY()==y-1)||(point.getX()==x && point.getY()==y-1)||(point.getX()==x-1 && point.getY()==y+1)){

            return point.getRegionNumber();
        }else{

            return 0;
        }
    }
}
