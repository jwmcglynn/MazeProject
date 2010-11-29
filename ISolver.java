/**
 * Maze Project
 * CS171
 * 
 * Jeff McGlynn
 * Jesus Quezada
 * 
 * November 29th, 2010
**/

import java.util.LinkedList;
import maze.*;

public interface ISolver {
    public LinkedList<Pair> solveMaze(Maze maze);
}