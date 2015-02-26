package Main;

import Graphics.WorldView;
import HMMLocator.HMMLocator;
import Navigation.Board;
import Navigation.Point;
import Sensor.RobotSensor;
import Simulator.RobotSimulator;

public class HMMGame {
	WorldView view;
	RobotSimulator simulator;
	RobotSensor sensor;
	HMMLocator locator;
	Board board;
	int locatorcorrect=0;
	int sensorcorrect=0;
	public HMMGame(WorldView view){
		this.view=view;
		this.simulator=new RobotSimulator(WorldView.NCOLUMNS,WorldView.NROWS);
		this.sensor=new RobotSensor();
		this.locator=new HMMLocator();
		board=new Board();
	}

	public void run(){
		for(int i=0;i<200001;i++){
			Point robotLocation=simulator.moveRobot();
			Point sensorLocation=sensor.getSensorReading(simulator);
			//setCurrentRobotAndSensor(robotLocation,sensorLocation);
			locator.addSensorReading(sensorLocation);
			Point locatorLocation=locator.mostProbablePosition();
			if(robotLocation.equals(locatorLocation)){
				locatorcorrect++;
			}
			if(robotLocation.equals(sensorLocation)){
				sensorcorrect++;
			}
			if(i%5000==0){
				System.out.println("SC= " +sensorcorrect+" percentageright= "+(sensorcorrect/(i+1.0)));
				System.out.println("LC= " +locatorcorrect+" percentageright= "+(locatorcorrect/(i+1.0)));
				view.setRobot(robotLocation);
				view.setSensor(sensorLocation);
				view.setLocator(locatorLocation);
				view.repaint();
			}
			
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	private void setCurrentRobotAndSensor(Point robotLocation,Point sensorLocation){
		board.clearBoard();
		board.setRobotLocation(robotLocation);
		board.setSensorReading(sensorLocation);
	}
}
