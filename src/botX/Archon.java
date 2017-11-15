package botX;

import battlecode.common.*;

public class Archon extends Robot {

    private boolean settled = false;
    private MapLocation myCurrentLocation = robotController.getLocation();
    private MapLocation[] enemyArchons = robotController.getInitialArchonLocations(Robot.enemy);
    private Direction buildDirection = myCurrentLocation.directionTo(enemyArchons[0]);
    private Direction safeDirection = buildDirection.rotateLeftDegrees(180);

    private int maxSoldiers = 15;
    private int maxGardeners = 6;
    private int gardenerCount = 0;
    private int soldierCount = 0;

    //Duties
    //Create Gardeners
    //Spend Bullets
    //Move away from attacking people.

    @Override
    public void onUpdate() {
        while (true) {
            try {
                //Assignment
                myCurrentLocation = robotController.getLocation();
                Debug.Log("My current location is ", myCurrentLocation);
                Direction dir = safeDirection.rotateLeftDegrees(180);

                //Hiring
                System.out.println(System.getProperty("bc.testing.seed", "0").hashCode() + 1);
                if (robotController.canHireGardener(dir) && gardenerCount < maxGardeners) {
                    robotController.hireGardener(dir);
                    gardenerCount++;
                    System.out.println("number of gardeners " + gardenerCount);
                }

                //Donating
                //You gotta spend that money.
                if(robotController.getTeamBullets() > 750){
                    robotController.donate(75);
                    System.out.println("I donated");
                }

                //Movement
                //Always try to get away from the enemy, but stop bouncing like an idiot.
                RobotInfo[] enemies = robotController.senseNearbyRobots(8, Robot.enemy);
                if(enemies.length > 0){
                    //I want to go say 10 units in front of me, is it on the map, if not keep going left or right until it is.
                    MapLocation hopeful = myCurrentLocation.add(0, 10);
                    if(!robotController.onTheMap(hopeful)){
                        for (int i = 0; i < 100; i++){
                            if(robotController.onTheMap(myCurrentLocation.add(i, 10))){
                                safeDirection = myCurrentLocation.directionTo(myCurrentLocation.add(i, 10));
                            }
                            else if(robotController.onTheMap(myCurrentLocation.add(i * -1, 10))){
                                safeDirection = myCurrentLocation.directionTo(myCurrentLocation.add(i * -1, 10));
                            }
                        }
                    }
                    settled = false;
                }
                if(!settled){
                    tryMove(safeDirection, 30, 3);
                }
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception for master");
                e.printStackTrace();
            }
        }
    }

    private void DonateBullets(){
        float bulletCount = robotController.getTeamBullets();
        int round = robotController.getRoundNum();
        int maxRounds = robotController.getRoundLimit();
        float ratio = round/maxRounds;
        //3000 rounds
        if(ratio > 0.05 && ratio < 0.1 && bulletCount > 750){
            //Throws game exception here for some reason.
            //robotController.donate(75);
        }

    }
}