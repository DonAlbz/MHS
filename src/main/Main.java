package main;


import test.Generator;
import test.Study;
import utility.InputDati;
import utility.MyMenu;

public class Main {
	public static boolean COMMENTED = false;

	private static final String WELCOLME = "Welcome to MHS Solver";
	private static final String BYE = "Task succesfully terminated.";
	private static final String SELECT = "Start menu'";
	private static final String[] VOICES = {"Solve a problem;", "Solve a problem with verbose description;",
			"Confront a .mhs file with a .staccato file;", "Generate a new .matrix file."};

	private static final String STUDY = "s";
	private static final String VERBOSE = "v";
	private static final String ERROR = "Command Unknown:\t";

	private static final String ROW_N = "Insert rows number:\t";

	private static final String COLUMN_N = "Insert columns number:\t";
	private static Solver solver;

	public static void main(String[] args) {
		welcome();
		
		MyMenu menu = new MyMenu(SELECT, VOICES);
		switch(menu.scegli()){
		case 1:
			solver = new Solver();
			solver.solve();
			break;
		case 2:
			COMMENTED = true;
			solver = new Solver();
			solver.solve();
			break;
		case 3: 
			new Study();
			break;
		case 4:
			Generator.createNewProblem(InputDati.leggiIntero(ROW_N),InputDati.leggiIntero(COLUMN_N));
			break;
		}
		
		


		//	Generator.createNewProblem(10, 11);


		/*
		 */ 
		//	System.out.println(solver.columnToString());
		//		solver.printDecimalSolution(false);
		/*BitSet prova = new BitSet();
	prova.set(0);
	prova.set(1);
	prova.set(3, 6);
	prova.set(10);
	System.out.println(prova);

	prova= prova.get(0, 5);
	System.out.println(prova);
	BitSet prova2 = new BitSet();
	prova2.set(0,2);
	prova2.set(3,5);
	System.out.println(prova2);
	System.out.println(prova.equals(prova2));
		 */
		bye();
	}
	private static void bye(){
		System.out.println(BYE);
	}


	private static void welcome() {
		System.out.println(WELCOLME);		
	}

}