package woundsDetection;

public class PathConverter {
    public static String  toJvmPath(String path){
        StringBuilder jvmPath=new StringBuilder();
        jvmPath.append("file:");
        int intialIndex=0;
        for(int i=0;i<path.length();i++){
            if(path.charAt(i)=='\\'){
                jvmPath.append(path.substring(intialIndex,i-1));
                intialIndex=i+1;
                jvmPath.append("\\");
            }
        }
        return jvmPath.toString();
    }

}
