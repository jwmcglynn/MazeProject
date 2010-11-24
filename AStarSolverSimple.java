import java.util.LinkedList;
import maze.Maze;
import maze.Pair;

public class AStarSolverSimple implements ISolver {
	public AStarSolverSimple() {
	}
	
	public LinkedList<Pair> solveMaze(Maze maze) {
		LinkedList<Pair> answer = new LinkedList<Pair>();
		Pair[][] pairWeCameFrom;
		int[][] didVisit;
		LinkedList<Pair> linkedStackStart = new LinkedList<Pair>();
		LinkedList<Pair> linkedStackEnd = new LinkedList<Pair>();
		
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
    		Pair goal = null;
    		if(currentLinkedStack == linkedStackStart) {
    			currentLinkedStack = linkedStackEnd;
    			currentTag = 2;
    			goal = maze.getStartLocation();
    		} else {
    			currentLinkedStack = linkedStackStart;
    			currentTag = 1;
    			goal = maze.getEndLocation();
    		}
    		
    		
    		// determine the best one to remove.
    		// go through every element in our list and see which one has the best heuristic
    		int bestHeuristic = Integer.MAX_VALUE;
    		Pair current = null;
    		for(Pair p : currentLinkedStack) {
    			int currentHeuristic = calcHeuristic(p, goal);

    			if(currentHeuristic < bestHeuristic) {
    				bestHeuristic = currentHeuristic;
    				current = p;
    			}
    		}
    		
    		if (current == null) continue; // Uh oh.
    		currentLinkedStack.remove(current);
    		
    		goal = current;
    		
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
    		answer.addFirst(answerFrontier);
    		answerFrontier = pairWeCameFrom[answerFrontier.x][answerFrontier.y];
    	}
    	
    	// Now backtrack on the other frontier.
    	while (answerOtherFrontier != null) {
    		answer.addLast(answerOtherFrontier);
    		answerOtherFrontier = pairWeCameFrom[answerOtherFrontier.x][answerOtherFrontier.y];
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
	
	private int calcHeuristic(Pair current, Pair goal) {
		// Manhattan Distance
		return Math.abs(goal.x - current.x) + Math.abs(goal.y - current.y);
	}
}

