package main;

import java.util.BitSet;
import java.util.Vector;
import exportData.Export;
import importDati.Import;
import utility.Converter;
import utility.Extraction;
import utility.InputDati;

// Proviamo ad applicare la legge di Pareto

public class Solver{
	private final static String pressKey = "Calculating solutions...\nPress ENTER to stop.";
	private int maxSolutionStep;
	private String fileName;
	private BitSet[] rows;
	private BitSet[] columns;
	private Vector<BitSet> allSolution;
	private int cardinality;
	private int totalColumns;
	private Export export;
	private Semaphore semaphore;
	private Thread threadKeyboard;
	private KeyController keyController;
	private long numberOfSolutions=0;
	private long initialTime;
	private long finalTime;
	private Recap recap;

	public Solver(){
		this.fileName = Import.getFileName();
		initialTime = System.currentTimeMillis();
		Import.loadMatrix(fileName);
		rows = Import.getRowsArray();
		columns = Import.getColumnsArray();
		//System.out.println(columnToString());
		allSolution = new Vector<BitSet>();
		cardinality = Import.getRows();
		totalColumns = Import.getColumns();
		export = new Export(fileName , totalColumns);
		maxSolutionStep = maxSolutionStep();
		semaphore  = new Semaphore();

	}


	private int maxSolutionStep(){
		//System.out.println(( 10000 / totalColumns));
		return (int)((double)10000000/ (double)(totalColumns *1.35));
	}

	public void solve(){
		keyController = new KeyController(semaphore);
		threadKeyboard = new Thread(keyController);
		threadKeyboard.start();
		solve(new Solution());


		export.writeSolution(allSolution);
		finalTime=System.currentTimeMillis() - initialTime;

		recap = new Recap(cardinality, totalColumns, numberOfSolutions, finalTime, semaphore.isStop());
		export.writeRecap(recap);
		System.out.println(recap.getReport());
		if(!semaphore.isStop()){
			semaphore.setStop(true);
			keyController.stop();
		}



		//export.closeWriter();
	}

	private void solve(Solution startSolution) {
		int latestRow;
		int latestColumn;
		if(startSolution.isEmpty()){
			latestRow=-1;
			latestColumn=0;
		}
		else{
			latestRow=startSolution.getLastRow();
			latestColumn = startSolution.getLastColumn();
		}
		int index= latestRow + 1;
		for (int column = rows[index].nextSetBit(0); column >= 0 && !semaphore.isStop(); column = rows[index].nextSetBit(column+1)) {
			//if(!startSolution.hasColumn(column)){


			Chunk found = findNextChunk(startSolution, column);
			if(Main.COMMENTED && !startSolution.isEmpty())
				System.out.println("startSolution: " + startSolution.getStringSolution());
			if (!check2(startSolution, found))
			{
				Solution postSolution = (Solution) startSolution.clone();
				postSolution.addChunk(found);
				if(Main.COMMENTED){
					System.out.println("Col : " + found.getColumn() + " added to Chuncks.");
					System.out.println();
					System.out.println();

				}/*if(!postSolution.isEmpty())
						System.out.println(postSolution.getStringSolution());*/
				if (found.getRow()== cardinality-1){
					//postSolution.addChunk(found);
					//System.out.println(postSolution.getStringSolution());
					if (Main.COMMENTED){
						System.out.println("added to final solutions:\t" + postSolution.getStringSolution());
						System.out.println();
						System.out.println("***************************************");
						System.out.println();
					}
					allSolution.add(postSolution.getBitSetSolution());
					numberOfSolutions++;
					if(allSolution.size()>maxSolutionStep){
						export.writeSolution(allSolution);
						allSolution.clear();				
					}
				}
				else{
					//postSolution = startSolution.clone();
					//postSolution.addChunk(found);
					/*	System.out.println(startSolution.getStringSolution());
						System.out.println(postSolution.getStringSolution());*/
					solve(postSolution);
				}				
			}
		}
	}


	private boolean statisticCheck(){
		boolean response;
		int temp = Extraction.estraiIntero(0, 1);
		response = (temp == 0);
		return response;

	}

	/*private boolean check(Solution solution, Chunk chunk){
		boolean response;
		int column = chunk.getColumn();
		if(solution.getSolution().size()>0){
			BitSet sample1 = (BitSet) columns[column].get(0, solution.getLastRow()+1);
			response=sample1.isEmpty();
			if(Main.COMMENTED){
				System.out.println("col: " + column);
				System.out.println("start: 0" + "   end: " + solution.getLastRow());
				System.out.println(bitSetToBinaryString(columns[column], cardinality +1));
				System.out.println(sample1);
				System.out.println("response: " + response);
				System.out.println();
				System.out.println("***************************************");
				System.out.println();

			}
		}
		else{
			response = true;
		}

		return response;
	}*/



	/**Returns true if column intersecate one of the element of starSolution
	 * Conditions: vector size > 0 and intersections > 1
	 * @param startSolution
	 * @param column
	 * @return
	 */
/*private boolean check(Solution startSolution, Chunk chunk) {
		boolean response = false;
		int column = chunk.getColumn();
		Vector<Chunk> solutionVector = startSolution.getSolution();
		int startRow = 0;
		int lastRow = 0;
		BitSet sample = new BitSet();
		for(int i = 0; !response &&	i < solutionVector.size() ; i++){
			if(Main.COMMENTED)
				System.out.println("column checked:\t" + column);
			lastRow = solutionVector.get(i).getRow();

			if(i>0){
				if(Main.COMMENTED){
					System.out.println("sample:\t\t" + Converter.bitSetToString(sample, cardinality));
					System.out.println("Row to Or:\t" + Converter.bitSetToString(columns[solutionVector.get(i-1).getColumn()], cardinality));
				}
				sample.or((BitSet) columns[solutionVector.get(i-1).getColumn()].clone());
				if(Main.COMMENTED)
					System.out.println("OR:\t\t" + Converter.bitSetToString(sample, cardinality));
			}
			if(Main.COMMENTED){
				System.out.println("solutionVector size: " + solutionVector.size());
				//System.out.println("startSolution: " + startSolution.getStringSolution());
				System.out.println("Chuncks: " + solutionVector.toString() + "     iteration: " + i);
			}
			response = checkColumnsFromZeroToRow((BitSet) columns[column].clone(), sample, lastRow);

			startRow=lastRow;			
		}
		if(!response)
			response = check2(startSolution, chunk);
		if(Main.COMMENTED){
			System.out.println("response: " + response);
			System.out.println();
		}
		return response;
	}

*/


	private boolean check2(Solution startSolution, Chunk chunk) {
		boolean response = false;
		if(startSolution.getSolution().size()>0){
			int column = chunk.getColumn();
			int lastRow = chunk.getRow();
			Vector<Chunk> solutionVector = startSolution.getSolution();
			BitSet columnToCheck = new BitSet(lastRow);			
			BitSet[] arrayBitSet = new BitSet[solutionVector.size()];
			BitSet[] tempBitSet = new BitSet[arrayBitSet.length];
			if(Main.COMMENTED)
				System.out.println("column checked:\t" + column);
			for(int i = 0 ; i <arrayBitSet.length ; i++){
				arrayBitSet [ i ] = ((BitSet) columns[solutionVector.get(i).getColumn()].clone()).get(0, lastRow + 1);
			}
			for(int i = 0 ; i< arrayBitSet.length && !response; i++ ){
				tempBitSet = arrayBitSet.clone();
				columnToCheck = (BitSet) columns[column].clone();
				columnToCheck = columnToCheck.get(0, lastRow + 1);
				if(Main.COMMENTED){
					System.out.println();
					System.out.println("Excluding column " + solutionVector.get(i).getColumn());
				}
				for(int j = 0 ; j <solutionVector.size() && !response; j++){

					if(j!=i){			


						if(Main.COMMENTED){
							System.out.println("Checking OR with column " + solutionVector.get(j).getColumn());
							System.out.println("columnToCheck:\t" + Converter.bitSetToString(columnToCheck, cardinality));
							System.out.println("tempColumn:\t" + Converter.bitSetToString(tempBitSet[j], cardinality));

						}
						columnToCheck.or(tempBitSet[j]);
						if(Main.COMMENTED){
							System.out.println("nexclearbit:\t" + columnToCheck.nextClearBit(0));
							System.out.println("lastRow:\t" + lastRow);
						}
						response= (columnToCheck.nextClearBit(0)>lastRow);
					}
				}
			}
		}
		if(Main.COMMENTED){
			System.out.println("response: " + response);
			System.out.println();
		}
		return response;

	}


	/**Returns true if column intersecate one of the element of starSolution
	 * Conditions: vector size > 0 and intersections > 1
	 * @param startSolution
	 * @param column
	 * @return
	 */
	/*private boolean check(Solution startSolution, int column) {
		boolean response = false;
		Vector<Chunk> solutionVector = startSolution.getSolution();
		int startRow = 0;
		int lastRow;
		BitSet sample = new BitSet();
		for(int i = 0; !response &&	i < solutionVector.size() ; i++){
			if(Main.COMMENTED)
				System.out.println("column checked:\t" + column);
			lastRow = solutionVector.get(i).getRow();

			if(i>0){
				if(Main.COMMENTED){
					System.out.println("sample:\t\t" + Converter.bitSetToString(sample, cardinality));
					System.out.println("Row to Or:\t" + Converter.bitSetToString(columns[solutionVector.get(i-1).getColumn()], cardinality));
				}
				sample.or((BitSet) columns[solutionVector.get(i-1).getColumn()].clone());
				if(Main.COMMENTED)
					System.out.println("OR:\t\t" + Converter.bitSetToString(sample, cardinality));
			}
			if(Main.COMMENTED){
				System.out.println("solutionVector size: " + solutionVector.size());
				//System.out.println("startSolution: " + startSolution.getStringSolution());
				System.out.println("Chuncks: " + solutionVector.toString() + "     iteration: " + i);
			}
			response = checkColumnsFromZeroToRow((BitSet) columns[column].clone(), sample, lastRow);
			if(Main.COMMENTED){
				System.out.println("response: " + response);
				System.out.println();
			}
			startRow=lastRow;			
		}
		return response;
	}*/

	private boolean checkColumnsFromZeroToRow(BitSet epsilon, BitSet sample, int lastRow) {
		epsilon = epsilon.get(0, lastRow+1);
		sample = sample.get(0, lastRow+1);
		if(Main.COMMENTED){
			System.out.println("last row:\t" + lastRow);
			System.out.println("epsilon: " + Converter.bitSetToString(epsilon, cardinality));
			System.out.println("sample:  " + Converter.bitSetToString(sample, cardinality));
			/*System.out.println(epsilon);
			System.out.println(sample);*/
		}
		epsilon.or(sample);
		if(Main.COMMENTED){
			System.out.println("epsilon.nextClearBit(0):\t" + epsilon.nextClearBit(0));
			System.out.println();
		}

		return (epsilon.nextClearBit(0)>lastRow);
	}


	/**Return true if bits between starRow and lastRow are the same for the two column passed as arguments
	 * @param column1
	 * @param column2
	 * @param startRow
	 * @param lastRow
	 */
	private boolean checkColumnsFromRowToRow(int column1, int column2,
			int startRow, int lastRow) {
		BitSet sample1 = (BitSet) columns[column1].get(startRow, lastRow + 1);
		BitSet sample2 = (BitSet) columns[column2].get(startRow, lastRow + 1);
		if(Main.COMMENTED){
			System.out.println("col1: " + column1 + "   col2: " + column2);
			System.out.println("start: " + startRow + "   end: " + lastRow);
			System.out.println(Converter.bitSetToString(columns[column1], cardinality));
			System.out.println(Converter.bitSetToString(columns[column2], cardinality));
			System.out.println(sample1);
			System.out.println(sample2);
		}
		sample1.and(sample2);
		if(Main.COMMENTED){
			System.out.println(sample1);
			System.out.println();
		}

		return sample1.equals(sample2);
	}

	private boolean checkIntersect(int column1, int column2,
			int startRow, int lastRow) {
		BitSet sample1 = (BitSet) columns[column1].get(startRow, lastRow + 1);
		BitSet sample2 = (BitSet) columns[column2].get(startRow, lastRow + 1);
		if(Main.COMMENTED){
			System.out.println("col1: " + column1 + "   col2: " + column2);
			System.out.println("start: " + startRow + "   end: " + lastRow);
			System.out.println(Converter.bitSetToString(columns[column1], cardinality));
			System.out.println(Converter.bitSetToString(columns[column2], cardinality));
			System.out.println(sample1);
			System.out.println(sample2);
		}
		sample1.and(sample2);
		if(Main.COMMENTED){
			System.out.println(sample1);
			System.out.println("restrizione del BitSet vuota: " + sample1.isEmpty());
			System.out.println();
		}

		return sample1.isEmpty();
	}


	private Chunk findNextChunk(Solution startSolution, int column) {		

		BitSet path = (BitSet) columns[column].clone();
		int lastRowReached = startSolution.getLastRow();

		for (Chunk chunk : startSolution.getSolution()) {
			BitSet temp = new BitSet();
			temp =	(BitSet) columns[chunk.getColumn()].clone();
			path.or(temp);
		}
		lastRowReached= path.nextClearBit(lastRowReached);

		return new Chunk(lastRowReached-1, column);
	}


	public String columnToString(){
		StringBuffer s = new StringBuffer();
		for (BitSet bitSet : columns) {
			s.append(bitSet.toString());
			s.append("\n");
		}
		return s.toString();
	}
	/*
	public void printDecimalSolution(boolean verbose){
		if(verbose){
			for (Solution solution : allSolution) {
				System.out.println(solution.getVerboseStringSolution());
			}
		}
		else{
			for (Solution solution : allSolution) {
				System.out.println(solution.getStringSolution());
			}
		}

	}
	 */

}