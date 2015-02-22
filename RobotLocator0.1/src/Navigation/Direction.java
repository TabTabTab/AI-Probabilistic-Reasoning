package Navigation;

public class Direction {
	int xIncrementation;
	int yIncrememtation;
	public Direction(int xIncrementation,int yIncrememtation){
		this.xIncrementation=xIncrementation;
		this.yIncrememtation=yIncrememtation;
	}
	public int getXIncrementation(){
		return xIncrementation;
	}
	public int getYIncrementation(){
		return yIncrememtation;
	}
	public boolean equals(Object obj){
		if (obj==null){
			return false;
		}
		Direction other=(Direction)obj;
		return this.xIncrementation==other.xIncrementation &&
				this.yIncrememtation==other.yIncrememtation;
	}
}
