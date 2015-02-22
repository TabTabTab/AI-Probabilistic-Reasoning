
package Graphics;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;

import Navigation.Board;


public class WorldView extends Applet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH=800; 
	public static final int HEIGHT=800; 
	public static final int NCOLUMNS=8;
	public static final int NROWS=8;
	private int[][] board;
	private String[][] boardIDs;
	public static final int EMPTY=0,ROBOT=1,SENSOR=-1,SENSORANDROBOT=2;
	
	public WorldView(){
		board= new int[NCOLUMNS][NROWS];
		boardIDs= new String[NCOLUMNS][NROWS];
		char[] letters="abcdefghijklmnopqrstuvwxyz".toCharArray();
		char[] numbers="123456789ABCDEFGHIJKLMNOPQ".toCharArray();
		for(int i=0;i<NCOLUMNS; i++){
			for(int j=0;j<NROWS;j++){
				board[i][j]=EMPTY;
				boardIDs[i][j]=""+letters[i]+numbers[j];
			}
		}
	}
	public void init(){
		this.setSize(WIDTH+1, HEIGHT+1);
	}
	public void paint(Graphics g){
		super.paint(g);
		drawMarkers(g);
		drawGrid(g);
	}
	public void setBoard(int[][] board){
		this.board=board;
		this.repaint();
	}
	public void setBoard(Board board){
		this.board=board.getBoard();
		this.repaint();
	}
	public void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		int xDistance=WIDTH/8;
		int yDistance=HEIGHT/8;
		
		for(int i=0;i<=board.length; i++){
			g.drawLine(0, i*yDistance, HEIGHT, i*yDistance);
			g.drawLine(i*xDistance, 0, i*xDistance,WIDTH);
		}
		for(int i=0;i<board.length; i++){
			for(int j=0;j<board[i].length;j++){
				int xCord=i*xDistance;
				int yCord=j*yDistance;
				//+1 and +10 used as ugly values
				g.drawString(boardIDs[i][j],xCord+1,yCord+11);
			}
		}
	}
	public void drawMarkers(Graphics g){
		int xDistance=WIDTH/NCOLUMNS;
		int yDistance=HEIGHT/NROWS;
		for(int i=0;i<NCOLUMNS; i++){
			for(int j=0;j<NROWS;j++){
				int xCord=i*xDistance;
				int yCord=j*yDistance;
				drawMarker(g,board[i][j],xCord,yCord);
			}
		}
		
		for(int i=0;i<board.length; i++){
			g.drawLine(0, (i+1)*yDistance, HEIGHT, (i+1)*yDistance);
			g.drawLine((i+1)*xDistance, 0, (i+1)*xDistance,WIDTH);
		}
	}

	
	private void drawMarker(Graphics g, int color, int xCord,int yCord){
		int xDia=WIDTH/NCOLUMNS -4;
		int yDia=HEIGHT/NROWS -4;
		xCord+=2;
		yCord+=2;
		switch(color){
		case(ROBOT):
			g.setColor(Color.BLACK);
			g.fillOval(xCord, yCord, xDia, yDia);
			g.setColor(Color.CYAN);
			g.fillOval(xCord+xDia/3, yCord+yDia/3, xDia/4, yDia/4);
			g.fillOval(xCord+2*xDia/3, yCord+2*yDia/3, xDia/4, yDia/4);
			g.setColor(Color.RED);
			g.fillOval(xCord+xDia/3, yCord+2*yDia/3, xDia/10, yDia/10);
			break;
		case(SENSOR):
			g.setColor(Color.RED);
			g.fillOval(xCord+xDia/4, yCord+yDia/4, xDia/2, yDia/2);
			g.setColor(Color.BLACK);
			g.drawOval(xCord+xDia/4, yCord+yDia/4, xDia/2, yDia/2);
			break;
		case (SENSORANDROBOT):
			g.setColor(Color.BLACK);
			g.fillOval(xCord, yCord, xDia, yDia);
			g.setColor(Color.CYAN);
			g.fillOval(xCord+xDia/3, yCord+yDia/3, xDia/4, yDia/4);
			g.fillOval(xCord+2*xDia/3, yCord+2*yDia/3, xDia/4, yDia/4);
			g.setColor(Color.RED);
			g.fillOval(xCord+xDia/3, yCord+2*yDia/3, xDia/10, yDia/10);
			
			//SENSOR POINT
			g.setColor(Color.RED);
			g.fillOval(xCord+xDia/4, yCord+yDia/4, xDia/2, yDia/2);
			g.setColor(Color.BLACK);
			g.drawOval(xCord+xDia/4, yCord+yDia/4, xDia/2, yDia/2);
			break;
		default:
			break;
		}
	}
}
