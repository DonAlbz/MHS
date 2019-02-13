package test;

import java.util.Collections;
import java.util.Vector;

public class TestSolution {
	private Vector<Integer> solutions = new Vector<Integer>();
	
	public void addSolution(int i){
		solutions.add(i);
	}
	
	
	public Vector<Integer> getSolutions(){
		return solutions;
	}
	
	public String toString(){
		StringBuffer s = new StringBuffer();
		s.append("{ ");
		for (Integer integer : solutions) {
			s.append(integer);
			if(!integer.equals(solutions.lastElement()))
				s.append(", ");
		}
		s.append(" }");
		return s.toString();
	}
	
	public boolean compare(TestSolution toCompare){
		boolean response= false;
		Collections.sort(solutions);
		Collections.sort(toCompare.getSolutions());
		response=solutions.equals(toCompare.getSolutions());		
		return response;
	}

}
