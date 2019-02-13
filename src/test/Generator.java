package test;

import importDati.Import;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import utility.Extraction;

public class Generator {


	public static String createNewProblem(int rows, int columns){
		BufferedWriter writer = null;
		String timeLog = null;
		try {
			//create a temporary file
			timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			File logFile = new File(Import.DIRECTORY + timeLog + ".matrix");

			// This will output the full path where the file will be written to...
			System.out.println(logFile.getCanonicalPath());

			writer = new BufferedWriter(new FileWriter(logFile));
			generateMatrix(rows, columns, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
		return timeLog;
	}

	private static void generateMatrix(int rows, int columns, BufferedWriter writer) {
		StringBuffer s = new StringBuffer();
		for(int i =0 ; i< rows; i++){
			for(int j = 0; j< columns; j++){
				s.append(Extraction.estraiIntero(0, 1) + " ");				
			}	
			s.append("-");
			try {
				writer.write(s.toString());
				writer.newLine();
				s.setLength(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}

