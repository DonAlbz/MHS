package importDati;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.Vector;

import exportData.Export;
import test.TestSolution;
import utility.InputDati;


public class Import {
	private static final String STACCATO_FORMAT = ".staccato";
	private static final String INSERT_NAME_FILE = "Insert name of .matrix file: ";
	private final static String COMMENTED_LINE = ";;;";
	public final static String DIRECTORY = "";
	private static final String FORMAT_IMPORT_FILE = ".matrix";
	private static final String FILE_NOT_FOUND = "File not found.\nRetry.\n\n";
	private static Vector<BitSet> vector = null;
	private static int rows;
	private static int columns;



	public static void loadMatrix(String file){

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(new File(DIRECTORY + file + FORMAT_IMPORT_FILE));
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			columns=-1;
			while ( columns == -1 && (strLine = br.readLine()) != null)   {
				if(!strLine.contains(COMMENTED_LINE) && !strLine.isEmpty()){				
					strLine=clearElement(strLine);
					if(vector==null){
						vector = new Vector<BitSet>();
					}
					else{
						vector.clear();
					}
					insertElement(strLine);
					columns= strLine.length();
				}
			}

			while ((strLine = br.readLine()) != null)   {
				if(!strLine.contains(COMMENTED_LINE) && !strLine.isEmpty()){				
					strLine=clearElement(strLine);
					if(vector==null){
						vector = new Vector<BitSet>();
					}
					insertElement(strLine);
				}
			}

			rows = vector.size();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
	}

	public static BitSet[] getRowsArray(){
		BitSet[] array = new BitSet[vector.size()];
		vector.toArray(array);
		return array;
	}


	public static BitSet[] getColumnsArray() {	
		BitSet[] bitSet = new BitSet[columns];

		for(int j=0; j<columns; j++){
			bitSet[j]=new BitSet(rows);
			for(int i = 0; i< rows; i++){
				if(vector.get(i).get(j)==true){
					bitSet[j].set(i);							
				}
			}
		}

		return bitSet;
	}



	private static void insertElement(String strLine) {
		int index=0;
		strLine=clearElement(strLine);
		BitSet temp = new BitSet(strLine.length());
		vector.add(temp);		
		index = strLine.indexOf("1", index);
		while(index!=-1){	
			temp.set(index);
			index++;
			index = strLine.indexOf("1", index);			
		}
	}



	private static String clearElement(String strLine) {
		strLine=strLine.replace("-", "");
		strLine=strLine.replace(" ", "");		
		return strLine;		
	}

	/**
	 * @return the rows
	 */
	public static int getRows() {
		return rows;
	}

	/**
	 * @return the columns
	 */
	public static int getColumns() {
		return columns;
	}

	public static String getFileName() {
		String name = 	InputDati.leggiStringaNonVuota(INSERT_NAME_FILE);
		File fileSearched = new File (name + FORMAT_IMPORT_FILE);
		while (!fileSearched.exists()){
			System.out.println(FILE_NOT_FOUND);
			name = 	InputDati.leggiStringaNonVuota(INSERT_NAME_FILE);
			fileSearched = new File (name + FORMAT_IMPORT_FILE);
		}
		return name;
	}

	public static String getFileName(String format) {
		String name = 	InputDati.leggiStringaNonVuota(INSERT_NAME_FILE);
		File fileSearched = new File (name + format);
		while (!fileSearched.exists()){
			System.out.println(FILE_NOT_FOUND);
			name = 	InputDati.leggiStringaNonVuota(INSERT_NAME_FILE);
			fileSearched = new File (name + format);
		}
		return name;
	}

	public static void loadMHSFile(String file){

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(new File(DIRECTORY + file + Export.FORMAT_EXP_FILE));
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			columns=-1;
			while ( columns == -1 && (strLine = br.readLine()) != null)   {
				if(!strLine.contains(COMMENTED_LINE) && !strLine.isEmpty()){				
					strLine=clearElement(strLine);
					if(vector==null){
						vector = new Vector<BitSet>();
					}
					else{
						vector.clear();
					}
					insertElement(strLine);
					columns= strLine.length();
				}
			}
			boolean go = true;
			while (go && (strLine = br.readLine()) != null)   {
				if(strLine.contains("*"))
					go = false;
				else{
					if(!strLine.contains(COMMENTED_LINE) && !strLine.isEmpty()){				
						strLine=clearElement(strLine);
						if(vector==null){
							vector = new Vector<BitSet>();
						}
						insertElement(strLine);
					}
				}
			}

			rows = vector.size();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
	}
	
	
	
	
	public static Vector<TestSolution> loadStaccatoFile(String file){
		Vector<TestSolution> testSolutionVector = new Vector<>();
		FileInputStream fis = null;

		try {

			fis = new FileInputStream(new File(DIRECTORY + file + STACCATO_FORMAT));
			DataInputStream in = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
				
					testSolutionVector.add(getStaccatoSolution(strLine));
					columns= strLine.length();
				
			}
			boolean go = true;
			while (go && (strLine = br.readLine()) != null)   {
				if(strLine.contains("*"))
					go = false;
				else{
					if(!strLine.contains(COMMENTED_LINE) && !strLine.isEmpty()){				
						strLine=clearElement(strLine);
						if(vector==null){
							vector = new Vector<BitSet>();
						}
						insertElement(strLine);
					}
				}
			}

			rows = vector.size();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
		return testSolutionVector;
	}
	
	private static TestSolution getStaccatoSolution(String stringa){
		TestSolution temp = new TestSolution();
		int start=0;
		String s = new String();
		for(int i = 0; i< stringa.length(); i++)
			if(stringa.charAt(i)  == ','){
				s=stringa.substring(start, i);
				s=s.replace(",", "");
				s=s.replace("h", "");
				s= s.trim();
				temp.addSolution(Integer.parseInt(s));
				start = i;
			}
		s=stringa.substring(start, stringa.length());
		s=s.replace(",", "");
		s=s.replace("h", "");
		s= s.trim();
		temp.addSolution(Integer.parseInt(s));
				
		return temp;
	}

}