package Sensor;

import java.util.ArrayList;

import Graphics.WorldView;
import Navigation.Point;

public class SensorUtils{
	public static Point sensorReading(Point current){
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
		ArrayList<Point> currentNeighbours=getDegreeNeighbours(current,1);
		for(Point p:currentNeighbours){
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
		}
		//report a distant neighbour location with possibility 0.025 each (1/40)*(max 16)
		currentNeighbours=getDegreeNeighbours(current,2);
		for(Point p:currentNeighbours){
			possiblePoints[currentArrayIndex]=p;
			currentArrayIndex++;
		}
		System.out.println("remaining nulls: "+(possiblePoints.length-currentArrayIndex));
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
	
	private static ArrayList<Point> getDegreeNeighbours(Point point,int degree){
		ArrayList<Point> neighbours=new ArrayList<Point>();
		int leftEdgeX=point.getX()-degree;
		int rightEdgeX=point.getX()+degree;
		int upperEdgeY=point.getY()-degree;
		int lowerEdgeY=point.getY()+degree;
		for(int x=leftEdgeX;x<=rightEdgeX;x++){
			for(int y=upperEdgeY;y<=lowerEdgeY;y++){
				Point current=new Point(x,y,Integer.MIN_VALUE);
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
		return (0<=point.getX() && point.getX()<WorldView.NCOLUMNS) && (0<=point.getY() && point.getY()<WorldView.NROWS);
	}
}
