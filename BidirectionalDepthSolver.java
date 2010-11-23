import java.util.LinkedList;
import maze.Maze;
import maze.Pair;

public class BidirectionalDepthSolver implements ISolver {
	private LinkedList<Pair> answer = new LinkedList<Pair>();
	private Pair[][] pairWeCameFrom;
	private int[][] didVisit;
	private LinkedList<Pair> linkedStackStart = new LinkedList<Pair>();
	private LinkedList<Pair> linkedStackEnd = new LinkedList<Pair>();
	
	public BidirectionalDepthSolver() {
	}
	
	public LinkedList<Pair> solveMaze(Maze maze) {
    	pairWeCameFrom = new Pair[maze.getWidth()][maze.getHeight()];
    	didVisit = new int[maze.getWidth()][maze.getHeight()];
    	
    	linkedStackStart.addFirst(maze.getStartLocation());
    	linkedStackEnd.addFirst(maze.getEndLocation());
    	
		didVisit[maze.getStartLocation().x][maze.getStartLocation().y] = 1;
		didVisit[maze.getEndLocation().x][maze.getEndLocation().y] = 2;
		
		LinkedList<Pair> currentLinkedStack = linkedStackStart;
		int currentTag = 1; // used to tag the didVisit with which frontier visited it.
		Pair answerFrontier = null;
		Pair answerOtherFrontier = null;
		
    	while(answerFrontier == null && linkedStackStart.size() != 0 && linkedStackEnd.size() != 0) {
    		if(currentLinkedStack == linkedStackStart) {
    			currentLinkedStack = linkedStackEnd;
    			currentTag = 2;
    		} else if (currentLinkedStack == linkedStackEnd) {
    			currentLinkedStack = linkedStackStart;
    			currentTag = 1;
    		}
    		
    		
    		
    		Pair current = currentLinkedStack.removeFirst();
    		LinkedList<Pair> neighbors = maze.Observe(current);
    	  	
    		for(Pair p : neighbors) {
    			if(didVisit[p.x][p.y] == 0) {
    				currentLinkedStack.addFirst(p);
    				pairWeCameFrom[p.x][p.y] = current;
    				didVisit[p.x][p.y] = currentTag;
    			} else if (didVisit[p.x][p.y] != currentTag) {
    				answerFrontier = current;
    				answerOtherFrontier = p;
    				break;
    			}
        	}
    	}
    	
    	// Retrace our steps from answerFrontier.
    	while (answerFrontier != null) {
    		Pair p = pairWeCameFrom[answerFrontier.x][answerFrontier.y];
    		if (p != null) answer.addFirst(p);
    		answerFrontier = p;
    	}
    	
    	// Now backtrack on the other frontier.
    	while (answerOtherFrontier != null) {
    		Pair p = pairWeCameFrom[answerOtherFrontier.x][answerOtherFrontier.y];
    		if (p != null) answer.addLast(p);
    		answerOtherFrontier = p;
    	}
    	
    	// Possibly reverse list to be from start->end.
    	if (currentLinkedStack == linkedStackEnd) {
        	LinkedList<Pair> reverseAnswer = new LinkedList<Pair>();
    		for (Pair p : answer) {
    			reverseAnswer.addFirst(p);
    		}
    		
    		return reverseAnswer;
    	} else {
    		return answer;
    	}
    }
}
