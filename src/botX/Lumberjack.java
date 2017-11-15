package botX;

import battlecode.common.*;

class Lumberjack extends Robot {

    //Is mostly an offensive class to destroy the other teams trees.
    //Use in combination with soliders and tanks.

    //Trees: Deals 5 damage to trees using Chop()
    public void onUpdate()
    {
        boolean moved = false;
        while (true){
            try {

                int val = robotController.readBroadcast(Constants.TreeBroadcastChannel);

                int found = val << 2;
                int x = val << 2;
                int y = val << 2;
                if(!moved) {
                    moved = tryMove(randomDirection());
                }
                TreeInfo[] trees = robotController.senseNearbyTrees(4.0f, robotController.getTeam());

                Clock.yield();
            }
            catch (Exception e){
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}
