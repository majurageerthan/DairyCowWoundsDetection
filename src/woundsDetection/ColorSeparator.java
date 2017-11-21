package woundsDetection;

import javafx.scene.paint.Color;

/*
This class contains static methods to proceesing color of each pixel in an image

 */
public class ColorSeparator {

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

}
