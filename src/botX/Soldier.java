package botX;

import battlecode.common.*;

class Soldier extends Robot {

    private MapLocation[] enemy = robotController.getInitialArchonLocations(Robot.enemy);
    private Direction movementDirection = robotController.getLocation().directionTo(enemy[0]);

    private int engagementDistance = 15;
    private int degreeRotationOnBulletEncounter = 25;

    public void onUpdate(){

       while(true){

           try {

               RobotInfo[] enemies = robotController.senseNearbyRobots(8, Robot.enemy);
               if(enemies.length > 0){
                   movementDirection = robotController.getLocation().directionTo(enemies[0].location);
                   if(robotController.getLocation().distanceTo(enemies[0].location) < engagementDistance){
                       robotController.fireSingleShot(movementDirection);
                   }
               }

               BulletInfo[] bullets = robotController.senseNearbyBullets(4.0f * robotController.getType().bodyRadius);
               if(bullets.length > 0){
                   for (int i = 0; i < bullets.length; i++){
                       if(Robot.willCollideWithMe(bullets[i]))   {
                           if(robotController.getID() % 2 == 0){
                               movementDirection = movementDirection.rotateRightDegrees(degreeRotationOnBulletEncounter);
                           }
                           else{
                               movementDirection = movementDirection.rotateLeftDegrees(degreeRotationOnBulletEncounter);
                           }
                       }
                   }
               }

               if(!tryMove(movementDirection)){
                   movementDirection = robotController.getLocation().directionTo(enemy[0]);
               }

               Clock.yield();
           }
           catch (Exception e){

           }
       }
    }
}
