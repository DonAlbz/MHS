package test;

import java.util.BitSet;
import java.util.Vector;
import exportData.Export;
import importDati.Import;


public class Study {

	private String fileName;
	private Vector<TestSolution> mhsSolutions = new Vector<TestSolution>();
	private Vector<TestSolution> staccatoSolutions = new Vector<TestSolution>();
	private String report;
	private int mhsSolutionOutNumber = 0;
	private int staccatoSolutionOutNumber = 0;

	public Study() {
		this.fileName = Import.getFileName(Export.FORMAT_EXP_FILE);
		study();
	}

	public void study(){
		mhsSolutions = getMhsSolutions();
		staccatoSolutions = getStaccatoSolution();
		report = compare();
		Export.writeStudy(this);
		}

	private String compare() {
		StringBuffer s = new StringBuffer();
		boolean response=false;
		for (int i = 0 ; i< mhsSolutions.size(); i++) {
			response=false;
			for (int j=0; j< staccatoSolutions.size() && !response; j++) {
				response=mhsSolutions.get(i).compare(staccatoSolutions.get(j));
			}
			if(!response){
				mhsSolutionOutNumber++;
				s.append("this mhsSolution is not included in staccato solutions:");
				s.append(System.lineSeparator());
				s.append(mhsSolutions.get(i).toString());				
				s.append(System.lineSeparator());
				s.append(System.lineSeparator());
			}
		}
		
		
		for (int i = 0 ; i< staccatoSolutions.size(); i++) {
			response=false;
			for (int j=0; j< mhsSolutions.size() && !response; j++) {
				response=mhsSolutions.get(j).compare(staccatoSolutions.get(i));
			}
			if(!response){
				staccatoSolutionOutNumber++;
				s.append("this staccatoSolution is not included in mhs solutions:");
				s.append(System.lineSeparator());
				s.append(staccatoSolutions.get(i).toString());				
				s.append(System.lineSeparator());	
				s.append(System.lineSeparator());
			}
			
			
		}
		
		s.append(System.lineSeparator());		
		System.out.println();
		s.append("Mhs solutions outnumber:" + mhsSolutionOutNumber);
		System.out.println("Mhs solutions outnumber:" + mhsSolutionOutNumber);
		s.append(System.lineSeparator());
		System.out.println();
		s.append(System.lineSeparator());
		s.append("Staccato solutions outnumber:" + staccatoSolutionOutNumber);
		System.out.println("Staccato solutions outnumber:" + staccatoSolutionOutNumber);
		System.out.println();
		s.append(System.lineSeparator());
		s.append(System.lineSeparator());
		//s.append("Comparation result:\t" + response);
		return s.toString();
	}

	private Vector<TestSolution> getStaccatoSolution() {
		return Import.loadStaccatoFile(fileName);
	}

	private String getStringSolutions(Vector<TestSolution> mhsSolutions2) {
		StringBuffer s = new StringBuffer();
		for (TestSolution testSolution : mhsSolutions2) {
			s.append(testSolution.toString());
			s.append(System.lineSeparator());
		}
		return s.toString();
	}

	private Vector<TestSolution> getMhsSolutions() {
		Vector<TestSolution> toReturn = new Vector<>();
		BitSet[] arrayBitSet;
		Import.loadMHSFile(fileName);
		arrayBitSet = Import.getRowsArray();
		for (int i= 0 ; i< arrayBitSet.length; i++){
			TestSolution tempSolution = new TestSolution();
			for(int j = arrayBitSet[i].nextSetBit(0); j >= 0 ; j= arrayBitSet[i].nextSetBit(j+1)){
				tempSolution.addSolution(j+1);
			}
			toReturn.add(tempSolution);	
		}

		return toReturn;
	}

	public String getReport(){
		return report;
	}

	public String getFileName(){
		return fileName;
	}
}
