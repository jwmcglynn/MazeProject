import java.util.LinkedList;
import maze.Maze;
import maze.Pair;

public class AdaptiveAStarSolver implements ISolver {
	//private float[][] stats = new float[70][70];
	private float[][] stats = new float[500][500];

	private float timesSolved = 0.0f;
	
	public AdaptiveAStarSolver() {
	}
	
	public LinkedList<Pair> solveMaze(Maze maze) {
		LinkedList<Pair> answer = new LinkedList<Pair>();
		Pair[][] pairWeCameFrom = new Pair[maze.getWidth()][maze.getHeight()];;
		int[][] didVisit = new int[maze.getWidth()][maze.getHeight()];;
		LinkedList<Pair> linkedStackStart = new LinkedList<Pair>();
		LinkedList<Pair> linkedStackEnd = new LinkedList<Pair>();
		Pair goal = maze.getEndLocation();
		
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
    		} else {
    			currentLinkedStack = linkedStackStart;
    			currentTag = 1;
    		}
    		
    		
    		// determine the best one to remove.
    		// go through every element in our list and see which one has the best heuristic
    		float bestHeuristic = Float.POSITIVE_INFINITY;
    		Pair current = null;
    		for(Pair p : currentLinkedStack) {
    			float currentHeuristic = calcHeuristic(p, goal);

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
    	
    	timesSolved++;

    	// Possibly reverse list to be from start->end.
    	if (currentLinkedStack == linkedStackEnd) {
        	LinkedList<Pair> reverseAnswer = new LinkedList<Pair>();
    		for (Pair p : answer) {
    			reverseAnswer.addFirst(p);
    		}
    		
    		// Add our answers to our statistics.
    		for(Pair p : reverseAnswer) {
    			stats[p.x][p.y] += 1;
    		}
    		
    		return reverseAnswer;
    	} else {
    		// Add our answers to our statistics.
       		for(Pair p : answer) {
    			stats[p.x][p.y] += 1;
    		}
    		return answer;
    	}
    }
	
	// After we find our solution, update our statistics so we have accurate information for our next run.
	private float calcHeuristic(Pair current, Pair goal) {
		// Manhattan Distance
		float manhattanDistance = getManhattanDistance(current, goal);
	
		// For now keep weight as constant. Tweak values to see which one produces better results on average.
		final float weight = 0.25f; // Lower = favor manhattan distance, higher = favor probability.
		if (timesSolved == 0) return manhattanDistance;
		
		float modifier = 1.0f - (stats[current.x][current.y] / timesSolved); // [0.0, 1.0], lower is better.
		modifier = modifier * weight; // [0.0, weight], lower is better.
		return manhattanDistance + manhattanDistance * modifier;
	}
	
	private float getManhattanDistance(Pair current, Pair goal) {
		return Math.abs(goal.x - current.x) + Math.abs(goal.y - current.y);
	}
}

















