package searchPractice;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Tools.Coords;
import RobotBattleRunner.RBRunner;
import robocode.Robot;

public class RouteBot extends Robot
{

  public void run ()
  {

    Coords randomGenerator = new Coords(randomGenerator.getRow(), randomGenerator.getCol());
    
    randomGenerator.randomOriginalCoords(randomGenerator.getRow(), randomGenerator.getCol());
    
    List<Coords> list = AStarAlgorithm.Astar((seed2.getBattlefield()));
    
    System.out.println(list);
    
    if((int)getHeading()!=0)
    {
      turnLeft((int)getHeading());
    }
    if(list!=null)
    {
      while(list.size()>1)
      {
        Coords initialpos = new Coords(list.get(0).getRow(), list.get(0).getCol());
        Coords nextpos = new Coords(list.get(1).getRow(), list.get(1).getCol());

        if(nextpos.getCol() > initialpos.getCol()) {
          if((int)getHeading()==0)
          {
            turnRight(90);
          }else if((int)getHeading()==90)
          {
            turnRight(180);
          }else if((int)getHeading()==180)
          {
            turnLeft(90);
          }
          ahead(64);

        } else if(nextpos.getCol() < initialpos.getCol())
        {
          if((int)getHeading()==0)
          {
            turnLeft(90);
          }else if((int)getHeading()==180)
          {
            turnRight(90);
          }else if((int)getHeading()==270)
          {
            turnRight(180);
          }
          ahead(64);

        } else if(nextpos.getRow() < initialpos.getRow())
        {
          if((int)getHeading()==90)
          {
            turnRight(90);
          }else if((int)getHeading()==180)
          {
            turnRight(180);
          }else if((int)getHeading()==270)
          {
            turnLeft(90);
          }
          ahead(64);

        } else if(nextpos.getRow() > initialpos.getRow())
        {
          if((int)getHeading()==0)
          {
            turnLeft(180);
          }else if((int)getHeading()==90)
          {
            turnLeft(90);
          }else if((int)getHeading()==270)
          {
            turnRight(90);
          }
          ahead(64);
        }
        list.remove(0);
      }
    }
  }
}