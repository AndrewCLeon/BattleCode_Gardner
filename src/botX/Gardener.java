package botX;
import battlecode.common.*;

import java.util.Random;

/*
 _____               _
|  __ \             | |
| |  \/ __ _ _ __ __| | ___ _ __   ___ _ __
| | __ / _` | '__/ _` |/ _ \ '_ \ / _ \ '__|
| |_\ \ (_| | | | (_| |  __/ | | |  __/ |
 \____/\__,_|_|  \__,_|\___|_| |_|\___|_|

*/
class Gardener extends Robot {

    private boolean settled = false;
    private Direction movementDirection = null;
    private Direction buildDirection = randomDirection();
    private MapLocation[] enemyArchons = robotController.getInitialArchonLocations(Robot.enemy);
    private MapLocation myCurrentLocation = robotController.getLocation();
    private float spacingScale = 6.0f * robotController.getType().bodyRadius;

    private float soldiersBuilt = 0;
    private float lumberjacksBuilt = 0;
    private float attempts = 60;
    private float degreeStep = 2;
    private float treeDegreeSpacing = 60;
    //Trees: Each tree starts off with 10 hp.
    //       Each tree gains .5 hp per round unless watered
    //       A gardener can water 1 tree per round which increases tree health by 5.
    public void onUpdate() {
        while (true) {
            try {
                myCurrentLocation = robotController.getLocation();
                Debug.Log("My current location is ", myCurrentLocation);

                if (movementDirection == null) {
                    movementDirection = buildDirection;
                    Debug.Log("Movement Direction was null, Movement direction is now ", buildDirection);
                }
                if(settled){
                    TreeInfo[] trees = robotController.senseNearbyTrees(robotController.getType().bodyRadius * 2, robotController.getTeam());
                    Debug.Log("Hey look, trees. C: " + trees.length);
                    if(trees.length < 6){
                        if(Math.random() < 0.7) {
                            if (robotController.canBuildRobot(RobotType.SOLDIER, buildDirection)) {
                                robotController.buildRobot(RobotType.SOLDIER, buildDirection);
                            }
                        }
                        if(Math.random() >= 0.7){
                            if(robotController.canBuildRobot(RobotType.TANK, buildDirection)){
                                robotController.buildRobot(RobotType.TANK, buildDirection);
                            }
                        }
                    }
                    if(robotController.getBuildCooldownTurns() == 0){
                        //Build a few trees
                        if(trees.length < 6)
                        {
                            Debug.Log("Build direction was rotated " + treeDegreeSpacing + " right, it is now ", buildDirection);
                            if (robotController.canPlantTree(buildDirection)) {
                                robotController.plantTree(buildDirection);
                                Debug.Log("Planted tree at ", buildDirection);
                            }
                            buildDirection = buildDirection.rotateRightDegrees(treeDegreeSpacing);
                        }
                        else{
                            Debug.Log("Trying to build a soldier");
                            if(robotController.canBuildRobot(RobotType.SOLDIER, buildDirection)){
                                robotController.buildRobot(RobotType.SOLDIER, buildDirection);
                            }
                            else{
                                buildDirection = buildDirection.rotateRightDegrees(treeDegreeSpacing);
                                Debug.Log("Nope, I guess I will rotate to ", buildDirection);
                            }
                        }
                    }

                    TreeInfo minHealthTree = null;
                    for (TreeInfo tree : trees) {
                        if (tree.health < 70) {
                            if (minHealthTree == null || tree.health < minHealthTree.health) {
                                minHealthTree = tree;
                            }
                        }
                    }
                    if (minHealthTree != null) {
                        robotController.water(minHealthTree.ID);
                        Debug.Log("This tree needs to be healed. ", minHealthTree);
                    }
                }//Not Settled.
                else {

                    if (tryMove(movementDirection)) {
                        System.out.println("moved");
                    } else {
                        Debug.Log("I can't move in that direction. ", movementDirection);
                        movementDirection = randomDirection();
                        Debug.Log("Movement direction reset to ", movementDirection);
                        tryMove(movementDirection);
                    }

                    if (!(robotController.isCircleOccupiedExceptByThisRobot(robotController.getLocation(), spacingScale))
                            && robotController.canSenseAllOfCircle(robotController.getLocation(), 4)) {
                        settled = true;
                        Debug.Log("I found my spot. ", myCurrentLocation);
                        if (robotController.canPlantTree(buildDirection)) {
                            robotController.plantTree(buildDirection);
                            buildDirection = buildDirection.rotateRightDegrees(treeDegreeSpacing);
                        }
                    }
                }
                Clock.yield();
            } catch (Exception e) {
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}
