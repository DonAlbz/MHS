package main;

import java.util.BitSet;
import java.util.Vector;

public class Solution {
	private Vector<Chunk> solution;

	/**
	 * @param solution
	 */
	public Solution(Vector<Chunk> solution) {
		this.solution = solution;
	}

	public Solution() {
		solution = new Vector<Chunk>();
	}
	
	public void addChunk(Chunk chunk){
		this.solution.addElement(chunk);
	}
	
	public int getLastRow(){
		if(solution.isEmpty())
			return 0;
		else
			return solution.lastElement().getRow();
	}
	
	public int getLastColumn(){
		if(solution.isEmpty())
			return 0;
		else
			return solution.lastElement().getColumn();
	}
	
	public boolean hasColumn(int i){
		boolean temp = false;
		
		for (Chunk chunk : solution) {
			if(chunk.getColumn()==i)
				temp=true;
		}		
		return temp;
	}
	
	
	public BitSet getBitSetSolution(){
		BitSet temp = new BitSet();
		
		for (Chunk chunk : solution) {
			temp.set(chunk.getColumn());
		}		
		return temp;
	}

	/**
	 * @return the solution
	 */
	public Vector<Chunk> getSolution() {
		return solution;
	}
	
	public boolean isEmpty(){
		return solution.isEmpty();
	}
	
	public String getVerboseStringSolution(){
		StringBuffer s = new StringBuffer();
		s.append("{");
		for (int i = 0; i< solution.size()-1; i++) {
			s.append(solution.get(i).toString());
			s.append(", ");
		}
		s.append(solution.lastElement().toString());
		s.append("}");
		return s.toString();
	}
	
	public String getStringSolution(){
		StringBuffer s = new StringBuffer();
		s.append("{");
		for (int i = 0; i< solution.size()-1; i++) {
			s.append(solution.get(i).getColumn());
			s.append(", ");
		}
		s.append(solution.lastElement().getColumn());
		s.append("}");
		return s.toString();
	}
	
	@SuppressWarnings("unchecked")
	public Solution clone(){
		Solution temp = new Solution ((Vector<Chunk>) solution.clone());
		return temp;
	}
	
}