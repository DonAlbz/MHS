package exportData;

import importDati.Import;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Vector;

import utility.Converter;
import main.Recap;
import test.Study;

public class Export {
	private File mhsFile;
	private int rowLength;
	private BufferedWriter writer = null;
	public final static String FORMAT_EXP_FILE =".mhs";


	public Export(String matrixFileName, int rowLength) {
		mhsFile = new File(Import.DIRECTORY + matrixFileName + FORMAT_EXP_FILE);
		if(mhsFile.exists()){
			mhsFile.delete();
			try {
				mhsFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.rowLength = rowLength;
	}


	public void writeSolution(Vector<BitSet> allSolution){
		StringBuffer s = new StringBuffer();
		for (BitSet solution : allSolution){
			s.append(Converter.bitSetToString(solution, rowLength));	
			s.append(System.lineSeparator());
		}
		try {
			writer = new BufferedWriter(new FileWriter(mhsFile, true));
			writer.write(s.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeWriter(){
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void writeRecap(Recap recap) {
		try {
			writer = new BufferedWriter(new FileWriter(mhsFile, true));
			writer.write(recap.getReport());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void writeStudy(Study study) {
		File studyFile = new File(Import.DIRECTORY + study.getFileName() + ".study");
		if(studyFile.exists()){
			studyFile.delete();
			try {
				studyFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter writerStudy = null;
		try {
			writerStudy = new BufferedWriter(new FileWriter(studyFile));
			writerStudy.write(study.getReport());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally{
			try {
				writerStudy.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}