package Navigation;
import Graphics.WorldView;
public class Board {
	private int[][] board;
	public Board(){
		board=new int[WorldView.NCOLUMNS][WorldView.NROWS];
		for(int i=0;i<board.length; i++){
			for(int j=0;j<board[i].length;j++){
				board[i][j]=WorldView.EMPTY;
			}
		}
	}
	
	public void clearBoard(){
		for(int i=0;i<board.length; i++){
			for(int j=0;j<board[i].length;j++){
				board[i][j]=WorldView.EMPTY;
			}
		}
	}
	public void setSensorReading(Point location){
		if (location!=null){
			if(board[location.getX()][location.getY()]==WorldView.ROBOT){
				board[location.getX()][location.getY()]=WorldView.SENSORANDROBOT;
			}else{
				board[location.getX()][location.getY()]=WorldView.SENSOR;
			}
		}
	}
	public void setRobotLocation(Point location){
		if (location!=null){
			if(board[location.getX()][location.getY()]==WorldView.SENSOR){
				board[location.getX()][location.getY()]=WorldView.SENSORANDROBOT;
			}else{
				board[location.getX()][location.getY()]=WorldView.ROBOT;
			}
		}
	}
	public int[][] getBoard(){
		return board;
	}
}
