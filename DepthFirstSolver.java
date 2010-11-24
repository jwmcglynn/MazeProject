import java.util.LinkedList;

import maze.Maze;
import maze.Pair;


public class DepthFirstSolver implements ISolver {
	public DepthFirstSolver() {
	}
	
	public LinkedList<Pair> solveMaze(Maze maze) {
		LinkedList<Pair> answer = new LinkedList<Pair>();
		Pair[][] pairWeCameFrom;
		boolean[][] didVisit;
		LinkedList<Pair> linkedStack = new LinkedList<Pair>();
		
    	pairWeCameFrom = new Pair[maze.getWidth()][maze.getHeight()];
    	didVisit = new boolean[maze.getWidth()][maze.getHeight()];
    	
		linkedStack.addFirst(maze.getStartLocation());
		didVisit[maze.getStartLocation().x][maze.getStartLocation().y] = true;
    	
    	while(linkedStack.size() != 0) {
    		Pair current = linkedStack.removeFirst();
    		
    		if(current == maze.getEndLocation()) {
    			break;
    		}
    		
    		LinkedList<Pair> neighbors = maze.Observe(current);
    	  	
    		for(Pair p : neighbors) {
    			if(!didVisit[p.x][p.y]) {
    				linkedStack.addFirst(p);
    				pairWeCameFrom[p.x][p.y] = current;
    				didVisit[p.x][p.y] = true;
    			}
        	}
    	}
    	// now our two dimensional array should point us to the last nodes we visited, allowing us
    	// to retrace our steps. 
    	Pair check = maze.getEndLocation();
    	
    	while(check != null) {
    		// we will start from the end and add the previousNode to the front of our list
    		answer.addFirst(check);
    		check = pairWeCameFrom[check.x][check.y];
    	}
    	
    	return answer;
    }
}
