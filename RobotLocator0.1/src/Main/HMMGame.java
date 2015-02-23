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
	public HMMGame(WorldView view){
		this.view=view;
		this.simulator=new RobotSimulator(WorldView.NCOLUMNS,WorldView.NROWS);
		this.sensor=new RobotSensor();
		this.locator=new HMMLocator();
		board=new Board();
	}



	public void run(){
		for(int i=0;i<100;i++){
			Point robotLocation=simulator.moveRobot();
			Point sensorLocation=sensor.getSensorReading(simulator);
			setCurrentRobotAndSensor(robotLocation,sensorLocation);
			locator.addSensorReading(sensorLocation);
			view.setRobot(robotLocation);
			view.setSensor(sensorLocation);
			view.setLocator(locator.mostProbablePosition());
			view.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void setCurrentRobotAndSensor(Point robotLocation,Point sensorLocation){
		board.clearBoard();
		board.setRobotLocation(robotLocation);
		board.setSensorReading(sensorLocation);
	}
}
