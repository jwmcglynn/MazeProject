import java.util.LinkedList;
import maze.*;

public interface ISolver {
    public LinkedList<Pair> solveMaze(Maze maze);
}