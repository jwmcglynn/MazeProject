import java.util.*;
import maze.*;
public class MazeSolver
{
    public MazeSolver()
    {}

    public LinkedList<Pair> solveMaze(Maze maze)
    {
		long start = System.nanoTime();
    	ISolver ds = new DummySolver();
    	LinkedList<Pair> ret = ds.solveMaze(maze);
		long end = System.nanoTime();
		
		System.out.println("Took " + (double) (end - start) / 1000000000.0 + " seconds to solve maze.");
		return ret;
    }
}
