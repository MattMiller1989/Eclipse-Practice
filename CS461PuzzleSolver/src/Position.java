
public class Position {
	int x;
	int y;
	
	public Position(int x,int y) {
		this.x=x;
		this.y=y;
	}
	public String toString() {
		return "("+x+","+y+")";
	}
	public boolean equals(Position otherPos) {
		return(this.x==otherPos.x)&&(this.y==otherPos.y);
	}
	public char goalSquare() //returns the char that is 'supposed' to be at this position
	{
		if(x==0&&y==0) return '1';
		if(x==0&&y==1) return '2';
		if(x==0&&y==2) return '3';
		if(x==1&&y==0) return '4';
		if(x==1&&y==1) return '5';
		if(x==1&&y==2) return '6';
		if(x==2&&y==0) return '7';
		if(x==2&&y==1) return '8';
		if(x==2&&y==2) return 'E';
		
		return ' ';
		
		
	}
	public int manhattanDisanceFrom(Position otherPos) {
		
		int manhattanDistance=Math.abs(this.x-otherPos.x)+Math.abs(this.y-otherPos.y);
		
		return manhattanDistance;
	}
}
