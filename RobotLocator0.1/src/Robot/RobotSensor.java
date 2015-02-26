package Robot;

import java.util.ArrayList;

import Navigation.NavigationLogic;
import Navigation.Point;

public class RobotSensor {

	
	public RobotSensor(){
	}
	
	public Point getSensorReading(RobotMovement robot){
		return sensorReading(robot.getLocation());
	}
	
	private Point sensorReading(Point current){
		//all possible readings are stored in an array. Multiple copies will exist in order to up the 
		//possibility of suh readings
		Point[] possiblePoints=new Point[40];
		int currentArrayIndex=0;
		//report the true location with possibility 0.1 (4/40)
		while(currentArrayIndex<4){
			possiblePoints[currentArrayIndex]=current;
			currentArrayIndex++;
		}
		//report a close neighbour location with possibility 0.05 each (2/40)*(max 8)
		ArrayList<Point> currentNeighbours=NavigationLogic.getDegreeNeighbours(current,1);
		for(Point p:currentNeighbours){
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
		}
		//report a distant neighbour location with possibility 0.025 each (1/40)*(max 16)
		currentNeighbours=NavigationLogic.getDegreeNeighbours(current,2);
		for(Point p:currentNeighbours){
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
		}
		//System.out.println("remaining nulls: "+(possiblePoints.length-currentArrayIndex));
		//reporst nothing with possibility 0.1 (4/10) as well as for every other impossible "location"
		//so...fill all remaining spots with nothing
		//add nulls to correspond to notning 
		while(currentArrayIndex<possiblePoints.length){
			possiblePoints[currentArrayIndex]=null;
			currentArrayIndex++;
		}
		//choose onw of these locations
		int randomIndex=(int)(Math.random()*possiblePoints.length);
		return possiblePoints[randomIndex];
	}
}
