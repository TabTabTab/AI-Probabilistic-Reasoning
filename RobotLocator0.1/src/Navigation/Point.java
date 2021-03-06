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
		Integer Y = y;
		Integer X = x;
		return Y.hashCode() + X.hashCode();
	}
	public boolean equals(Object obj){
		if (obj==null){
			return false;
		}
		Point other= (Point)obj;
		if(this.x==other.x && this.y==other.y){
			return true;
		}
		return false;
	}

}
