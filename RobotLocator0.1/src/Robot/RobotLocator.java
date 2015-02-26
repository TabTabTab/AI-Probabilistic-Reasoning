package Robot;

import java.util.ArrayList;

import Graphics.WorldView;
import Navigation.Direction;
import Navigation.NavigationLogic;
import Navigation.Point;
import Navigation.State;

public class RobotLocator {
	private static final int NORTH=0;
	private static final int EAST=1;
	private static final int SOUTH=2;
	private static final int WEST=3;
	public static final int NDIRECTIONS=4;

	
	private double[][][] probabilityMatrix;

	public RobotLocator(){
		probabilityMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS][NDIRECTIONS];
		double initialProbability= 1.0/(WorldView.NCOLUMNS*WorldView.NROWS*NDIRECTIONS);
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				for(int d=0;d<NDIRECTIONS;d++)
					probabilityMatrix[x][y][d]=initialProbability;
			}
		}
	}

	public void addSensorReading(Point reading){
		double[][][] newProbabilityMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS][NDIRECTIONS];
		double normalisationAlpha=0;
		for(int x =0; x<WorldView.NCOLUMNS;x++){
			for(int y =0;y<WorldView.NROWS;y++){
				for(int d =0;d<NDIRECTIONS;d++){
					double p=sensorValueProbability(new State(new Point(x,y),d),reading);
					double probabilitySum =0;
					for( int oldX =0; oldX<WorldView.NCOLUMNS;oldX++){
						for(int oldY =0;oldY<WorldView.NROWS;oldY++){
							for(int oldDir =0;oldDir<NDIRECTIONS;oldDir++){
								double oldProb=probabilityMatrix[oldX][oldY][oldDir];
								//optimera pga konstant?
								State from = new State(new Point(oldX,oldY),oldDir);
								State to = new State(new Point(x,y),d);
								double tripProbability = transitionProbability(from,to);
								probabilitySum += (oldProb*tripProbability);
							}
						}
					}
					newProbabilityMatrix[x][y][d] = p * probabilitySum;
					normalisationAlpha+=newProbabilityMatrix[x][y][d];
				}
			}
		}

		
		for( int x =0; x<WorldView.NCOLUMNS;x++){
			for(int y =0;y<WorldView.NROWS;y++){
				for(int d =0;d<NDIRECTIONS;d++){
					newProbabilityMatrix[x][y][d] /= normalisationAlpha;
				}
			}
			
		}
		probabilityMatrix=newProbabilityMatrix;
	}


	public Point mostProbablePosition(){
		int xVal=0;
		int yVal=0;
		double maxProbability=Double.MIN_VALUE;
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				double tempProbability=0;
				for(int d =0;d<NDIRECTIONS;d++){
					tempProbability+=probabilityMatrix[x][y][d];
				}
				if(tempProbability>maxProbability){
					maxProbability=tempProbability;
					xVal=x;
					yVal=y;
				}	
			}
		}
		return new Point(xVal,yVal);
	}

	private double sensorValueProbability(State state,Point reading){

		Point location=state.getLocation();
		ArrayList<Point> deg1Neighbours=NavigationLogic.getDegreeNeighbours(location,1);
		ArrayList<Point> deg2Neighbours=NavigationLogic.getDegreeNeighbours(location,2);

		if(reading==null){
			double nullChance = 0.1;
			nullChance += ((8-deg1Neighbours.size()) * 0.05);
			nullChance += ((16-deg2Neighbours.size()) * 0.025);
			return nullChance;
		}

		if(location.equals(reading)){
			return 0.1;
		}else if(deg1Neighbours.contains(reading)){
			return 0.05;
		}else if(deg2Neighbours.contains(reading)){
			return 0.025;
		}else{
			return 0;
		}
	}


	private static double transitionProbability(State from,State to){
		//if same dir, they have taken the 70% route


		double probability=0;

		ArrayList<State> possibleStates=new ArrayList<State>();

		State north=new State(new Point(from.getX(),from.getY()-1),NORTH);
		State east=new State(new Point(from.getX()+1,from.getY()),EAST);
		State south=new State(new Point(from.getX(),from.getY()+1),SOUTH);
		State west=new State(new Point(from.getX()-1,from.getY()),WEST);
		if(validPoint(north.getLocation())){
			possibleStates.add(north);
		}
		if(validPoint(east.getLocation())){
			possibleStates.add(east);
		}
		if(validPoint(south.getLocation())){
			possibleStates.add(south);
		}
		if(validPoint(west.getLocation())){
			possibleStates.add(west);
		}
		if(possibleStates.contains(to)){

			if(from.getDirection()==to.getDirection()){
				probability = 0.7;
			}
			else{
				Direction oldDirection=intDirToDir(from.getDirection());
				int newX=from.getX()+oldDirection.getXIncrementation();
				int newY=from.getY()+oldDirection.getYIncrementation();
				Point possibleDestination=new Point(newX,newY);
				if(validPoint(possibleDestination)){
					probability = 0.3 / (possibleStates.size()-1);
				}
				else{
					probability = 1.0/possibleStates.size();
				}
			}
		}

		return probability;
	}
	public static boolean validPoint(Point point){
		return (0<=point.getX() && point.getX()<WorldView.NCOLUMNS) && (0<=point.getY() && point.getY()<WorldView.NROWS);
	}

	private static Direction intDirToDir(int dir){
		Direction direction;
		switch(dir){
		case(NORTH):
			direction=new Direction(0,-1);
		break;
		case(SOUTH):
			direction=new Direction(0,1);
		break;
		case(WEST):
			direction=new Direction(-1,0);
		break;
		case(EAST):
			direction=new Direction(1,0);
		break;
		default:
			direction=null;
			break;
		}
		return direction;
	}
}
