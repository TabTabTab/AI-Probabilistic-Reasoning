package Sensor;

import Navigation.Point;
import Simulator.RobotSimulator;

public class RobotSensor {

	
	public RobotSensor(){
	}
	
	public Point getSensorReading(RobotSimulator robot){
		//currentPoint=new Point(currentX,currentY);
		return SensorUtils.sensorReading(robot.getLocation());
	}
}
