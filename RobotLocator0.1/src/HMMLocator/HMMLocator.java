package HMMLocator;

import java.util.ArrayList;

import Graphics.WorldView;
import Navigation.*;

public class HMMLocator {
	private static final int NORTH=0;
	private static final int EAST=1;
	private static final int SOUTH=2;
	private static final int WEST=3;
	public static final int NDIRECTIONS=4;
	/*
	 * states: 
	 * 			direction
	 * 			position
	 * 
	 */
	/*
	 * 
	 */
	//x,y,dir
	private double[][][] probabilityMatrix;

	public HMMLocator(){
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


		//	if(reading == null){
		// handle it
		//borde ta den punkten med mest sannolikhet och lägga till ett i direktion
		//}
		//else{
		double[][][] newProbabilityMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS][NDIRECTIONS];
		double normalisationAlpha=0;
		for(int x =0; x<WorldView.NCOLUMNS;x++){
			for(int y =0;y<WorldView.NROWS;y++){
				for(int d =0;d<NDIRECTIONS;d++){
					double p=sensorValueProbability(new State(new Point(x,y),d),reading);
					double temp =0;
					for( int x2 =0; x2<WorldView.NCOLUMNS;x2++){
						for(int y2 =0;y2<WorldView.NROWS;y2++){
							for(int d2 =0;d2<NDIRECTIONS;d2++){
								double oldProb=probabilityMatrix[x2][y2][d2];
								//optimera pga konstant?
								double tripProbability = probabilityToGetFromTo(new State(new Point(x2,y2),d2),new State(new Point(x,y),d));
								temp += (oldProb*tripProbability);
							}
						}
					}
					newProbabilityMatrix[x][y][d] = p * temp;
					normalisationAlpha+=newProbabilityMatrix[x][y][d];
				}
			}
		}

		probabilityMatrix=newProbabilityMatrix;
		for( int x2 =0; x2<WorldView.NCOLUMNS;x2++){
			for(int y2 =0;y2<WorldView.NROWS;y2++){
				for(int d2 =0;d2<NDIRECTIONS;d2++){
					newProbabilityMatrix[x2][y2][d2]/=normalisationAlpha;
					//System.out.print(probabilityMatrix[x2][y2]+ " ");
				}
			}
			//System.out.println();
		}

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
	private double[][] concatonateMatrix(ArrayList<double[][]> matrices){
		double[][] newMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS];
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				newMatrix[x][y]=0;
			}
		}
		for(double[][] m:matrices){
			for(int x=0;x<WorldView.NCOLUMNS;x++){
				for(int y=0;y<WorldView.NROWS;y++){
					newMatrix[x][y]+=m[x][y];
				}
			}
		}
		return newMatrix;
	}

	private double sensorValueProbability(State state,Point reading){

		Point location=state.getLocation();
		ArrayList<Point> deg1Neighbours=getDegreeNeighbours(location,1);
		ArrayList<Point> deg2Neighbours=getDegreeNeighbours(location,2);

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

	private static ArrayList<Point> getDegreeNeighbours(Point point,int degree){
		ArrayList<Point> neighbours=new ArrayList<Point>();
		int leftEdgeX=point.getX()-degree;
		int rightEdgeX=point.getX()+degree;
		int upperEdgeY=point.getY()-degree;
		int lowerEdgeY=point.getY()+degree;
		for(int x=leftEdgeX;x<=rightEdgeX;x++){
			for(int y=upperEdgeY;y<=lowerEdgeY;y++){
				Point current=new Point(x,y);
				//Throw out unvalid points
				if(!validPoint(current)){
					continue;
				}
				if (x==leftEdgeX || x==rightEdgeX || y==upperEdgeY || y==lowerEdgeY){
					//the point is on the edge
					neighbours.add(current);
				}
			}
		}
		return neighbours;
	}
	private static double probabilityToGetFromTo(State from,State to){
		//if same dir, they have taken the 70% route


		double probability=0;
		Point pointTo = to.getLocation();

		ArrayList<State> possibleStates=new ArrayList<State>();

		State maybePossible1=new State(new Point(from.getX(),from.getY()+1),NORTH);
		State maybePossible2=new State(new Point(from.getX()+1,from.getY()),EAST);
		State maybePossible3=new State(new Point(from.getX(),from.getY()-1),SOUTH);
		State maybePossible4=new State(new Point(from.getX()-1,from.getY()),WEST);
		if(validPoint(maybePossible1.getLocation())){
			possibleStates.add(maybePossible1);
		}
		if(validPoint(maybePossible2.getLocation())){
			possibleStates.add(maybePossible2);
		}
		if(validPoint(maybePossible3.getLocation())){
			possibleStates.add(maybePossible3);
		}
		if(validPoint(maybePossible4.getLocation())){
			possibleStates.add(maybePossible4);
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
					probability = 1/possibleStates.size();
				}
			}
		}

		return probability;
	}
	public static boolean validPoint(Point point){
		return (0<=point.getX() &&point.getX()<WorldView.NCOLUMNS) && (0<=point.getY() && point.getY()<WorldView.NROWS);
	}

	private static Direction intDirToDir(int dir){
		Direction direction;
		switch(dir){
		case(NORTH):
			direction=new Direction(0,1);
		break;
		case(SOUTH):
			direction=new Direction(0,-1);
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
