package botX;

import battlecode.common.*;

class Tank extends Robot {

    private MapLocation[] enemy = robotController.getInitialArchonLocations(Robot.enemy);
    private Direction movementDirection = robotController.getLocation().directionTo(enemy[0]);

    public void onUpdate(){

        while(true){

            try {

                tryMove(movementDirection);

                RobotInfo[] enemies = robotController.senseNearbyRobots(8.0f * Robot.bodyRadius, Robot.enemy);
                TreeInfo[] enemyTrees = robotController.senseNearbyTrees(8.0f * Robot.bodyRadius, Robot.enemy);

                if(enemies.length > 0){
                    int x = 0;
                    int y = 0;
                    for(int i = 0; i < enemies.length; i++){
                        x += enemies[i].location.x;
                        y += enemies[i].location.y;
                    }
                    float avgX = x / enemies.length;
                    float avgY = y / enemies.length;
                    MapLocation averageLocation = new MapLocation(avgX, avgY);
                    if(robotController.canFireTriadShot()){
                        robotController.fireTriadShot(robotController.getLocation().directionTo(averageLocation));
                    }
                }

                if(enemyTrees.length > 0){
                    int x = 0;
                    int y = 0;
                    for(int i = 0; i < enemyTrees.length; i++){
                        x += enemyTrees[i].location.x;
                        y += enemyTrees[i].location.y;
                    }
                    float avgX = x / enemies.length;
                    float avgY = y / enemies.length;
                    MapLocation averageLocation = new MapLocation(avgX, avgY);
                    movementDirection = robotController.getLocation().directionTo(averageLocation);
                }
                Clock.yield();
            }
            catch (Exception e){

            }
        }
    }
}
