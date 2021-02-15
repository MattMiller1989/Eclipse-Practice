import java.util.ArrayList;
import java.io.*; 
import java.util.*; 
public class AStar {
	
	
	Puzzle goal;
	
	public AStar(Puzzle root) {
		
		goal=doAStar(root);
		printPath();
	}

	public Puzzle doAStar(Puzzle start) {
		PriorityQueue<Puzzle> openNodes=new PriorityQueue<Puzzle>();
		Set<Puzzle> closedNodes=new HashSet<Puzzle>();
		
		
		openNodes.add(start);
		
		while(!openNodes.isEmpty()) {
			Puzzle currBestNode=openNodes.poll();
			
			if(currBestNode.isGoal()) {
				
				
					return currBestNode;
				
			}else {
				openNodes.remove(currBestNode); 
				closedNodes.add(currBestNode);
				currBestNode.makeChildren(openNodes,closedNodes);				
				
				for(Puzzle child: currBestNode.getChildren()) {
					
						openNodes.add(child);
					
				}
			}
	
		}
		
		return null;
	}
	
	public void printPath() {
		
		
		ArrayList<String> pathList=goal.moveList();
		
		for(String move:pathList) {
			System.out.println(move);
		}
		
		
	}
}
