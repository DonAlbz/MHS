package main;

public class Chunk {

	private int row;
	private int column;

	public Chunk(int row, int column){
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	public String toString(){
		String string="("+ row + ", " + column + ")";
		return string;
	}
	
}