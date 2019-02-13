package main;

public class Recap {
	private int totalRows;
	private int totalColumns;
	private long totalSolutionFound;
	private long totalTime;
	private boolean interrupted;
	private final static String INTERRUPTED ="Search interrupted before ended.";
	private final static String N_ROW="number of rows: \t\t\t";
	private final static String N_COLUMNS="number of columns:\t\t\t";
	private final static String N_SOLUTIONS="number of solutions found:\t\t";
	private final static String COMPUTING_TIME="computing time:\t\t\t\t";
	private final static String MS="ms";
	private String report;
	
	
	
	public Recap(int totalRows, int totalColumns, long totalSolutionFound, long totalTime, boolean interruped) {
		super();
		this.totalRows = totalRows;
		this.totalColumns = totalColumns;
		this.totalSolutionFound = totalSolutionFound;
		this.totalTime = totalTime;
		this.interrupted = interruped;
		report = this.toString();
	}
	
	private String getCommentSeparator(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(getCornice());
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(System.lineSeparator());
		return stringBuffer.toString();
	}

	private String getCornice() {
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i< totalColumns; i++)
			stringBuffer.append("*");
		return stringBuffer.toString();
	}
	
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getCommentSeparator());
		if(interrupted){
			stringBuffer.append(INTERRUPTED);
			stringBuffer.append(System.lineSeparator());
			stringBuffer.append(System.lineSeparator());
		}
		stringBuffer.append(N_ROW);
		stringBuffer.append(totalRows);
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(N_COLUMNS);
		stringBuffer.append(totalColumns);
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(N_SOLUTIONS);
		stringBuffer.append(totalSolutionFound);
		stringBuffer.append(System.lineSeparator());
		stringBuffer.append(COMPUTING_TIME);
		stringBuffer.append(totalTime);
		stringBuffer.append(MS);
		stringBuffer.append(System.lineSeparator());		
		return stringBuffer.toString();
		
	}
	
	public String getReport(){
		return report;
	}
	
	

}
