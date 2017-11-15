package botX;

import battlecode.common.*;

public class Debug {

    public static void Log(String message){
        System.out.println(message);
    }

    public static void Log(String message, MapLocation location){
        System.out.println(message + " X: " + location.x + " Y: " + location.y);
    }

    public static void Log(String message, Direction direction){
        System.out.println(message + " R: " + direction.radians);
    }

    public static void Log(String message, TreeInfo treeInfo){
        System.out.println(message + " ID: " + treeInfo.getID() + " H: " + treeInfo.health);
    }
}
