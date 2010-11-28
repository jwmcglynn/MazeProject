import java.util.*;

import maze.*;
public class MazeSolver
{
	ISolver ds;
	
	public enum Type
	{
		BreadthFirst
		, DepthFirst
		, BidirectionalDepth
		, AStarSimple
		, AStar
		, AdaptiveAStar
	}
	
	private Type m_genType;
	
	/**
	 * Constructor called from test code.
	 * 
	 * @param k
	 */
	public MazeSolver(int k) {
		m_genType = Type.AdaptiveAStar;
		ds = new AdaptiveAStarSolver();
	}
	
	/**
	 * Create solver with specific type.
	 * TODO: Remove before submitting.
	 * @param genType
	 */
    public MazeSolver(Type genType)
    {
    	m_genType = genType;
    	ds = null;
    	
    	switch (m_genType) {
	    	case BreadthFirst:
	    		ds = new BreadthFirstSolver();
	    		break;
	    	case DepthFirst:
	    		ds = new DepthFirstSolver();
	    		break;
	    	case BidirectionalDepth:
	    		ds = new BidirectionalDepthSolver();
	    		break;
	    	case AStarSimple:
	    		ds = new AStarSolverSimple();
	    		break;
	    	case AStar:
	    		ds = new AStarSolver();
	    		break;
	    	case AdaptiveAStar:
	    		ds = new AdaptiveAStarSolver();
	    		break;
    	}
    }

    public LinkedList<Pair> solveMaze(Maze maze)
    {
		long start = System.nanoTime();

		LinkedList<Pair> ret = ds.solveMaze(maze);
		long end = System.nanoTime();
		
		System.out.println("Took " + m_genType + " " + (double) (end - start) / 1000000000.0 + " seconds to solve maze.");
		return ret;
    }
    
    public String toString() {
    	return m_genType.toString();
    }
}
