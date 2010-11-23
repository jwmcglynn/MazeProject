import java.util.*;

import maze.*;
public class MazeSolver
{
	public enum Type
	{
		BreadthFirst
		, DepthFirst
		, BidirectionalDepth
	}
	
	private Type m_genType;
	
    public MazeSolver(Type genType)
    {
    	m_genType = genType;
    }

    public LinkedList<Pair> solveMaze(Maze maze)
    {
		long start = System.nanoTime();
    	ISolver ds = null;
    	
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
    	}

		LinkedList<Pair> ret = ds.solveMaze(maze);
		long end = System.nanoTime();
		
		System.out.println("Took " + m_genType + " " + (double) (end - start) / 1000000000.0 + " seconds to solve maze.");
		return ret;
    }
}
