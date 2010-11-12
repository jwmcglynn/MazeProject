import java.util.*;
import maze.*;
public class MazeSolver
{
    public MazeSolver()
    {}

    public LinkedList<Pair> solveMaze(Maze maze)
    {
    	DummySolver ds = new DummySolver();
        return ds.solveMaze(maze);
    }
}
