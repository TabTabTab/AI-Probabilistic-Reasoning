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
		double initialProbability= 1/(WorldView.NCOLUMNS*WorldView.NROWS);
		for(int x=0;x<WorldView.NCOLUMNS;x++){
			for(int y=0;y<WorldView.NROWS;y++){
				probabilityMatrix[x][y]=0;
			}
		}
	}
	
	public void addSensorReading(Point reading){
		if ( reading == null){
			//TODO: handle the null value with the help of heuristics, for now we just ignore
		}else{
			double sum=0;
			for(int x=0;x<WorldView.NCOLUMNS;x++){
				for(int y=0;y<WorldView.NROWS;y++){
					double p=probabilityToGetReadingFromBatPointA(new Point(x,y),reading);
					if(probabilityMatrix[x][y]==0){
						probabilityMatrix[x][y]=0;
					}else{
						probabilityMatrix[x][y]+=p;
					}
					sum+=probabilityMatrix[x][y];
				}
			}
			for(int x=0;x<WorldView.NCOLUMNS;x++){
				for(int y=0;y<WorldView.NROWS;y++){
					probabilityMatrix[x][y]/=sum;
				}
			}
		}
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
	
	private double probabilityToGetReadingFromBatPointA(Point from,Point to){
		ArrayList<Point> deg1Neighbours=getDegreeNeighbours(from,1);
		ArrayList<Point> deg2Neighbours=getDegreeNeighbours(from,2);
		//first check not needed
		if(from.getX()==to.getY()){
			return 0.1;
		}else if(deg1Neighbours.contains(to)){
			return 0.05;
		}else if(deg2Neighbours.contains(to)){
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
	public static boolean validPoint(Point point){
		return (0<=point.getX() &&point.getX()<WorldView.NCOLUMNS) && (0<=point.getY() && point.getY()<WorldView.NROWS);
	}
}
