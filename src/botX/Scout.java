package botX;

import battlecode.common.*;

class Scout extends Robot {

    private MapLocation[] enemy = robotController.getInitialArchonLocations(Robot.enemy);
    private MapLocation startingLocation = robotController.getLocation();
    private Direction movementDirection = robotController.getLocation().directionTo(enemy[0]);

    public void onUpdate(){

        while(true){

            try {
                //Build squads.
                //Don't fire at each other.
                boolean moved = tryMove(movementDirection, 10, 6);
                if(!moved){
                    movementDirection = movementDirection.rotateLeftDegrees(90);
                    tryMove(movementDirection);
                }

                RobotInfo[] enemies = robotController.senseNearbyRobots(4, Robot.enemy);
                TreeInfo[] treesAroundMe = robotController.senseNearbyTrees(4, Robot.enemy);

                if(enemies.length > 0){
                    for (int i = 0; i < enemies.length; i++){
                        robotController.broadcast(Constants.EnemyPositionBroadcastChannel,
                                Constants.EnemyFound &
                                        (byte)enemies[i].location.x &
                                        (byte)enemies[i].location.y);
                        System.out.println("I found an enemy at X:" + enemies[i].location.x + " Y:" + enemies[i].location.y);
                    }
                    //Figure out what to do to not run into enemies.
                }

                if(treesAroundMe.length > 0){
                    for (int i = 0; i < treesAroundMe.length; i++){

                        robotController.broadcast(Constants.TreeBroadcastChannel,
                                Constants.TreeFound &
                                        (byte) treesAroundMe[i].location.x &
                                        (byte) treesAroundMe[i].location.y);
                        System.out.println("I found a tree at X:" + treesAroundMe[i].location.x + " Y:" + treesAroundMe[i].location.y);
                    }
                }
                Clock.yield();
            }
            catch (Exception e){

            }
        }
    }
}
