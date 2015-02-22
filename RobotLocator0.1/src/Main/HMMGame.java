package Main;

import Graphics.WorldView;
import Navigation.Board;
import Navigation.Point;
import Sensor.RobotSensor;
import Simulator.RobotSimulator;

public class HMMGame {
	WorldView view;
	RobotSimulator simulator;
	RobotSensor sensor;
	Board board;
	public HMMGame(WorldView view){
		this.view=view;
		this.simulator=new RobotSimulator(WorldView.NCOLUMNS,WorldView.NROWS);
		this.sensor=new RobotSensor();
		board=new Board();
	}



	public void run(){
		for(int i=0;i<100;i++){
			Point robotLocation=simulator.moveRobot();
			Point sensorLocation=sensor.getSensorReading(simulator);
			setCurrentRobotAndSensor(robotLocation,sensorLocation);
			view.setBoard(board);
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
