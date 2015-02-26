package Robot;

import Graphics.WorldView;
import Navigation.Point;

public class Robot {
	WorldView view;
	RobotMovement movement;
	RobotSensor sensor;
	RobotLocator locator;
	int locatorCorrect=0;
	int sensorCorrect=0;
	public Robot(WorldView view){
		this.view=view;
		this.movement=new RobotMovement(WorldView.NCOLUMNS,WorldView.NROWS);
		this.sensor=new RobotSensor();
		this.locator=new RobotLocator();
	}

	public void run(){
		while(true){
			//the location should actually be hidden
			Point robotLocation=movement.moveRobot();
			Point sensorLocation=sensor.getSensorReading(movement);
			locator.addSensorReading(sensorLocation);
			Point locationGuess=locator.mostProbablePosition();
			view.setRobot(robotLocation);
			view.setSensor(sensorLocation);
			view.setLocator(locationGuess);
			view.repaint();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void statisticsRun(int numberOfSteps){
		for(int i=0;i<numberOfSteps;i++){
			//the location should actually be hidden
			Point robotLocation=movement.moveRobot();
			Point sensorLocation=sensor.getSensorReading(movement);
			locator.addSensorReading(sensorLocation);
			Point locationGuess=locator.mostProbablePosition();
			if(robotLocation.equals(locationGuess)){
				locatorCorrect++;
			}
			if(robotLocation.equals(sensorLocation)){
				sensorCorrect++;
			}
			if(i%250==0){
				System.out.println("Number of steps moved: "+i);
				System.out.println("Locator correct guess percentage: "+(100*locatorCorrect/(i+1.0)));
			}
		}
	}

}
