package Navigation;

public class State {
	private Point location;
	private int direction;
	public State(Point location, int direction){
		this.location=location;
		this.direction=direction;
	}
	public Point getLocation(){
		return location;
	}
	public int getDirection(){
		return direction;
	}
	public int getX(){
		return location.getX();
	}
	public int getY(){
		return location.getY();
	}
	public int hashCode(){
		Integer X = location.getX();
		Integer Y = location.getY();
		Integer D = direction;
		return Y.hashCode() + X.hashCode()+D.hashCode();
	}
	public boolean equals(Object obj){
		if (obj==null){
			return false;
		}
		State that= (State)obj;
		//return Integer.compare(x,other.x)==0 && Integer.compare(y,other.y)==0;
		
		if(this.location.equals(that.location) && this.direction==that.direction){
			return true;
		}
		return false;
	}
}
