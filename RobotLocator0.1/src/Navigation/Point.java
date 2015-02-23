package Navigation;

public class Point {
	private int x;
	private int y;
	private int direction;
	
	// 0, 1, 2,3
	
	
	public Point(int x,int y, int direction){
		this.x=x;
		this.y=y;
		this.direction=direction;
	}


	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getDir(){
		return direction;
	}
	
	
	public int hashCode(){
		Integer Y = y;
		Integer X = x;
		return Y.hashCode() + X.hashCode();
	}
	
	
	//behöver vi kolla direction här?
	public boolean equals(Object obj){
		if (obj==null){
			return false;
		}
		Point other= (Point)obj;
		//return Integer.compare(x,other.x)==0 && Integer.compare(y,other.y)==0;
		
		if(this.x==other.x && this.y==other.y){
			return true;
		}
		return false;
	}
	public Point deepCopy(){
		return new Point(x,y,direction);
	}

}
