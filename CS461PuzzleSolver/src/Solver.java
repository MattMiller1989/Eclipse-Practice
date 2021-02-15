import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Solver {
	
	static ArrayList<Puzzle> puzzleList=new ArrayList<Puzzle>();
	Puzzle goalPuzzle;
	
	public static void main(String[] args) {
		
		
		
		
		try {
			runSolver();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//printPuzzles();
		solvePuzzles();
		//testGoal();
	}
	public static void runSolver() throws FileNotFoundException {
		
		 //List of puzzles
		/* 
		 * https://www.tutorialspoint.com/How-to-read-a-2d-array-from-a-file-in-java
		 * ^used as a refresher for java.scanner
		 * */
		
		//File inFile=new File("solvable_test.txt");
		File inFile=new File("prog1_input.txt");
		Scanner inScanner=new Scanner(inFile);
		
		
		
		int numPuzzles=Integer.parseInt(inScanner.nextLine()); //Skips the first line
		
		for(int a=0;a<numPuzzles;a++) { 
			
			char [][] currGrid=new char[3][3];
			
			for(int x=0; x<currGrid.length;x++) {
				String[] currLine=inScanner.nextLine().trim().split(" ");
				
				for(int y=0;y<currLine.length;y++) {
					currGrid[x][y]=currLine[y].charAt(0);
				}
			}
			
			Puzzle currPuzzle=new Puzzle(currGrid);
			puzzleList.add(currPuzzle);
			
			inScanner.nextLine(); //Skips the line for the next puzzle
		}
		
		
		
	}
	public static void printPuzzles() {
		

		for(Puzzle p:puzzleList) {
			p.toString();
		}
		
	}
	public static void solvePuzzles() {
		
		int puzNum=1;
		for(Puzzle p:puzzleList) {
			
			System.out.println("\nStarting puzzle: "+puzNum);
			
			
			if(p.isSolvable()) {
			
				AStar currAStar=new AStar(p);
			}else {
				System.out.println("The puzzle is not solvable");
			}
			
			
			puzNum++;
		}
	}
	public static void testGoal() {
		for(Puzzle p:puzzleList) {
			System.out.println(p.isGoal());
		}
	}
	
}
