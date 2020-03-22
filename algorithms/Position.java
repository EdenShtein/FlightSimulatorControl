package algorithms;

public class Position { //Help class for out actual position in the matrix
	private int row;
	private int col;
	//CTORS
	public Position()
	{
		this.col=0;
		this.row=0;
	}
	
	public Position(int row,int col)
	{
		this.row=row;
		this.col=col;
	}
	
	//Getters and Setters
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
	//Position must override tostring ,equals and HashCode 
	@Override
	public String toString() {
		return row + "," + col;
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return equals((Position)obj);
	}
	
	public boolean equals(Position other) {
		return getRow() == other.getRow() && getCol() == other.getCol();
	}
	

}
