import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.*; 
import java.util.*; 

public class Puzzle implements Comparable<Puzzle>{
	
	char [][] grid;
	Puzzle parent=null;
	Set<Puzzle> children=new HashSet<Puzzle>(); 
	ArrayList<Puzzle> ancestors=new ArrayList<Puzzle>();
	ArrayList<Character> squareList=new ArrayList<Character>(Arrays.asList('1','2','3','4','5','6','7','8','E'));
	String currMove;
	int nodeDistance;
	
	public Puzzle(char[][] numGrid) { //Constructor for first node i.e. no parents
		grid=numGrid;
		currMove="none";

		
	}
	public Puzzle(char[][] numGrid, Puzzle parent, ArrayList<Puzzle> inheritedAncestors, String lastMove) { //Constructor for taking the previous list of ancestors
		grid=numGrid;
		this.parent=parent;
		nodeDistance=inheritedAncestors.size();
		
		for(Puzzle a:inheritedAncestors) {
			
			ancestors.add(a);
		}
		ancestors.add(this.parent);

		currMove=lastMove;
	}
	public String toString() { //toString override
		
		String ret="";
		
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				ret=ret+" "+grid[x][y];
			}
			ret+="\n";
		}
		return ret;
		
	}
	
	public boolean isSolvable() { //Determines if a puzzle is solvable based on the number of inversions
		//If the number of inversions is odd at the input state, it is not solvable
		//If the number of inversions is even at the input state, it is solvable
		// Concept taken from: https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
		
		
		int numInversions=findInversions();
		boolean solvable;
		
		if(numInversions%2==0) {
			solvable=true;
		}else {
			solvable=false;
		}
		
		return solvable;
		
	}

	public int findInversions() { //
		int numInversions = 0;
		
		ArrayList<Integer> intList=new ArrayList<Integer>();
		
		
		for(int x=0;x<3;x++) {
			
			for(int y=0;y<3;y++) {		
														//Converts to INT so it can check for inversion
				if(grid[x][y]=='E') {
					
					intList.add(((int)0));						//replace the 'E' with a -1
				}else {
					intList.add(((int)grid[x][y])-48);
					
				}
				
			}
		}
		
		for(int i=0;i<intList.size()-1;i++) {
			
			for(int j=i+1;j<intList.size();j++) {
				
				if((intList.get(j)<intList.get(i))&&(intList.get(i)>0)&&(intList.get(j)>0)) {
					
					numInversions++;
				}
			}
		}
		
		return numInversions;
		
	}
	public Position findPos(char square) { //returns the position of a given character
		int pX=-1;
		int pY=-1;
		
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				if(grid[x][y]==square) {
					pX=x;
					pY=y;
				}
			}
		}
		return new Position(pX,pY);
		
	}
	public char squareAtPos(Position pos) {
		return grid[pos.x][pos.y];
	}
	public Position goalPositionOf(char c) {
	
		switch (c) {
		case '1':
			return new Position(0,0);
		case '2':
			return new Position(0,1);
		case '3':
			return new Position(0,2);
		case '4':
			return new Position(1,0);
		case '5':
			return new Position(1,1);
		case '6':
			return new Position(1,2);
		case '7':
			return new Position(2,0);
		case '8':
			return new Position(2,1);
		case 'E':
			return new Position(2,2);
		default:
			return null;
			
			
		}
	}
	public void makeChildren(PriorityQueue<Puzzle> openNodes, Set<Puzzle> closedNodes){
		Position ePos=findPos('E');								// Find the empty square
		
		
		if(ePos.x>0 && !currMove.equals("down")) {                                           //If the empty square can be moved up
			
			char[][] upChildGrid=new char[3][3];
			
			for(int x=0;x<3;x++) {
				for(int y=0;y<3;y++) {
					upChildGrid[x][y]=grid[x][y];
				}
			}
			char upTemp=upChildGrid[ePos.x][ePos.y];
			upChildGrid[ePos.x][ePos.y]=upChildGrid[ePos.x-1][ePos.y];
			upChildGrid[ePos.x-1][ePos.y]=upTemp;
			
			
			
			Puzzle upChild=new Puzzle(upChildGrid,this,ancestors,"up");
			
			if(!isAncestor(upChild)&&!closedNodes.contains(upChild)&&!openNodes.contains(upChild)) {
				children.add(upChild);
			}
		}
		if(ePos.x<2 && !currMove.equals("up")) {                                           //If the empty square can be moved down
			
			
			char[][] downChildGrid=new char[3][3];
			
			for(int x=0;x<3;x++) {
				for(int y=0;y<3;y++) {
					downChildGrid[x][y]=grid[x][y];
				}
			}
			char downTemp=downChildGrid[ePos.x][ePos.y];
			
			downChildGrid[ePos.x][ePos.y]=downChildGrid[ePos.x+1][ePos.y];
			downChildGrid[ePos.x+1][ePos.y]=downTemp;
			
			Puzzle downChild=new Puzzle(downChildGrid,this,ancestors,"down");
			
			if(!isAncestor(downChild)&&!closedNodes.contains(downChild)&&!openNodes.contains(downChild)) {
				children.add(downChild);
			}
		}
		if(ePos.y>0 && !currMove.equals("right")) {  									//If the empty square can be moved left
			
			
			
			char[][] leftChildGrid=new char[3][3];
			
			for(int x=0;x<3;x++) {
				for(int y=0;y<3;y++) {
					leftChildGrid[x][y]=grid[x][y];
				}
			}
			char leftTemp=leftChildGrid[ePos.x][ePos.y];
			
			leftChildGrid[ePos.x][ePos.y]=leftChildGrid[ePos.x][ePos.y-1];
			leftChildGrid[ePos.x][ePos.y-1]=leftTemp;
			
			Puzzle leftChild=new Puzzle(leftChildGrid,this,ancestors,"left");
			
			if(!isAncestor(leftChild)&&!closedNodes.contains(leftChild)&&!openNodes.contains(leftChild)) {
				children.add(leftChild);
			}
		}
		if(ePos.y<2 && !currMove.equals("left")) {                                           //If the empty square can be moved right
			
			
			char[][] rightChildGrid=new char[3][3];
			
			for(int x=0;x<3;x++) {
				for(int y=0;y<3;y++) {
					rightChildGrid[x][y]=grid[x][y];
				}
			}
			char Uptemp=rightChildGrid[ePos.x][ePos.y];
			
			rightChildGrid[ePos.x][ePos.y]=rightChildGrid[ePos.x][ePos.y+1];
			rightChildGrid[ePos.x][ePos.y+1]=Uptemp;
			
			Puzzle rightChild=new Puzzle(rightChildGrid,this,ancestors,"right");
			
			if(!isAncestor(rightChild)&&!closedNodes.contains(rightChild)&&!openNodes.contains(rightChild)) {
				children.add(rightChild);
			}
		}
		
		
	}
	public boolean isAncestor(Puzzle otherPuzzle) {
		//System.out.println(ancestors.size());
		boolean ancestorFound=false;
		
		for(Puzzle currAncestor:ancestors) {
			if(currAncestor.equals(otherPuzzle)) {
				ancestorFound=true;
			}
		}
		
		return ancestorFound;
	}
	public boolean equals(Puzzle otherPuzzle) {
		
		boolean isEqual=true;
		
		for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
				if(this.grid[x][y]!=otherPuzzle.grid[x][y]) {
					isEqual=false;
				}
			}
		}
		return isEqual;
	}
	public String childrenString() {
		String ret="";
		//System.out.println(children.size());
		for(Puzzle p:children) {
			ret+=p.toString()+"\n";
		}
		return ret;
	}
	
	public Set<Puzzle> getChildren(){
		//Collections.sort(children);
		return children;
	}

	
	public int getNumAncestors() {
		return nodeDistance;
	}
	public int getTotalManhattanDistance() {
		
		
		int distanceSum=0;
		
		for(char square:squareList) {
			
			Position currPos=findPos(square);					//Finds the position of each of the squares
			Position goalPos=goalPositionOf(square);			//Finds the goal position of each of the squares
			
			int currDistance=currPos.manhattanDisanceFrom(goalPos);  
			distanceSum+=currDistance;
			
		}
		
		return distanceSum;
		
	}
	public int getTotalNodeCost() {
		
		int distanceFromRoot=nodeDistance;
		int distanceFromGoal=getTotalManhattanDistance();
		
		return distanceFromRoot+distanceFromGoal;
	}
	public boolean isGoal() {								//Checks to see if puzzle is in "Goal"
		
		boolean goal=true;
		
		for(char square:squareList) {
			if(!findPos(square).equals(goalPositionOf(square))) {
				goal=false;
			}
		}
		
		return goal;
	}
	public ArrayList<String> moveList(){
		
		ArrayList<String> listOfMoves=new ArrayList<String>();
		int index=0;
		for(Puzzle ancestor: ancestors) {
			
			if(ancestor.currMove!="none") {
				String ancestorMove=""+index+".) Empty space moved "+ancestor.currMove;
				listOfMoves.add(ancestorMove);
			}
			index++;			
		}
		
		listOfMoves.add(""+index+".) Empty space moved "+currMove);
		return listOfMoves;
	}
	@Override
	public int compareTo(Puzzle o) {
		// TODO Auto-generated method stub
		//return 0;
		
		if(getTotalNodeCost()<o.getTotalNodeCost()) {
			return -1;
		}else if(o.getTotalNodeCost()<getTotalNodeCost()) {
			return 1;
		}
		
		return 0;
	}
	public boolean hasChildren() {
		
		return children.size()>0;
	}
	
	
}
