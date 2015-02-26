package Robot;

import Navigation.Direction;
import Navigation.Point;

public class RobotMovement {
	int NColumns;
	int NRows;
	Point currentPoint;
	Direction direction;
	public RobotMovement(int NColumns, int NRows){
		this.NColumns=NColumns;
		this.NRows=NRows;
		this.currentPoint=getRandomLocation();
		this.direction=getRandomDirection();
	}
	/**
	 * 
	 * @return a new location for the robot
	 */
	public Point moveRobot(){
		changeCords();
		return currentPoint;
	}
	public Point getLocation(){
		return currentPoint;
	}
	
	private void changeCords(){
		if (willCollide(currentPoint, direction)){
			direction=getRandomNonCollidingDirection(direction, currentPoint);
		}else{
			//no wall encountered
			//roll dice 0.3 new dir
			//			0.7 old dir
			double roll=Math.random();
			if(roll<0.3){
				//new dir
				direction=getRandomNonCollidingDirection(direction, currentPoint);
			}
		}
		int newX=currentPoint.getX()+direction.getXIncrementation();
		int newY=currentPoint.getY()+direction.getYIncrementation();
		currentPoint=new Point(newX,newY);
	}

	
	
	private boolean willCollide(Point point, Direction currentDirection){
		int newX=point.getX()+currentDirection.getXIncrementation();
		int newY=point.getY()+currentDirection.getYIncrementation();
		return newX<0 || newX>=NColumns || newY<0 || newY>=NRows;
	}
	private Direction getRandomDirection(){
		Direction newDirection=null;
		int roll=(int)(Math.random()*4);
		switch(roll){
		case(0):
			newDirection=new Direction(0,-1);
			break;
		case(1):
			newDirection=new Direction(0,1);
			break;
		case(2):
			newDirection=new Direction(-1,0);
			break;
		case(3):
			newDirection=new Direction(1,0);
			break;
		}
		return newDirection;
	}
	private Direction getRandomDirection(Direction excludedDirection){
		Direction newDirection=getRandomDirection();
		while(newDirection.equals(excludedDirection)){
			newDirection=getRandomDirection();
		}
		return newDirection;
	}
	private Direction getRandomNonCollidingDirection(Direction excludedDirection, Point currentPoint){
		Direction newDirection = getRandomDirection(excludedDirection);
		while(willCollide(currentPoint,newDirection)){
			newDirection = getRandomDirection(excludedDirection);
		}
		return newDirection;
	}

	private Point getRandomLocation(){
		 int x=(int)(Math.random()*NColumns);
		 int y=(int)(Math.random()*NRows);
		 return new Point(x,y);
	}
	
}


