package HMMLocator;

import java.util.ArrayList;

import Graphics.WorldView;
import Navigation.Point;

public class HMMLocator {
	/*
	 * states: 
	 * 			direction
	 * 			position
	 * 
	 */
	private double[][] probabilityMatrix;
	public HMMLocator(){
		probabilityMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS];
		double initialProbability= 1.0/(WorldView.NCOLUMNS*WorldView.NROWS);
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				probabilityMatrix[x][y]=initialProbability;
			}
		}
	}

	public void addSensorReading(Point reading){
	

	//	if(reading == null){
			// handle it
			//borde ta den punkten med mest sannolikhet och lägga till ett i direktion
		//}
		//else{
			double[][] newProbabilityMatrix= new double[WorldView.NCOLUMNS][WorldView.NROWS];
			for( int x =0; x<WorldView.NCOLUMNS;x++){
				for(int y =0;y<WorldView.NROWS;y++){

					double p=sensorValueProbability(new Point(x,y),reading);
					

					double temp =0;

					for( int x2 =0; x2<WorldView.NCOLUMNS;x2++){
						for(int y2 =0;y2<WorldView.NROWS;y2++){
							double oldProb=probabilityMatrix[x2][y2];
							double tripProbability = probabilityToGetFromTo(new Point(x2,y2),new Point(x,y));
							
							
							temp += (oldProb*tripProbability);
							
						}
					}
					
					
					
					newProbabilityMatrix[x][y] = p * temp;

				}
			}

			probabilityMatrix=newProbabilityMatrix;
//			for( int x2 =0; x2<WorldView.NCOLUMNS;x2++){
//				for(int y2 =0;y2<WorldView.NROWS;y2++){
//					System.out.print(probabilityMatrix[x2][y2]+ " ");
//				}
//				System.out.println();
//			}
		//}





	}


	public Point mostProbablePosition(){
		int xVal=0;
		int yVal=0;
		double maxProbability=Double.MIN_VALUE;
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				if(probabilityMatrix[x][y]>maxProbability){
					maxProbability=probabilityMatrix[x][y];
					xVal=x;
					yVal=y;
				}
			}
		}
		return new Point(xVal,yVal);
	}
	private double[][] matrixClone(){
		double[][] theClone = new double[WorldView.NCOLUMNS][WorldView.NROWS];
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				theClone[x][y]=probabilityMatrix[x][y];
			}
		}
		return theClone;
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

	private double sensorValueProbability(Point location,Point reading){
		
		ArrayList<Point> deg1Neighbours=getDegreeNeighbours(location,1);
		ArrayList<Point> deg2Neighbours=getDegreeNeighbours(location,2);
		
		if(reading==null){
			//TODO handle it
			double nullChance = 0.1;
			nullChance += (8-deg1Neighbours.size() * 0.05);
			nullChance += (16-deg2Neighbours.size() * 0.025);
			return nullChance;
		}

		//first check not needed
		if(location.equals(reading)){
			return 0.1;
		}else if(deg1Neighbours.contains(reading)){
			return 0.05;
		}else if(deg2Neighbours.contains(reading)){
			return 0.025;
		}else{
			System.out.println("WTF här borde vi inte kunna hamna?");
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
	private static double probabilityToGetFromTo(Point from,Point to){
		double probability=0;
		ArrayList<Point> possibleDestinations=new ArrayList<Point>();
		
		Point maybePossible1=new Point(from.getX()+1,from.getY());
		Point maybePossible2=new Point(from.getX()-1,from.getY());
		Point maybePossible3=new Point(from.getX(),from.getY()+1);
		Point maybePossible4=new Point(from.getX(),from.getY()-1);
		if(validPoint(maybePossible1)){
			possibleDestinations.add(maybePossible1);
		}
		if(validPoint(maybePossible2)){
			possibleDestinations.add(maybePossible2);
		}
		if(validPoint(maybePossible3)){
			possibleDestinations.add(maybePossible3);
		}
		if(validPoint(maybePossible4)){
			possibleDestinations.add(maybePossible4);
		}
		if(possibleDestinations.contains(to)){
			probability=1.0/possibleDestinations.size();
			System.out.println(probability);
		}
		
		return probability;
	}
	public static boolean validPoint(Point point){
		return (0<=point.getX() &&point.getX()<WorldView.NCOLUMNS) && (0<=point.getY() && point.getY()<WorldView.NROWS);
	}
}
