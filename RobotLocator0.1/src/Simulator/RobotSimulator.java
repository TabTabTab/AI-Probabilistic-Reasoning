package Simulator;

import Navigation.Direction;
import Navigation.Point;

public class RobotSimulator {
	int NColumns;
	int NRows;
	Point currentPoint;
	Direction direction;
	public RobotSimulator(int NColumns, int NRows){
		this.NColumns=NColumns;
		this.NRows=NRows;

		this.currentPoint=SimulatorUtils.getRandomLocation(NColumns, NRows);
		this.direction=SimulatorUtils.getRandomValidDirection(currentPoint, NColumns, NRows);
	}
	/**
	 * 
	 * @return a new location for the robot
	 */
	public Point moveRobot(){
		changeCords();
		return currentPoint.deepCopy();

	}

	private void changeCords(){
		if (SimulatorUtils.willCollide(currentPoint, direction, NRows, NColumns)){
			direction=SimulatorUtils.getRandomValidDirection(direction, currentPoint, NColumns,NRows);
		}else{

			//no wall encountered
			//roll dice 0.3 new dir
			//			0.7 old dir
			double roll=Math.random();
			if(roll<0.3){
				//new dir
				direction=SimulatorUtils.getRandomValidDirection(direction, currentPoint, NColumns,NRows);
			}
		}
		int newX=currentPoint.getX()+direction.getXIncrementation();
		int newY=currentPoint.getY()+direction.getYIncrementation();
		
		
		int dir=-10;
		if(direction.getXIncrementation()==1){
			dir=1;
		}
		else if(direction.getXIncrementation()==-1){
			dir=3;
		}
		else if (direction.getYIncrementation()==-1){
			dir=0;
		}
		else{
			dir=2;
		}
		currentPoint=new Point(newX,newY,dir);
	}
	public Point getLocation(){
		return currentPoint.deepCopy();
	}
}


