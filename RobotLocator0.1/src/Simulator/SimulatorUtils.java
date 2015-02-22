package Simulator;

import Navigation.Direction;
import Navigation.Point;
public class SimulatorUtils {
	
	public static boolean isAtEdge(Point point, Direction currentDirection, int NColumns, int NRows){
		int newX=point.getX()+currentDirection.getXIncrementation();
		int newY=point.getY()+currentDirection.getYIncrementation();
		return newX<0 || newX>=NColumns || newY<0 || newY>=NRows;
	}
	public static Direction getRandomDirection(){
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
	public static Direction getRandomDirection(Direction excludedDirection){
		Direction newDirection = excludedDirection;
		while(newDirection.equals(excludedDirection)){
			newDirection=getRandomDirection();
		}
		return newDirection;
	}
	public static Direction getRandomValidDirection(Direction excludedDirection, Point currentPoint, int NColumns,int NRows){
		Direction newDirection = getRandomDirection(excludedDirection);
		while(isAtEdge(currentPoint,newDirection,NRows,NColumns)){
			newDirection = getRandomDirection(excludedDirection);
		}
		return newDirection;
	}
	public static Direction getRandomValidDirection(Point currentPoint, int NColumns, int NRows){
		Direction newDirection = getRandomDirection();
		while(isAtEdge(currentPoint,newDirection,NRows,NColumns)){
			newDirection = getRandomDirection();
		}
		return newDirection;
	}
	public static Point getRandomLocation(int NColumns,int NRows){
		 int x=(int)(Math.random()*NColumns);
		 int y=(int)(Math.random()*NRows);
		 return new Point(x,y);
	}
	
	
}
