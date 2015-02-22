package Navigation;

public class Point {
	private int x;
	private int y;
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int hashCode(){
		return Integer.hashCode(x)+Integer.hashCode(y);
	}
	public boolean equals(Object obj){
		if (obj==null){
			return false;
		}
		Point other= (Point)obj;
		return Integer.compare(x,other.x)==0 && Integer.compare(y,other.y)==0;
	}
	public Point deepCopy(){
		return new Point(x,y);
	}
	
}
